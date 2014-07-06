package org.apache.wolf.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class JMXEnabledThreadPoolExecutor extends DebuggableThreadPoolExecutor {

	public JMXEnabledThreadPoolExecutor(int corePoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue,
			NamedThreadFactory threadFactory, String jmxPath) {
		super(corePoolSize,corePoolSize,keepAliveTime,unit,workQueue,threadFactory,jmxPath);
	}

}
