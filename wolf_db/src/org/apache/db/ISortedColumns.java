package org.apache.db;

import java.nio.ByteBuffer;
import java.util.SortedMap;

import org.apache.wolf.data.basetype.AbstractType;
import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.utils.HeapAllocator;


public interface ISortedColumns extends IIterableColumns {

	public interface Factory {
		public ISortedColumns create(AbstractType<?> comparator,boolean insertRversed);
		public ISortedColumns fromSorted(SortedMap<ByteBuffer,IColumn> sm,boolean insertReversed);
	}

	void addColumn(IColumn column, HeapAllocator allocator);

}
