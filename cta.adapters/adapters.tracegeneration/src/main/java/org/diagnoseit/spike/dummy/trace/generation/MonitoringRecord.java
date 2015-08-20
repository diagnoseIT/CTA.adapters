package org.diagnoseit.spike.dummy.trace.generation;

public class MonitoringRecord implements Comparable<MonitoringRecord>{
	
	private String operationName;
	private String platformID;
	private int index;
	private int stackDepth;
	private long startTime;
	private long duration;
	private long executionTime;
	private long CPUTime;
	private long traceId;
	private boolean start;
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
	/**
	 * @return the start
	 */
	public boolean isStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(boolean start) {
		this.start = start;
	}
	public int compareTo(MonitoringRecord o) {
		int c = this.platformID.compareTo(o.getPlatformID());
		if(c == 0){
			int c2 = this.index - o.index;
			if(c2 == 0){
				return (int)(this.traceId - o.traceId);
			}else{
				return c2;
			}
		}else{
			return c;
		}
		
	}
	
	@Override
	public String toString() {
		return this.getPlatformID() + "  ix:" + this.getIndex() + "  sd:" + this.getStackDepth() + " ih:" + this.getInCorrelationHash();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((inCorrelationHash == null) ? 0 : inCorrelationHash
						.hashCode());
		result = prime * result + index;
		result = prime * result
				+ ((operationName == null) ? 0 : operationName.hashCode());
		result = prime
				* result
				+ ((outCorrelationHash == null) ? 0 : outCorrelationHash
						.hashCode());
		result = prime * result
				+ ((platformID == null) ? 0 : platformID.hashCode());
		result = prime * result + stackDepth;
		result = prime * result + (start ? 1231 : 1237);
		result = prime * result + (int) (traceId ^ (traceId >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonitoringRecord other = (MonitoringRecord) obj;
		if (inCorrelationHash == null) {
			if (other.inCorrelationHash != null)
				return false;
		} else if (!inCorrelationHash.equals(other.inCorrelationHash))
			return false;
		if (index != other.index)
			return false;
		if (operationName == null) {
			if (other.operationName != null)
				return false;
		} else if (!operationName.equals(other.operationName))
			return false;
		if (outCorrelationHash == null) {
			if (other.outCorrelationHash != null)
				return false;
		} else if (!outCorrelationHash.equals(other.outCorrelationHash))
			return false;
		if (platformID == null) {
			if (other.platformID != null)
				return false;
		} else if (!platformID.equals(other.platformID))
			return false;
		if (stackDepth != other.stackDepth)
			return false;
		if (start != other.start)
			return false;
		if (traceId != other.traceId)
			return false;
		return true;
	}
	/**
	 * @return the executionTime
	 */
	public long getExecutionTime() {
		return executionTime;
	}
	/**
	 * @param executionTime the executionTime to set
	 */
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	/**
	 * @return the cPUTime
	 */
	public long getCPUTime() {
		return CPUTime;
	}
	/**
	 * @param cPUTime the cPUTime to set
	 */
	public void setCPUTime(long cPUTime) {
		CPUTime = cPUTime;
	}

	
	
	
}
