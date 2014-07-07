package org.apache.wolf.gossip.state.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import org.apache.wolf.gossip.Gossiper;
import org.apache.wolf.gossip.state.ApplicationState;
import org.apache.wolf.gossip.state.EndpointState;
import org.apache.wolf.gossip.state.HeartBeatState;
import org.apache.wolf.gossip.state.VersionedValue;
import org.apache.wolf.serialize.IVersionedSerializer;

public class EndpointSerializer implements IVersionedSerializer<EndpointState> {

	@Override
	public void serialized(EndpointState t, DataOutput dos, int version)
			throws IOException {
		HeartBeatState hbState=t.getHeartBeatState();
		HeartBeatState.serialized().serialized(hbState, dos, version);
		int size=t.applicationState.size();
		dos.writeInt(size);
		for(Map.Entry<ApplicationState, VersionedValue> entry:t.applicationState.entrySet()){
			VersionedValue value=entry.getValue();
			dos.writeInt(entry.getKey().ordinal());
			VersionedValue.serializer.serialized(value,dos,version);
		}

	}

	@Override
	public EndpointState deserialized(DataInput dis, int version)
			throws IOException {
		HeartBeatState hbstate=HeartBeatState.serialized().deserialized(dis, version);
		EndpointState epState=new EndpointState(hbstate);
		
		int appStateSize=dis.readInt();
		for(int i=0;i<appStateSize;i++){
			int key=dis.readInt();
			VersionedValue value=VersionedValue.serializer.deserialized(dis, version);
			epState.addApplicationState(Gossiper.STATE[key],value);
		}
		return epState;
		
	}

	@Override
	public long serializedSize(EndpointState t, int version) {
		throw new UnsupportedOperationException();
	}

}
