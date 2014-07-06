package org.apache.wolf.message.handler;

import org.apache.wolf.message.Message;

public interface IVerbHandler {

	void doVerb(Message message, String id);

}
