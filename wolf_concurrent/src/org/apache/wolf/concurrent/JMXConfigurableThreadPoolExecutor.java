package org.apache.wolf.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JMXConfigurableThreadPoolExecutor extends JMXEnabledThreadPoolExecutor {

	public JMXConfigurableThreadPoolExecutor(int corePoolSize, long keepAliveTime,
			TimeUnit unit,
			BlockingQueue<Runnable> workQueue,
			NamedThreadFactory threadFactory, String jmxPath) {
		super(corePoolSize,keepAliveTime,unit,workQueue,threadFactory,jmxPath);
	}

}
