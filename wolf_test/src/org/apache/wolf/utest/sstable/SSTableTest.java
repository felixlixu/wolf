package org.apache.wolf.utest.sstable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.naming.ConfigurationException;

import org.apache.wolf.sstable.SSTableReader;
import org.apache.wolf.utils.ByteBufferUtil;
import org.junit.Test;

public class SSTableTest {

	@Test
	public void testSingleWrite() throws IOException, ConfigurationException{
		ByteBuffer key=ByteBufferUtil.bytes(Integer.toString(11233));
		ByteBuffer bytes=ByteBuffer.wrap(new byte[1024]);
		new Random().nextBytes(bytes.array());
		
		Map<ByteBuffer,ByteBuffer> map=new HashMap<ByteBuffer,ByteBuffer>();
		map.put(key, bytes);
		SSTableReader ssTable=SSTableUtils.prepare().cf("Standard1").writeRow(map);
	}
}
