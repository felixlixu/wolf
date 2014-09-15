package org.apache.wolf.dht.partition;

import javax.naming.ConfigurationException;

import org.apache.wolf.utils.FBUtilities;

public class PartitionFactory {

	public static IPartitioner newPartitioner(String partitionerClassName) throws ConfigurationException {
		if(!partitionerClassName.contains(".")){
			partitionerClassName="org.apache.wolf.dht.partition"+partitionerClassName;
		}
		return FBUtilities.construct(partitionerClassName, "partitioner");
	}
}
