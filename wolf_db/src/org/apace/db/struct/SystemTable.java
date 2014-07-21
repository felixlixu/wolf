package org.apace.db.struct;

import java.io.IOException;

public class SystemTable {

	public static int incrementAndGetGeneration() throws IOException{
		Table table=Table.open(Table.SYSTEM_TABLE);
		return 0;
	}
}
