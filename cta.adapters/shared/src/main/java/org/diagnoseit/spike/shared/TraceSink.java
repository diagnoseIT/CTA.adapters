package org.diagnoseit.spike.shared;

import org.diagnoseit.spike.shared.trace.Trace;

public interface TraceSink {
	public void appendTrace(Trace inputTrace);
}
