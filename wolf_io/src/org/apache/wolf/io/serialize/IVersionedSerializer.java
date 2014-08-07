package org.apache.wolf.io.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface IVersionedSerializer<T> {

	public void serialized(T t,DataOutput dos,int version) throws IOException;
	
	public T deserialized(DataInput dis,int version) throws IOException;
	
	public long serializedSize(T t,int version);
}
