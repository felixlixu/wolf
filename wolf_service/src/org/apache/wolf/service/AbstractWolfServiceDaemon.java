package org.apache.wolf.service;

public abstract class AbstractWolfServiceDaemon implements IWolfServiceDaemon {

	public void activite() {
		setup();
	}

	private void setup() {
		StorageService.instance.initServer();
	}
}
