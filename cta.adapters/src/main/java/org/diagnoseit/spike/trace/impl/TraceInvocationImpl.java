package org.diagnoseit.spike.trace.impl;

import java.util.ArrayList;
import java.util.List;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.SubTrace;
import org.diagnoseit.spike.trace.TraceInvocation;

public class TraceInvocationImpl extends InvocationRecord implements
		TraceInvocation {

	private SubTraceImpl targetTrace;

	public TraceInvocationImpl(MonitoringRecord record, SubTraceImpl targetTrace) {
		super(record);
		this.targetTrace = targetTrace;
	}

	public List<Callable> getCallees() {
		List<Callable> result = new ArrayList<Callable>();
		result.add(targetTrace.getRoot());
		return result;
	}

	public SubTrace getTargetTrace() {
		return targetTrace;
	}

}
