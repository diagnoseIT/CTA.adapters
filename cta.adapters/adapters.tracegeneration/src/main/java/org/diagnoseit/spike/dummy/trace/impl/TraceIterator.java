package org.diagnoseit.spike.dummy.trace.impl;

import java.util.Iterator;
import java.util.Stack;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.TreeIterator;

public class TraceIterator implements TreeIterator<Callable> {
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

			if (nextRec.isSubTraceInvocation()) {
				iteratorStack.push(currentIterator);
				currentIterator = nextRec.getInvokedSubTrace().iterator();
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

	@Override
	public int currentDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
