package org.apache.wolf.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
        
        /*scheduledGossipTask = executor.scheduleWithFixedDelay(new GossipTask(),
                Gossiper.intervalInMillis,
                Gossiper.intervalInMillis,
                TimeUnit.MILLISECONDS);*/
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
}
