package org.diagnoseit.spike.dummy.trace.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.diagnoseit.spike.dummy.trace.impl.MonitoringDataProcessing;
import org.diagnoseit.spike.shared.trace.Trace;

public class DataGenerationWorker implements Runnable {
	private static final long AVG_INTER_DATA_TIME = 30; // in ms
	private static final double VARIANCE = 2.0; // in % from avg
	private static final double PROB_NEW = 0.4; // in % from avg
	private static final double PROB_NEW_LOCATION = 0.1; // in % from avg
	private static final int MIN_DEPTH = 5; // in % from avg
	private static final String[] platformIDs = { "ApplicationServer1",
			"ApplicationServer2", "DBServer", "JMSServer" };
	private static final String[] containerIDs = { "A", "B", "C", "D" };
	private static AtomicInteger numActive = new AtomicInteger(0);
	private static final Random rand = new Random(System.currentTimeMillis());
	private Stack<MonitoringRecord> recordStack = new Stack<MonitoringRecord>();
	private Stack<Integer> indexStack = new Stack<Integer>();
	private Stack<Integer> depthStack = new Stack<Integer>();
	private List<MonitoringRecord> replayRecords = new ArrayList<MonitoringRecord>();

	private long traceId;
	private String platFormId;
	private int index = -1;
	private int depth = -1;
	private boolean executedOnce = false;
	private boolean memory = false;
private List<String> usedPlatformIds = new ArrayList<String>();
	public DataGenerationWorker(boolean memory) {
		this.memory = memory;
		traceId = rand.nextLong();
		platFormId = platformIDs[rand.nextInt(platformIDs.length)] + ":"
				+ containerIDs[rand.nextInt(containerIDs.length)];
		usedPlatformIds.add(platFormId);
	}

	public void run() {
		numActive.incrementAndGet();
		if (executedOnce) {
			replayTrace();

		} else {
			createNewTrace();
		}
		numActive.decrementAndGet();
		if (memory) {
			DataGenerator.getInstance().generatorMemory.add(this);
		}
	}

	private void replayTrace() {
		traceId = rand.nextLong();
		for (MonitoringRecord rec : replayRecords) {
			rec.setTraceId(traceId);
			MonitoringDataProcessing.getInstance().addRecord(rec);
			interRecordSleep();
		}
	}

	private void createNewTrace() {

		createNewMonitoringRecord();
		int depth = 1;
		int maxDepth = 0;
		;
		while (!recordStack.isEmpty()) {
			if (maxDepth < depth) {
				maxDepth = depth;
			}
			if (maxDepth < MIN_DEPTH || rand.nextDouble() < PROB_NEW) {
				depth++;
				createNewMonitoringRecord();
			} else {
				depth--;
				emitMonitoringRecord();
			}

			interRecordSleep();
		}
		executedOnce = true;
	}

	private void interRecordSleep() {
		double pCent = rand.nextDouble() * 2.0 * VARIANCE - VARIANCE;

		long sleepTime = Math.max(AVG_INTER_DATA_TIME
				+ (long) (pCent * (double) AVG_INTER_DATA_TIME), 1);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// ignore exception
			e.printStackTrace();
		}
	}

	private void createNewMonitoringRecord() {


		MonitoringRecord rec = new MonitoringRecord();
		
		rec.setOperationName("method" + (int) (rand.nextDouble() * 100.0)
				+ "()");
		rec.setStartTime(System.currentTimeMillis());
		
		
		if (rand.nextDouble() < PROB_NEW_LOCATION && !recordStack.isEmpty()) {
			
			do{
			platFormId = platformIDs[rand.nextInt(platformIDs.length)] + ":"
					+ containerIDs[rand.nextInt(containerIDs.length)];
			} while (usedPlatformIds.contains(platFormId));
			usedPlatformIds.add(platFormId);
			int corrHash = rand.nextInt();
			recordStack.peek().setOutCorrelationHash(corrHash);
			rec.setInCorrelationHash(corrHash);
			indexStack.push(index);
			depthStack.push(depth);
			index = 0;
			depth = 0;
		}else{
			depth++;
			index++;
		}
		rec.setIndex(index);
		rec.setTraceId(traceId);
		rec.setStackDepth(depth);
		rec.setPlatformID(platFormId);
		recordStack.push(rec);
		
	}

	private void emitMonitoringRecord() {

		MonitoringRecord rec = recordStack.pop();
		rec.setDuration(System.currentTimeMillis() - rec.getStartTime());
		MonitoringDataProcessing.getInstance().addRecord(rec);
		replayRecords.add(rec);
		platFormId = rec.getPlatformID();
		if(rec.getIndex() == 0 && !indexStack.isEmpty()){
			index = indexStack.pop();
			depth = depthStack.pop();
		}else{
			depth--;
		}
		
	}

	public static int numActive() {
		return numActive.get();
	}
	
	public long getTrace(){
		return traceId;
	}
}
