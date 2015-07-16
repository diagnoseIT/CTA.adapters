package org.diagnoseit.spike.trace.impl;

import java.util.List;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.OperationInvocation;

public class MethodExecution extends InvocationRecord implements
		OperationInvocation {
	private String operationName;

	public MethodExecution(MonitoringRecord record) {
		super(record);
		setOperationName(record.getOperationName());
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public List<Callable> getCallees() {
		return trace.getChildrenOf(this);
	}

}
