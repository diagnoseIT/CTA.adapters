package org.diagnoseit.spike.trace.inspectit.impl;

import info.novatec.inspectit.cmr.model.PlatformIdent;
import info.novatec.inspectit.communication.data.InvocationSequenceData;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.Location;
import org.diagnoseit.spike.trace.SubTrace;

public class IISubTraceImpl implements SubTrace, Location {

	private static final int MAX_PRINTABLE_AMOUNT = 200;
	private static final String UNKOWN = "unknown";
	private final InvocationSequenceData isData;
	private final Callable root;
	protected final PlatformIdent pIdent;
	private long maxDepth = -1;

	public IISubTraceImpl(InvocationSequenceData isData, PlatformIdent pIdent) {
		this.root = new IICallableImpl(isData, this, null, 0);
		this.isData = isData;
		this.pIdent = pIdent;

	}

	@Override
	public Callable getRoot() {
		return (Callable) isData;
	}

	@Override
	public SubTrace getParent() {
		// TODO temporary dummy implementation
		return null;
	}

	@Override
	public List<SubTrace> getSubTraces() {
		// TODO temporary dummy implementation
		return Collections.EMPTY_LIST;
	}

	@Override
	public Location getLocation() {
		return this;
	}

	@Override
	public long getId() {
		return isData.getId();
	}

	@Override
	public long maxDepth() {
		if (maxDepth < 0) {
			for (Callable callable : this) {
				if (callable.getDepth() > maxDepth) {
					maxDepth = callable.getDepth();
				}
			}
		}

		return maxDepth;
	}

	@Override
	public long size() {
		return (int) isData.getChildCount() + 1;
	}

	@Override
	public Iterator<Callable> iterator() {
		return new IISubTraceIterator(root);
	}

	@Override
	public String getHost() {
		if (pIdent.getDefinedIPs().isEmpty()) {
			return UNKOWN;
		} else {
			for (String ip : pIdent.getDefinedIPs()) {
				if (!ip.equals("localhost") && !ip.equals("127.0.0.1")) {
					return ip;
				}
			}
			return UNKOWN;
		}
	}

	@Override
	public String getRuntimeEnvironment() {
		return pIdent.getAgentName() + (pIdent.getId() == null ? "" : "-" + pIdent.getId());
	}

	@Override
	public String getApplication() {
		return UNKOWN;
	}

	@Override
	public String getBusinessTransaction() {
		return UNKOWN;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for (Callable callable : this) {
			if (callable.getPosition() == MAX_PRINTABLE_AMOUNT) {
				for (int i = 0; i < callable.getDepth(); i++) {
					strBuilder.append("   ");
				}
				strBuilder.append("...");
			} else if (callable.getPosition() > MAX_PRINTABLE_AMOUNT) {
				return strBuilder.toString();
			} else {
				for (int i = 0; i < callable.getDepth(); i++) {
					strBuilder.append("   ");
				}
				strBuilder.append(callable.toString());
				strBuilder.append("\n");
			}
		}

		return strBuilder.toString();
	}

}
