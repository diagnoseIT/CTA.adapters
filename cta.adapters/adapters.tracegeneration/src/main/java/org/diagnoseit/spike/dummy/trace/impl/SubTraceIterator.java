package org.diagnoseit.spike.dummy.trace.impl;

import java.util.Iterator;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.TreeIterator;

public class SubTraceIterator implements TreeIterator<SubTrace> {

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
			
			
		}while(callable != null && !(callable.isSubTraceInvocation()));
		
		if(callable != null && (callable.isSubTraceInvocation())){
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

	@Override
	public int currentDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
