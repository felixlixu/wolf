package org.apache.wolf.locator.data;

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
	
	public static final Schema instance=new Schema(INITIAL_VERSION);

	public void addSystemTable(KSMetaData systemTable) {
		tables.put(systemTable.getName(),systemTable);
	}

}
