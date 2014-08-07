package org.apache.db;

import java.nio.ByteBuffer;
import java.util.Iterator;

import org.apache.db.ISortedColumns.Factory;
import org.apache.db.filter.QueryPath;
import org.apache.wolf.data.basetype.AbstractType;
import org.apache.wolf.data.basetype.ColumnFamilyType;
import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.io.serialize.IColumnSerializer;
import org.apache.wolf.io.serialize.SuperColumnSerializer;
import org.apache.wolf.metadata.CFMetaData;
import org.apache.wolf.metadata.Schema;

public class ColumnFamily extends AbstractColumnContainer {

	private final CFMetaData cfm;

	public ColumnFamily(CFMetaData cfm, ISortedColumns map) {
		super(map);
		assert cfm!=null;
		this.cfm=cfm;
	}

	@Override
	public Iterator<IColumn> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ColumnFamily create(String table_, String columnFamilyName) {
		return create(Schema.instances.getCFMetaData(table_, columnFamilyName));
	}

	private static ColumnFamily create(CFMetaData cfMetadata) {
		return create(cfMetadata,TreeMapBackedStortedColumns.factory());
	}

	private static ColumnFamily create(CFMetaData cfMetadata, Factory factory) {
		return create(cfMetadata,factory,false);
	}

	private static ColumnFamily create(CFMetaData cfMetadata, Factory factory,
			boolean reversedInsertOrder) {
		return new ColumnFamily(cfMetadata,factory.create(cfMetadata.comparator, reversedInsertOrder));
	}

	public void addColumn(QueryPath path, ByteBuffer value, long timestamp,
			int timeToLive) {
		assert path.getColumnFamilyName()!=null:path;
		assert !metadata().getDefaultValidator().isCommutative();
		Column column;
		if(timeToLive>0){
			column=new ExpiringColumn(path.getColumnName(),value,timestamp,timeToLive);
		}else{
			column=new Column(path.getColumnName(),value,timestamp);
		}
		addColumn(path.getSuperColumnName(),column);
	}

	private void addColumn(ByteBuffer superColumnName, Column column) {
		IColumn c;
		if(superColumnName==null){
			c=column;
		}else{
			assert isSuper();
			c=new SuperColumn(superColumnName,getSubComparator());
			c.addColumn(column);
		}
		addColumn(c);
	}



	private AbstractType getSubComparator() {
		IColumnSerializer s=getColumnSerializer();
		return (s instanceof SuperColumnSerializer)?((SuperColumnSerializer)s).getComparator():null;
	}

	private IColumnSerializer getColumnSerializer() {
		return cfm.getColumnSerializer();
	}

	private boolean isSuper() {
		return getType()==ColumnFamilyType.Super;
	}

	private ColumnFamilyType getType() {
		return cfm.getCfType();
	}

	private CFMetaData metadata() {
		return cfm;
	}

}
