package org.apache.db.struct;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.ConfigurationException;

import org.apache.db.column.storge.ColumnFamilyStoreService;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.io.util.FileUtils;
import org.apache.wolf.locator.strategy.AbstractReplicationStrategy;
import org.apache.wolf.metadata.CFMetaData;
import org.apache.wolf.metadata.KSMetaData;
import org.apache.wolf.metadata.Schema;
import org.apache.wolf.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Table {
	private String name;
	private volatile AbstractReplicationStrategy replicationStrategy;
	private final Map<Integer,ColumnFamilyStoreService> columnFamilyStores=new ConcurrentHashMap<Integer,ColumnFamilyStoreService>();
	private  static Logger logger=LoggerFactory.getLogger(Table.class);

	public Table(String table) throws ConfigurationException {
		setName(table);
		KSMetaData ksm=DBSchema.instance.Schemainstance.getKSMetaData(table);
		assert ksm!=null :"Unknow keyspace :" +table;
		try{
			createReplicationStrategy(ksm);
		}catch(ConfigurationException e){
			throw new RuntimeException(e);
		}
		
		for(String dataDir:DatabaseDescriptor.getAllDataFileLocations()){
			try{
				String keyspaceDir=dataDir+File.separator+table;
				FileUtils.createDirectory(keyspaceDir);
				
				File streamingDir=new File(keyspaceDir,"stream");
				if(streamingDir.exists())
					FileUtils.deleteRecursive(streamingDir);
			}catch(IOException ex){
				throw new IOError(ex);
			}
		}
		
		for(CFMetaData cfm:new ArrayList<CFMetaData>(Schema.instances.getTableDefinition(table).cfMetaData().values())){
			logger.debug("Initializing {} {} ",getName(),cfm.getCfName());
			initCf(cfm.getCfId(),cfm.getCfName());
		}
	}

	private void initCf(Integer cfId, String cfName) throws ConfigurationException {
		assert !columnFamilyStores.containsKey(cfId):String.format("tried to init %s as %s,but already useed by %s", cfName,cfId,columnFamilyStores.get(cfId));
		columnFamilyStores.put(cfId, ColumnFamilyStoreService.createColumnFamilyStore(this,cfName));
	}

	public void createReplicationStrategy(KSMetaData ksm) throws ConfigurationException {
		if(replicationStrategy!=null)
			StorageService.instance.getTokenMetadata().unregister(replicationStrategy);
		replicationStrategy=AbstractReplicationStrategy.createReplicationStrategy(ksm.getName(),
				ksm.getStrategyClass(),
				StorageService.instance.getTokenMetadata(),
				DatabaseDescriptor.getEndpointSnitch(),
				ksm.getStrategyOptions());
		replicationStrategy.validateOptions();
	}

	public static Table open(String table) throws ConfigurationException {
		return open(table,DBSchema.instance);
	}

	private static Table open(String table, DBSchema schema) throws ConfigurationException {
		Table tableinstance=schema.getTableInstance(table);
		if(tableinstance==null){
			synchronized(Table.class){
				tableinstance=schema.getTableInstance(table);
				if(tableinstance==null){
					tableinstance=new Table(table);
				}
			}
		}
		return tableinstance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
