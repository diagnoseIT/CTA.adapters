/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.util.ArrayList;
import java.util.List;

import kieker.common.util.signature.Signature;
import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import kieker.tools.traceAnalysis.systemModel.Operation;

import org.diagnoseit.spike.shared.trace.AdditionalInformation;
import org.diagnoseit.spike.shared.trace.Callable;
import org.diagnoseit.spike.shared.trace.SubTrace;

/**
 * @author Okanovic
 *
 */
public class CallableImpl implements Callable {
	Callable parent;
	SubTrace containingSubTrace;
	SubTrace targetTrace;
	List<Callable> callees = new ArrayList<Callable>();
	long executionTime;
	long entryTime;
	long exitTime;
	long responseTime;
	String signature;
	String simpleName;
	String fullName;
	long cpuTime;
	boolean constructor;
	String returnType;

	private long position;
	private long depth;

	@Override
	public String toString() {
//		return "CallableImpl [entryTime=" + entryTime + ", exitTime=" + exitTime + ", responseTime=" + responseTime
//				+ ", signature=" + signature + "]";
		return signature + " (" + responseTime / 1000000 + " ms)";
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

		Signature s = abstractMessage.getReceivingExecution().getOperation().getSignature();
		Operation o = abstractMessage.getReceivingExecution().getOperation();
		this.simpleName = s.getName();
		this.returnType = s.getReturnType();
		this.fullName = o.getComponentType().getFullQualifiedName() + "." + this.simpleName;
		String params = "";
		for (String param : s.getParamTypeList()) {
			params += param + ", ";
		}
		if (params.length() > 2)
			params = params.substring(0, params.length() - 2);
		this.signature = this.fullName + "(" + params + ")";
	}

	public CallableImpl(Callable lastCallable, AbstractMessage abstractMessage) {
		// TODO Auto-generated constructor stub
		this.parent = lastCallable;
		this.containingSubTrace = lastCallable.getContainingTrace();

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
	public SubTrace getContainingTrace() {
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
		return signature;
	}

	@Override
	public String getSimpleName() {
		return simpleName;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public boolean isConstructor() {
		return constructor;
	}

	@Override
	public List<String> getLables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasLabel(String label) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getPosition() {
		return position;
	}

	@Override
	public long getDepth() {
		return depth;
	}

	@Override
	public List<AdditionalInformation> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getAdditionalInformation(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public SubTrace getTargetTrace() {
		return targetTrace;
	}

	public void setTargetTrace(SubTrace targetTrace) {
		this.targetTrace = targetTrace;
	}
}