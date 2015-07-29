package org.diagnoseit.spike.trace.impl;

import java.util.Iterator;

import org.diagnoseit.spike.trace.Callable;

public class SubClassIteratorWrapper implements Iterator<Callable>{
private final Iterator<InvocationRecord> it;
	public SubClassIteratorWrapper(Iterator<InvocationRecord> it) {
		this.it=it;
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public Callable next() {
		return it.next();
	}
	


}
