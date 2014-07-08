package org.apache.wolf.gossip.message.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.InetAddress;

import org.apache.wolf.gossip.message.GossipDigest;
import org.apache.wolf.message.serialize.CompactEndpointSerializationHelper;
import org.apache.wolf.serialize.IVersionedSerializer;

public class GossipDigestSerializer implements
		IVersionedSerializer<GossipDigest> {

	@Override
	public void serialized(GossipDigest t, DataOutput dos, int version)
			throws IOException {
		CompactEndpointSerializationHelper.serialize(t.getAddress(), dos);
		dos.writeInt(t.getGeneration());
		dos.writeInt(t.getMaxVersion());
	}

	@Override
	public GossipDigest deserialized(DataInput dis, int version)
			throws IOException {
		InetAddress endpoint=CompactEndpointSerializationHelper.deserialize(dis);
		int generation =dis.readInt();
		int maxVersion=dis.readInt();
		return new GossipDigest(endpoint,generation,maxVersion);
	}

	@Override
	public long serializedSize(GossipDigest t, int version) {
		throw new UnsupportedOperationException();
	}

}
