package org.apache.db;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.SortedMap;

import org.apache.wolf.data.basetype.AbstractType;
import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.utils.HeapAllocator;

public class AtomicSortedColumns implements ISortedColumns {

	public AtomicSortedColumns(AbstractType<?> comparator) {
		
	}

	public AtomicSortedColumns(SortedMap<ByteBuffer, IColumn> sm) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addColumn(IColumn column, HeapAllocator allocator) {
		// TODO Auto-generated method stub

	}

	public static final ISortedColumns.Factory factory=new Factory() {

		@Override
		public ISortedColumns create(AbstractType<?> comparator,
				boolean insertRversed) {
			return new AtomicSortedColumns(comparator);
		}

		@Override
		public ISortedColumns fromSorted(SortedMap<ByteBuffer, IColumn> sm,
				boolean insertReversed) {
			return new AtomicSortedColumns(sm);
		}
	};

	@Override
	public Iterator<IColumn> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
