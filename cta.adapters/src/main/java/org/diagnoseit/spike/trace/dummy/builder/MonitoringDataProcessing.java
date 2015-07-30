package org.diagnoseit.spike.trace.dummy.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.rules.processing.DiagnoseIT;
import org.diagnoseit.spike.trace.dummy.impl.SubTraceImpl;
import org.diagnoseit.spike.trace.dummy.impl.TraceImpl;

public class MonitoringDataProcessing implements Runnable {
	private static MonitoringDataProcessing instance;

	public static MonitoringDataProcessing getInstance() {
		if (instance == null) {
			instance = new MonitoringDataProcessing();
		}
		return instance;
	}

	private Map<Long, TraceImpl> traces;
	private Map<Integer, SubTraceImpl> hashTraceMapping;
	private BlockingQueue<MonitoringRecord> recordQueue;

	private volatile boolean run = false;

	private MonitoringDataProcessing() {
		recordQueue = new LinkedBlockingDeque<MonitoringRecord>();
		hashTraceMapping = new HashMap<Integer, SubTraceImpl>();
		traces = new HashMap<Long, TraceImpl>();
	}

	public synchronized void  addRecord(MonitoringRecord rec) {
		try {
			recordQueue.put(rec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		while (run) {
			MonitoringRecord record = null;
			synchronized(this){
				record = recordQueue.poll();
			} 
			if(record == null){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (record != null) {
				processMonitoringRecord(record);
			}

		}

	}

	private void processMonitoringRecord(MonitoringRecord record) {
		long traceId = record.getTraceId();
		if (traces.get(traceId) == null) {
			traces.put(traceId, new TraceImpl());
		}
		TraceImpl traceImpl = traces.get(traceId);
		traceImpl.addMonitoringRecord(record);
		if (record.getStackDepth() == 0
				&& record.getInCorrelationHash() == null) {
			// last event of trace --> trace complete
			traces.remove(traceImpl.getLogicalTraceId());
			try {
				synchronized (this) {
//					System.out.println(traceImpl);
					DiagnoseIT.getInstance().appendTrace(traceImpl);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
