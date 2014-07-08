package org.apache.wolf.gossip.state;

import java.util.Map;

import org.apache.wolf.gossip.state.serialize.EndpointStateSerializer;
import org.apache.wolf.serialize.IVersionedSerializer;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointState {
	
	private static final IVersionedSerializer<EndpointState> serializer = new EndpointStateSerializer();
	protected static Logger logger = LoggerFactory.getLogger(EndpointState.class);
	private volatile HeartBeatState hbState;
	private volatile long updateTimestamp;
	private volatile boolean isAlive;
	private volatile boolean hasToken;
	public Map<ApplicationState,VersionedValue> applicationState=new NonBlockingHashMap<ApplicationState,VersionedValue>();

	//private final static IVersionedSerializer<EndpointState> serializer=new EndpointSerializer();
	
	public EndpointState(HeartBeatState initHbState) {
		this.hbState=initHbState;
		updateTimestamp=System.currentTimeMillis();
		isAlive=true;
		hasToken=false;
	}

	public EndpointState() {
		
	}

	public void markAlive() {
		isAlive=true;
	}

	public HeartBeatState getHeartBeatState() {
		return this.hbState;
	}

	public void addApplicationState(ApplicationState key,
			VersionedValue value) {
		this.applicationState.put(key, value);
	}

	public Map<ApplicationState,VersionedValue> getApplicationStateMap() {
		return applicationState;
	}

	public long getUpdateTimestamp() {
		return updateTimestamp;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isHasToken() {
		return hasToken;
	}

	public void setUpdateTimestamp(long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setHasToken(boolean hasToken) {
		this.hasToken = hasToken;
	}

	public static IVersionedSerializer<EndpointState> getSerializer() {
		return serializer;
	}

}
