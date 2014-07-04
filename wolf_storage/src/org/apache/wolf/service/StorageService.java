package org.apache.wolf.service;

import org.apache.wolf.utils.FBUtilities;



public class StorageService {

	public static final StorageService instance = new StorageService();
	private static final int RING_DELAY = 10;

	public synchronized void initServer() {
		initServer(RING_DELAY);
	}

	public void initServer(int delay) {
		joinTokenRing(delay);
	}

	private void joinTokenRing(int delay) {
		MessageService.instance.listen(FBUtilities.getLocalAddress());
	}

}
