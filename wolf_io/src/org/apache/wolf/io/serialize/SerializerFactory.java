package org.apache.wolf.io.serialize;

public class SerializerFactory {

	public static IColumnSerializer getColumnSerializer(){
		return new ColumnSerializer();
	}
}
