/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.diagnoseit.spike.kieker.trace.impl.SubTraceImpl;
import org.diagnoseit.spike.kieker.trace.impl.TraceImpl;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.NestingCallable;

/**
 * @author Okanovic
 *
 */
public abstract class AbstractCallableImpl implements Callable, Serializable {
	private static final long serialVersionUID = -3497901525807859440L;

	protected AbstractNestingCallableImpl parent;

	protected long entryTime = -1;

	protected List<Integer> labelIds;

	protected List<AdditionalInformation> additionInfos;

	protected SubTrace containingSubTrace;

	public void setContainingSubTrace(SubTraceImpl subTrace) {
		this.containingSubTrace = subTrace;
	}

	public AbstractCallableImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace) {
		super();
		this.parent = parent;
		this.entryTime = entryTime;
		this.labelIds = labelIds;
		this.additionInfos = additionInfos;
		this.containingSubTrace = containingSubTrace;
	}

	@Override
	public Collection<AdditionalInformation> getAdditionalInformation() {
		if (additionInfos == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(additionInfos);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AdditionalInformation> Collection<T> getAdditionalInformation(Class<T> type) {
		List<T> result = new ArrayList<T>();
		for (AdditionalInformation aInfo : additionInfos) {
			if (type.isAssignableFrom(aInfo.getClass())) {
				result.add((T) aInfo);
			}
		}
		return Collections.unmodifiableList(result);
	}

	@Override
	public SubTrace getContainingSubTrace() {
		return containingSubTrace;
	}

	@Override
	public List<String> getLabels() {
		if (labelIds == null) {
			return Collections.emptyList();
		}
		List<String> labels = new ArrayList<String>();
		TraceImpl trace = ((TraceImpl) getContainingSubTrace().getContainingTrace());
		for (int id : labelIds) {
			// TODO fix this
			// labels.add(trace.getStringConstant(id));
		}
		return Collections.unmodifiableList(labels);
	}

	@Override
	public NestingCallable getParent() {
		return parent;
	}

	@Override
	public long getTimestamp() {
		return entryTime;
	}

	@Override
	public boolean hasLabel(String arg0) {
		return getLabels().contains(arg0);
	}

}
