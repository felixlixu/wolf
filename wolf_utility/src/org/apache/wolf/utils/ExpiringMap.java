package org.apache.wolf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

public class ExpiringMap<K,V> {

	private static final Logger logger=LoggerFactory.getLogger(ExpiringMap.class);
	private static class CacheableObject<T>{
		private final T value;
		private final long createdAt;
		private final long expiration;
		CacheableObject(T o,long e){
			assert o!=null;
			value=o;
			expiration=e;
			createdAt=System.currentTimeMillis();
		}
		T getValue(){
			return value;
		}
		
		boolean isReadyToDieAt(long time){
			return ((time-createdAt)>expiration);
		}
	}
	
	public ExpiringMap(long defaultExpiration,final Function<Pair<K,V>,?> postExpireHook){
		
	}

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
