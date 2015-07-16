package org.diagnoseit.spike.monitoring;

public class MonitoringRecord {
	
	private String operationName;
	private String platformID;
	private int index;
	private int stackDepth;
	private long startTime;
	private long duration;
	private long traceId;
	private Integer outCorrelationHash = null;
	private Integer inCorrelationHash = null;
	
	/**
	 * @return the platformID
	 */
	public String getPlatformID() {
		return platformID;
	}
	/**
	 * @param platformID the platformID to set
	 */
	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the stackDepth
	 */
	public int getStackDepth() {
		return stackDepth;
	}
	/**
	 * @param stackDepth the stackDepth to set
	 */
	public void setStackDepth(int stackDepth) {
		this.stackDepth = stackDepth;
	}
	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}
	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	/**
	 * @return the traceId
	 */
	public long getTraceId() {
		return traceId;
	}
	/**
	 * @param traceId the traceId to set
	 */
	public void setTraceId(long traceId) {
		this.traceId = traceId;
	}
	/**
	 * @return the outCorrelationHash
	 */
	public Integer getOutCorrelationHash() {
		return outCorrelationHash;
	}
	/**
	 * @param outCorrelationHash the outCorrelationHash to set
	 */
	public void setOutCorrelationHash(Integer outCorrelationHash) {
		this.outCorrelationHash = outCorrelationHash;
	}
	/**
	 * @return the inCorrelationHash
	 */
	public Integer getInCorrelationHash() {
		return inCorrelationHash;
	}
	/**
	 * @param inCorrelationHash the inCorrelationHash to set
	 */
	public void setInCorrelationHash(Integer inCorrelationHash) {
		this.inCorrelationHash = inCorrelationHash;
	}

	
	
	
}
