package org.apace.db.struct;

import javax.naming.ConfigurationException;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.locator.data.KSMetaData;
import org.apache.wolf.locator.strategy.AbstractReplicationStrategy;
import org.apache.wolf.service.StorageService;


public class Table {

	public static String SYSTEM_TABLE="system";
	private String name;
	private volatile AbstractReplicationStrategy replicationStrategy;

	public Table(String table) {
		name=table;
		KSMetaData ksm=Schema.instance.getKSMetaData(table);
		assert ksm!=null :"Unknow keyspace :" +table;
		try{
			createReplicationStrategy(ksm);
		}catch(ConfigurationException e){
			throw new RuntimeException(e);
		}
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

	public static Table open(String table) {
		return open(table,Schema.instance);
	}

	private static Table open(String table, Schema schema) {
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

}
