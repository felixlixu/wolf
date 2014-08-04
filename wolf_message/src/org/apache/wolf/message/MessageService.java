package org.apache.wolf.message;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ThreadPoolExecutor;

import javax.naming.ConfigurationException;

import org.apache.wolf.concurrent.Stage;
import org.apache.wolf.concurrent.StageManager;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.message.handler.IMessageCall;
import org.apache.wolf.message.handler.IVerbHandler;
import org.apache.wolf.message.msg.CallbackInfo;
import org.apache.wolf.message.msg.Message;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.message.producer.MessageServiceProducer;
import org.apache.wolf.utils.ExpiringMap;
import org.apache.wolf.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

public class MessageService {

	private static int version=MessageServiceProducer.version_;
	private Logger logger_=LoggerFactory.getLogger(MessageService.class);
	private final ExpiringMap<String,CallbackInfo> callbacks;
	private static final long DEFAULT_CALLBACK_TIMEOUT = DatabaseDescriptor.getRpcTimeout();
	
	public static MessageService instance=new MessageService();
	public MessageService(){
		Function<Pair<String,CallbackInfo>,?> timeoutReporter=new Function<Pair<String,CallbackInfo>,Object>(){
			public Object apply(Pair<String,CallbackInfo> pair){
				return null;
			}
		};
		callbacks=new ExpiringMap<String,CallbackInfo>(DEFAULT_CALLBACK_TIMEOUT,timeoutReporter);
	}
	
	public void registerVerbHandler(MessageVerb verb,IVerbHandler handler){
		MessageServiceProducer.instance.registerVerbHandler(verb, handler);
	}
	
	public String sendRR(Message message,InetAddress to,IMessageCall cb,long timeout){
		return MessageServiceProducer.instance.sendRR(message, to, cb, timeout);
	}

	public void listen(InetAddress localAddress) throws ConfigurationException, IOException {
		MessageServiceProducer.instance.listen(localAddress);
	}

	public void waitUntilListening() {
		MessageServiceProducer.instance.waitUntilListening();
	}

	public static int getVersion() {
		return version;
	}

	public void sendOneWay(Message message, InetAddress to) {
		MessageServiceProducer.instance.sendOneWay(message,to);
	}

	public void waitForCallbacks() {
		logger_.info("Waiting for messaging service to quiesce");
		assert !StageManager.getStage(Stage.MUTATION).isShutdown();
		callbacks.shutdown();
	}	

}
