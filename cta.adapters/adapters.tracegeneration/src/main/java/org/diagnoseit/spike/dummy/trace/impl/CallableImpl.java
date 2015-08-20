package org.diagnoseit.spike.dummy.trace.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.diagnoseit.spike.dummy.trace.generation.MonitoringRecord;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.Callable;
import rocks.cta.api.core.SubTrace;

public class CallableImpl implements Callable {

	private static final String PACKAGE_DELIMITER = ".";
	private static final String EMPTY_STRING = "";
	protected TraceImpl trace;
	protected String platformId;
	protected MonitoringRecord internalRecord;

	public CallableImpl(TraceImpl trace, String platformId, MonitoringRecord internalRecord) {
		this.trace = trace;
		this.platformId = platformId;
		this.internalRecord = internalRecord;
	}

	@Override
	public List<Callable> getCallees() {
		List<Callable> children = new ArrayList<Callable>();
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (rec.getTraceId() == trace.getLogicalTraceId() && rec.getPlatformID().equals(platformId)) {
				if (rec.getIndex() > internalRecord.getIndex() && rec.getStackDepth() == internalRecord.getStackDepth() + 1) {

					children.add(new CallableImpl(trace, platformId, rec));

				} else if (rec.getIndex() > internalRecord.getIndex()) {
					break;
				}
			}

		}

		return children;
	}

	@Override
	public Callable getParent() {
		MonitoringRecord tmpRec = null;
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (rec.getTraceId() == trace.getLogicalTraceId() && rec.getPlatformID().equals(platformId)) {
				if (rec.getIndex() >= internalRecord.getIndex()) {
					return new CallableImpl(trace, platformId, tmpRec);
				} else if (rec.getStackDepth() == internalRecord.getStackDepth() - 1) {
					tmpRec = rec;
				}
			}

		}
		return null;
	}

	@Override
	public long getExecutionTime() {
		return internalRecord.getExecutionTime();
	}

	@Override
	public long getEntryTime() {
		return internalRecord.getStartTime();
	}

	@Override
	public long getExitTime() {
		return internalRecord.getStartTime() + internalRecord.getDuration();
	}

	@Override
	public String getSignature() {
		return internalRecord.getOperationName();
	}

	@Override
	public boolean isConstructor() {
		return internalRecord.getOperationName().contains("<init>");
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

	@Override
	public String toString() {
		return getPackageName() + PACKAGE_DELIMITER + getClassName() + PACKAGE_DELIMITER + "  [" + getResponseTime() + getMethodName() + " | " + getExecutionTime() + " | " + getCPUTime() + "]";

	}

	@Override
	public long getCPUTime() {
		return internalRecord.getCPUTime();
	}

	@Override
	public String getClassName() {
		return EMPTY_STRING;
	}

	@Override
	public SubTrace getContainingSubTrace() {
		return new SubTraceImpl(trace, platformId);
	}

	@Override
	public SubTrace getInvokedSubTrace() {
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (internalRecord.getOutCorrelationHash().equals(rec.getInCorrelationHash())) {
				return new SubTraceImpl(trace, rec.getPlatformID());
			}
		}
		return null;
	}

	@Override
	public String getMethodName() {
		return internalRecord.getOperationName();
	}

	@Override
	public String getPackageName() {
		return EMPTY_STRING;
	}

	@Override
	public List<String> getParameterTypes() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public long getResponseTime() {
		return internalRecord.getDuration();
	}

	@Override
	public String getReturnType() {
		return "void";
	}

	@Override
	public boolean isAsyncInvocation() {
		return false;
	}

	@Override
	public boolean isSubTraceInvocation() {
		return getInvokedSubTrace() != null;
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
