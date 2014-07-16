package org.apache.wolf.sstable;

import java.util.Set;

import org.apache.wolf.conf.CFMetaData;
import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.sstable.data.Component;
import org.apache.wolf.sstable.data.Descriptor;


public class SSTableReader extends SSTable {

	public SSTableReader(Descriptor descriptor, Set<Component> components,
			CFMetaData cfMetaData, IPartitioner partitioner) {
		super(descriptor, components, cfMetaData, partitioner);
		// TODO Auto-generated constructor stub
	}
}
