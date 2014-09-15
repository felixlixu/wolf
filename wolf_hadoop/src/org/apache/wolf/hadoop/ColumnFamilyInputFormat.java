package org.apache.wolf.hadoop;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.thrift.TException;
import org.apache.wolf.data.basetype.IColumn;
import org.apache.wolf.dht.partition.IPartitioner;
import org.apache.wolf.dht.ring.Range;
import org.apache.wolf.dht.token.Token;
import org.apache.wolf.thrift.InvalidRequestException;
import org.apache.wolf.thrift.KeyRange;
import org.apache.wolf.thrift.TokenRange;
import org.apache.wolf.thrift.Wolf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnFamilyInputFormat extends InputFormat<ByteBuffer,SortedMap<ByteBuffer,IColumn>>
	implements org.apache.hadoop.mapred.InputFormat<ByteBuffer, SortedMap<ByteBuffer,IColumn>>
{
	
	private static final Logger logger=LoggerFactory.getLogger(ColumnFamilyInputFormat.class);
	
	public static final String MAPRED_TASK_ID="mapred.task.id";

	public static final String CASSANDRA_HADOOP_MAX_KEY_SIZE="cassandra.hadoop.max_key_size";
	public static final int CASSANDRA_HADOOP_MAX_KEY_SIZE_DEFAULT=8192;
	
	private String keyspace;
	private String cfName;
	
	private static void validateConfiguration(Configuration conf){
		if(ConfigHelper.getInputKeyspace(conf)==null||ConfigHelper.getInputColumnFamily(conf)==null){
			throw new UnsupportedOperationException("you must set the keyspace and columnfamily with setColumnFamily()");
		}
		if(ConfigHelper.getInputSlicePredicate(conf)==null){
			throw new UnsupportedOperationException("you must set the predicate with setPredicate");
		}
	}

	@Override
	public org.apache.hadoop.mapred.InputSplit[] getSplits(JobConf arg0, int arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public org.apache.hadoop.mapreduce.RecordReader<ByteBuffer, SortedMap<ByteBuffer, IColumn>> createRecordReader(
			org.apache.hadoop.mapreduce.InputSplit arg0, TaskAttemptContext arg1)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<org.apache.hadoop.mapreduce.InputSplit> getSplits(
			JobContext context) throws IOException, InterruptedException {
		Configuration conf=context.getConfiguration();
		validateConfiguration(conf);
		List<TokenRange> masterRangeNodes=getRangeMap(conf);
		keyspace=ConfigHelper.getInputKeyspace(context.getConfiguration());
		cfName=ConfigHelper.getInputColumnFamily(context.getConfiguration());
		ExecutorService executor=Executors.newCachedThreadPool();
		List<org.apache.hadoop.mapreduce.InputSplit> splits=new ArrayList<org.apache.hadoop.mapreduce.InputSplit>();
		try{
			List<Future<List<org.apache.hadoop.mapreduce.InputSplit>>> splitfutures=new ArrayList<Future<List<org.apache.hadoop.mapreduce.InputSplit>>>();
			KeyRange jobKeyRange=ConfigHelper.getInputKeyRange(conf);
			IPartitioner partitioner=null;
			Range<Token> jobRange=null;
			if(jobKeyRange!=null){
				partitioner=ConfigHelper.getPartitioner(context.getConfiguration());
				jobRange=new Range<Token>(partitioner.getTokenFactory().fromString(jobKeyRange.start_token),
						partitioner.getTokenFactory().fromString(jobKeyRange.end_token),
						partitioner);
			}
			for(TokenRange range:masterRangeNodes){
				if(jobRange==null){
					splitfutures.add(executor.submit(new SplitCallable(range,conf)));
				}else{
					Range<Token> dhtRange=new Range<Token>(partitioner.getTokenFactory().fromString(range.start_token),
							partitioner.getTokenFactory().fromString(range.end_token),
							partitioner);
					if(dhtRange.intersects(jobRange)){
						for(Range<Token> intersection:dhtRange.intersectionWith(jobRange)){
							range.start_token=partitioner.getTokenFactory().toString(intersection.left);
							range.end_token=partitioner.getTokenFactory().toString(intersection.right);
							splitfutures.add(executor.submit(new SplitCallable(range,conf)));
						}
					}
				}
			}
			for(Future<List<org.apache.hadoop.mapreduce.InputSplit>> futureInputSplits:splitfutures){
				try{
					splits.addAll(futureInputSplits.get());
				}catch(Exception e){
					throw new IOException("Could not get input splits",e);
				}
			}
		}finally{
			executor.shutdownNow();
		}
		assert splits.size()>0;
		Collections.shuffle(splits,new Random(System.nanoTime()));
		return splits;
	}

	private List<TokenRange> getRangeMap(Configuration conf) throws IOException {
		Wolf.Client client=ConfigHelper.getClientFromAddressList(conf);
		List<TokenRange> map;
		try{
			map=client.describe_ring(ConfigHelper.getInputKeyspace(conf));
		}catch(TException e){
			throw new RuntimeException(e);
		}
		return map;
	}
	
	class SplitCallable implements Callable<List<InputSplit>>{

		private final TokenRange range;
		private final Configuration conf;
		
		public SplitCallable(TokenRange tr,Configuration conf){
			this.range=tr;
			this.conf=conf;
		}
		
		@Override
		public List<InputSplit> call() throws Exception {
			ArrayList<InputSplit> splits=new ArrayList<InputSplit>();
			List<String> tokens=getSubSplits(keyspace,cfName,range,conf);
			assert range.rpc_endpoints.size()==range.endpoints.size():"rpc_endpoints size must match endpoints size";
			String[] endpoints=range.endpoints.toArray(new String[range.endpoints.size()]);
			int endpointIndex =0;
			for(String endpoint:range.rpc_endpoints){
				String endpoint_address=endpoint;
				if(endpoint_address==null||endpoint_address.equals("0.0.0.0")){
					endpoint_address=range.endpoints.get(endpointIndex);
				}
				endpoints[endpointIndex++]=InetAddress.getByName(endpoint_address).getHostName();
			}
			for(int i=1;i<tokens.size();i++){
				ColumnFamilySplit split=new ColumnFamilySplit(tokens.get(i-1),tokens.get(i),endpoints);
				logger.debug("adding"+split);
				splits.add(split);
			}
			return splits;
		}
		
		public List<String> getSubSplits(String keyspace,String cfName,TokenRange range,Configuration conf) throws IOException, InvalidRequestException{
			int splitsize=ConfigHelper.getInputSplitSize(conf);
			for(int i=0;i<range.rpc_endpoints.size();i++){
				String host=range.rpc_endpoints.get(i);
				if(host==null||host.equals("0.0.0.0")){
					host=range.endpoints.get(i);
				}
				try{
					Wolf.Client client=ConfigHelper.createConnection(host, ConfigHelper.getRpcPort(conf), true);
					client.set_keyspace(keyspace);
					return client.describe_splits(cfName, range.start_token, range.end_token, splitsize);
				}catch(IOException e){
					logger.debug("failed connect to endpoint "+host,e);
				}catch(TException e){
					throw new RuntimeException(e);
				}
			}
			throw new IOException("failed connecting to all endpoints " + StringUtils.join(range.endpoints, ","));
		}
	}

	@Override
	public RecordReader<ByteBuffer, SortedMap<ByteBuffer, IColumn>> getRecordReader(
			org.apache.hadoop.mapred.InputSplit arg0, JobConf arg1,
			Reporter arg2) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
