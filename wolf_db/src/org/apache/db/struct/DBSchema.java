package org.apache.db.struct;

import java.util.Map;
import java.util.UUID;

import org.apache.wolf.metadata.Schema;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

public class DBSchema {

	public Schema Schemainstance=Schema.instances;
	private final Map<String,Table> tableInstances=new NonBlockingHashMap<String,Table>();
	
	public static DBSchema instance=new DBSchema();
	
	public DBSchema() {	
	}

	public Table getTableInstance(String table) {
		return tableInstances.get(table);
	}
	
	

}
