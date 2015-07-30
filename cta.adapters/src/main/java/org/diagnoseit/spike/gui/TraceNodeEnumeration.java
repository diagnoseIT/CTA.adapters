package org.diagnoseit.spike.gui;

import java.util.Enumeration;
import java.util.List;

import org.diagnoseit.spike.trace.Callable;

public class TraceNodeEnumeration implements Enumeration<TraceTreeNode> {

	private List<Callable> entries;
	private int nextIx = 0;

	public TraceNodeEnumeration(List<Callable> entries) {
		super();
		this.entries = entries;
	}

	@Override
	public boolean hasMoreElements() {
		return nextIx < entries.size();
	}

	@Override
	public TraceTreeNode nextElement() {
		Callable callable = entries.get(nextIx);
		nextIx++;
		return new TraceTreeNode(callable);
	}

}
