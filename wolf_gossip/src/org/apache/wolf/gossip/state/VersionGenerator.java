package org.apache.wolf.gossip.state;

import java.util.concurrent.atomic.AtomicInteger;

public class VersionGenerator {

	private static AtomicInteger version_=new AtomicInteger(0);

	public static int getNextVersion() {
		return version_.getAndIncrement();
	}
}
