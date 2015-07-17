package org.diagnoseit.spike.monitoring.datamocking;

public class DataGenerator implements Runnable {
	
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

		}
	}

}
