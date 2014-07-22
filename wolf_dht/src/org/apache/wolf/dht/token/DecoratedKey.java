package org.apache.wolf.dht.token;

import java.nio.ByteBuffer;

import org.apache.wolf.dht.partition.IPartitioner;

public class DecoratedKey<T extends Token> extends RowPosition {

	private final T token;
	public final ByteBuffer key;

	public DecoratedKey(T token, ByteBuffer key) {
		assert token!=null && key!=null&&key.remaining()>0;
		this.token=token;
		this.key=key;
	}

	@Override
	public Token<?> getToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMinimum(IPartitioner partitioner) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(RowPosition arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
