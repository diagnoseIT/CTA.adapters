package org.diagnoseit.spike.trace.inspectit.impl;

import java.util.Iterator;
import java.util.Stack;

import org.diagnoseit.spike.trace.Callable;

public class IICallableIterator implements Iterator<Callable> {

	private Stack<Integer> indexStack = new Stack<Integer>();
	private Callable nextCallable;
	private int currentChildIx = 0;

	public IICallableIterator(Callable root) {
		nextCallable = root;
	}

	@Override
	public boolean hasNext() {
		return nextCallable != null;
	}

	@Override
	public Callable next() {
		Callable thisNext = nextCallable;
		if (nextCallable != null) {
			while ((nextCallable.getCallees() == null
					|| nextCallable.getCallees().isEmpty() || currentChildIx >= nextCallable
					.getCallees().size())
					&& nextCallable.getParent() != null) {
				nextCallable = (IICallableImpl) nextCallable.getParent();
				currentChildIx = indexStack.pop();
			}
			if (currentChildIx < nextCallable.getCallees().size()) {
				// nextCallable : next child
				nextCallable = nextCallable
						.getCallees().get(currentChildIx);
				currentChildIx++;
				indexStack.push(currentChildIx);
			} else {
				nextCallable = null;
			}
		}
		return thisNext;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}

}
