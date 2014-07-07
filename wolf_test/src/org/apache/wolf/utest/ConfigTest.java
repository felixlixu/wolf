package org.apache.wolf.utest;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import junit.framework.Assert;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.junit.Test;

public class ConfigTest {

	public String helloWorld(){
		return "HelloWorld";
	}
	
	@Test
	public void ListAddresstest() throws UnknownHostException {
		Assert.assertEquals(DatabaseDescriptor.getListenAddress().getHostAddress(), "127.0.0.1");
		Assert.assertEquals(DatabaseDescriptor.getStoragePort(), 9025);
		Assert.assertEquals(DatabaseDescriptor.getRpcTimeout(), 60000);
		Assert.assertEquals(DatabaseDescriptor.getConcurrentWriters(), 5);
		Assert.assertEquals(DatabaseDescriptor.getSeeds().isEmpty(), false);
	}

}
