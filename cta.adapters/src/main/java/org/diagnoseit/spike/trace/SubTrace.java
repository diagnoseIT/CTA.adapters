package org.diagnoseit.spike.trace;

import java.util.List;

public interface SubTrace {
	OperationInvocation getRoot();
	SubTrace getParent();
	List<SubTrace> getSubTraces();
	Location getLocation();
	long getId();
	int depth();
	int size();
}
