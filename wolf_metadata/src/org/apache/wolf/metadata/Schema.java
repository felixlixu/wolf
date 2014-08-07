package org.apache.wolf.metadata;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.ConfigurationException;

import org.apache.wolf.utils.Pair;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Schema {

	private volatile UUID version;
	
	private final Map<String,KSMetaData> tables=new NonBlockingHashMap<String,KSMetaData>();
	
	private static final Logger logger = LoggerFactory.getLogger(Schema.class);
	
	public Schema(UUID initialVersion) {
		version=initialVersion;
	}

	public static final UUID INITIAL_VERSION = new UUID(4096, 0);

	public static Schema instances=new Schema(INITIAL_VERSION);
	
	private final BiMap<Pair<String,String>,Integer> cfIdMap=HashBiMap.create();

	private static final int MIN_CF_ID=1000;

	private final AtomicInteger cfldGen=new AtomicInteger(MIN_CF_ID);

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

	public void load(CFMetaData cfm) throws ConfigurationException {
		Pair<String,String> key=new Pair<String,String>(cfm.getKsName(),cfm.getCfName());
		if(cfIdMap.containsKey(key)){
			throw new ConfigurationException("Attempt to assign id to existing column family.");
		}
		logger.debug("Adding {} to cfIdMap",cfm);
		cfIdMap.put(key, cfm.getCfId());
	}

	public Set<String> getTables() {
		return tables.keySet();
	}

	public UUID getVersion() {
		return version;
	}
	
	public void setVersion(UUID newVersion){
		version=newVersion;
	}

	public int nextCFId() {
		return cfldGen.getAndIncrement();
	}

	public Integer getId(String ksName, String cfName) {
		return cfIdMap.get(new Pair<String,String>(ksName,cfName));
	}
}
