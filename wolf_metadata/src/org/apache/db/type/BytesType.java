package org.apache.db.type;

import java.nio.ByteBuffer;
import java.util.NavigableMap;


public class BytesType extends AbstractType<ByteBuffer> {

	public static final BytesType instance=new BytesType();
	
	BytesType(){}

	@Override
	public int compare(ByteBuffer arg0, ByteBuffer arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
