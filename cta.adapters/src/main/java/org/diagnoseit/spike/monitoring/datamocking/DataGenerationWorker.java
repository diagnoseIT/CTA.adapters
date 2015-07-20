package org.diagnoseit.spike.monitoring.datamocking;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.builder.MonitoringDataProcessing;

public class DataGenerationWorker implements Runnable{
	private static final long AVG_INTER_DATA_TIME = 30; // in ms
	private static final double VARIANCE = 2.0; // in % from avg
	private static final double PROB_NEW = 0.42; // in % from avg
	private static final int MIN_DEPTH = 5; // in % from avg
	private static final String[] platformIDs = {"ApplicationServer1","ApplicationServer2","DBServer","JMSServer"};
	private static final String[] containerIDs = {"A","B","C","D"};
	private static  AtomicInteger numActive = new AtomicInteger(0);
	private final Random rand = new Random(System.currentTimeMillis());
	private Stack<MonitoringRecord> recordStack = new Stack<MonitoringRecord>();
	
	private final long traceId; 
	private final String platFormId;
	private int index = 0; 
	
	public DataGenerationWorker() {
		traceId = rand.nextLong();
		platFormId = platformIDs[rand.nextInt(platformIDs.length)] + ":" + containerIDs[rand.nextInt(containerIDs.length)];
	}
	
	
	
	public void run() {
		numActive.incrementAndGet();
		createNewMonitoringRecord();
		int depth = 1;
		int maxDepth = 0;;
		while (!recordStack.isEmpty()) {
			if(maxDepth < depth){
				maxDepth = depth;
			}
			if(maxDepth < MIN_DEPTH || rand.nextDouble() < PROB_NEW){
				depth++;
				createNewMonitoringRecord();
			}else{
				depth--;
				emitMonitoringRecord();
			}
			
			
			
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
		numActive.decrementAndGet();
	}

	private void createNewMonitoringRecord() {
		index++;
		MonitoringRecord rec = new MonitoringRecord();
		rec.setIndex(index);
		rec.setOperationName("method"+(int)(rand.nextDouble()*100.0)+"()");
		rec.setStartTime(System.currentTimeMillis());
		rec.setTraceId(traceId);
		rec.setStackDepth(recordStack.size());
		rec.setPlatformID(platFormId);
		recordStack.push(rec);
	}
	
	private void emitMonitoringRecord() {
		MonitoringRecord rec = recordStack.pop();
		rec.setDuration(System.currentTimeMillis() - rec.getStartTime());
		MonitoringDataProcessing.getInstance().addRecord(rec);
	}
	
	public static int numActive(){
		return numActive.get();
	}
}
