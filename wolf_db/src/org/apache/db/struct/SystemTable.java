package org.apache.db.struct;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.naming.ConfigurationException;

import org.apache.db.type.BytesType;
import org.apache.wolf.utils.ByteBufferUtil;
import org.apache.wolf.utils.StaticField;

public class SystemTable {

	private static final ByteBuffer CLUSTERNAME=ByteBufferUtil.bytes("ClusterName");

	public static int incrementAndGetGeneration() throws IOException{
		Table table=Table.open(StaticField.SYSTEMTABLE);
		return 0;
	}

	public static void checkHealth() throws ConfigurationException  {
		Table table=null;
		try{
			table=Table.open(StaticField.SYSTEMTABLE);
		}catch(AssertionError err){
			ConfigurationException ex=new ConfigurationException("Could not read system table!");
			err.initCause(ex);
			throw ex;
		}
		SortedSet<ByteBuffer> cols=new TreeSet<ByteBuffer>(BytesType.instance);
		cols.add(CLUSTERNAME);
	}
}
