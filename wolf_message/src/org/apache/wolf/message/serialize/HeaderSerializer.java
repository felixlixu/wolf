package org.apache.wolf.message.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.Map;

import org.apache.wolf.message.Header;
import org.apache.wolf.message.producer.MessageServiceProducer;
import org.apache.wolf.serialize.IVersionedSerializer;

public class HeaderSerializer implements IVersionedSerializer<Header> {

	public void serialized(Header t, DataOutput dos, int version)
			throws IOException {
		CompactEndpointSerializationHelper.serialize(t.getFrom(),dos);
		dos.writeInt(t.getVerb().ordinal());
		if(t.getDetails()==null){
			dos.writeInt(0);
			return;
		}
		dos.writeInt(t.getDetails().size());
		for(String key:t.getDetails().keySet()){
			dos.writeUTF(key);
			byte[] value=t.getDetails().get(key);
			dos.writeInt(value.length);
			dos.write(value);
		}
	}

	public Header deserialized(DataInput dis, int version) throws IOException {
		InetAddress from=CompactEndpointSerializationHelper.deserialize(dis);
		int verbOrdinal=dis.readInt();
		int size=dis.readInt();
		Map<String,byte[]> detail=new Hashtable<String,byte[]>(size);
		for(int i=0;i<size;i++){
			String key=dis.readUTF();
			int length=dis.readInt();
			byte[] value=new byte[length];
			dis.readFully(value);
			detail.put(key, value);
		}
		return new Header(from,MessageServiceProducer.VERBS[verbOrdinal],detail);
	}

	public long serializedSize(Header t, int version) {
		throw  new UnsupportedOperationException();
	}

}
