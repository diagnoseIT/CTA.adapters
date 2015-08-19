package org.diagnoseit.spike.kieker.trace.impl;

import java.util.Iterator;

import org.diagnoseit.spike.shared.trace.SubTrace;

public class SubTraceIterator implements Iterator<SubTrace>{
	private SubTrace currentTrace;
	private boolean called = false;

	public SubTraceIterator(SubTrace currentTrace) {
		super();
		this.currentTrace = currentTrace;
	}

	@Override
	public boolean hasNext() {
		return !called;
	}

	@Override
	public SubTrace next() {
		if(called){
			called = true;
			return null;
		}else{
			called = true;
			return currentTrace;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
}
