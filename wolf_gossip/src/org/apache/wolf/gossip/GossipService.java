package org.apache.wolf.gossip;

import org.apache.wolf.produce.GossiperServiceProducer;


public class GossipService {

	public static GossipService instance=new GossipService();
	
	public void start(int gernerationNbr){
		GossiperServiceProducer.instance.start(gernerationNbr);
	}
	
	public void maybeInitializeLocalState(){
		
	}
}
