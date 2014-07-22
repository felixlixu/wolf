package org.apache.wolf.dht.partition;

import java.nio.ByteBuffer;

import org.apache.wolf.dht.token.DecoratedKey;
import org.apache.wolf.dht.token.Token;
import org.apache.wolf.dht.token.TokenFactory;

public interface IPartitioner<T extends Token<?>> {

	T getMinimumToken();
	
	T getToken(ByteBuffer key);
	
	@SuppressWarnings("rawtypes")
	T midpoint(Token left,Token right);
	
	@SuppressWarnings("rawtypes")
	TokenFactory getTokenFactory();

	DecoratedKey decoratekey(ByteBuffer key);

}
