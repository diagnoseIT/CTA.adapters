package org.diagnoseit.spike.shared;

import java.util.Properties;

import rocks.cta.api.core.Trace;

public interface TraceSource {
	public void initialize(Properties properties, TraceSink traceSink);

	public boolean isManualSource();

	public Trace submitNextTrace();

	public void startTraceGeneration();

	public void stopTraceGeneration();
}
