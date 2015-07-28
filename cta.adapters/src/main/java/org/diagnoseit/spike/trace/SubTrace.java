package org.diagnoseit.spike.trace;

import java.util.List;

public interface SubTrace extends Iterable<Callable>{
	Callable getRoot();
	SubTrace getParent();
	List<SubTrace> getSubTraces();
	Location getLocation();
	long getId();
	int maxDepth();
	int size();
}
