package org.apache.wolf.gossip.state.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import org.apache.wolf.gossip.state.ApplicationState;
import org.apache.wolf.gossip.state.EndpointState;
import org.apache.wolf.gossip.state.HeartBeatState;
import org.apache.wolf.gossip.state.VersionedValue;
import org.apache.wolf.produce.GossiperServiceProducer;
import org.apache.wolf.serialize.IVersionedSerializer;

public class EndpointStateSerializer implements
		IVersionedSerializer<EndpointState> {

	@Override
	public void serialized(EndpointState t, DataOutput dos, int version)
			throws IOException {
		HeartBeatState hbstate=t.getHeartBeatState();
		HeartBeatState.getSerializer().serialized(hbstate, dos, version);
		if (t.getApplicationStateMap()==null){
			dos.writeInt(0);
			return;
		}
		int size=t.getApplicationStateMap().size();
		dos.writeInt(size);
		for(Map.Entry<ApplicationState, VersionedValue> entry:t.getApplicationStateMap().entrySet()){
			VersionedValue value=entry.getValue();
			dos.writeInt(entry.getKey().ordinal());
			VersionedValue.getSerializer().serialized(value, dos, version);
		}
	}

	@Override
	public EndpointState deserialized(DataInput dis, int version)
			throws IOException {
		HeartBeatState hbState=HeartBeatState.getSerializer().deserialized(dis, version);
		EndpointState epState=new EndpointState(hbState);
		
		int appStateSize=dis.readInt();
		for(int i=0;i<appStateSize;i++){
			int key=dis.readInt();
			VersionedValue value=VersionedValue.getSerializer().deserialized(dis, version);
			epState.addApplicationState(GossiperServiceProducer.STATE[key], value);
		}
		return epState;
	}

	@Override
	public long serializedSize(EndpointState t, int version) {
		throw new UnsupportedOperationException();
	}

}
