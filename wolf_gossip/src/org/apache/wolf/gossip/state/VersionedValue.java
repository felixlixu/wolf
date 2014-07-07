package org.apache.wolf.gossip.state;

import org.apache.wolf.gossip.state.serialize.VersionedSerializer;
import org.apache.wolf.serialize.IVersionedSerializer;

public class VersionedValue implements Comparable<VersionedValue> {

	public static IVersionedSerializer<VersionedValue> serializer;
	
	static{
		serializer=new VersionedSerializer();
	}

	public VersionedValue(String value, int version) {
		this.value=value;
		this.version=version;
	}

	public String getValue() {
		return value;
	}

	public int getVersion() {
		return version;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String value;
	public int version;

	@Override
	public int compareTo(VersionedValue arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
