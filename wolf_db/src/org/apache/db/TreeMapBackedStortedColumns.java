package org.apache.db;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.wolf.data.basetype.AbstractType;
import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.utils.HeapAllocator;


public class TreeMapBackedStortedColumns extends AbstractThreadUnSafeSrotedColumns implements ISortedColumns{

	public static final ISortedColumns.Factory factory=new Factory(){

		@Override
		public ISortedColumns create(AbstractType<?> comparator,
				boolean insertRversed) {
			return new TreeMapBackedStortedColumns(comparator);
		}

		@Override
		public ISortedColumns fromSorted(SortedMap<ByteBuffer, IColumn> sortedMap,
				boolean insertReversed) {
			return new TreeMapBackedStortedColumns(sortedMap);
		}
		
	};
	private final Map<ByteBuffer, IColumn> map;
	
	public TreeMapBackedStortedColumns(AbstractType<?> comparator) {
		this.map=new TreeMap<ByteBuffer,IColumn>(comparator);
	}

	public TreeMapBackedStortedColumns(SortedMap<ByteBuffer, IColumn> columns) {
		this.map=new TreeMap<ByteBuffer,IColumn>(columns);
	}

	public static ISortedColumns.Factory factory() {
		return factory;
	}

	@Override
	public void addColumn(IColumn column, HeapAllocator allocator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<IColumn> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
