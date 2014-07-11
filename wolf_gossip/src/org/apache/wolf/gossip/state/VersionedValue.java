package org.apache.wolf.gossip.state;

import org.apache.wolf.gossip.state.serialize.VersionedSerializer;
import org.apache.wolf.serialize.IVersionedSerializer;

public class VersionedValue implements Comparable<VersionedValue> {

	private static IVersionedSerializer<VersionedValue> serializer=new VersionedSerializer();
    public final static char DELIMITER = ',';
    public final static String DELIMITER_STR = new String(new char[] { DELIMITER });

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

	public static IVersionedSerializer<VersionedValue> getSerializer() {
		return serializer;
	}

}
