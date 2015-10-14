/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import kieker.tools.traceAnalysis.systemModel.Execution;
import kieker.tools.traceAnalysis.systemModel.MessageTrace;
import kieker.tools.traceAnalysis.systemModel.SynchronousCallMessage;

import org.diagnoseit.spike.kieker.trace.impl.callables.AbstractNestingCallableImpl;
import org.diagnoseit.spike.kieker.trace.impl.callables.DatabaseInvocationImpl;
import org.diagnoseit.spike.kieker.trace.impl.callables.HttpRequestProcessingImpl;
import org.diagnoseit.spike.kieker.trace.impl.callables.MethodInvocationImpl;
import org.diagnoseit.spike.kieker.trace.impl.callables.RemoteInvocationImpl;
import org.diagnoseit.spike.kieker.trace.impl.util.CallableResolver;

import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.TreeIterator;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.RemoteInvocation;
import rocks.cta.api.utils.CallableIteratorOnTrace;
import rocks.cta.api.utils.StringUtils;
import rocks.cta.api.utils.SubTraceIterator;

/**
 * @author Okanovic
 *
 */
public class TraceImpl extends AbstractIdentifiableImpl implements Trace, Serializable {
	private static final long serialVersionUID = 81641878420161262L;

	public SubTrace rootSubTrace;
	private long traceId;
	private transient int size = -1;

	public TraceImpl(MessageTrace trace) {
		this.traceId = trace.getTraceId();
		List<AbstractMessage> messages = new ArrayList<AbstractMessage>();
		messages.addAll(trace.getSequenceAsVector());
		Location lastLocation = null;
		SubTrace lastSubTrace = null;
		Callable lastCallable = null;
		Callable newCallable = null;
		// create first callable which is either method invocation or HTTP
		// request
		Execution firstReceivingExecution = messages.get(0).getReceivingExecution();
		newCallable = generateCallable(firstReceivingExecution, null, null);
		SubTrace rootSubTrace = new SubTraceImpl(this, newCallable, messages.get(0));
		this.rootSubTrace = rootSubTrace;
		lastSubTrace = rootSubTrace;
		lastCallable = lastSubTrace.getRoot();
		((AbstractNestingCallableImpl)lastCallable).setContainingSubTrace((SubTraceImpl)lastSubTrace);
		lastLocation = rootSubTrace.getLocation();

		messages.remove(0);
		for (AbstractMessage abstractMessage : messages) {
			// call message
			if (abstractMessage instanceof SynchronousCallMessage) {
				// if the location is the same as previous, add callable
				Location newLocation = new LocationImpl(abstractMessage.getReceivingExecution());
				if (lastLocation.equals(newLocation)) {
					newCallable = generateCallable(abstractMessage.getReceivingExecution(), (AbstractNestingCallableImpl) lastCallable, lastSubTrace);
					((AbstractNestingCallableImpl) lastCallable).addCallee(newCallable);
				} else { // else add subtrace
					// first create remote invocation and add it to previous
					// subtrace
					((SubTraceImpl)lastSubTrace).setLastCallable(lastCallable);
					SubTrace newSubTrace = new SubTraceImpl(this, null, lastSubTrace, abstractMessage);
					RemoteInvocation ri = new RemoteInvocationImpl((AbstractNestingCallableImpl) lastCallable, abstractMessage.getTimestamp(), null, null,
							lastSubTrace, newSubTrace);
					((AbstractNestingCallableImpl)lastCallable).addCallee(ri);
					lastSubTrace.getSubTraces().add(newSubTrace);
					lastLocation = newLocation;
					lastSubTrace = newSubTrace;
					newCallable = generateCallable(abstractMessage.getReceivingExecution(), null, lastSubTrace);
					((SubTraceImpl)lastSubTrace).setRoot(newCallable);
				}
				lastCallable = newCallable;
				((AbstractNestingCallableImpl)lastCallable).setContainingSubTrace((SubTraceImpl)lastSubTrace);
			} else {
				// reply message
				if(lastCallable.getParent() != null)
					lastCallable = lastCallable.getParent();
				else if(lastSubTrace.getParent() != null) {
					lastSubTrace = lastSubTrace.getParent();
					lastCallable = ((SubTraceImpl)lastSubTrace).getLastCallable();
				}
			}
		}
	}

	private Callable generateCallable(Execution receivingExecution, AbstractNestingCallableImpl parent, SubTrace containingSubTrace) {
		Callable lastCallable = null;
		if (CallableResolver.isHttpCall(receivingExecution)) {
			lastCallable = new HttpRequestProcessingImpl();
		} else if (CallableResolver.isDbCall(receivingExecution)) {
			lastCallable = new DatabaseInvocationImpl();
		} else {
			lastCallable = new MethodInvocationImpl(receivingExecution, parent, null, null, containingSubTrace);
		}
		return lastCallable;
	}

	@Override
	public TreeIterator<Callable> iterator() {
		return new CallableIteratorOnTrace(getRoot());
	}

	@Override
	public SubTrace getRoot() {
		return rootSubTrace;
	}

	public void setRoot(SubTraceImpl root) {
		this.rootSubTrace = root;
	}

	@Override
	public TreeIterator<SubTrace> subTraceIterator() {
		return new SubTraceIterator(getRoot());
	}

	@Override
	public long getTraceId() {
		return traceId;
	}

	@Override
	public int size() {
		if (size < 0) {
			int count = 0;
			Iterator<SubTrace> iterator = this.subTraceIterator();
			while (iterator.hasNext()) {
				SubTrace sTrace = iterator.next();
				count += sTrace.size();
			}
			size = count;
		}

		return size;
	}

	@Override
	public long getExclusiveTime() {
		return getResponseTime();
	}

	@Override
	public long getResponseTime() {
		if (rootSubTrace == null) {
			return 0;
		} else {
			return rootSubTrace.getResponseTime();
		}
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}
}