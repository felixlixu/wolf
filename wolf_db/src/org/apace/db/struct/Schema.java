package org.apace.db.struct;

import java.util.Map;

import org.apache.wolf.locator.data.KSMetaData;
import org.apache.wolf.sstable.SSTableSchema;
import org.cliffc.high_scale_lib.NonBlockingHashMap;



public class Schema {

	public static Schema instance=new Schema();
	private static SSTableSchema sstableschema=SSTableSchema.instance;
	
	private final Map<String,Table> tableInstance=new NonBlockingHashMap<String,Table>();
	private final Map<String,KSMetaData> tables=new NonBlockingHashMap<String,KSMetaData>();
	

	public Table getTableInstance(String table) {
		return tableInstance.get(table);
	}


	public KSMetaData getKSMetaData(String table) {
		assert table!=null;
		return tables.get(table);
	}

}
