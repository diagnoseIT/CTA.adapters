package org.diagnoseit.spike.trace.dummy.impl;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.SubTrace;
import org.diagnoseit.spike.trace.TraceInvocation;

public class TraceInvocationImpl extends CallableImpl implements TraceInvocation {

	public TraceInvocationImpl(TraceImpl trace, String platformId,
			MonitoringRecord internalRecord) {
		super(trace, platformId, internalRecord);
	}

	public SubTrace getTargetTrace() {
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (internalRecord.getOutCorrelationHash().equals(rec.getInCorrelationHash())) {
				return new SubTraceImpl(trace, rec.getPlatformID());
			}
		}
		return null;
		
	}

	public boolean isSync() {
		return true;
	}



}
