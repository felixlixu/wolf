package org.apache.wolf.conf;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.naming.ConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class DatabaseDescriptor {
	
	private static String DEFAULT_CONFIGURATION="wolf.yaml";
	private static Logger logger=LoggerFactory.getLogger(DatabaseDescriptor.class);
	private static InetAddress listenAddress;

	private static Config conf;
	
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
				TypeDescription seedDesc=new TypeDescription(SeedProviderDef.class);
				seedDesc.putMapPropertyType("parameters", String.class, String.class);
				constructor.addTypeDescription(seedDesc);
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

	public static InetAddress getListenAddress() {
		return listenAddress;
	}
}
