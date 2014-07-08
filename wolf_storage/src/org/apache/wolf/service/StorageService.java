package org.apache.wolf.service;

import java.io.IOException;

import javax.naming.ConfigurationException;

import org.apache.wolf.gossip.message.handler.GossipDigestSynVerbHandler;
import org.apache.wolf.message.MessageVerb;
import org.apache.wolf.message.handler.HandlerTest;
import org.apache.wolf.utils.FBUtilities;



public class StorageService {

	public static final StorageService instance = new StorageService();
	private static final int RING_DELAY = 10;

	public synchronized void initServer() {
		initServer(RING_DELAY);
	}
	
	public StorageService(){
		MessageService.instance.registerVerbHandler(MessageVerb.MUTATION, new GossipDigestSynVerbHandler());
		MessageService.instance.registerVerbHandler(MessageVerb.GOSSIP_DIGEST_SYN, new HandlerTest());
	}

	public void initServer(int delay) {
			try {
				joinTokenRing(delay);
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void joinTokenRing(int delay) throws ConfigurationException, IOException {
		MessageService.instance.listen(FBUtilities.getLocalAddress());
	}

}
