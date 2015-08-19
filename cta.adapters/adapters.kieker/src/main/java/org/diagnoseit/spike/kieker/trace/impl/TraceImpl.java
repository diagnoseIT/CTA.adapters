/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import kieker.tools.traceAnalysis.systemModel.MessageTrace;
import kieker.tools.traceAnalysis.systemModel.SynchronousCallMessage;

import org.diagnoseit.spike.shared.trace.Callable;
import org.diagnoseit.spike.shared.trace.Location;
import org.diagnoseit.spike.shared.trace.SubTrace;
import org.diagnoseit.spike.shared.trace.Trace;

/**
 * @author Okanovic
 *
 */
public class TraceImpl implements Trace {
	SubTrace root;

	long traceId;

	public TraceImpl(MessageTrace trace) {
		this.traceId = trace.getTraceId();
		List<AbstractMessage> messages = new ArrayList<AbstractMessage>();
		messages.addAll(trace.getSequenceAsVector());
		Location lastLocation = null;
		SubTrace lastSubTrace = null;
		Callable lastCallable = null;
		Callable newCallable = null;
		SubTrace root = new SubTraceImpl(this, messages.get(0));
		this.root = root;
		lastSubTrace = root;
		lastCallable = lastSubTrace.getRoot();
		lastLocation = root.getLocation();

		messages.remove(0);
		for (AbstractMessage abstractMessage : messages) {
			// call message
			if (abstractMessage instanceof SynchronousCallMessage) {
				// if the location is the same as previous, add callable
				newCallable = new CallableImpl(lastCallable, abstractMessage);
				if (lastLocation.equals(new LocationImpl(abstractMessage))) {
					// add callable to last callable, so we go deeper
					lastCallable.getCallees().add(newCallable);
				} else { // else add subtrace
					SubTrace newSubTrace = new SubTraceImpl(lastSubTrace, newCallable, abstractMessage);
					lastSubTrace.getSubTraces().add(newSubTrace);
					((CallableImpl)lastCallable).setTargetTrace(newSubTrace);
					lastLocation = newSubTrace.getLocation();
					lastSubTrace = newSubTrace;
				}
				lastCallable = newCallable;
			} else {
				// reply message
				lastCallable = lastCallable.getParent();
			}
		}
	}

	@Override
	public Iterator<Callable> iterator() {
		return root.iterator();
	}

	@Override
	public SubTrace getRoot() {
		return root;
	}

	@Override
	public long getLogicalTraceId() {
		return traceId;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	@Override
	public long maxDepth() {
		return root.maxDepth();
	}

	@Override
	public long size() {
		return root.size();
	}

	@Override
	public Iterator<SubTrace> subTraceIterator() {
		return new SubTraceIterator(root);
	}
}
