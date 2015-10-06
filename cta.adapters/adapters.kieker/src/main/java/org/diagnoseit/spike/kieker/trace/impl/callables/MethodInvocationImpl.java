/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
	public long getCPUTime() {
		return cpuTime;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public long getExclusiveCPUTime() {
		if (exclusiveCPUTime < 0) {
			exclusiveCPUTime = cpuTime;

			for (MethodInvocation child : getCallees(MethodInvocation.class)) {
				exclusiveCPUTime -= child.getCPUTime();
			}
		}

		return exclusiveCPUTime;
	}

	@Override
	public String getMethodName() {
		return methodName;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public List<String> getParameterTypes() {
		return parameterTypes;
	}

	@Override
	public Map<Integer, String> getParameterValues() {
		return parameterValues;
	}

	@Override
	public String getReturnType() {
		return returnType;
	}

	@Override
	public String getSignature() {
		StringBuilder strBuilder = new StringBuilder();
		boolean first = true;
		for (String pType : getParameterTypes()) {
			if (first) {
				first = false;
			} else {
				strBuilder.append(",");
			}
			strBuilder.append(pType);

		}
		return getPackageName() + PACKAGE_DELIMITER + getClassName() + PACKAGE_DELIMITER + getMethodName() + "(" + strBuilder.toString() + ")";
	}

	@Override
	public boolean hasParameterValues() {
		return parameterValues != null;
	}

	@Override
	public boolean isConstructor() {
		return methodName.equalsIgnoreCase(CONSTRUCTOR_PATTERN);
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}
}