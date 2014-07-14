package org.apache.wolf;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.ring.IRingPosition;
import org.apache.wolf.token.Token;

public class DhtService {

	private static IPartitioner partitioner;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int firstTokenIndex(final ArrayList ring,Token start,boolean insertMin){
		assert ring.size()>0;
		
		int i=Collections.binarySearch(ring, start);
		if(i<0){
			i=(i+1)*(-1);
			if(i>=ring.size()){
				i=insertMin?-1:0;
			}
		}
		return i;
	}
	
	@SuppressWarnings("rawtypes")
	public static Token firstToken(final ArrayList<Token> ring,Token start){
		if(ring.size()==0){
			return start;
		}
		return ring.get(firstTokenIndex(ring,start,false));
	}

	public static IPartitioner getPartitioner() {
		return partitioner;
	}

	public static void setPartitioner(IPartitioner par) {
		partitioner = par;
	}
}
