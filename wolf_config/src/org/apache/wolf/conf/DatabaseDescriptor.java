package org.apache.wolf.conf;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.ConfigurationException;

import org.apache.wolf.locator.snitch.DynamicEndpointSnitch;
import org.apache.wolf.locator.snitch.EndpointSnitchInfo;
import org.apache.wolf.locator.snitch.IEndpointSnitch;
import org.apache.wolf.partition.IPartitioner;
import org.apache.wolf.utils.FBUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class DatabaseDescriptor {
	
	private static String DEFAULT_CONFIGURATION="wolf.yaml";
	private static Logger logger=LoggerFactory.getLogger(DatabaseDescriptor.class);
	private static InetAddress listenAddress;
	private static Config conf;
	private static InetAddress broadcastAddress;
	private static IEndpointSnitch snitch;
	private static IPartitioner partitioner;
	
    public static void initDefaultsOnly()
    {
        conf = new Config();
    }
	
	static{
		try{
			if(conf==null){
				URL url=getStorageConfigURL();
				logger.info("Loading settings from"+url);
				InputStream input=null;
				try{
					input=url.openStream();
				}catch(IOException e){
					throw new AssertionError(e);
				}
				Constructor constructor=new Constructor(Config.class);
			    Yaml yaml=new Yaml(constructor);
			    conf=(Config)yaml.load(input);
			}
			if(conf==null||conf.getListen_address()!=null){
				try{
					listenAddress=InetAddress.getByName(conf.getListen_address());
				}catch(UnknownHostException e){
					throw new ConfigurationException("Unknown listen_address"+conf.getListen_address()+"");
				}
			}
			if(conf.getBroadcast_address()!=null){
				if(conf.getBroadcast_address().equals("0.0.0.0")){
					throw new ConfigurationException("broadcast_address cannot be 0.0.0.0!");
				}
				try{
					broadcastAddress=InetAddress.getByName(conf.getBroadcast_address());
				}catch(UnknownHostException e){
					throw new ConfigurationException("Unknown broadcast_address "+ conf.getBroadcast_address());
				}
			}
			
			if(conf.getPartitioner()==null){
				throw new ConfigurationException("Missing directive:partitioner");
			}
			try{
				partitioner=FBUtilities.construct(conf.getPartitioner(), "partitioner");
			}catch(Exception e){
				throw new ConfigurationException("Invalid partitioner class");
			}
			
			if(conf.getEndpoint_snitch()==null){
				throw new ConfigurationException("Missing endpoint_snitch directive");
			}
			snitch=CreateEndpointSnitch(conf.getEndpoint_snitch());
			EndpointSnitchInfo.create();
			
		}catch(ConfigurationException e){
			logger.error("Fatal configuration error",e);
			System.err.println(e.getMessage()
					+"\nFatal configuration error;unable to start server.see log for stacktrace");
		}catch(Exception e){
			logger.error("Fatal configuration error",e);
			System.err.println(e.getMessage()
					+"\nFatal configuration error;unable to start server.see log for stacktrace");			
		}
	}

	private static URL getStorageConfigURL() throws ConfigurationException {
		String configUrl=System.getProperty("wolf.yaml");
		if(configUrl==null){
			configUrl=DEFAULT_CONFIGURATION;
		}
		URL url;
		try{
			url=new URL(configUrl);
			url.openStream().close();
		}catch(Exception ex){
			ClassLoader loader=DatabaseDescriptor.class.getClassLoader();
			url=loader.getResource(configUrl);
			if(url==null){
				throw new ConfigurationException("Cannot locate " + configUrl);
			}
		}
		return url;
	}

	private static IEndpointSnitch CreateEndpointSnitch(String endpoint_snitch) throws ConfigurationException {
		IEndpointSnitch snitch=FBUtilities.construct(endpoint_snitch,"snith");
		return conf.dynamic_snitch?new DynamicEndpointSnitch(snitch):snitch;
	}

	public static InetAddress getListenAddress() {
		return listenAddress;
	}

	public static int getStoragePort() {
		return Integer.parseInt(conf.getStorage_port());
	}

	public static long getRpcTimeout() {
		return Long.parseLong(conf.getRpc_Timeout());
	}

	public static int getConcurrentWriters() {
		return conf.getConcurrent_writes();
	}

	public static Set<InetAddress> getSeeds() {
		String[] keys=conf.getSeeds();
		Set<InetAddress> seeds=new HashSet<InetAddress>();
		for(String key:keys){
			try {
				seeds.add(InetAddress.getByName(key));
			} catch (UnknownHostException e) {
				logger.error("init host failed. the host name is: ", e.getMessage());
			}
		}
		return seeds;
	}

	public static InetAddress getBroadcastAddress() {
		return broadcastAddress;
	}
	
    public static void setBroadcastAddress(InetAddress broadcastAdd)
    {
        broadcastAddress = broadcastAdd;
    }

    public static IEndpointSnitch getEndpointSnitch()
    {
        return snitch;
    }
    
    public static void setEndpointSnitch(IEndpointSnitch eps)
    {
        snitch = eps;
    }

	public static String getClusterName() {
		return conf.getCluster_name();
	}

	public static IPartitioner getPartitioner() throws ConfigurationException {
		return partitioner;
	}
}
