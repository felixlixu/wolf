package org.apache.wolf.produce;

import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.wolf.concurrent.DebuggableScheduledThreadPoolExecutor;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.gossip.GossipService;
import org.apache.wolf.gossip.message.GossipDigest;
import org.apache.wolf.gossip.message.GossipDigestSynMessage;
import org.apache.wolf.gossip.state.ApplicationState;
import org.apache.wolf.gossip.state.EndpointState;
import org.apache.wolf.gossip.state.HeartBeatState;
import org.apache.wolf.gossip.state.VersionedValue;
import org.apache.wolf.io.util.FastByteArrayOutputStream;
import org.apache.wolf.locator.snitch.IEndpointStateChangeSubscriber;
import org.apache.wolf.message.MessageService;
import org.apache.wolf.message.msg.IMessageProducer;
import org.apache.wolf.message.msg.Message;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.util.ConfFBUtilities;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GossiperServiceProducer {
	private Random random = new Random();
	public final static int intervalInMillis = 1000;
    private Comparator<InetAddress> inetcomparator = new Comparator<InetAddress>()
    {
        public int compare(InetAddress addr1,  InetAddress addr2)
        {
            return addr1.getHostAddress().compareTo(addr2.getHostAddress());
        }
    };
	private Set<InetAddress> seeds=new ConcurrentSkipListSet<InetAddress>(inetcomparator);
	private Map<InetAddress,EndpointState> endpointStateMap=new ConcurrentHashMap<InetAddress,EndpointState>();
	private static Logger logger = LoggerFactory.getLogger(GossipService.class);
	private ScheduledFuture<?> scheduledGossipTask;
	private static final DebuggableScheduledThreadPoolExecutor executor=new DebuggableScheduledThreadPoolExecutor("Task");
	private Map<InetAddress, Long> unreachableEndpoints = new ConcurrentHashMap<InetAddress, Long>();
	private final ConcurrentMap<InetAddress, Integer> versions = new NonBlockingHashMap<InetAddress, Integer>();
	
	public static GossiperServiceProducer instance=new GossiperServiceProducer();
	public static final ApplicationState[] STATE = ApplicationState.values();;
	public Set<InetAddress> liveEndpoints =  new ConcurrentSkipListSet<InetAddress>(inetcomparator);
	private List<IEndpointStateChangeSubscriber> subscribers=new CopyOnWriteArrayList<IEndpointStateChangeSubscriber>();
	
	
	public void start(int gernerationNbr){
		Set<InetAddress> seedHosts=DatabaseDescriptor.getSeeds();
		for(InetAddress seed:seedHosts){
			seeds.add(seed);
		}
		maybeInitalizeLocalState(gernerationNbr);
		EndpointState localState=endpointStateMap.get(ConfFBUtilities.getBroadcastAddress());
		DatabaseDescriptor.getEndpointSnitch().gossiperStarting();;
		
        if (logger.isTraceEnabled())
            logger.trace("gossip started with generation " + localState.getHeartBeatState().getGeneration());
        
        setScheduledGossipTask(executor.scheduleWithFixedDelay(new GossipTask(),
                GossiperServiceProducer.intervalInMillis,
                GossiperServiceProducer.intervalInMillis,
                TimeUnit.MILLISECONDS));
	}
	
	
	private void maybeInitalizeLocalState(int gernerationNbr) {
		EndpointState localState=endpointStateMap.get(ConfFBUtilities.getBroadcastAddress());
		if(localState==null){
			HeartBeatState hbState=new HeartBeatState(gernerationNbr);
			localState=new EndpointState(hbState);
			localState.markAlive();
			endpointStateMap.put(ConfFBUtilities.getBroadcastAddress(),localState);
		}
	}
	
	private void makeRandomGossipDigest(List<GossipDigest> gDigests) {
		EndpointState epState;
		int generation=0;
		int maxVersion=0;
		List<InetAddress> endpoints=new ArrayList<InetAddress>(endpointStateMap.keySet());
		Collections.shuffle(endpoints,random);
		for(InetAddress endpoint:endpoints){
			epState=endpointStateMap.get(endpoint);
			if(epState!=null){
				generation=epState.getHeartBeatState().getGeneration();
				maxVersion=getMaxEndpointStateVersion(epState);
			}
			gDigests.add(new GossipDigest(endpoint,generation,maxVersion));
		}
		
        if (logger.isTraceEnabled())
        {
            StringBuilder sb = new StringBuilder();
            for ( GossipDigest gDigest : gDigests )
            {
                sb.append(gDigest);
                sb.append(" ");
            }
                logger.trace("Gossip Digests are : " + sb.toString());
        }
	}
	
	private int getMaxEndpointStateVersion(EndpointState epState) {
		int maxVersion=epState.getHeartBeatState().getHeartBeatVersion();
		for(VersionedValue value:epState.getApplicationStateMap().values()){
			maxVersion=Math.max(maxVersion, value.version);
		}
		return maxVersion;
	}
	
	private Message makeGossipDigestSynMessage(
			List<GossipDigest> gDigests, Integer version) throws IOException {
		GossipDigestSynMessage gDigestMessage=new GossipDigestSynMessage(DatabaseDescriptor.getClusterName(),gDigests);
		FastByteArrayOutputStream bos=new FastByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		GossipDigestSynMessage.serializer.serialized(gDigestMessage,dos,version);
		return new Message(ConfFBUtilities.getBroadcastAddress(),MessageVerb.GOSSIP_DIGEST_SYN,bos.toByteArray(), version);
	}

	public ScheduledFuture<?> getScheduledGossipTask() {
		return scheduledGossipTask;
	}

	private void setScheduledGossipTask(ScheduledFuture<?> scheduledGossipTask) {
		this.scheduledGossipTask = scheduledGossipTask;
	}

	private boolean doGossipToLiveMember(IMessageProducer prod) {
		int size=liveEndpoints.size();
		if(size==0)
			return false;
		return true;
	}


	public void doStatusCheck() {
		// TODO Auto-generated method stub
		
	}


	public void doGossipToSeed(IMessageProducer prod) {
		int size=seeds.size();
		if(size>0){
			if(size==1&&seeds.contains(ConfFBUtilities.getBroadcastAddress())){
				//return;
			}
			if(liveEndpoints.size()==0){
				sendGossip(prod,seeds);
			}else{
                double probability = seeds.size() / (double)( liveEndpoints.size() + unreachableEndpoints.size() );
                double randDbl = random.nextDouble();
                if ( randDbl <= probability )
                    sendGossip(prod, seeds);
			}
		}
	}

	private void doGossipToUnreachableMember(IMessageProducer prod) {
		double liveEndpointCount=liveEndpoints.size();
		double unreachableEndpointCount=unreachableEndpoints.size();
		if(unreachableEndpointCount>0){
			double prob=unreachableEndpointCount / (liveEndpointCount + 1);
			double randDbl=random.nextDouble();
			if(randDbl<prob)
				sendGossip(prod,unreachableEndpoints.keySet());
		}
	}
	
	private boolean sendGossip(IMessageProducer prod, Set<InetAddress> epSet) {
		int size=epSet.size();
		if(size<1)
			return false;
		List<InetAddress> liveEndpoints=new ArrayList<InetAddress>(epSet);
		int index=(size==1)?0:random.nextInt(size);
		InetAddress to=liveEndpoints.get(index);
		if(logger.isTraceEnabled()){
			logger.trace("Sending a GossipDigestSynMessage to {} ...", to);
		}
		try{
			MessageService.instance.sendOneWay(prod.getMessage(getVersion(to)),to);
		}catch(IOException ex){
			throw new IOError(ex);
		}
		return seeds.contains(to);
	}

	private Integer getVersion(InetAddress to) {
		Integer v=versions.get(to);
		if(v==null){
			return MessageService.getVersion();
		}else{
			return v;
		}
	}
	
	public void register(IEndpointStateChangeSubscriber subscriber) {
		subscribers.add(subscriber);
	}

	private class GossipTask implements Runnable {	
		@Override
		public void run() {
			try{
				MessageService.instance.waitUntilListening();
				endpointStateMap.get(ConfFBUtilities.getBroadcastAddress()).getHeartBeatState().updateHearBeatState();
				if(logger.isTraceEnabled()){
					logger.trace("My heartbeat is now " + endpointStateMap.get(ConfFBUtilities.getBroadcastAddress()).getHeartBeatState().getHeartBeatVersion());
				}
				final List<GossipDigest> gDigests=new ArrayList<GossipDigest>();
				GossiperServiceProducer.instance.makeRandomGossipDigest(gDigests);
				if(gDigests.size()>0){
					IMessageProducer prod=new IMessageProducer(){
						@Override
						public Message getMessage(Integer version)
								throws IOException {
							return makeGossipDigestSynMessage(gDigests,version);
						}
					};
					boolean gossipedToSeed=doGossipToLiveMember(prod);
					doGossipToUnreachableMember(prod);
					
					if(!gossipedToSeed||liveEndpoints.size()<seeds.size()){
						doGossipToSeed(prod);
					}
					if(logger.isTraceEnabled()){
						logger.trace("Performing status check......");
					}
					doStatusCheck();
				}
			}catch(Exception e){
				logger.error("Gossip error",e);
			}
			
		}
	}

}
