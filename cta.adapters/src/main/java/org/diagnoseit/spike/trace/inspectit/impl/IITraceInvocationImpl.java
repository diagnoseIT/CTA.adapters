package org.diagnoseit.spike.trace.inspectit.impl;

import java.util.List;

import org.diagnoseit.spike.trace.AdditionalInformation;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.SubTrace;
import org.diagnoseit.spike.trace.TraceInvocation;

public class IITraceInvocationImpl implements TraceInvocation {

	@Override
	public List<Callable> getCallees() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Callable getParent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SubTrace getContainingTrace() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getExecutionTime() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getEntryTime() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getExitTime() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSignature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSimpleName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFullName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isConstructor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getLables() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLabel(String label) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<AdditionalInformation> getAdditionalInformation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> List<T> getAdditionalInformation(Class<T> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SubTrace getTargetTrace() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSync() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getPosition() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getDepth() {
		throw new UnsupportedOperationException();
	}

}
