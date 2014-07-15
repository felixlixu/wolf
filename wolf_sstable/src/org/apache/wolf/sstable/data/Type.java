package org.apache.wolf.sstable.data;

import java.util.EnumSet;

public enum Type {

	DATA("Data.db"),
	PRIMARY_INDEX("Index.db"),
	FILTER("Filter.db"),
	COMPACTED_MARKER("Compacted"),
	COMPRESSION_INFO("CompressionInfo.db"),
	STATS("Statistics.db"),
	DIGEST("Digest.sha1");
	
	final String repr;
	
	final static EnumSet<Type> TYPES = EnumSet.allOf(Type.class);
	
	Type(String repr){
		this.repr=repr;
	}
	
	static Type fromRepresentation(String repr){
		for(Type type:TYPES){
			if(repr.equals(type.repr))
				return type;
		}
	throw new RuntimeException("Invalid SSTable component: '" + repr + "'");
	}
}
