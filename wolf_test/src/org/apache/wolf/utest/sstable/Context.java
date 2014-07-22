package org.apache.wolf.utest.sstable;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.naming.ConfigurationException;

import org.apache.wolf.dht.token.DecoratedKey;
import org.apache.wolf.sstable.SSTableReader;
import org.apache.wolf.sstable.SSTableWriter;
import org.apache.wolf.sstable.data.Component;
import org.apache.wolf.sstable.data.Descriptor;
public class Context {

	private String cfname="Keyspace1";
	private Descriptor dest;
	private String ksname="Standard1";
	private int generation;
	private boolean cleanup;

	public Context cf(String cfname){
		this.cfname=cfname;
		return this;
	}

	public SSTableReader writeRow(Map<ByteBuffer, ByteBuffer> entries) throws IOException, ConfigurationException {
		File datafile=(dest==null)?tempSSTableFile(ksname,cfname,generation):new File(dest.filenameFor(Component.DATA));
		SSTableWriter writer=new SSTableWriter(datafile.getAbsolutePath(),entries.size());
		SortedMap<DecoratedKey,ByteBuffer> sorted=new TreeMap<DecoratedKey,ByteBuffer>();
		for(Map.Entry<ByteBuffer, ByteBuffer> entry:entries.entrySet()){
			sorted.put(writer.partitioner.decoratekey(entry.getKey()),entry.getValue());
		}
		final Iterator<Map.Entry<DecoratedKey,ByteBuffer>> iter=sorted.entrySet().iterator();
		return write(sorted.size(),new Appender(){

			@Override
			public boolean append(SSTableWriter writer) throws IOException {
				if(!iter.hasNext()){
					return false;
				}
				Map.Entry<DecoratedKey, ByteBuffer> entry=iter.next();
				writer.append(entry.getKey(),entry.getValue());
				return true;
			}
		},writer);
	}

	private SSTableReader write(int expectedSize, Appender appender,SSTableWriter writer) throws IOException, ConfigurationException {
        //File datafile = (dest == null) ? tempSSTableFile(ksname, cfname, generation) : new File(dest.filenameFor(Component.DATA));
        //SSTableWriter writer = new SSTableWriter(datafile.getAbsolutePath(), expectedSize);
        long start = System.currentTimeMillis();
        while (appender.append(writer)) { /* pass */ }
        SSTableReader reader = writer.closeAndOpenReader();
        // mark all components for removal
        if (cleanup){
            for (Component component : reader.components){
                //new File(reader.descriptor.filenameFor(component)).deleteOnExit();
            }
        }
        return reader;
	}

	private File tempSSTableFile(String tablename, String cfname, int generation) throws IOException {
		File tempDir=File.createTempFile(tablename, cfname);
		if(!tempDir.delete()||!tempDir.mkdir()){
			 throw new IOException("Temporary directory creation failed.");
		}
		tempDir.deleteOnExit();
		File tabledir=new File(tempDir,tablename);
		tabledir.mkdir();
		tabledir.deleteOnExit();
		File datafile=new File(new Descriptor(tabledir,tablename,cfname,generation,false).filenameFor("Data.db"));
		if(!datafile.createNewFile()){
			throw new IOException("unable to create file"+datafile);
		}
		datafile.deleteOnExit();
		return datafile;
	}
	
	public static abstract class Appender{
		public abstract boolean append(SSTableWriter writer) throws IOException;
	}
}
