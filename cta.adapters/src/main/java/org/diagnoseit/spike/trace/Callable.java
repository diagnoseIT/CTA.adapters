package org.diagnoseit.spike.trace;

import java.util.List;

public interface Callable {
	List<Callable> getCallees();
	OperationInvocation getParent();
	SubTrace getContainingTrace();
	long getExecutionTime();
	long getEntryTime();
	long getExitTime();
}
