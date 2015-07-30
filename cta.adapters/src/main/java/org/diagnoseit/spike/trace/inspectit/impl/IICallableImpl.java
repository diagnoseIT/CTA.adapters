package org.diagnoseit.spike.trace.inspectit.impl;

import info.novatec.inspectit.cmr.model.MethodIdent;
import info.novatec.inspectit.communication.data.InvocationSequenceData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.diagnoseit.spike.trace.AdditionalInformation;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.SubTrace;

public class IICallableImpl implements Callable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203223671330309320L;

	private static final String CONSTRUCTOR_SUFFIX = "<init>";
	private final InvocationSequenceData isData;
	private List<Callable> children = null;
	private Callable parent = null;
	private final IISubTraceImpl containingTrace;
	private boolean firstChildAccess = true;
	private MethodIdent methodIdentifier = null;
	private final long position;
	private final long depth;

	public IICallableImpl(InvocationSequenceData isData, IISubTraceImpl containingTrace, Callable parent, long relPosition) {
		this.isData = isData;
		this.containingTrace = containingTrace;
		this.parent = parent;
		if (parent != null) {
			depth = parent.getDepth() + 1;
			position = parent.getPosition() + relPosition;
		} else {
			depth = 0;
			position = 0;
		}

	}

	@Override
	public List<Callable> getCallees() {
		if (children == null) {
			long counter = 1;
			children = new ArrayList<Callable>(isData.getNestedSequences().size());
			for (InvocationSequenceData isd : isData.getNestedSequences()) {
				children.add(new IICallableImpl(isd, containingTrace, this, counter));
				counter++;
			}
		}

		return children;
	}

	@Override
	public Callable getParent() {
		return isData.getParentSequence() == null ? null : (Callable) isData.getParentSequence();
	}

	@Override
	public SubTrace getContainingTrace() {
		return containingTrace;
	}

	@Override
	public long getExecutionTime() {
		return (long) isData.getDuration();
	}

	@Override
	public long getEntryTime() {
		return isData.getTimeStamp().getTime();

	}

	@Override
	public long getExitTime() {
		return isData.getTimeStamp().getTime() + (long) isData.getDuration();
	}

	@Override
	public String getSignature() {
		MethodIdent mi = getMethodIdentifier();
		if (mi != null) {
			String fqn = mi.getPackageName() + "." + mi.getMethodName() + "(";
			boolean first = true;
			for (String par : mi.getParameters()) {
				if (first) {
					fqn += par;
					first = false;
				} else {
					fqn += "," + par;
				}

			}
			fqn += ")";
			return fqn;
		} else {
			return null;
		}
	}

	@Override
	public String getSimpleName() {
		MethodIdent mi = getMethodIdentifier();
		if (mi != null) {
			return mi.getFQN() + "." + mi.getMethodName();
		} else {
			return null;
		}
	}

	@Override
	public String getFullName() {
		MethodIdent mi = getMethodIdentifier();
		if (mi != null) {
			return mi.getFQN() + "." + mi.getMethodName();
		} else {
			return null;
		}
	}

	private MethodIdent getMethodIdentifier() {
		if (methodIdentifier == null) {
			long mIdent = isData.getMethodIdent();
			for (MethodIdent mi : containingTrace.pIdent.getMethodIdents()) {

				if (mi.getId().equals(mIdent)) {
					methodIdentifier = mi;
					break;
				}
			}
		}

		return methodIdentifier;
	}

	@Override
	public boolean isConstructor() {
		return getSimpleName().contains(CONSTRUCTOR_SUFFIX);
	}

	@Override
	public List<String> getLables() {
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
	public <T> List<T> getAdditionalInformation(Class<T> type) {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	@Override
	public String toString() {
		return getFullName() + "  " + getExecutionTime();
	}

	@Override
	public long getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
