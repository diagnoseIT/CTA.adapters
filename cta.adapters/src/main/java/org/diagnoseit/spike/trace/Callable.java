package org.diagnoseit.spike.trace;

import java.util.List;

public interface Callable {
	List<Callable> getCallees();
	Callable getParent();
	SubTrace getContainingTrace();
	long getExecutionTime();
	long getEntryTime();
	long getExitTime();
	String getSignature();
	boolean isConstructor();
	List<String> getLables();
	boolean hasLabel(String label);
	List<AdditionalInformation> getAdditionalInformation();
	<T> List<T> getAdditionalInformation(Class<T> type);
}
