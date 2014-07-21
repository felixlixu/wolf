package org.apache.wolf.locator.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wolf.locator.strategy.AbstractReplicationStrategy;
import org.apache.wolf.locator.strategy.LocalStrategy;

public class KSMetaData {

	private final String name;
	private final Class<? extends AbstractReplicationStrategy> strategyClass;
	private final Map<String,String> strategyOptions;
	private final boolean durableWrites;
	private final Map<String, CFMetaData> cfMetaData;
	
	public KSMetaData(String name,
			Class<? extends AbstractReplicationStrategy> strategyClass,
			Map<String,String> strategyOptions,boolean durableWrites,Iterable<CFMetaData> cfDefs){
		this.name=name;
		this.strategyClass=strategyClass;
		this.strategyOptions=strategyOptions;
		Map<String,CFMetaData> cfmap=new HashMap<String,CFMetaData>();
		for(CFMetaData cfm:cfDefs){
			cfmap.put(cfm.getCfName(), cfm);
		}
		this.cfMetaData=Collections.unmodifiableMap(cfmap);
		this.durableWrites=durableWrites;
	}

	public static KSMetaData systemKeyspace(){
		List<CFMetaData> cfDefs=Arrays.asList();
		return new KSMetaData("system",LocalStrategy.class,optsWithRF(1),true,cfDefs);
	}
	
	private static Map<String, String> optsWithRF(final Integer rf) {
		Map<String,String> ret=new HashMap<String,String>();
		ret.put("replication", rf.toString());
		return ret;
	}

	public String getName() {
		return name;
	}

	public Class<? extends AbstractReplicationStrategy> getStrategyClass() {
		return strategyClass;
	}

	public Map<String,String> getStrategyOptions() {
		return strategyOptions;
	}
}
