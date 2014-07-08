package org.apache.wolf.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JMXEnabledThreadPoolExecutor extends DebuggableThreadPoolExecutor {

	public JMXEnabledThreadPoolExecutor(int corePoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue,
			NamedThreadFactory threadFactory, String jmxPath) {
		super(corePoolSize,corePoolSize,keepAliveTime,unit,workQueue,threadFactory,jmxPath);
	}

	public JMXEnabledThreadPoolExecutor(String threadPoolName) {
		this(1,Integer.MAX_VALUE,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),new NamedThreadFactory(threadPoolName),"internal");
	}

	public JMXEnabledThreadPoolExecutor(Stage stage) {
		this(stage.getJmxName(),stage.getJmxType());
	}

	public JMXEnabledThreadPoolExecutor(String jmxName, String jmxPath) {
		this(1,Integer.MAX_VALUE,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),new NamedThreadFactory(jmxName),jmxPath);
	}

}
