package org.apache.wolf.utest.sstable;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.naming.ConfigurationException;

import org.apache.wolf.dht.token.DecoratedKey;
import org.apache.wolf.dht.token.StringToken;
import org.apache.wolf.io.util.SequentialWriter;
import org.apache.wolf.sstable.SSTableWriter;
import org.apache.wolf.utils.ByteBufferUtil;
import org.junit.Test;

public class WriterTest {

	private SequentialWriter dataFile;
	@Test
	public void test() throws IOException {
		dataFile=SequentialWriter.open(new File("D:\\123.txt"),true);
		dataFile.write("123333".getBytes());
		//dataFile.write("123333".getBytes());
		dataFile.close();
	}
	
	@Test
	public void SSTableWriteTest() throws ConfigurationException, IOException{
		String filePath="D:\\123.txt";
		ByteBuffer key=ByteBufferUtil.bytes(Integer.toString(11233));
		ByteBuffer bytes=ByteBuffer.wrap(new byte[1024]);
		new Random().nextBytes(bytes.array());
		
		Map<ByteBuffer,ByteBuffer> map=new HashMap<ByteBuffer,ByteBuffer>();
		map.put(key, bytes);
		SSTableWriter writer=new SSTableWriter(filePath,map.size());
		DecoratedKey key1=new DecoratedKey(new StringToken(key.toString()),key);
		writer.append(key1, map.get(key));
	}

}
