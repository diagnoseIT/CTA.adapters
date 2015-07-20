package org.diagnoseit.spike.monitoring.datamocking;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataGenerator implements Runnable {
	private static final int MAX_JOB_QUEUE = 10;
	private static final int CORE_POOL_SIZE = 2;
	private static final int MAX_POOL_SIZE = 5;
	private static DataGenerator instance;
	
	public static DataGenerator getInstance() {
		if (instance == null) {
			instance = new DataGenerator();
		}
		return instance;
	}
	
	private BlockingQueue<Runnable> workingQueue = new LinkedBlockingQueue<Runnable>();
	private ExecutorService threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,1,TimeUnit.SECONDS,workingQueue);

	
	private boolean run = false;

	

	private DataGenerator() {

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
				threadPool.execute(new DataGenerationWorker());
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
