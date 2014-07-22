package org.apache.wolf.metadata;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

public class Schema {

	private volatile UUID version;
	
	private final Map<String,KSMetaData> tables=new NonBlockingHashMap<String,KSMetaData>();
	
	public Schema(UUID initialVersion) {
		version=initialVersion;
	}

	public static final UUID INITIAL_VERSION = new UUID(4096, 0);

	public static Schema instances=new Schema(INITIAL_VERSION);

	public void addSystemTable(KSMetaData systemTable) {
		tables.put(systemTable.getName(),systemTable);
	}

	public CFMetaData getCFMetaData(String tableName,String cfName) {
		assert tableName!=null;
		KSMetaData ksm=tables.get(tableName);
		return (ksm==null)?null:ksm.cfMetaData().get(cfName);
	}

	public KSMetaData getKSMetaData(String table) {
		assert table!=null;
		return tables.get(table);
	}

	public Map<String,CFMetaData> getTableMetaData(String table) {
		assert table!=null;
		KSMetaData ksm=tables.get(table);
		assert ksm!=null;
		return ksm.cfMetaData();
	}

	public KSMetaData getTableDefinition(String table) {
		return getKSMetaData(table);
	}
}
