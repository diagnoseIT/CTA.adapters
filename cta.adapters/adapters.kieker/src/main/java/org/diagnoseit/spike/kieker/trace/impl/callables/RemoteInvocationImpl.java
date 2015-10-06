/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.util.List;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.callables.RemoteInvocation;
import rocks.cta.api.utils.StringUtils;

/**
 * @author Okanovic
 *
 */
public class RemoteInvocationImpl extends AbstractTimedCallableImpl implements RemoteInvocation {
	public RemoteInvocationImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace, SubTrace targetSubTrace) {
		super(parent, entryTime, labelIds, additionInfos, containingSubTrace);
		this.targetSubTrace = targetSubTrace;
	}

	private static final long serialVersionUID = 1129980818226035999L;

	String target;
	SubTrace targetSubTrace;

	@Override
	public String getTarget() {
		if (targetSubTrace != null && target == null) {
			target = targetSubTrace.getLocation().toString();
		}
		return target;
	}

	@Override
	public Location getTargetLocation() {
		if (targetSubTrace != null) {
			return targetSubTrace.getLocation();
		}
		return null;
	}

	@Override
	public SubTrace getTargetSubTrace() {
		return targetSubTrace;
	}

	@Override
	public boolean hasTargetSubTrace() {
		return targetSubTrace != null;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}
}