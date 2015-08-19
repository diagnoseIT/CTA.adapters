/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kieker.tools.traceAnalysis.systemModel.AbstractMessage;

import org.diagnoseit.spike.shared.trace.Callable;
import org.diagnoseit.spike.shared.trace.Location;
import org.diagnoseit.spike.shared.trace.SubTrace;
import org.diagnoseit.spike.shared.trace.Trace;

/**
 * @author Okanovic
 *
 */
public class SubTraceImpl implements SubTrace {
	private static final int MAX_PRINTABLE_AMOUNT = 200;
	private Callable root;
	private long maxDepth = -1;
	long childCount;
	private Trace trace;
	private SubTrace parent;
	private Location location;

	private List<Callable> callables = new ArrayList<Callable>();
	private List<SubTrace> subTraces = new ArrayList<SubTrace>();

	/**
	 * Constructor creates subtrace belonging to parent subtrace.
	 * @param parent
	 * @param message
	 */
	public SubTraceImpl(SubTrace parent, Callable root, AbstractMessage message) {
		// TODO
		this.parent = parent;
		this.trace = parent.getContainingTrace();
		this.root = root;
		this.callables.add(root);
		this.location = new LocationImpl(message);
	}

	/**
	 * Constructor creates root subtrace, belonging to provided trace.
	 * @param trace
	 * @param message
	 */
	public SubTraceImpl(Trace trace, AbstractMessage message) {
		// TODO
		this.trace = trace;
		this.parent = null;
		this.root = new CallableImpl(this, message);
		this.callables.add(root);
		this.location = new LocationImpl(message);
	}

	@Override
	public Callable getRoot() {
		return root;
	}

	@Override
	public SubTrace getParent() {
		return parent;
	}

	@Override
	public List<SubTrace> getSubTraces() {
		return subTraces;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public long getId() {
		// TODO trace id + location name
		return trace.getLogicalTraceId();
	}

	@Override
	public long maxDepth() {
		if (maxDepth < 0) {
			for (Callable callable : this) {
				if (callable.getDepth() > maxDepth) {
					maxDepth = callable.getDepth();
				}
			}
		}

		return maxDepth;
	}

	@Override
	public long size() {
		return childCount;
	}

	@Override
	public Iterator<Callable> iterator() {
		return new CallableIterator(root);
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for (Callable callable : this) {
			if (callable.getPosition() == MAX_PRINTABLE_AMOUNT) {
				for (int i = 0; i < callable.getDepth(); i++) {
					strBuilder.append("   ");
				}
				strBuilder.append("...");
			} else if (callable.getPosition() > MAX_PRINTABLE_AMOUNT) {
				return strBuilder.toString();
			} else {
				for (int i = 0; i < callable.getDepth(); i++) {
					strBuilder.append("   ");
				}
				strBuilder.append(callable.toString());
				strBuilder.append("\n");
			}
		}

		return strBuilder.toString();
	}

	@Override
	public Trace getContainingTrace() {
		return trace;
	}

	public void addCallable(AbstractMessage abstractMessage) {
		// TODO Auto-generated method stub
		childCount ++;
		callables.add(new CallableImpl(this, abstractMessage));
	}

	public Callable last() {
		return callables.get(callables.size() - 1);
	}
}