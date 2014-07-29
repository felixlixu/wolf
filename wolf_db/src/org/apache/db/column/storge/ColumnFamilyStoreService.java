package org.apache.db.column.storge;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.ConfigurationException;

import org.apache.db.struct.Table;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.dht.partition.IPartitioner;
import org.apache.wolf.metadata.CFMetaData;
import org.apache.wolf.metadata.Schema;
import org.apache.wolf.sstable.SSTable;
import org.apache.wolf.sstable.data.Component;
import org.apache.wolf.sstable.data.Descriptor;
import org.apache.wolf.utils.DefaultInteger;
import org.apache.wolf.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;

public class ColumnFamilyStoreService {

	private final Table table;
	private final CFMetaData metadata;
	private volatile DefaultInteger minCompactionThreshold;
	private volatile DefaultInteger maxCompactionThreshold;
	private final IPartitioner partitioner;
	private AtomicInteger fileIndexGenerator=new AtomicInteger();
	private static Logger logger = LoggerFactory.getLogger(ColumnFamilyStoreService.class);
	
	public ColumnFamilyStoreService(Table table, String columnfamilyName,
			IPartitioner partitioner, int generation, CFMetaData cfMetaData) {
		assert cfMetaData!=null:"null metadata for"+table+"."+columnfamilyName;
		this.table=table;
		columnfamilyName=columnfamilyName;
		this.metadata=cfMetaData;
		this.minCompactionThreshold=new DefaultInteger(metadata.getMinCompactionThreshold());
		this.maxCompactionThreshold=new DefaultInteger(metadata.getMaxCompactionThreshold());
		this.partitioner=partitioner;
		
		//this.indexManager=new SecondaryIndexManager(this);
		fileIndexGenerator.set(generation);
		if(logger.isDebugEnabled())
			logger.debug("Starting CFS {}",columnfamilyName);
		
		Set<Map.Entry<Descriptor, Set<Component>>> entries=files(table.getName(),columnfamilyName,false,false).entrySet();
		//this.compactionStrategy=metadata.createCompactionStrategyInstance(this);
	}

	private Map<Descriptor,Set<Component>> files(String name, String columnfamilyName, boolean includeCompacted, boolean includeTemporary) {
		final Map<Descriptor,Set<Component>> sstables=new HashMap<Descriptor,Set<Component>>();
		for(String directory:DatabaseDescriptor.getAllDataFileLocationsForTable(name)){
			for(Pair<Descriptor,Component> component:files(new File(directory),columnfamilyName)){
				if(component!=null){
					if((includeCompacted||!new File(component.left.filenameFor(Component.COMPACTED_MARKER)).exists())
							&&(includeTemporary||!component.left.temp)){
						Set<Component> components=sstables.get(component.left);
						if(components==null){
							components=new HashSet<Component>();
							sstables.put(component.left, components);
						}
					}else{
						logger.debug("not including compacted sstable " + component.left.cfname + "-" + component.left.generation);
					}
				}
			}
		}
		return sstables;
	}

	public static void scrubDataDirectories(String sYSTEMTABLE, String cfName) {
		// TODO Auto-generated method stub
		
	}

	public static ColumnFamilyStoreService createColumnFamilyStore(Table table,
			String cfName) throws ConfigurationException {
		return createColumnFamilyStore(table,cfName,DatabaseDescriptor.getPartitioner(),Schema.instances.getCFMetaData(table.getName(), cfName));
	}

	private static synchronized ColumnFamilyStoreService createColumnFamilyStore(
			Table table, String columnfamilyName, IPartitioner partitioner,
			CFMetaData cfMetaData) {
		List<Integer> generations=new ArrayList<Integer>();
		for(String path:DatabaseDescriptor.getAllDataFileLocationsForTable(table.getName())){
			Iterable<Pair<Descriptor,Component>> pairs=files(new File(path),columnfamilyName);
			File incrementalsPath=new File(path,"backups");
			if(incrementalsPath.exists()){
				pairs=Iterables.concat(pairs,files(incrementalsPath,columnfamilyName));
			}
			for(Pair<Descriptor,Component> pair:pairs){
				Descriptor desc=pair.left;
				if(!desc.cfname.equals(columnfamilyName)){
					continue;
				}
				generations.add(desc.generation);
				if(!desc.isCompatible()){
					throw new RuntimeException(String.format("Can't open incompatible SSTable!Current version %s found file:%s", Descriptor.CURRENT_VERSION));
				}
			}
		}
		Collections.sort(generations);
		int value =(generations.size()>0)?(generations.get(generations.size()-1)):0;
		return new ColumnFamilyStoreService(table,columnfamilyName,partitioner,value,cfMetaData);
	}

	private static List<Pair<Descriptor,Component>> files(File path,
			String columnfamilyName) {
		final List<Pair<Descriptor,Component>> sstables=new ArrayList<Pair<Descriptor,Component>>();
		final String sstableFilePrefix=columnfamilyName+Component.separator;
		path.listFiles(new FileFilter(){

			@Override
			public boolean accept(File file) {
				if(file.isDirectory()||!file.getName().startsWith(sstableFilePrefix)){
					return false;
				}
				Pair<Descriptor,Component> pair=SSTable.tryComponentFromFilename(file.getParentFile(),file.getName());
				if(pair!=null)
					sstables.add(pair);
				return false;
			}
			
		});
		return sstables;
	}

}
