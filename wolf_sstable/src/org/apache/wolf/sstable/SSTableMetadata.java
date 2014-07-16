package org.apache.wolf.sstable;

import org.apache.wolf.sstable.data.Collector;

public class SSTableMetadata {

	public static Collector createCollector() {
		return new Collector();
	}

}
