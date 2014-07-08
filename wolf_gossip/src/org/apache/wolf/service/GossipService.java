package org.apache.wolf.service;

import org.apache.wolf.gossip.GossiperServiceProducer;

public class GossipService {

	public void start(int gernerationNbr){
		GossiperServiceProducer.instance.start(gernerationNbr);
	}
}
