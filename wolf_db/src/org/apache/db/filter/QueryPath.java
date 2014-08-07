package org.apache.db.filter;

import java.nio.ByteBuffer;

import org.apache.wolf.thrift.ColumnPath;

public class QueryPath {

	private final String columnFamilyName;
	private final ByteBuffer superColumnName;
	private final ByteBuffer columnName;

	public QueryPath(ColumnPath cp) {
		this(cp.column_family,cp.super_column,cp.column);
	}

	public QueryPath(String column_family, ByteBuffer super_column,
			ByteBuffer column) {
		this.columnFamilyName=column_family;
		this.superColumnName=super_column;
		this.columnName=column;
	}

	public String getColumnFamilyName() {
		return columnFamilyName;
	}

	public ByteBuffer getColumnName() {
		return columnName;
	}

	public ByteBuffer getSuperColumnName() {
		return superColumnName;
	}

}
