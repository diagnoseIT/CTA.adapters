package org.diagnoseit.spike.gui;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import org.diagnoseit.spike.trace.Callable;

public class TraceTreeNode implements TreeNode {
	private Callable callable;

	public TraceTreeNode(Callable callable) {
		super();
		this.callable = callable;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return new TraceTreeNode(callable.getCallees().get(childIndex));
	}

	@Override
	public int getChildCount() {
		return callable.getCallees().size();
	}

	@Override
	public TreeNode getParent() {
		return callable.getParent() == null ? null : new TraceTreeNode(callable.getParent());
	}

	@Override
	public int getIndex(TreeNode node) {
		TraceTreeNode ttn = (TraceTreeNode) node;

		return callable.getCallees().indexOf(ttn.callable);

	}

	@Override
	public boolean getAllowsChildren() {
		return callable.getCallees() != null && !callable.getCallees().isEmpty();
	}

	@Override
	public boolean isLeaf() {
		return callable.getCallees() == null || callable.getCallees().isEmpty();
	}

	@Override
	public Enumeration children() {
		return new TraceNodeEnumeration(callable.getCallees());
	}
	
	@Override
	public String toString() {
		return callable.toString();
	}

}
