package org.diagnoseit.spike.trace.dummy.impl;

import java.util.Iterator;
import java.util.Stack;

import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.TraceInvocation;

public class TraceIterator implements Iterator<Callable> {
	private Stack<Iterator<Callable>> iteratorStack;
	private Iterator<Callable> currentIterator;

	public TraceIterator(SubTraceImpl subTrace) {
		iteratorStack = new Stack<Iterator<Callable>>();
		currentIterator = subTrace.iterator();
	}

	public boolean hasNext() {
		try {
			while (currentIterator != null && !currentIterator.hasNext()) {
				currentIterator = iteratorStack.pop();
			}
		} catch (Exception e) {
			return false;
		}
		return currentIterator != null;

	}

	public CallableImpl next() {
		boolean hasNext = hasNext();
		if (hasNext) {
			CallableImpl nextRec = (CallableImpl) currentIterator.next();
			if (nextRec instanceof TraceInvocationImpl) {
				iteratorStack.push(currentIterator);
				currentIterator = ((TraceInvocation) nextRec)
						.getTargetTrace().iterator();
			}

			return nextRec;
		} else {
			return null;
		}

	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}

}
