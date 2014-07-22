package org.apache.wolf.utest;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.metadata.Schema;
import org.apache.wolf.utils.StaticField;
import org.junit.Test;

public class MetadataTest {

	@Test
	public void test() throws ClassNotFoundException {
		Assert.assertTrue(Schema.instances!=null);
		this.getClass().getClassLoader().loadClass(DatabaseDescriptor.class.getName());
		//Assert.assertTrue(Schema.instances.getKSMetaData(StaticField.SYSTEMTABLE)!=null);
	}

}
