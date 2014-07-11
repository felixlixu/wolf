package org.apache.wolf.partition;

import java.nio.ByteBuffer;

import org.apache.wolf.token.Token;
import org.apache.wolf.token.TokenFactory;

public interface IPartitioner<T extends Token<?>> {

	T getMinimumToken();
	
	T getToken(ByteBuffer key);
	
	@SuppressWarnings("rawtypes")
	T midpoint(Token left,Token right);
	
	@SuppressWarnings("rawtypes")
	TokenFactory getTokenFactory();

}
