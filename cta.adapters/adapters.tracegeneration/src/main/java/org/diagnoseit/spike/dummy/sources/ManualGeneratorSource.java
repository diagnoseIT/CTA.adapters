package org.diagnoseit.spike.dummy.sources;

import java.util.Properties;

import org.diagnoseit.spike.dummy.trace.generation.DataGenerationWorker;
import org.diagnoseit.spike.dummy.trace.generation.MonitoringDataProcessing;
import org.diagnoseit.spike.shared.TraceSink;
import org.diagnoseit.spike.shared.TraceSource;

import rocks.cta.api.core.Trace;

public class ManualGeneratorSource implements TraceSource {

	@Override
	public void initialize(Properties properties, TraceSink traceSink) {
		MonitoringDataProcessing.getInstance().setSink(traceSink);
		MonitoringDataProcessing.getInstance().start();
	}

	@Override
	public boolean isManualSource() {
		return true;
	}

	@Override
	public Trace submitNextTrace() {
		DataGenerationWorker worker = new DataGenerationWorker(false);
		worker.run();
		return MonitoringDataProcessing.getInstance().getLastGeneratedTrace();
	}

	@Override
	public void startTraceGeneration() {
		throw new UnsupportedOperationException(
				"This operation is NOT available for manual trace sources!");
	}

	@Override
	public void stopTraceGeneration() {
		throw new UnsupportedOperationException(
				"This operation is NOT available for manual trace sources!");
	}

}
