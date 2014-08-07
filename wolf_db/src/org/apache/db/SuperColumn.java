package org.apache.db;

import java.nio.ByteBuffer;
import java.util.Iterator;

import org.apache.wolf.data.basetype.AbstractType;
import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.utils.HeapAllocator;

public class SuperColumn extends AbstractColumnContainer implements IColumn {

	private final ByteBuffer name;

	public SuperColumn(ByteBuffer superColumnName, AbstractType comparator) {
		this(superColumnName,AtomicSortedColumns.factory.create(comparator,false));
	}

	public SuperColumn(ByteBuffer name, IColumn columns) {
        super(columns);
        assert name != null;
        assert name.remaining() <= IColumn.MAX_NAME_LENGTH;
        this.name = name;		
	}

	@Override
	public Iterator<org.apache.wolf.data.basetype.IColumn> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addColumn(IColumn column, HeapAllocator allocator) {
		// TODO Auto-generated method stub
		
	}

}
