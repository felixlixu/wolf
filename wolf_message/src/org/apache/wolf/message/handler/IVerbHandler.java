package org.apache.wolf.message.handler;

import org.apache.wolf.message.msg.Message;

public interface IVerbHandler {

	void doVerb(Message message, String id);

}
