package org.apache.wolf.service;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import javax.naming.ConfigurationException;

import org.apache.wolf.concurrent.Stage;
import org.apache.wolf.concurrent.StageManager;
import org.apache.wolf.gossip.GossipService;
import org.apache.wolf.gossip.message.handler.GossipDigestSynVerbHandler;
import org.apache.wolf.locator.snitch.IEndpointStateChangeSubscriber;
import org.apache.wolf.locator.token.TokenMetadata;
import org.apache.wolf.message.MessageService;
import org.apache.wolf.message.handler.HandlerTest;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.util.ConfFBUtilities;
import org.apache.wolf.utils.WrappedRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class StorageService implements IEndpointStateChangeSubscriber {

	private static Logger logger=LoggerFactory.getLogger(StorageService.class);
	public static final StorageService instance = new StorageService();
	private boolean initialized;
	private boolean isClientMode;
	private TokenMetadata tokenMetadata_=new TokenMetadata();
	public static final int RING_DELAY=getRingDelay();
	

	public boolean isInitialized() {
		return initialized;
	}

	private static int getRingDelay() {
		return 30*1000;
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
		Thread drainOnShutdown=new Thread(new WrappedRunnable(){
			@Override
			protected void runMayThrow() throws Exception {
				ThreadPoolExecutor mutationStage=StageManager.getStage(Stage.MUTATION);
			}
		},"StorageServiceShutdownHook");
		/*try {
			joinTokenRing(delay);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	private void joinTokenRing(int delay) throws ConfigurationException, IOException {
		MessageService.instance.listen(ConfFBUtilities.getLocalAddress());
	}

	public TokenMetadata getTokenMetadata() {
		return tokenMetadata_;
	}

	public void initClient() throws ConfigurationException, IOException {
		initClient(RING_DELAY);
	}

	private void initClient(int delay) throws ConfigurationException, IOException {
		if(initialized){
			
		}
		initialized=true;
		logger.info("Starting up client wolf");
		GossipService.instance.register(this);
		GossipService.instance.start((int)(System.currentTimeMillis()/1000));
		MessageService.instance.listen(ConfFBUtilities.getLocalAddress());
		
		try{
			Thread.sleep(delay);
		}
		catch(Exception e){
			throw new IOException(e);
		}
	}

}
