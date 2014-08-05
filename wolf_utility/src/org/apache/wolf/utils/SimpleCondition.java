package org.apache.wolf.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class SimpleCondition implements Condition{

	boolean set;
	
	@Override
	public synchronized void await() throws InterruptedException {
		while(!set)
			wait();
	}
	
	public synchronized void reset(){
		set=false;
	}
	
    public synchronized boolean isSignaled()
    {
        return set;
    }

	@Override
	public synchronized boolean await(long time, TimeUnit unit) throws InterruptedException {
        assert unit == TimeUnit.DAYS || unit == TimeUnit.HOURS || unit == TimeUnit.MINUTES || unit == TimeUnit.SECONDS || unit == TimeUnit.MILLISECONDS;

        long end = System.currentTimeMillis() + unit.convert(time, TimeUnit.MILLISECONDS);
        while (!set && end > System.currentTimeMillis())
        {
            TimeUnit.MILLISECONDS.timedWait(this, end - System.currentTimeMillis());
        }
        return set;
	}

	@Override
	public long awaitNanos(long arg0) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void awaitUninterruptibly() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean awaitUntil(Date arg0) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void signal() {
		set=true;
		notify();
	}

	@Override
	public synchronized void signalAll() {
		set=true;
		notifyAll();
	}

}
