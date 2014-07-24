package org.apache.wolf.db.type;

import java.nio.ByteBuffer;

public class UTF8Type extends AbstractType<ByteBuffer> {

	public static final UTF8Type instance=new UTF8Type();

	@Override
	public int compare(ByteBuffer arg0, ByteBuffer arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
}
