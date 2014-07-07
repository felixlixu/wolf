package org.apache.wolf.gossip.state;

import java.util.Map;

import org.apache.wolf.gossip.state.serialize.EndpointSerializer;
import org.apache.wolf.serialize.IVersionedSerializer;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointState {
	
	protected static Logger logger = LoggerFactory.getLogger(EndpointState.class);
	private volatile HeartBeatState hbState;
	private volatile long updateTimestamp;
	private volatile boolean isAlive;
	private volatile boolean hasToken;
	public Map<ApplicationState,VersionedValue> applicationState=new NonBlockingHashMap<ApplicationState,VersionedValue>();

	private final static IVersionedSerializer<EndpointState> serializer=new EndpointSerializer();
	
	public EndpointState(HeartBeatState initHbState) {
		this.hbState=initHbState;
		updateTimestamp=System.currentTimeMillis();
		isAlive=true;
		hasToken=false;
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

}
