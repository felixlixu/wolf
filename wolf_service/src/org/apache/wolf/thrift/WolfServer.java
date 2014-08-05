package org.apache.wolf.thrift;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

public class WolfServer implements Wolf.Iface {

	@Override
	public void login(AuthenticationRequest auth_request) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_keyspace(String keyspace) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ColumnOrSuperColumn get(ByteBuffer key, ColumnPath column_path,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnOrSuperColumn> get_slice(ByteBuffer key,
			ColumnParent column_parent, SlicePredicate predicate,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int get_count(ByteBuffer key, ColumnParent column_parent,
			SlicePredicate predicate, ConsistencyLevel consistency_level)
			throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<ByteBuffer, List<ColumnOrSuperColumn>> multiget_slice(
			List<ByteBuffer> keys, ColumnParent column_parent,
			SlicePredicate predicate, ConsistencyLevel consistency_level)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<ByteBuffer, Integer> multiget_count(List<ByteBuffer> keys,
			ColumnParent column_parent, SlicePredicate predicate,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeySlice> get_range_slices(ColumnParent column_parent,
			SlicePredicate predicate, KeyRange range,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeySlice> get_indexed_slices(ColumnParent column_parent,
			IndexClause index_clause, SlicePredicate column_predicate,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(ByteBuffer key, ColumnParent column_parent,
			Column column, ConsistencyLevel consistency_level)
			throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(ByteBuffer key, ColumnParent column_parent,
			CounterColumn column, ConsistencyLevel consistency_level)
			throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(ByteBuffer key, ColumnPath column_path, long timestamp,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove_counter(ByteBuffer key, ColumnPath path,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batch_mutate(
			Map<ByteBuffer, Map<String, List<Mutation>>> mutation_map,
			ConsistencyLevel consistency_level) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void truncate(String cfname) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> describe_schema_versions()
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KsDef> describe_keyspaces() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String describe_cluster_name() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String describe_version() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TokenRange> describe_ring(String keyspace) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String describe_partitioner() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String describe_snitch() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KsDef describe_keyspace(String keyspace) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> describe_splits(String cfName, String start_token,
			String end_token, int keys_per_split) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String system_add_column_family(CfDef cf_def) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String system_drop_column_family(String column_family)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String system_add_keyspace(KsDef ks_def) throws TException {
		if(ks_def!=null){
			System.out.print("The arg is right.");
		}
		return "Hello World.";
	}

	@Override
	public String system_drop_keyspace(String keyspace) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String system_update_keyspace(KsDef ks_def) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String system_update_column_family(CfDef cf_def) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CqlResult execute_cql_query(ByteBuffer query, Compression compression)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CqlPreparedResult prepare_cql_query(ByteBuffer query,
			Compression compression) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CqlResult execute_prepared_cql_query(int itemId, List<String> values)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}
