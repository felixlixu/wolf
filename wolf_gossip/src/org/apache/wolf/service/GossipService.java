package org.apache.wolf.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.wolf.concurrent.DebuggableScheduledThreadPoolExecutor;
import org.apache.wolf.concurrent.DebuggableThreadPoolExecutor;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.gossip.Gossiper;
import org.apache.wolf.gossip.state.EndpointState;
import org.apache.wolf.gossip.state.HeartBeatState;
import org.apache.wolf.utils.FBUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GossipService {

	private Set<InetAddress> seeds;
	Map<InetAddress,EndpointState> endpointStateMap=new ConcurrentHashMap<InetAddress,EndpointState>();
	protected static Logger logger = LoggerFactory.getLogger(GossipService.class);
	private ScheduledFuture<?> scheduledGossipTask;
	private static final DebuggableScheduledThreadPoolExecutor executor=new DebuggableScheduledThreadPoolExecutor("Task");
	public void start(int gernerationNbr){
		Set<InetAddress> seedHosts=DatabaseDescriptor.getSeeds();
		for(InetAddress seed:seedHosts){
			seeds.add(seed);
		}
		maybeInitalizeLocalState(gernerationNbr);
		EndpointState localState=endpointStateMap.get(FBUtilities.getBroadcastAddress());
		DatabaseDescriptor.getEndpointSnitch().gossiperStarting();;
		
        if (logger.isTraceEnabled())
            logger.trace("gossip started with generation " + localState.getHeartBeatState().getGeneration());
        
        scheduledGossipTask = executor.scheduleWithFixedDelay(new GossipTask(),
                Gossiper.intervalInMillis,
                Gossiper.intervalInMillis,
                TimeUnit.MILLISECONDS);
	}

	public void maybeInitalizeLocalState(int gernerationNbr) {
		EndpointState localState=endpointStateMap.get(FBUtilities.getBroadcastAddress());
		if(localState==null){
			HeartBeatState hbState=new HeartBeatState(gernerationNbr);
			localState=new EndpointState(hbState);
			localState.markAlive();
			endpointStateMap.put(FBUtilities.getBroadcastAddress(),localState);
		}
	}
	
	public class GossipTask implements Runnable {	
		@Override
		public void run() {
			try{
				MessageService.instance.waitUntilListening();
				endpointStateMap.get(FBUtilities.getBroadcastAddress()).getHeartBeatState().updateHearBeatState();
				if(logger.isTraceEnabled()){
					logger.trace("My heartbeat is now " + endpointStateMap.get(FBUtilities.getBroadcastAddress()).getHeartBeatState().getHeartBeatVersion());
				}
				final List<GossipDigest> gDigests=new ArrayList<GossipDigest>();
				Gossiper.instance.makeRandomGossipDigest(gDigest);
				if(gDigests.size>0){
					
				}
			}catch(Exception e){
				logger.error("Gossip error",e);
			}
			
		}

	}
}
