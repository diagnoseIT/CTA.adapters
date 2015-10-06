/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.TreeIterator;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.NestingCallable;
import rocks.cta.api.utils.CallableIterator;

/**
 * @author Okanovic
 *
 */
public abstract class AbstractNestingCallableImpl extends AbstractTimedCallableImpl implements NestingCallable, Serializable {
	public AbstractNestingCallableImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace, long responseTime, long exclusiveTime) {
		super(parent, entryTime, labelIds, additionInfos, containingSubTrace, responseTime, exclusiveTime);
		this.callees.addAll(callees);
		this.childCount = callees.size();
	}

	private static final long serialVersionUID = -5263454158084783592L;
	int childCount = 0;
	List<Callable> callees = new ArrayList<Callable>();

	@Override
	public TreeIterator<Callable> iterator() {
		return new CallableIterator(this);
	}

	@Override
	public List<Callable> getCallees() {
		if (callees == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList(callees);
		}
	}

	public void addCallee(Callable callee) {
		if (callees == null) {
			callees = new ArrayList<Callable>();
		}
		callees.add(callee);
		int additionalNumChildren = 1;
		if (callee instanceof NestingCallable) {
			additionalNumChildren += ((NestingCallable) callee).getChildCount();
		}
		updateChildCount(additionalNumChildren);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Callable> List<T> getCallees(Class<T> type) {
		if (callees == null) {
			return Collections.emptyList();
		} else {
			List<T> result = null;
			for (Callable callable : callees) {
				if (type.isAssignableFrom(callable.getClass())) {
					if (result == null) {
						result = new ArrayList<T>();
					}
					result.add((T) callable);
				}
			}

			return result == null ? Collections.emptyList() : Collections.unmodifiableList(result);
		}
	}

	@Override
	public int getChildCount() {
		return childCount;
	}

	protected void updateChildCount(int childCountIncrease) {
		this.childCount += childCountIncrease;
		if (parent != null) {
			parent.updateChildCount(childCountIncrease);
		}
	}
}