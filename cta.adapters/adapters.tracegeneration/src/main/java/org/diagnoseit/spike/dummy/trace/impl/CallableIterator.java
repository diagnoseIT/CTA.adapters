package org.diagnoseit.spike.dummy.trace.impl;

import java.util.Iterator;

import org.diagnoseit.spike.dummy.trace.generation.MonitoringRecord;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.TreeIterator;

public class CallableIterator implements TreeIterator<Callable> {

	private Iterator<MonitoringRecord> tIterator;
	private MonitoringRecord next;
	private String platformId;
	private TraceImpl trace;

	public CallableIterator(String platformId, TraceImpl trace) {
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
		nextCallable = new CallableImpl(trace, platformId, next);

		moveInternalIterator();
		return nextCallable;
	}

	private void moveInternalIterator() {
		do {
			if (!tIterator.hasNext()) {
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

	@Override
	public int currentDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
