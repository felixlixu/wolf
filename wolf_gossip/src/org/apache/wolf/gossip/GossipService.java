package org.apache.wolf.gossip;

import org.apache.wolf.locator.snitch.IEndpointStateChangeSubscriber;
import org.apache.wolf.produce.GossiperServiceProducer;


public class GossipService {

	public static GossipService instance=new GossipService();
	
	public void start(int l){
		GossiperServiceProducer.instance.start(l);
	}
	
	public void maybeInitializeLocalState(){
		
	}

	public void register(IEndpointStateChangeSubscriber subscriber) {
		GossiperServiceProducer.instance.register(subscriber);
	}
}
