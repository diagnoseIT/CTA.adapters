package org.diagnoseit.spike.trace.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.rules.processing.DiagnoseIT;
import org.diagnoseit.spike.trace.Trace;
import org.diagnoseit.spike.trace.impl.LocationImpl;
import org.diagnoseit.spike.trace.impl.MethodExecution;
import org.diagnoseit.spike.trace.impl.SubTraceImpl;
import org.diagnoseit.spike.trace.impl.TraceImpl;
import org.diagnoseit.spike.trace.impl.TraceInvocationImpl;

public class MonitoringDataProcessing implements Runnable {
	private static MonitoringDataProcessing instance;

	public static MonitoringDataProcessing getInstance() {
		if (instance == null) {
			instance = new MonitoringDataProcessing();
		}
		return instance;
	}

	private Map<Long, SubTraceImpl> traces;
	private Map<Integer, SubTraceImpl> hashTraceMapping;
	private BlockingQueue<MonitoringRecord> recordQueue;

	private volatile boolean run = false;

	private MonitoringDataProcessing() {
		recordQueue = new LinkedBlockingDeque<MonitoringRecord>();
		hashTraceMapping =  new HashMap<Integer, SubTraceImpl>();
		traces = new HashMap<Long, SubTraceImpl>();
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
			try {
				record = recordQueue.poll(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				record = null;
			}

			if (record != null) {
				processMonitoringRecord(record);
			}

		}

	}

	private void processMonitoringRecord(MonitoringRecord record) {
		long traceId = record.getTraceId();
		if (traces.get(traceId) == null) {
			traces.put(traceId, new SubTraceImpl(traceId, new LocationImpl(
					record.getPlatformID())));
		}
		SubTraceImpl traceImpl = traces.get(traceId);
		
		if (record.getStackDepth() == 0
				&& record.getInCorrelationHash() == null) {
			// last event of trace --> trace complete
			MethodExecution mExecution = new MethodExecution(record);
			traceImpl.addInvocationRecord(mExecution);
			traces.remove(traceImpl.getId());
			Trace trace = new TraceImpl(traceImpl);
			try {
				DiagnoseIT.getInstance().appendTrace(trace);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		} else if (record.getStackDepth() == 0
				&& record.getInCorrelationHash() != null) {
			// last event of sub-trace --> sub-trace complete
			MethodExecution mExecution = new MethodExecution(record);
			traceImpl.addInvocationRecord(mExecution);
			hashTraceMapping.put(record.getInCorrelationHash(), traceImpl);
		} else if (record.getOutCorrelationHash() != null) {
			// call to a sub-trace
			SubTraceImpl subtrace= hashTraceMapping.get(record.getOutCorrelationHash());
			
			TraceInvocationImpl traceInvocImpl = new TraceInvocationImpl(record, subtrace);
			traceImpl.addInvocationRecord(traceInvocImpl);
			traceImpl.addSubTrace(subtrace);
			hashTraceMapping.remove(record.getOutCorrelationHash());
			traces.remove(subtrace.getId());
			
		} else {
			MethodExecution mExecution = new MethodExecution(record);
			traceImpl.addInvocationRecord(mExecution);
		}
	}

}
