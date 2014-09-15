package org.apache.wolf.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ConfigurationException;

import org.apache.hadoop.conf.Configuration;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.wolf.dht.partition.IPartitioner;
import org.apache.wolf.dht.partition.PartitionFactory;
import org.apache.wolf.thrift.KeyRange;
import org.apache.wolf.thrift.SlicePredicate;
import org.apache.wolf.thrift.Wolf.Client;
import org.apache.wolf.utils.FBUtilities;
import org.apache.wolf.utils.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigHelper {

	private static final Logger logger=LoggerFactory.getLogger(ConfigHelper.class);
	
	private static final String INPUT_KEYSPACE_CONFIG = "cassandra.input.keyspace";
	private static final String INPUT_COLUMNFAMILY_CONFIG = "cassandra.input.columnfamily";
	private static final String INPUT_PREDICATE_CONFIG = "cassandra.input.predicate";
	private static final String INITAL_THRIFT_ADDRESS = "cassandra.thrift.address";
	private static final String INPUT_KEYRANGE_CONFIG = "cassandra.input.keyRange";
	private static final String PARTITIONER_CONFIG = "cassandra.partitioner.class";
	private static final String THRIFT_PORT = "cassandra.thrift.port";

	private static final String INPUT_SPLIT_SIZE_CONFIG = "cassandra.input.split.size";

	private static final int DEFAULT_SPLIT_SIZE = 64*1024;

	public static String getInputKeyspace(Configuration conf){
		return conf.get(INPUT_KEYSPACE_CONFIG);
	}

	public static String getInputColumnFamily(Configuration conf) {
		return conf.get(INPUT_COLUMNFAMILY_CONFIG);
	}

	public static SlicePredicate getInputSlicePredicate(Configuration conf) {
		return predicateFromString(conf.get(INPUT_PREDICATE_CONFIG));
	}
	
    private static SlicePredicate predicateFromString(String st)
    {
        assert st != null;
        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
        SlicePredicate predicate = new SlicePredicate();
        try
        {
            deserializer.deserialize(predicate, Hex.hexToBytes(st));
        }
        catch (TException e)
        {
            throw new RuntimeException(e);
        }
        return predicate;
    }

	public static Client getClientFromAddressList(Configuration conf) throws IOException {
		String[] addresses=ConfigHelper.getInitialAddress(conf).split(",");
		Client client=null;
		List<IOException> exceptions=new ArrayList<IOException>();
		for(String address:addresses){
			try{
				client=createConnection(address,ConfigHelper.getRpcPort(conf),true);
				break;
			}catch(IOException ioe){
				exceptions.add(ioe);
			}
		}
		if(client==null){
			logger.error("failed to connect to any initial addresses");
			for(IOException ioe:exceptions){
				logger.error("",ioe);
			}
			throw exceptions.get(exceptions.size()-1);
		}
		return client;
	}

	public static Client createConnection(String host, int port,
			boolean framed) throws IOException {
		TSocket socket=new TSocket(host,port);
		TTransport trans=framed?new TFramedTransport(socket):socket;
		try{
			trans.open();
		}catch(TTransportException e){
			throw new IOException("unable to connect to server",e);
		}
		return new Client(new TBinaryProtocol(trans));
	}

	public static int getRpcPort(Configuration conf) {
		return Integer.parseInt(conf.get(THRIFT_PORT));
	}

	private static String getInitialAddress(Configuration conf) {
		return conf.get(INITAL_THRIFT_ADDRESS);
	}

	public static KeyRange getInputKeyRange(Configuration conf) {
		String str=conf.get(INPUT_KEYRANGE_CONFIG);
		return null!=str?keyRangeFromString(str):null;
	}

	private static KeyRange keyRangeFromString(String str) {
		assert str!=null;
		TDeserializer serializer=new TDeserializer(new TBinaryProtocol.Factory());
		KeyRange range=new KeyRange();
		try{
			serializer.deserialize(range, Hex.hexToBytes(str));
		}catch(TException e){
			throw new RuntimeException(e);
		}
		return range;
	}

	public static int getInputSplitSize(Configuration conf) {
		return conf.getInt(INPUT_SPLIT_SIZE_CONFIG, DEFAULT_SPLIT_SIZE);
	}

	public static IPartitioner getPartitioner(Configuration conf) {
		try{
			return PartitionFactory.newPartitioner(conf.get(PARTITIONER_CONFIG));
		}catch(ConfigurationException e){
			throw new RuntimeException(e);
		}
	}
}
