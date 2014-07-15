package org.apache.wolf.sstable;

import java.util.Set;

import org.apache.wolf.sstable.data.Component;
import org.apache.wolf.sstable.data.Descriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SSTable {
	public SSTable(String filename) {
		// TODO Auto-generated constructor stub
	}

	static final Logger logger=LoggerFactory.getLogger(SSTable.class);
	public static String TEMPFILE_MARKER="tmp";
	public Descriptor descriptor;
	public Set<Component> components;
}
