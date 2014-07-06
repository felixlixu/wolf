package org.apache.wolf.concurrent;

import java.util.EnumMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.wolf.conf.DatabaseDescriptor;

public class StageManager {

	private static EnumMap<Stage,ThreadPoolExecutor> stages=new EnumMap<Stage,ThreadPoolExecutor>(Stage.class);
	public static final long KEEPLIVE=60;
	
	static{
		stages.put(Stage.MUTATION, multiThreadedConfigurableStage(Stage.MUTATION,DatabaseDescriptor.getConcurrentWriters()));
	}
	
	public static ExecutorService getStage(Stage stage) {
		return stages.get(stage);
	}

	private static ThreadPoolExecutor multiThreadedConfigurableStage(
			Stage stage, int numberThreads) {
		return new JMXConfigurableThreadPoolExecutor(numberThreads,
					KEEPLIVE,
					TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(),
					new NamedThreadFactory(stage.getJmxName()),
					stage.getJmxType()
				);
	}
  
}
