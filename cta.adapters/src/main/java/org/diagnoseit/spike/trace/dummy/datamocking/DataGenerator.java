package org.diagnoseit.spike.trace.dummy.datamocking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataGenerator implements Runnable {
	private static final int MAX_JOB_QUEUE = 10;
	private static final int CORE_POOL_SIZE = 1;
	private static final int MAX_POOL_SIZE = 1;
	private static final int GENERATOR_MEMORY_SIZE = 5;
	private static final double PROB_NEW_TRACE = 0.3;
	public static final boolean ENABLE_N_PLUS_ONE = true;
	
	private static DataGenerator instance;

	
	
	public static DataGenerator getInstance() {
		if (instance == null) {
			instance = new DataGenerator();
		}
		return instance;
	}
	
	protected List<DataGenerationWorker> generatorMemory = new ArrayList<DataGenerationWorker>();
	private ExecutorService threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,1,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
	private final Random rand = new Random(System.currentTimeMillis());
	
	private boolean run = false;

	

	private DataGenerator() {
		for(int i = 0; i < GENERATOR_MEMORY_SIZE;i++){
			generatorMemory.add(new DataGenerationWorker(true));
		}
		
	}

	public void start() {
		run = true;
		new Thread(this).start();
	}

	public void stop() {
		run = false;
	}

	public void run() {
		while(run){
			if(DataGenerationWorker.numActive() < MAX_JOB_QUEUE){
				DataGenerationWorker generatorWorker = null;
				if(rand.nextDouble() < PROB_NEW_TRACE || generatorMemory.isEmpty()){
					generatorWorker = new DataGenerationWorker(false);
				}else{
					int ix = rand.nextInt(generatorMemory.size());
					generatorWorker = generatorMemory.remove(ix);
					
				}
				threadPool.execute(generatorWorker);
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
