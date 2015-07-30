package org.diagnoseit.spike.trace.dummy.impl;

import java.util.ArrayList;
import java.util.List;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.AdditionalInformation;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.SubTrace;

public class CallableImpl implements Callable {

	protected TraceImpl trace;
	protected String platformId;
	protected MonitoringRecord internalRecord;
	
	
	public CallableImpl(TraceImpl trace, String platformId, MonitoringRecord internalRecord) {
		this.trace=trace;
		this.platformId=platformId;
		this.internalRecord=internalRecord;
	}
	
	public List<Callable> getCallees() {
		List<Callable> children = new ArrayList<Callable>();
		for(MonitoringRecord rec : trace.monitoringRecords){
			if(rec.getTraceId() == trace.getLogicalTraceId() && rec.getPlatformID().equals(platformId)){
				if(rec.getIndex() > internalRecord.getIndex() && rec.getStackDepth() == internalRecord.getStackDepth()+1){
					if(rec.getOutCorrelationHash() == null){
						children.add(new CallableImpl(trace, platformId, rec));
					}else{
						children.add(new TraceInvocationImpl(trace, platformId, rec));
					}
					
					
					
				}else if(rec.getIndex() > internalRecord.getIndex()){
					break;
				}
			}
			
		}
		
		return children;
	}

	public Callable getParent() {
		MonitoringRecord tmpRec=null;
		for(MonitoringRecord rec : trace.monitoringRecords){
			if(rec.getTraceId() == trace.getLogicalTraceId() && rec.getPlatformID().equals(platformId)){
				if(rec.getIndex() >= internalRecord.getIndex()){
					return new CallableImpl(trace, platformId, tmpRec);
				}else if(rec.getStackDepth() == internalRecord.getStackDepth() -1){
					tmpRec = rec;
				}
			}
			
		}
		return null;
	}

	public SubTrace getContainingTrace() {
		return new SubTraceImpl(trace, platformId);
	}

	public long getExecutionTime() {
		return internalRecord.getDuration();
	}

	public long getEntryTime() {
		return internalRecord.getStartTime();
	}

	public long getExitTime() {
		return internalRecord.getStartTime() + internalRecord.getDuration();
	}

	public String getSignature() {
		return internalRecord.getOperationName();
	}

	public boolean isConstructor() {
		return internalRecord.getOperationName().contains("<init>");
	}

	public List<String> getLables() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasLabel(String label) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<AdditionalInformation> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> getAdditionalInformation(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSimpleName() {
		return getSignature();
	}

	public String getFullName() {
		return getSignature();
	}

	@Override
	public long getPosition() {
		return internalRecord.getIndex();
	}

	@Override
	public long getDepth() {
		return internalRecord.getStackDepth();
	}

}
