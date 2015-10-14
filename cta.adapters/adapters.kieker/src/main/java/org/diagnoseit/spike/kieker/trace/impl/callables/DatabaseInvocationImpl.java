/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.callables.MethodInvocation;

/**
 * @author Okanovic
 *
 */
public class DatabaseInvocationImpl extends AbstractNestingCallableImpl implements MethodInvocation {
	public DatabaseInvocationImpl() {
		this(null, 0, null, null, null, 0, 0);
		// TODO only temporary, must be removed later
	}

	public DatabaseInvocationImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace, long responseTime, long exclusiveTime) {
		super(parent, entryTime, labelIds, additionInfos, containingSubTrace, responseTime, exclusiveTime);
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException();
	}

	private static final long serialVersionUID = -5261233572253671375L;

	@Override
	public Optional<Long> getCPUTime() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<String> getClassName() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Long> getExclusiveCPUTime() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<String> getMethodName() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<String> getPackageName() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<List<String>> getParameterTypes() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Map<Integer, String>> getParameterValues() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<String> getReturnType() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public String getSignature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Boolean> isConstructor() {
		// TODO Auto-generated method stub
		return Optional.of(false);
	}

}
