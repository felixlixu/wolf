package org.apache.wolf.conf;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.naming.ConfigurationException;

import org.apache.wolf.dht.partition.IPartitioner;
import org.apache.wolf.locator.db.migration.Migration;
import org.apache.wolf.locator.snitch.DynamicEndpointSnitch;
import org.apache.wolf.locator.snitch.EndpointSnitchInfo;
import org.apache.wolf.locator.snitch.IEndpointSnitch;
import org.apache.wolf.metadata.CFMetaData;
import org.apache.wolf.metadata.KSMetaData;
import org.apache.wolf.metadata.Schema;
import org.apache.wolf.util.ConfFBUtilities;
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
	public static final String SNAPSHOT_SUBDIR_NAME = "snapshots";
	private static InetAddress rpcAddress;
	
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
			
			if(conf.getRpc_address()!=null){
				try{
					rpcAddress=InetAddress.getByName(conf.getRpc_address());
				}catch(UnknownHostException e){
					
				}
			}else{
				rpcAddress=ConfFBUtilities.getLocalAddress();
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
			KSMetaData systemMeta=KSMetaData.systemKeyspace();
			Schema.instances.load(CFMetaData.VersionCf);
			Schema.instances.addSystemTable(systemMeta);
			
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

	public static int getRpcPort() {
		return conf.getRpc_port();
	}

	public static InetAddress getRpcAddress() {
		return rpcAddress;
	}

	public static String[] getAllDataFileLocations() {
		return conf.getData_file_directories();
	}

 	public static String getCommitLogLocation() {
		return conf.getCommitlog_directory();
	}

	public static String getSavedCachesLocation() {
		return conf.getSaved_caches_directory();
	}

	public static void loadSchemas() {
		UUID uuid=Migration.getLastMigrationId();
		if(uuid==null){
			logger.info("Couldn't detect and schema definitions in local storage.");
			boolean hasExistingTables=false;
			for(String dataDir:getAllDataFileLocations()){
                File dataPath = new File(dataDir);
                if (dataPath.exists() && dataPath.isDirectory())
                {
                    // see if there are other directories present.
                    int dirCount = dataPath.listFiles(new FileFilter()
                    {
                        public boolean accept(File pathname)
                        {
                            return pathname.isDirectory();
                        }
                    }).length;
                    if (dirCount > 0)
                        hasExistingTables = true;
                }
                if (hasExistingTables)
                {
                    break;
                }
			}
		}
	}

	public static String[] getAllDataFileLocationsForTable(String name) {
		String[] tableLocations=new String[conf.getData_file_directories().length];
		for(int i=0;i<conf.getData_file_directories().length;i++){
			tableLocations[i]=conf.getData_file_directories()[i]+File.separator+name;
		}
		return tableLocations;
	}

	public static int getThriftMaxMessageLength() {
		return conf.getThrift_max_messagelength();
	}

	public static int getThriftFramedTransportSize() {
		return conf.getThrift_framed_transport_size()*1024*1024;
	}
}
