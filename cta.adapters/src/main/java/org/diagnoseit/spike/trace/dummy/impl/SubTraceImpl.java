package org.diagnoseit.spike.trace.dummy.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.diagnoseit.spike.monitoring.MonitoringRecord;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.Location;
import org.diagnoseit.spike.trace.SubTrace;

public class SubTraceImpl implements SubTrace {

	protected TraceImpl trace;
	private String platformId;
	private Location location;

	public SubTraceImpl(TraceImpl trace, String platformId) {
		this.trace = trace;
		this.platformId = platformId;
		location = new LocationImpl(platformId);
	}

	public Callable getRoot() {
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (rec.getIndex() == 0
					&& rec.getTraceId() == trace.getLogicalTraceId()
					&& rec.getPlatformID().equals(platformId)) {
				return new CallableImpl(trace, platformId, rec);
			}
		}
		return null;
	}

	public SubTrace getParent() {
		MonitoringRecord rootRecord = null;
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (rec.getIndex() == 0
					&& rec.getTraceId() == trace.getLogicalTraceId()
					&& rec.getPlatformID().equals(platformId)) {
				rootRecord = rec;
				break;
			}
		}

		if (rootRecord != null && rootRecord.getInCorrelationHash() != null) {
			Integer hash = rootRecord.getInCorrelationHash();
			for (MonitoringRecord rec : trace.monitoringRecords) {
				if (rec.getOutCorrelationHash() == hash
						&& rec.getTraceId() == trace.getLogicalTraceId()
						&& !rec.getPlatformID().equals(platformId)) {
					return new SubTraceImpl(trace, rec.getPlatformID());
				}
			}
		}

		return null;
	}

	public List<SubTrace> getSubTraces() {
		Set<SubTrace> subtraces = new HashSet<SubTrace>();
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (rec.getOutCorrelationHash() != null
					&& rec.getTraceId() == trace.getLogicalTraceId()
					&& rec.getPlatformID().equals(platformId)) {
				MonitoringRecord tmpRec = rec;
				for (MonitoringRecord rec2 : trace.monitoringRecords) {
					if (rec2.getInCorrelationHash() == tmpRec
							.getOutCorrelationHash()) {
						subtraces.add(new SubTraceImpl(trace, rec2
								.getPlatformID()));
						break;
					}
				}

			}
		}
		return new ArrayList<SubTrace>(subtraces);
	}

	public Location getLocation() {
		return location;
	}

	public long getId() {
		return trace.getLogicalTraceId() * 7 + location.hashCode();
	}

	public long maxDepth() {
		int max = -1;
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (rec.getIndex() == 0
					&& rec.getTraceId() == trace.getLogicalTraceId()) {
				if (max < rec.getIndex()) {
					max = rec.getIndex();
				}
			}
		}
		return max;
	}

	public long size() {
		int count = 0;
		for (MonitoringRecord rec : trace.monitoringRecords) {
			if (rec.getIndex() == 0
					&& rec.getTraceId() == trace.getLogicalTraceId()) {
				count++;
			}
		}
		return count;
	}

	public Iterator<Callable> iterator() {
		return new SubTraceIterator(platformId, trace);
	}

}
