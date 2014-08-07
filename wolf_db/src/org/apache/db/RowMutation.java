package org.apache.db;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.db.filter.QueryPath;
import org.apache.wolf.message.msg.IMessageProducer;
import org.apache.wolf.message.msg.Message;
import org.apache.wolf.metadata.Schema;

public class RowMutation implements IMutation,IMessageProducer{

	private String table_;
	private ByteBuffer key_;
	private Map<Integer,ColumnFamily> modifications_=new HashMap<Integer,ColumnFamily>();

	public RowMutation(String table, ByteBuffer key) {
		table_=table;
		key_=key;
	}

	@Override
	public Message getMessage(Integer version) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(QueryPath path, ByteBuffer value, long timestamp) {
		add(path,value,timestamp,0);
	}

	private void add(QueryPath path, ByteBuffer value, long timestamp, int timeToLive) {
		Integer id=Schema.instances.getId(table_,path.getColumnFamilyName());
		ColumnFamily columnFamily=modifications_.get(id);
		if(columnFamily==null){
			columnFamily=ColumnFamily.create(table_,path.getColumnFamilyName());
			modifications_.put(id, columnFamily);
		}
		columnFamily.addColumn(path,value,timestamp,timeToLive);
	}

}
