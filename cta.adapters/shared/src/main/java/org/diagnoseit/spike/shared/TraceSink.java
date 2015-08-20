package org.diagnoseit.spike.shared;

import rocks.cta.api.core.Trace;

public interface TraceSink {
	public void appendTrace(Trace inputTrace);
}
