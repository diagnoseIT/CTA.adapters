package org.diagnoseit.spike.trace.inspectit.impl;

import java.util.List;

import org.diagnoseit.spike.trace.AdditionalInformation;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.SubTrace;
import org.diagnoseit.spike.trace.TraceInvocation;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IITraceInvocationImpl implements TraceInvocation {

	@Override
	public List<Callable> getCallees() {
		throw new NotImplementedException();
	}

	@Override
	public Callable getParent() {
		throw new NotImplementedException();
	}

	@Override
	public SubTrace getContainingTrace() {
		throw new NotImplementedException();
	}

	@Override
	public long getExecutionTime() {
		throw new NotImplementedException();
	}

	@Override
	public long getEntryTime() {
		throw new NotImplementedException();
	}

	@Override
	public long getExitTime() {
		throw new NotImplementedException();
	}

	@Override
	public String getSignature() {
		throw new NotImplementedException();
	}

	@Override
	public String getSimpleName() {
		throw new NotImplementedException();
	}

	@Override
	public String getFullName() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isConstructor() {
		throw new NotImplementedException();
	}

	@Override
	public List<String> getLables() {
		throw new NotImplementedException();
	}

	@Override
	public boolean hasLabel(String label) {
		throw new NotImplementedException();
	}

	@Override
	public List<AdditionalInformation> getAdditionalInformation() {
		throw new NotImplementedException();
	}

	@Override
	public <T> List<T> getAdditionalInformation(Class<T> type) {
		throw new NotImplementedException();
	}

	@Override
	public SubTrace getTargetTrace() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isSync() {
		throw new NotImplementedException();
	}

	@Override
	public long getPosition() {
		throw new NotImplementedException();
	}

	@Override
	public long getDepth() {
		throw new NotImplementedException();
	}

}
