package org.apache.wolf.ring;

import java.io.Serializable;

import org.apache.wolf.partition.IPartitioner;

public class Range<T extends IRingPosition<?>> extends AbstractBounds<T> implements Comparable<Range<T>>,Serializable {

	public Range(T left, T right, IPartitioner<?> partitioner) {
		super(left, right, partitioner);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1547396150113202050L;

	@Override
	public int compareTo(Range<T> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
