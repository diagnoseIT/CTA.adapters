package org.diagnoseit.spike.trace.impl;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.OperationInvocation;
import org.diagnoseit.spike.trace.SubTrace;

public abstract class InvocationRecord implements Comparable<InvocationRecord>,
		Callable {

	private MonitoringRecord record;
	protected SubTraceImpl trace;

	public InvocationRecord(MonitoringRecord record) {
		this.record = record;
	}

	public int getIndex() {
		return record.getIndex();
	}

	public int getStackDepth() {
		return record.getStackDepth();
	}

	public String getPlatformID() {
		return record.getPlatformID();
	}

	public long getExecutionTime() {
		return record.getDuration();
	}

	public long getEntryTime() {
		return record.getStartTime();
	}

	public long getExitTime() {
		return record.getStartTime() + record.getDuration();
	}

	public OperationInvocation getParent() {
		return trace.getParentOf(this);
	}

	public SubTrace getContainingTrace() {
		return getTrace();
	}

	/**
	 * @return the trace
	 */
	protected SubTraceImpl getTrace() {
		return trace;
	}

	/**
	 * @param trace
	 *            the trace to set
	 */
	protected void setTrace(SubTraceImpl trace) {
		this.trace = trace;
	}

	public int compareTo(InvocationRecord o) {
		return this.getIndex() - o.getIndex();
	}

}
