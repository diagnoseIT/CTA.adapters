/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.util.*;
import java.util.stream.Collectors;

import kieker.tools.traceAnalysis.systemModel.Execution;
import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.callables.MethodInvocation;
import rocks.cta.api.utils.StringUtils;

/**
 * @author Okanovic
 *
 */
public class MethodInvocationImpl extends AbstractNestingCallableImpl implements MethodInvocation {
	private static final long serialVersionUID = -8621678119497714613L;

	private static final String PACKAGE_DELIMITER = ".";

	private static final String CONSTRUCTOR_PATTERN = "<init>";

	private String methodName;
	private String packageName;
	private List<String> parameterTypes;
	private Map<Integer, String> parameterValues;
	private String returnType;

	private long exclusiveCPUTime;

	private long cpuTime;

	private String className;

	protected MethodInvocationImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace, long responseTime, long exclusiveTime) {
		super(parent, entryTime, labelIds, additionInfos, containingSubTrace, responseTime, exclusiveTime);
	}

	// see about exclusive time
	public MethodInvocationImpl(Execution receivingExecution, AbstractNestingCallableImpl parent, List<Integer> labelIds,
			List<AdditionalInformation> additionInfos, SubTrace containingSubTrace) {
		super(parent, receivingExecution.getTin(), labelIds, additionInfos, containingSubTrace, receivingExecution.getTout() - receivingExecution.getTin(), -1);
		this.entryTime = receivingExecution.getTin();
		this.responseTime = receivingExecution.getTout() - this.entryTime;
		// TODO fix
		this.cpuTime = -1;

		this.className = receivingExecution.getOperation().getComponentType().getTypeName();
		this.methodName = receivingExecution.getOperation().getSignature().getName();
		this.packageName = receivingExecution.getOperation().getComponentType().getPackageName();
		if (this.parameterTypes == null) {
			this.parameterTypes = new ArrayList<String>();
		}
		this.parameterTypes.addAll(Arrays.asList(receivingExecution.getOperation().getSignature().getParamTypeList()));
		this.parameterValues = Collections.EMPTY_MAP;
		this.returnType = receivingExecution.getOperation().getSignature().getReturnType();
	}

	@Override
	public Optional<Long> getCPUTime() {
		return Optional.ofNullable(cpuTime);
	}

	@Override
	public Optional<String> getClassName() {
		return Optional.ofNullable(className);
	}

	@Override
	public Optional<Long> getExclusiveCPUTime() {
		if (exclusiveCPUTime < 0) {
			exclusiveCPUTime = cpuTime;

			for (MethodInvocation child : getCallees(MethodInvocation.class)) {
				exclusiveCPUTime -= child.getCPUTime().orElse((long) 0);
			}
		}

		return Optional.ofNullable(exclusiveCPUTime);
	}

	@Override
	public Optional<String> getMethodName() {
		return Optional.ofNullable(methodName);
	}

	@Override
	public Optional<String> getPackageName() {
		return Optional.ofNullable(packageName);
	}

	@Override
	public Optional<List<String>> getParameterTypes() {
		return Optional.ofNullable(parameterTypes);
	}

	@Override
	public Optional<Map<Integer, String>> getParameterValues() {
		return Optional.ofNullable(parameterValues);
	}

	@Override
	public Optional<String> getReturnType() {
		return Optional.ofNullable(returnType);
	}

	@Override
	public String getSignature() {
		return getPackageName() + PACKAGE_DELIMITER + getClassName() + PACKAGE_DELIMITER + getMethodName() + "(" + parameterTypes.stream().collect(Collectors.joining(",")) + ")";
	}

	@Override
	public Optional<Boolean> isConstructor() {
		return methodName != null ? Optional.of(methodName.equalsIgnoreCase(CONSTRUCTOR_PATTERN)) : Optional.empty();
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}
}