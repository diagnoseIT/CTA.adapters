package org.diagnoseit.spike.trace;

public interface TraceInvocation extends Callable{
	SubTrace getTargetTrace();
}
