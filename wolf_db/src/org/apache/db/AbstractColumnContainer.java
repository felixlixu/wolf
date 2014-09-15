package org.apache.db;

import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.utils.HeapAllocator;

public abstract class AbstractColumnContainer implements IColumnContainer,IIterableColumns{

	private final ISortedColumns columns;

	public AbstractColumnContainer(ISortedColumns columns) {
		this.columns=columns;
	}

	public void addColumn(IColumn column){
		addColumn(column,HeapAllocator.instance);
	}

	private void addColumn(IColumn column, HeapAllocator allocator) {
		columns.addColumn(column,allocator);
	}
}
