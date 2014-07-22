package org.apache.wolf.dht.ring;

import java.io.Serializable;

import org.apache.wolf.dht.partition.IPartitioner;

public class AbstractBounds<T extends IRingPosition<?>> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private enum Type{
		RANGE,
		BOUNDS
	}
	
	public final T left;
	public final T right;
	private final IPartitioner<?> partitioner;
	
	public AbstractBounds(T left,T right,IPartitioner<?> partitioner){
		this.left=left;
		this.right=right;
		this.partitioner=partitioner;
	}

	public IPartitioner<?> getPartitioner() {
		return partitioner;
	}
	
}
