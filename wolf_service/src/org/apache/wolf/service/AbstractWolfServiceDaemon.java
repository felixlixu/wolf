package org.apache.wolf.service;

import java.io.File;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wolf.conf.DatabaseDescriptor;
import org.apache.wolf.utils.CLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractWolfServiceDaemon implements IWolfServiceDaemon {

	private Logger log=LoggerFactory.getLogger(AbstractWolfServiceDaemon.class);
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
		
		StorageService.instance.initServer();
	}

	public static void initLog4j() {
		
	}
}
