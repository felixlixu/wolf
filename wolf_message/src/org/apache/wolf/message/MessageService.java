package org.apache.wolf.message;

import java.io.IOException;
import java.net.InetAddress;

import javax.naming.ConfigurationException;

import org.apache.wolf.message.handler.IMessageCall;
import org.apache.wolf.message.handler.IVerbHandler;
import org.apache.wolf.message.msg.Message;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.message.producer.MessageServiceProducer;

public class MessageService {

	private static int version=MessageServiceProducer.version_;
	
	public static MessageService instance=new MessageService();
	public MessageService(){
		
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

}
