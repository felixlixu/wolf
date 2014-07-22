package org.apache.wolf.service;

import java.io.IOException;

import javax.naming.ConfigurationException;

import org.apache.wolf.gossip.message.handler.GossipDigestSynVerbHandler;
import org.apache.wolf.locator.token.TokenMetadata;
import org.apache.wolf.message.MessageService;
import org.apache.wolf.message.handler.HandlerTest;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.util.ConfFBUtilities;



public class StorageService {

	public static final StorageService instance = new StorageService();
	private static final int RING_DELAY = 10;
	private boolean initialized;
	private boolean isClientMode;
	private TokenMetadata tokenMetadata_=new TokenMetadata();

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public synchronized void initServer() {
		initServer(RING_DELAY);
	}
	
	public StorageService(){
		MessageService.instance.registerVerbHandler(MessageVerb.MUTATION, new GossipDigestSynVerbHandler());
		MessageService.instance.registerVerbHandler(MessageVerb.GOSSIP_DIGEST_SYN, new HandlerTest());
	}

	public void initServer(int delay) {
		if(initialized){
			if(isClientMode){
				throw new UnsupportedOperationException("StorageService does not supported switching modes.");
			}
			return;
		}
		initialized=true;
		isClientMode=false;
		
		try {
			joinTokenRing(delay);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void joinTokenRing(int delay) throws ConfigurationException, IOException {
		MessageService.instance.listen(ConfFBUtilities.getLocalAddress());
	}

	public TokenMetadata getTokenMetadata() {
		return tokenMetadata_;
	}

}
