package org.apache.wolf.db;

import static org.junit.Assert.*;

import javax.naming.ConfigurationException;

import org.apache.db.struct.Table;
import org.junit.Test;

public class dbTest {

	@Test
	public void test() throws ConfigurationException {
		Table.open("Test");
	}

}
