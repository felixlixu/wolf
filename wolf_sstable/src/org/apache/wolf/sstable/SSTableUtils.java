package org.apache.wolf.sstable;

import org.apache.wolf.sstable.data.Context;


public class SSTableUtils {

    public static String TABLENAME = "Keyspace1";
    public static String CFNAME = "Standard1";
	
	public static Context prepare() {
		return new Context();
	}

}
