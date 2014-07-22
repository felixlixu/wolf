package org.apache.wolf.service;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import javax.naming.ConfigurationException;
import org.apache.db.column.storge.ColumnFamilyStoreService;
import org.apache.db.struct.DBSchema;
import org.apache.db.struct.SystemTable;
import org.apache.log4j.PropertyConfigurator;
import org.apache.wolf.cache.service.CacheService;
import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.metadata.CFMetaData;
import org.apache.wolf.metadata.Schema;
import org.apache.wolf.utils.CLibrary;
import org.apache.wolf.utils.StaticField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;


public abstract class AbstractWolfServiceDaemon implements IWolfServiceDaemon {

	private static Logger log=LoggerFactory.getLogger(AbstractWolfServiceDaemon.class);
	protected int listenPort;
	protected InetAddress listenAddr;
	static final AtomicInteger exceptions=new AtomicInteger();
	
	public void activite() {
		String pidFile=System.getProperty("cassandra-pidfile");
		try{
			setup();
			if(pidFile!=null){
				new File(pidFile).deleteOnExit();
			}
			start();
		}catch(Throwable e){
			log.error("Exception encountered during stratup.",e);
			e.printStackTrace();
			System.out.println("Exception encountered during startup:"+e.getMessage());
			System.exit(3);
		}
	}

	private void start() {
		
	}

	private void setup() {
		log.info("JVM vendor/version: {}/{}", System.getProperty("java.vm.name"), System.getProperty("java.version") );
		log.info("Heap size: {}/{}", Runtime.getRuntime().totalMemory(), Runtime.getRuntime().maxMemory());
		log.info("Classpath: {}", System.getProperty("java.class.path"));
		CLibrary.tryMlockall();
		
		listenPort=DatabaseDescriptor.getRpcPort();
		listenAddr=DatabaseDescriptor.getRpcAddress();
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				exceptions.incrementAndGet();
				log.error("Fatal exception in thread"+t,e);
				for(Throwable e2=e;e2!=null;e2=e2.getCause()){
					if(e2 instanceof OutOfMemoryError){
						System.exit(100);
					}
				}
			}
		});
		
		Iterable<String> dirs=Iterables.concat(Arrays.asList(DatabaseDescriptor.getAllDataFileLocations()),
				Arrays.asList(new String[]{DatabaseDescriptor.getCommitLogLocation(),
						DatabaseDescriptor.getSavedCachesLocation()}));
		for(String dataDir:dirs){
			log.debug("Checking directory {}",dataDir);
			File dir=new File(dataDir);
			if(dir.exists())
				assert dir.isDirectory()&&dir.canRead()&&dir.canWrite()&&dir.canExecute()
				:String.format("Directory %s is not accessable.", dataDir);
		}
		
		if(CacheService.instance==null)
			throw new RuntimeException("Failed to initialize Cache Service");
		
		
		for(CFMetaData cfm:Schema.instances.getTableMetaData(StaticField.SYSTEMTABLE).values()){
			 ColumnFamilyStoreService.scrubDataDirectories(StaticField.SYSTEMTABLE, cfm.getCfName());
		}
		try{
			SystemTable.checkHealth();
		}catch (ConfigurationException e)
        {
            log.error("Fatal exception during initialization", e);
            System.exit(100);
        }
		DatabaseDescriptor.loadSchemas();
		StorageService.instance.initServer();
	}

	public static void initLog4j() {
		String path=Class.class.getClass().getResource("/").getPath();
		PropertyConfigurator.configure(path+"log4j.properties");
	}
}
