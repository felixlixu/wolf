package org.apache.wolf.gossip.state.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.wolf.gossip.state.HeartBeatState;
import org.apache.wolf.io.serialize.IVersionedSerializer;

public class HeartBeatStateSerializer implements
		IVersionedSerializer<HeartBeatState> {

	@Override
	public void serialized(HeartBeatState t, DataOutput dos, int version)
			throws IOException {
		dos.writeInt(t.getGeneration());
		dos.writeInt(t.getVersion());
	}

	@Override
	public HeartBeatState deserialized(DataInput dis, int version)
			throws IOException {
		return new HeartBeatState(dis.readInt(),dis.readInt());
	}

	@Override
	public long serializedSize(HeartBeatState t, int version) {
		throw new UnsupportedOperationException();
	}

}
