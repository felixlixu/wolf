package org.apache.wolf.ring;

import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.token.Token;

public interface IRingPosition<T> {

	public Token<?> getToken();
	
	public boolean isMinimum(IPartitioner<?> partitioner);
}
