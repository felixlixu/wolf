package org.apache.wolf.dht.ring;

import org.apache.wolf.dht.partition.IPartitioner;
import org.apache.wolf.dht.token.Token;

public interface IRingPosition<T> extends Comparable<T> {

	public Token<?> getToken();
	
	public boolean isMinimum(IPartitioner partitioner);
}
