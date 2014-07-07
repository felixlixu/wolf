package org.apache.wolf.utest;

import javax.naming.ConfigurationException;

import junit.framework.Assert;

import org.apache.wolf.util.FBUtilities;
import org.junit.Test;

public class FButilitiesTest {

	@Test
	public void classNameTest(){
		try {
			FBUtilities.classForName("org.apache.wolf.utest.ConfigTest", "readable");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void constructtest() {
		try {
			ConfigTest config=FBUtilities.construct("org.apache.wolf.utest.ConfigTest", "snitch");
			Assert.assertNotNull(config);
			Assert.assertEquals(config.helloWorld(), "HelloWorld");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

}
