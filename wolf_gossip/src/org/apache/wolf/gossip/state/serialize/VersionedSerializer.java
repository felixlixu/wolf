package org.apache.wolf.gossip.state.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.wolf.gossip.state.VersionedValue;
import org.apache.wolf.serialize.IVersionedSerializer;

public class VersionedSerializer implements
		IVersionedSerializer<VersionedValue> {

	@Override
	public void serialized(VersionedValue t, DataOutput dos, int version)
			throws IOException {
		dos.writeUTF(t.getValue());
		dos.writeInt(t.getVersion());
	}

	@Override
	public VersionedValue deserialized(DataInput dis, int version)
			throws IOException {
		String value=dis.readUTF();
		int valVersion=dis.readInt();
		return new VersionedValue(value,valVersion);
	}

	@Override
	public long serializedSize(VersionedValue t, int version) {
		throw new UnsupportedOperationException();
	}

}
