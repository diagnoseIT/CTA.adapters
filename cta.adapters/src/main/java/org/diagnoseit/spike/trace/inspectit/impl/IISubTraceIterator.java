package org.diagnoseit.spike.trace.inspectit.impl;

import java.util.Iterator;

import org.diagnoseit.spike.trace.SubTrace;

public class IISubTraceIterator implements Iterator<SubTrace>{

	private SubTrace currentTrace;
	private boolean called = false;
	public IISubTraceIterator(SubTrace currentTrace) {
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
