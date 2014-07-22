package org.apache.wolf.service;

public class WolfServiceDaemon extends AbstractWolfServiceDaemon {
	
	private static WolfServiceDaemon instance;
	static{
		AbstractWolfServiceDaemon.initLog4j();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance=new WolfServiceDaemon();
		instance.activite();
		System.out.println("start success");
	}

}
