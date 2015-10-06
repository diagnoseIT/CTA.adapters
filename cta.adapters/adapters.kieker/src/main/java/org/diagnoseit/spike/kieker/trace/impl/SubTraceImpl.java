/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.TreeIterator;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.NestingCallable;
import rocks.cta.api.utils.CallableIterator;
import rocks.cta.api.utils.StringUtils;

/**
 * @author Okanovic
 *
 */
public class SubTraceImpl implements SubTrace, Serializable {
	private static final long serialVersionUID = 8520603674813053640L;

	// TODO check
	Callable root;
	Location location;
	Trace containingTrace;
	SubTrace parent;
	List<SubTrace> subTraces = new ArrayList<SubTrace>();
	long subTraceId;
	transient int maxDepth = -1;
	transient int size = -1;

	public SubTraceImpl(Callable root, Location location, Trace containingTrace, SubTrace parent, List<SubTrace> subTraces, long subTraceId) {
		super();
		this.root = root;
		this.location = location;
		this.containingTrace = containingTrace;
		this.parent = parent;
		if (subTraces != null)
			this.subTraces.addAll(subTraces);
		this.subTraceId = subTraceId;
	}

	public SubTraceImpl(TraceImpl traceImpl, Callable root, AbstractMessage abstractMessage) {
		this(root, new LocationImpl(abstractMessage.getReceivingExecution()), traceImpl, null, null, traceImpl.getTraceId());
	}

	public SubTraceImpl(TraceImpl traceImpl, Callable root, SubTrace parent, AbstractMessage abstractMessage) {
		this(root, new LocationImpl(abstractMessage.getReceivingExecution()), traceImpl, parent, null, traceImpl.getTraceId());
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public TreeIterator<Callable> iterator() {
		return new CallableIterator(getRoot());
	}

	@Override
	public Trace getContainingTrace() {
		return containingTrace;
	}

	@Override
	public long getId() {
		return subTraceId;
	}

	@Override
	public SubTrace getParent() {
		return parent;
	}

	public Callable getRoot() {
		return root;
	}

	@Override
	public List<SubTrace> getSubTraces() {
		return subTraces;
	}

	@Override
	public long getExclusiveTime() {
		long exclTime = getResponseTime();
		for (SubTrace child : getSubTraces()) {
			exclTime -= child.getResponseTime();
		}
		return exclTime;
	}

	@Override
	public long getResponseTime() {
		if (root instanceof NestingCallable) {
			return ((NestingCallable) root).getResponseTime();
		} else {
			return 0;
		}
	}

	@Override
	public int maxDepth() {
		if (getRoot() instanceof NestingCallable) {
			maxDepth = maxDepth((NestingCallable) getRoot());
		} else {
			maxDepth = 0;
		}

		return maxDepth;
	}

	private int maxDepth(NestingCallable callable) {
		if (callable.getCallees().isEmpty()) {
			return 0;
		} else {
			int maxDepth = -1;
			for (NestingCallable child : callable.getCallees(NestingCallable.class)) {
				int depth = maxDepth(child);
				if (depth > maxDepth) {
					maxDepth = depth;
				}
			}
			return maxDepth + 1;
		}
	}

	@Override
	public int size() {
		int count = 0;
		for (@SuppressWarnings("unused")
		Callable cbl : this) {
			count++;
		}
		size = count;

		return size;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (subTraceId ^ (subTraceId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SubTraceImpl other = (SubTraceImpl) obj;
		if (subTraceId != other.subTraceId) {
			return false;
		}
		return true;
	}

	// for coming back from subtrace
	transient Callable lastCallable;

	public Callable getLastCallable() {
		return lastCallable;
	}

	public void setLastCallable(Callable lastCallable) {
		this.lastCallable = lastCallable;
	}

	public void setRoot(Callable root) {
		this.root = root;
	}
}