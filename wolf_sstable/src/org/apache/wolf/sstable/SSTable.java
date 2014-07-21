package org.apache.wolf.sstable;

import java.util.HashSet;
import java.util.Set;

import org.apache.wolf.locator.data.CFMetaData;
import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.sstable.data.Collector;
import org.apache.wolf.sstable.data.Component;
import org.apache.wolf.sstable.data.Descriptor;
import org.apache.wolf.sstable.data.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SSTable {

	static final Logger logger=LoggerFactory.getLogger(SSTable.class);
	public static final String COMPONENT_DATA = Type.DATA.repr;
	
	public static String TEMPFILE_MARKER="tmp";
	public Descriptor descriptor;
	public Set<Component> components;
	public final IPartitioner partitioner;
	private CFMetaData metadata;
	
	public SSTable(Descriptor descriptor, Set<Component> components,
			CFMetaData cfMetaData, IPartitioner partitioner) {
		assert descriptor!=null;
		assert components!=null;
		assert partitioner!=null;
		
		this.descriptor=descriptor;
		Set<Component> dataComponents=new HashSet<Component>(components);
		/*for(Component component:components){
			assert component.type!=Component.Type.COMPACTED_MARKER;
		}*/
		this.partitioner=partitioner;
		this.metadata=metadata;
	}
	
	protected String getFilename() {
		return descriptor.filenameFor(COMPONENT_DATA);
	}
	
}
