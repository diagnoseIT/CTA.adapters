/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.io.Serializable;
import java.util.List;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.callables.NestingCallable;
import rocks.cta.api.core.callables.TimedCallable;

/**
 * @author Okanovic
 *
 */
public class AbstractTimedCallableImpl extends AbstractCallableImpl implements TimedCallable, Serializable {
	private static final long serialVersionUID = 8126224575872353423L;

	public AbstractTimedCallableImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace) {
		super(parent, entryTime, labelIds, additionInfos, containingSubTrace);
	}

	public AbstractTimedCallableImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace, long responseTime, long exclusiveTime) {
		super(parent, entryTime, labelIds, additionInfos, containingSubTrace);
		this.responseTime = responseTime;
		this.exclusiveTime = exclusiveTime;
	}

	protected long responseTime = -1;

	private transient long exclusiveTime = -1;

	@Override
	public long getExclusiveTime() {
		if (exclusiveTime < 0) {
			exclusiveTime = responseTime;
			if (this instanceof NestingCallable) {
				for (TimedCallable tCallable : ((NestingCallable) this).getCallees(TimedCallable.class)) {
					exclusiveTime -= tCallable.getResponseTime();
				}
			}
		}
		return exclusiveTime;
	}

	@Override
	public long getResponseTime() {
		return responseTime;
	}

	@Override
	public long getExitTime() {
		return getTimestamp() + Math.round(((double) responseTime) * Trace.NANOS_TO_MILLIS_FACTOR);
	}
}