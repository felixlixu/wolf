package org.apache.wolf.dht.ring;

import java.io.Serializable;
import java.util.Set;

import org.apache.wolf.dht.partition.IPartitioner;
import org.apache.wolf.dht.token.Token;

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

	public boolean intersects(Range<Token> that) {
		return intersectionWith(that).size>0;
	}
	
	public Set<Range<T>> intersectionWith(Range<T> that){
		if(that.contains(this)){
			return rangeSet(this);
		}
	}

}
