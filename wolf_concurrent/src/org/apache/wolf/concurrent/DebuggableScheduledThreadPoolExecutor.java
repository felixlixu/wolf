package org.apache.wolf.concurrent;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class DebuggableScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

	public DebuggableScheduledThreadPoolExecutor(String threadpoolName) {
		this(1,threadpoolName,Thread.NORM_PRIORITY);
	}

	public DebuggableScheduledThreadPoolExecutor(int corePoolSize, String threadpoolName,
			int priority) {
		super(corePoolSize,new NamedThreadFactory(threadpoolName,priority));
	}

}
