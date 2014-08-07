package org.apache.wolf.data.basetype;

import org.apache.wolf.utils.FBUtilities;
import org.apache.wolf.utils.HeapAllocator;

public interface IColumn {

	int MAX_NAME_LENGTH = FBUtilities.MAX_UNSIGNED_SHORT;

	void addColumn(IColumn column);

	void addColumn(IColumn column, HeapAllocator allocator);

}
