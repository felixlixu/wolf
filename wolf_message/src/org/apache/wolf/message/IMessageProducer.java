package org.apache.wolf.message;

import java.io.IOException;

public interface IMessageProducer {

	public Message getMessage(Integer version) throws IOException;
}
