package org.apache.wolf.token;

import java.nio.ByteBuffer;

import javax.naming.ConfigurationException;

public abstract class TokenFactory<T> {
	public abstract ByteBuffer toByteArray(Token<T> token);
	public abstract Token<T> fromByteArray(ByteBuffer bytes);
	public abstract Token<T> fromString(String string);
	public abstract void validate(String token) throws ConfigurationException;
}
