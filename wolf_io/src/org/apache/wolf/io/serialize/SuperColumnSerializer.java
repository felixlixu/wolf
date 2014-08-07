package org.apache.wolf.io.serialize;

import org.apache.wolf.data.basetype.AbstractType;

public class SuperColumnSerializer implements IColumnSerializer{

	@SuppressWarnings("rawtypes")
	private AbstractType comparator;
	@SuppressWarnings("rawtypes")
	public SuperColumnSerializer(AbstractType comparator){
		this.comparator=comparator;
	}
	
	@SuppressWarnings("rawtypes")
	public AbstractType getComparator() {
		return comparator;
	}

}
