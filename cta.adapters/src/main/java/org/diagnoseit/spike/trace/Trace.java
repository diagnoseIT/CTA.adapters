package org.diagnoseit.spike.trace;

public interface Trace extends Iterable<Callable>{
	SubTrace getRoot();
	long getLogicalTraceId();
}
