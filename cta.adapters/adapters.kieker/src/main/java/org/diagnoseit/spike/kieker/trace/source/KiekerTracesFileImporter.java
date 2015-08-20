package org.diagnoseit.spike.kieker.trace.source;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import kieker.analysis.exception.AnalysisConfigurationException;

import org.diagnoseit.spike.kieker.trace.test.TraceConversion;
import org.diagnoseit.spike.shared.TraceSink;
import org.diagnoseit.spike.shared.TraceSource;

import rocks.cta.api.core.Trace;

public class KiekerTracesFileImporter implements TraceSource {

	private static final String DATA_PATHS_KEY = "kieker.fileimporter.datapaths";
	private TraceSink traceSink;
	private LinkedBlockingQueue<Trace> kiekerQueue;

	@Override
	public void initialize(Properties properties, TraceSink traceSink) {
		this.traceSink = traceSink;
		kiekerQueue = new LinkedBlockingQueue<Trace>();

		String dataPathsStr = properties.getProperty(DATA_PATHS_KEY);
		if (dataPathsStr == null) {
			throw new IllegalArgumentException("Data paths have not been specified for the Kieker file importer trace source.");
		}
		String[] traceFolders = dataPathsStr.split(",");

		try {
			TraceConversion.runAnalysis(kiekerQueue, traceFolders);
		} catch (AnalysisConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isManualSource() {
		return true;
	}

	@Override
	public Trace submitNextTrace() {
		Trace trace;

		try {
			trace = kiekerQueue.poll(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new IllegalStateException();
		}

		traceSink.appendTrace(trace);

		return trace;
	}

	@Override
	public void startTraceGeneration() {
		throw new UnsupportedOperationException("This operation is NOT available for manual trace sources!");
	}

	@Override
	public void stopTraceGeneration() {
		throw new UnsupportedOperationException("This operation is NOT available for manual trace sources!");
	}

}
