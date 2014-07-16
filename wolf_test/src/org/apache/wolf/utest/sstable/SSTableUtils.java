package org.apache.wolf.utest.sstable;



public class SSTableUtils {

    public static String TABLENAME = "Keyspace1";
    public static String CFNAME = "Standard1";
	
	public static Context prepare() {
		return new Context();
	}

}
