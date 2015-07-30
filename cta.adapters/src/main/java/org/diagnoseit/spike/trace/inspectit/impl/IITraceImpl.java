package org.diagnoseit.spike.trace.inspectit.impl;

import info.novatec.inspectit.cmr.model.PlatformIdent;
import info.novatec.inspectit.communication.data.InvocationSequenceData;

import java.util.Iterator;

import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.SubTrace;
import org.diagnoseit.spike.trace.Trace;

public class IITraceImpl implements Trace {

	private SubTrace root;
	
	
	
	public IITraceImpl(InvocationSequenceData root, PlatformIdent pIdent) {
		super();
		this.root = new IISubTraceImpl(this, root, pIdent) ;
	}

	@Override
	public Iterator<Callable> iterator() {
		return root.iterator();
	}

	@Override
	public SubTrace getRoot() {
		return root;
	}

	@Override
	public long getLogicalTraceId() {
		return root.getId();
	}
	
	@Override
	public String toString() {
		return root.toString();
	}

	@Override
	public long maxDepth() {
		return root.maxDepth();
	}

	@Override
	public long size() {
		return root.size();
	}

	@Override
	public Iterator<SubTrace> subTraceIterator() {
		return new IISubTraceIterator(root);
	}

}
