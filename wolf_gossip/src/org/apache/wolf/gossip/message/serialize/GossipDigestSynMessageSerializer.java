package org.apache.wolf.gossip.message.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wolf.gossip.message.GossipDigest;
import org.apache.wolf.gossip.message.GossipDigestSynMessage;
import org.apache.wolf.serialize.IVersionedSerializer;

public class GossipDigestSynMessageSerializer implements
		IVersionedSerializer<GossipDigestSynMessage> {

	@Override
	public void serialized(GossipDigestSynMessage t, DataOutput dos, int version)
			throws IOException {
		dos.writeUTF(t.getClusterId());
		if(t.getgDigests()==null){
			dos.writeInt(0);
		}
		int size=t.getgDigests().size();
		dos.writeInt(size);
		for(GossipDigest gDigest:t.getgDigests()){
			GossipDigest.getSerializer().serialized(gDigest, dos, version);
		}
	}

	@Override
	public GossipDigestSynMessage deserialized(DataInput dis, int version)
			throws IOException {
		String clusterId=dis.readUTF();
		int digestsize=dis.readInt();
		List<GossipDigest> gDigests=new ArrayList<GossipDigest>();
		for(int i=0;i<digestsize;i++){
			gDigests.add(GossipDigest.getSerializer().deserialized(dis, version));
		}
		return new GossipDigestSynMessage(clusterId,gDigests);
	}

	@Override
	public long serializedSize(GossipDigestSynMessage t, int version) {
		throw new UnsupportedOperationException();
	}

}
