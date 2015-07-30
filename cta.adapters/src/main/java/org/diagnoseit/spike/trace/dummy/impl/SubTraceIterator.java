package org.diagnoseit.spike.trace.dummy.impl;

import java.util.Iterator;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.Callable;

public class SubTraceIterator implements Iterator<Callable> {

	private Iterator<MonitoringRecord> tIterator;
	private MonitoringRecord next;
	private String platformId;
	private TraceImpl trace;

	public SubTraceIterator(String platformId, TraceImpl trace) {
		super();
		this.tIterator = trace.monitoringRecords.iterator();
		this.platformId = platformId;
		this.trace = trace;
		moveInternalIterator();
		
	}

	public boolean hasNext() {

		return next != null;
	}

	public Callable next() {
		Callable nextCallable = null;
		if(next.getOutCorrelationHash() == null){
			nextCallable = new CallableImpl(trace, platformId, next);
		}else{
			nextCallable = new TraceInvocationImpl(trace, platformId, next);
		}
		moveInternalIterator();
		return nextCallable;
	}

	private void moveInternalIterator() {
		do {
			if(!tIterator.hasNext()){
				next = null;
				break;
			}
			next = tIterator.next();
		} while (next.getPlatformID() != platformId);
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}

}
