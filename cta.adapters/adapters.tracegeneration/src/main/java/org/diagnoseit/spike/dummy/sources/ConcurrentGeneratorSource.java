package org.diagnoseit.spike.dummy.sources;

import java.util.Properties;

import org.diagnoseit.spike.dummy.trace.generation.DataGenerator;
import org.diagnoseit.spike.dummy.trace.impl.MonitoringDataProcessing;
import org.diagnoseit.spike.shared.TraceSink;
import org.diagnoseit.spike.shared.TraceSource;
import org.diagnoseit.spike.shared.trace.Trace;

public class ConcurrentGeneratorSource implements TraceSource {

	@Override
	public void initialize(Properties properties, TraceSink traceSink) {
		MonitoringDataProcessing.getInstance().setSink(traceSink);
		MonitoringDataProcessing.getInstance().start();
	}

	@Override
	public boolean isManualSource() {
		return false;
	}

	@Override
	public Trace submitNextTrace() {
		throw new UnsupportedOperationException(
				"This operation is only available for manual trace sources!");
	}

	@Override
	public void startTraceGeneration() {
		DataGenerator.getInstance().start();
	}

	@Override
	public void stopTraceGeneration() {
		DataGenerator.getInstance().stop();
		MonitoringDataProcessing.getInstance().stop();
	}

}
