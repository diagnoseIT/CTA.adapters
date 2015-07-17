package org.diagnoseit.spike.trace.impl;

import java.util.Iterator;
import java.util.Stack;

public class TraceIterator implements Iterator<InvocationRecord> {

	private Stack<Iterator<InvocationRecord>> iteratorStack;
	private Iterator<InvocationRecord> currentIterator;

	public TraceIterator(SubTraceImpl subTrace) {
		iteratorStack = new Stack<Iterator<InvocationRecord>>();
		currentIterator = subTrace.iterator();
	}

	public boolean hasNext() {

		while (currentIterator != null && !currentIterator.hasNext()) {
			currentIterator = iteratorStack.pop();
		}

		return currentIterator != null;

	}

	public InvocationRecord next() {
		boolean hasNext = hasNext();
		if (hasNext) {
			InvocationRecord nextRec = currentIterator.next();
			if (nextRec instanceof TraceInvocationImpl) {
				iteratorStack.push(currentIterator);
				currentIterator = ((SubTraceImpl) ((TraceInvocationImpl) nextRec)
						.getTargetTrace()).iterator();
			}

			return nextRec;
		} else {
			return null;
		}

	}

}
