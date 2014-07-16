package org.apache.wolf.sstable;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.naming.ConfigurationException;

import org.apache.wolf.conf.CFMetaData;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.db.Schema;
import org.apache.wolf.io.util.SequentialWriter;
import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.sstable.data.Collector;
import org.apache.wolf.sstable.data.Component;
import org.apache.wolf.sstable.data.Descriptor;
import org.apache.wolf.token.DecoratedKey;
import org.apache.wolf.utils.ByteBufferUtil;

public class SSTableWriter extends SSTable {

	private final SequentialWriter dataFile;
	private DecoratedKey<?> lastWrittenKey;
	
	public SSTableWriter(String filename, long keyCount) throws ConfigurationException, FileNotFoundException {
		this(filename,
			  keyCount,
			  Schema.instance.getCFMetaData(Descriptor.fromFilename(filename)),
			  DatabaseDescriptor.getPartitioner(),
			  SSTableMetadata.createCollector()
			  );
	}
	
	public SSTableWriter(String filename, long keyCount, CFMetaData cfMetaData,
			IPartitioner partitioner, Collector createCollector) throws FileNotFoundException {
		super(Descriptor.fromFilename(filename),
				components(cfMetaData),
				cfMetaData,
				partitioner);
		dataFile=SequentialWriter.open(new File(getFilename()),true);
		dataFile.setComputeDigest();
	}



	private static Set<Component> components(CFMetaData metadata) {
		Set<Component> components=new HashSet<Component>(Arrays.asList(Component.DATA,Component.FILTER,Component.PRIMARY_INDEX,Component.STATS));
		components.add(Component.DIGEST);
		return components;
	}

	public SSTableReader closeAndOpenReader() throws IOException {
		 dataFile.close();
		return null;
	}

	public void append(DecoratedKey decoratedKey, ByteBuffer value) throws IOException {
		long currentPosition=beforeAppend(decoratedKey);
		ByteBufferUtil.writeWithShortLength(decoratedKey.key,dataFile.stream);
		assert value.remaining()>0;
		dataFile.stream.writeLong(value.remaining());
		ByteBufferUtil.write(value,dataFile.stream);
		afterAppend(decoratedKey,currentPosition);
	}

	private void afterAppend(DecoratedKey decoratedKey, long currentPosition) {
        /*bf.add(key.key);
        long indexPosition = indexFile.getFilePointer();
        ByteBufferUtil.writeWithShortLength(key.key, indexFile.stream);
        indexFile.stream.writeLong(dataPosition);
        if (logger.isTraceEnabled())
            logger.trace("wrote index of " + key + " at " + indexPosition);
	*/
	}

	private long beforeAppend(DecoratedKey decoratedKey) throws IOException {
		if(decoratedKey==null)
			throw new IOException("Keys must not be null.");
        if (lastWrittenKey != null && lastWrittenKey.compareTo(decoratedKey) > 0)
        {
            logger.info("Last written key : " + lastWrittenKey);
            logger.info("Current key : " + decoratedKey);
            logger.info("Writing into file " + getFilename());
            throw new IOException("Keys must be written in ascending order.");
        }
		 return (lastWrittenKey == null) ? 0 : dataFile.getFilePointer();
	}

}
