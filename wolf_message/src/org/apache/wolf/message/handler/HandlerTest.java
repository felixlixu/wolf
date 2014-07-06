package org.apache.wolf.message.handler;

import org.apache.wolf.message.Message;

public class HandlerTest implements IVerbHandler {

	public void doVerb(Message message, String id) {
		System.out.println("Handler test successful");
	}

}
