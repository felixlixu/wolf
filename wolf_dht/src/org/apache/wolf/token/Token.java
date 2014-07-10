package org.apache.wolf.token;

import java.io.Serializable;

import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.ring.IRingPosition;

public abstract class Token<T> implements IRingPosition<Token<T>>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final T token;
	
	protected Token(T t) {
		this.token=t;
	}

	@Override
	public Token<T> getToken() {
		return this;
	}

	@Override
	public boolean isMinimum(IPartitioner<?> partitioner) {
		return this.equals(partitioner.getMinimumToken());
	}

	 /**
     * This determines the comparison for node destination purposes.
     */
    abstract public int compareTo(Token<T> o);
}
