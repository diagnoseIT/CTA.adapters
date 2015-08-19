package org.diagnoseit.spike.dummy.trace.impl;

import java.util.Iterator;

import org.diagnoseit.spike.shared.trace.Callable;
import org.diagnoseit.spike.shared.trace.SubTrace;
import org.diagnoseit.spike.shared.trace.TraceInvocation;

public class SubTraceIterator implements Iterator<SubTrace> {

	private Iterator<Callable> cIterator;
	private TraceImpl traceImpl;
	private String platformId = null;

	public SubTraceIterator(TraceImpl traceImpl, Iterator<Callable> cIterator) {
		super();
		this.cIterator = cIterator;
		this.traceImpl = traceImpl;
		if (cIterator.hasNext()) {
			platformId = ((CallableImpl) cIterator.next()).platformId;
		}
	}

	@Override
	public boolean hasNext() {
		return platformId != null;
	}

	@Override
	public SubTrace next() {
		SubTrace result =  new SubTraceImpl(traceImpl, platformId);
		Callable callable = null;
		do{
			if(cIterator.hasNext()){
				callable = cIterator.next();
			}else{
				callable = null;
			}
			
			
		}while(callable != null && !(callable instanceof TraceInvocation));
		
		if(callable != null && (callable instanceof TraceInvocation)){
			platformId = ((CallableImpl) cIterator.next()).platformId;
		}else{
			platformId = null;
		}
		return result;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}

}
