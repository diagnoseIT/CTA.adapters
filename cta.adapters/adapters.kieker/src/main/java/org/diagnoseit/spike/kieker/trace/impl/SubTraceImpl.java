/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.util.ArrayList;
import java.util.List;

import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import rocks.cta.api.core.Callable;
import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.TreeIterator;
import rocks.cta.api.utils.CallableIterator;
import rocks.cta.api.utils.StringUtils;

/**
 * @author Okanovic
 *
 */
public class SubTraceImpl implements SubTrace {
	private Callable root;
	private int maxDepth = -1;
	int childCount;
	private Trace trace;
	private SubTrace parent;
	private Location location;

	private List<Callable> callables = new ArrayList<Callable>();
	private List<SubTrace> subTraces = new ArrayList<SubTrace>();

	/**
	 * Constructor creates subtrace belonging to parent subtrace.
	 * 
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
	 * 
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
	public int maxDepth() {
		if (maxDepth < 0) {
			for (Callable callable : this) {
				CallableImpl callableImpl = (CallableImpl) callable;

				if (callableImpl.getDepth() > maxDepth) {
					maxDepth = callableImpl.getDepth();
				}
			}
		}

		return maxDepth;
	}

	@Override
	public int size() {
		return childCount;
	}

	@Override
	public TreeIterator<Callable> iterator() {
		return new CallableIterator(root);
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

	@Override
	public Trace getContainingTrace() {
		return trace;
	}

	public void addCallable(AbstractMessage abstractMessage) {
		// TODO Auto-generated method stub
		childCount++;
		callables.add(new CallableImpl(this, abstractMessage));
	}

	public Callable last() {
		return callables.get(callables.size() - 1);
	}
}