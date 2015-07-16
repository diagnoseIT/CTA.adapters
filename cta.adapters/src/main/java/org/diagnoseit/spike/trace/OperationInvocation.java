package org.diagnoseit.spike.trace;

public interface OperationInvocation extends Callable {
	String getOperationName();
}
