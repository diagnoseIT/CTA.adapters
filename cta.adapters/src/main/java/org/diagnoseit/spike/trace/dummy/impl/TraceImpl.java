package org.diagnoseit.spike.trace.dummy.impl;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Stack;
import java.util.TreeSet;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.SubTrace;
import org.diagnoseit.spike.trace.Trace;
import org.diagnoseit.spike.trace.TraceInvocation;

public class TraceImpl implements Trace {

	protected NavigableSet<MonitoringRecord> monitoringRecords = new TreeSet<MonitoringRecord>();

	public void addMonitoringRecord(MonitoringRecord rec) {
		monitoringRecords.add(rec);
	}

	public Iterator<Callable> iterator() {
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
			if (cImpl instanceof TraceInvocation) {
				strBuilder.append("-->");
				platformStack.push(cImpl.internalRecord.getPlatformID());
				depthStack.push(depth + 1);
				indent += depth + 1;
			} else {
				strBuilder.append(cImpl.getSignature() + " - " + cImpl.getExecutionTime() + "ms   " + cImpl.getContainingTrace().getLocation().getHost() + ", "
						+ cImpl.getContainingTrace().getLocation().getRuntimeEnvironment());
			}
			strBuilder.append("\n");
			currentPlatform = cImpl.internalRecord.getPlatformID();
		}
		return strBuilder.toString();
	}

	@Override
	public long maxDepth() {
		long max = -1;
		for (Callable callable : this) {
			if (callable.getDepth() > max) {
				max = callable.getDepth();
			}
		}
		return max;
	}

	@Override
	public long size() {
		return sumSizes(getRoot());
	}

	private long sumSizes(SubTrace subTrace) {
		long sum = 0;
		sum += subTrace.size();
		for (SubTrace child : subTrace.getSubTraces()) {
			sum += sumSizes(child);
		}
		return sum;
	}

}
