package org.diagnoseit.spike.monitoring.datamocking;

import java.util.Random;

public class DataGenerator implements Runnable {
	private static final long AVG_INTER_DATA_TIME = 50; // in ms
	private static final double VARIANCE = 0.5; // in % from avg
	private static final Random rand = new Random(System.currentTimeMillis());
	private static DataGenerator instance;

	public static DataGenerator getInstance() {
		if (instance == null) {
			instance = new DataGenerator();
		}
		return instance;
	}

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
		while (run) {
			double pCent = rand.nextDouble() * 2.0 * VARIANCE - VARIANCE;

			long sleepTime = AVG_INTER_DATA_TIME
					+ (long) (pCent * (double) AVG_INTER_DATA_TIME);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// ignore exception
				e.printStackTrace();
			}
			createNewMonitoringRecord();

		}
	}

	private void createNewMonitoringRecord() {

	}

}
