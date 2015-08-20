package org.diagnoseit.spike.dummy.trace.impl;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Stack;
import java.util.TreeSet;

import org.diagnoseit.spike.dummy.trace.generation.MonitoringRecord;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.TreeIterator;

public class TraceImpl implements Trace {

	protected NavigableSet<MonitoringRecord> monitoringRecords = new TreeSet<MonitoringRecord>();

	public void addMonitoringRecord(MonitoringRecord rec) {
		monitoringRecords.add(rec);
	}

	public TreeIterator<Callable> iterator() {
		return new TraceIterator((SubTraceImpl) getRoot());
	}

	public SubTrace getRoot() {
		for (MonitoringRecord rec : monitoringRecords) {
			if (rec.getIndex() == 0 && rec.getInCorrelationHash() == null) {
				return new SubTraceImpl(this, rec.getPlatformID());
			}
		}
		return null;
	}

	public long getLogicalTraceId() {
		return monitoringRecords.first().getTraceId();
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder("#######################\n");
		Stack<String> platformStack = new Stack<String>();
		String currentPlatform = null;
		Stack<Integer> depthStack = new Stack<Integer>();
		int indent = 0;
		for (Callable callable : this) {
			CallableImpl cImpl = (CallableImpl) callable;
			int depth = cImpl.internalRecord.getStackDepth();
			if (!platformStack.isEmpty() && platformStack.peek().equals(cImpl.internalRecord.getPlatformID())) {
				indent -= depthStack.pop();
				currentPlatform = platformStack.pop();
			}
			for (int i = 0; i < indent + depth; i++) {
				strBuilder.append("   ");
			}
			if (cImpl.isSubTraceInvocation()) {
				strBuilder.append("-->");
				platformStack.push(cImpl.internalRecord.getPlatformID());
				depthStack.push(depth + 1);
				indent += depth + 1;
			} else {
				strBuilder.append(cImpl.getSignature() + " - " + cImpl.getExecutionTime() + "ms   " + cImpl.getContainingSubTrace().getLocation().getHost() + ", "
						+ cImpl.getContainingSubTrace().getLocation().getRuntimeEnvironment());
			}
			strBuilder.append("\n");
			currentPlatform = cImpl.internalRecord.getPlatformID();
		}
		return strBuilder.toString();
	}

	

	@Override
	public int size() {
		return sumSizes(getRoot());
	}

	private int sumSizes(SubTrace subTrace) {
		int sum = 0;
		sum += subTrace.size();
		for (SubTrace child : subTrace.getSubTraces()) {
			sum += sumSizes(child);
		}
		return sum;
	}

	@Override
	public TreeIterator<SubTrace> subTraceIterator() {
		return new SubTraceIterator(this, iterator());
	}

}
