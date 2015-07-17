package org.diagnoseit.spike.trace.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.Location;
import org.diagnoseit.spike.trace.OperationInvocation;
import org.diagnoseit.spike.trace.SubTrace;

public class SubTraceImpl implements SubTrace, Iterable<InvocationRecord>{

	private NavigableSet<InvocationRecord> invocationRecords;

	private SubTraceImpl parent = null;
	private List<SubTrace> subTraces = null;
	private long traceId;
	private Location location;

	public SubTraceImpl(long id, Location location) {
		traceId = id;
		invocationRecords = new TreeSet<InvocationRecord>();
		this.location=location;
	}

	public OperationInvocation getRoot() {
		InvocationRecord first = invocationRecords.first();
		if (!(first instanceof OperationInvocation)) {
			throw new RuntimeException(
					"invocation record is not of type OperationInvocation!");
		}
		return (OperationInvocation) first;
	}

	public SubTrace getParent() {
		return parent;
	}

	public List<SubTrace> getSubTraces() {
		return subTraces;
	}

	public Location getLocation() {
		return location;
	}

	public long getId() {
		return traceId;
	}

	public int depth() {
		int max = Integer.MIN_VALUE;
		for (InvocationRecord rec : invocationRecords) {
			if (rec.getStackDepth() > max) {
				max = rec.getStackDepth();
			}
		}
		return max;
	}

	public int size() {
		return invocationRecords.size();
	}

	protected MethodExecution getParentOf(InvocationRecord mExec) {
		InvocationRecord tmpExec = mExec;
		while (tmpExec != null
				&& tmpExec.getStackDepth() != mExec.getStackDepth() - 1) {
			tmpExec = invocationRecords.lower(tmpExec);
		}
		if (!(tmpExec instanceof MethodExecution)) {
			throw new RuntimeException(
					"invocation record is not of type MethodExecution!");
		}
		return (MethodExecution) tmpExec;
	}

	protected List<Callable> getChildrenOf(MethodExecution mExec) {
		List<Callable> result = new ArrayList<Callable>();
		InvocationRecord tmpExec = invocationRecords.higher(mExec);
		while (tmpExec != null
				&& tmpExec.getStackDepth() > mExec.getStackDepth()) {
			result.add(tmpExec);
			tmpExec = invocationRecords.higher(tmpExec);
		}
		return result;
	}

	public void addInvocationRecord(InvocationRecord rec) {
		rec.setTrace(this);
		invocationRecords.add(rec);
	}

	protected void setParentTrace(SubTraceImpl parent) {
		this.parent = parent;
	}

	public void addSubTrace(SubTraceImpl subTrace) {
		if (subTraces == null) {
			subTraces = new ArrayList<SubTrace>();
		}
		subTraces.add(subTrace);
		subTrace.setParentTrace(this);
	}

	public Iterator<InvocationRecord> iterator() {
		return invocationRecords.iterator();
	}



}
