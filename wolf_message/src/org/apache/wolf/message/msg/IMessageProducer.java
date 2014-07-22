package org.apache.wolf.message.msg;

import java.io.IOException;

public interface IMessageProducer {

	public Message getMessage(Integer version) throws IOException;
}
