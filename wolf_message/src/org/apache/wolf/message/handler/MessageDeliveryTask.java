package org.apache.wolf.message.handler;

import org.apache.wolf.message.msg.Message;
import org.apache.wolf.message.msg.MessageVerb;
import org.apache.wolf.message.producer.MessageServiceProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDeliveryTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageDeliveryTask.class);
	private final Message message;
	private final String id;

	public MessageDeliveryTask(Message message,String id){
		assert message!=null;
		this.message=message;
		this.id=id;
	}
	
	public void run() {
		MessageVerb verb=message.getVerb();
		IVerbHandler verbHandler=MessageServiceProducer.instance.getVerbHandler(verb);
		if(verbHandler==null){
			logger.debug("Unknown verb {} ",verb);
			return;
		}
		
		verbHandler.doVerb(message,id);
	}

}
