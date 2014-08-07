package org.apache.wolf.data.basetype;

import java.nio.ByteBuffer;



public class BytesType extends AbstractType<ByteBuffer> {

	public static final BytesType instance=new BytesType();
	
	BytesType(){}

	@Override
	public int compare(ByteBuffer arg0, ByteBuffer arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
