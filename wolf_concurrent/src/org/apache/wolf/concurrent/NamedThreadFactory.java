package org.apache.wolf.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

	private final String id;
	private final int priority;
	protected final AtomicInteger n=new AtomicInteger(1);
	
	public NamedThreadFactory(String id){
		this(id,Thread.NORM_PRIORITY);
	}
	
	public NamedThreadFactory(String id, int priority) {
		this.id=id;
		this.priority=priority;
	}

	public Thread newThread(Runnable r) {
		String name=id+":" +n.getAndIncrement();
		Thread thread=new Thread(r,name);
		thread.setPriority(priority);
		return thread;
	}

}
