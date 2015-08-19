package org.diagnoseit.spike.shared;

import java.util.NoSuchElementException;
import java.util.Properties;

import org.diagnoseit.spike.shared.trace.Trace;

public interface TraceSource {
	public void initialize(Properties properties, TraceSink traceSink);

	public boolean isManualSource();

	public Trace submitNextTrace();

	public void startTraceGeneration();

	public void stopTraceGeneration();
}
