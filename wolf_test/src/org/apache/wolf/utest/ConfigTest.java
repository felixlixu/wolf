package org.apache.wolf.utest;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.junit.Test;

public class ConfigTest {

	@Test
	public void ListAddresstest() {
		Assert.assertEquals(DatabaseDescriptor.getListenAddress().getHostAddress(), "127.0.0.1");
	}

}
