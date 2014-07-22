package org.apache.wolf.gossip.message.handler;

import org.apache.wolf.message.handler.IVerbHandler;
import org.apache.wolf.message.msg.Message;

public class GossipDigestSynVerbHandler implements IVerbHandler {

	@Override
	public void doVerb(Message message, String id) {
		System.out.println("Handler test successful");
	}

}
