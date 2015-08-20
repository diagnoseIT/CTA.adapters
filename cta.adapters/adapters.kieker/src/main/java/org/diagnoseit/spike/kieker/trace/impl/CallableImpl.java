/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import kieker.tools.traceAnalysis.systemModel.Operation;
import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.Callable;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.utils.StringUtils;

/**
 * @author Okanovic
 *
 */
public class CallableImpl implements Callable {
	private static final String PACKAGE_DELIMITER = ".";
	Callable parent;
	SubTrace containingSubTrace;
	SubTrace targetTrace;
	List<Callable> callees = new ArrayList<Callable>();
	long executionTime;
	long entryTime;
	long exitTime;
	long responseTime;

	Operation operation;
	long cpuTime;
	boolean constructor;

	private int position;
	private int depth;

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

	private void parseMessageData(AbstractMessage abstractMessage) {
		this.entryTime = abstractMessage.getReceivingExecution().getTin();
		this.exitTime = abstractMessage.getReceivingExecution().getTout();
		this.responseTime = this.exitTime - this.entryTime;
		// TODO fix
		this.executionTime = this.responseTime;
		// TODO fix
		this.cpuTime = this.responseTime;

		this.position = abstractMessage.getReceivingExecution().getEoi();
		this.depth = abstractMessage.getReceivingExecution().getEss();

		operation = abstractMessage.getReceivingExecution().getOperation();

	}

	public CallableImpl(Callable lastCallable, AbstractMessage abstractMessage) {
		// TODO Auto-generated constructor stub
		this.parent = lastCallable;
		this.containingSubTrace = lastCallable.getContainingSubTrace();

		parseMessageData(abstractMessage);

		this.parent.getCallees().add(this);
	}

	public CallableImpl(SubTraceImpl subtrace, AbstractMessage abstractMessage) {
		// TODO Auto-generated constructor stub
		this.parent = null;
		this.containingSubTrace = subtrace;

		parseMessageData(abstractMessage);
	}

	@Override
	public List<Callable> getCallees() {
		return callees;
	}

	@Override
	public Callable getParent() {
		return parent;
	}

	@Override
	public SubTrace getContainingSubTrace() {
		return containingSubTrace;
	}

	@Override
	public long getExecutionTime() {
		return executionTime;
	}

	@Override
	public long getEntryTime() {
		return entryTime;
	}

	@Override
	public long getExitTime() {
		return exitTime;
	}

	@Override
	public String getSignature() {
		String params = "";
		for (String param : operation.getSignature().getParamTypeList()) {
			params += param + ", ";
		}
		if (params.length() > 2)
			params = params.substring(0, params.length() - 2);

		return operation.getComponentType().getFullQualifiedName() + PACKAGE_DELIMITER + operation.getSignature().getName() + params;
	}

	@Override
	public boolean isConstructor() {
		return constructor;
	}

	@Override
	public List<String> getLabels() {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	@Override
	public boolean hasLabel(String label) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AdditionalInformation> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	@Override
	public <T extends AdditionalInformation> Collection<T> getAdditionalInformation(Class<T> type) {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	public void setTargetTrace(SubTrace targetTrace) {
		this.targetTrace = targetTrace;
	}

	@Override
	public long getCPUTime() {
		return cpuTime;
	}

	@Override
	public String getClassName() {
		String className = operation.getComponentType().getFullQualifiedName();
		String pkgName = operation.getComponentType().getPackageName();
		if (pkgName != null && !pkgName.isEmpty()) {
			className = className.substring(pkgName.length() + 1);
		}

		return className;
	}

	@Override
	public SubTrace getInvokedSubTrace() {
		return targetTrace;
	}

	@Override
	public String getMethodName() {
		return operation.getSignature().getName();
	}

	@Override
	public String getPackageName() {
		return operation.getComponentType().getPackageName();
	}

	@Override
	public List<String> getParameterTypes() {
		return Arrays.asList(operation.getSignature().getParamTypeList());
	}

	@Override
	public long getResponseTime() {
		return responseTime;
	}

	@Override
	public String getReturnType() {
		return operation.getSignature().getReturnType();
	}

	@Override
	public boolean isAsyncInvocation() {
		return false;
	}

	@Override
	public boolean isSubTraceInvocation() {
		return targetTrace != null;
	}

	protected int getPosition() {
		return position;
	}

	protected int getDepth() {
		return depth;
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return -1;
	}
}