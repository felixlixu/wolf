package org.apache.wolf.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DebuggableThreadPoolExecutor extends ThreadPoolExecutor {

	public DebuggableThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public DebuggableThreadPoolExecutor(int corePoolSize, int maxPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue,
			NamedThreadFactory threadFactory, String jmxPath) {
		super(corePoolSize,maxPoolSize,keepAliveTime,unit,workQueue,threadFactory);
		super.prestartCoreThread();
		
	}

}
