package org.apache.db;

import java.nio.ByteBuffer;

import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.utils.Allocator;
import org.apache.wolf.utils.HeapAllocator;

public class Column implements IColumn {

	private final ByteBuffer name;
	private final ByteBuffer value;
	private final long timestamp;

	public Column(ByteBuffer name, ByteBuffer value, long timestamp) {
		assert name!=null;
		assert value!=null;
		assert name.remaining()<=IColumn.MAX_NAME_LENGTH;
		this.name=name;
		this.value=value;
		this.timestamp=timestamp;
	}

	@Override
	public void addColumn(IColumn column) {
		addColumn(null,null);
	}

	private void addColumn(IColumn column, Allocator allocator) {
		throw new UnsupportedOperationException("This operation is not supported for simple columns.");
	}

	@Override
	public void addColumn(IColumn column, HeapAllocator allocator) {
		// TODO Auto-generated method stub
		
	}

}
