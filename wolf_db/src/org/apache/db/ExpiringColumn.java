package org.apache.db;

import java.nio.ByteBuffer;

public class ExpiringColumn extends Column {

	private final int timeToLive;
	private final int localExpirationTime;

	public ExpiringColumn(ByteBuffer name, ByteBuffer value, long timestamp,int timeToLive) {
		this(name,value,timestamp,timeToLive,(int)(System.currentTimeMillis()/1000)+timeToLive);
	}

	public ExpiringColumn(ByteBuffer name, ByteBuffer value, long timestamp,
			int timeToLive, int localExpirationTime) {
		super(name,value,timestamp);
		assert timeToLive>0:timeToLive;
		assert localExpirationTime>0:localExpirationTime;
		this.timeToLive=timeToLive;
		this.localExpirationTime=localExpirationTime;
	}

}
