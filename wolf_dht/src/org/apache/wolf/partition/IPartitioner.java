package org.apache.wolf.partition;

import java.nio.ByteBuffer;

import org.apache.wolf.token.Token;

public interface IPartitioner<T extends Token<?>> {

	T getMinimumToken();
	
	T getToken(ByteBuffer key);
	
	T midpoint(Token left,Token right);

}
