package org.diagnoseit.spike.launcher;

import info.novatec.inspectit.cmr.model.PlatformIdent;
import info.novatec.inspectit.communication.data.InvocationSequenceData;

import java.io.IOException;
import java.util.Iterator;

import org.diagnoseit.spike.monitoring.datamocking.DataGenerationWorker;
import org.diagnoseit.spike.monitoring.datamocking.DataGenerator;
import org.diagnoseit.spike.rules.processing.DiagnoseIT;
import org.diagnoseit.spike.trace.Trace;
import org.diagnoseit.spike.trace.dummy.builder.MonitoringDataProcessing;
import org.diagnoseit.spike.trace.inspectit.impl.IITraceImpl;
import org.diagnoseit.spike.trace.inspectit.reader.InvocationSequences;
import org.diagnoseit.spike.trace.inspectit.reader.SerializerWrapper;

public class SpikeLauncher {

	private static final TraceSource TRACE_SOURCE = TraceSource.INSPECTIT_IMPORT;
	private static final String INSPECTIT_DATA_PATH = "C:/Users/awe/Desktop/inspectIT-TestData/fromIvan";
	private static Iterator<InvocationSequenceData> isDataIterator = null;
	private static PlatformIdent pIdent = null;

	public static void main(String[] args) throws IOException {

		DiagnoseIT.getInstance().start();

		selectTraceSource();

	}

	/**
	 * selects the source for the traces
	 * 
	 * @throws IOException
	 */
	private static void selectTraceSource() throws IOException {
		switch (TRACE_SOURCE) {
		case GENERATOR_CONCURRENT:
			MonitoringDataProcessing.getInstance().start();
			DataGenerator.getInstance().start();
			break;
		case GENERATOR_MANUAL:
			MonitoringDataProcessing.getInstance().start();
			new TraceTriggerGui();
			break;
		case INSPECTIT_IMPORT:
			SerializerWrapper serializer = new SerializerWrapper();
			InvocationSequences iSequences = serializer.readInvocationSequencesFromDir(INSPECTIT_DATA_PATH);
			isDataIterator = iSequences.iterator();
			pIdent = iSequences.getPlatformIdent();
			new TraceTriggerGui();
			break;

		default:
			break;
		}
	}

	/**
	 * when using manual trace triggering, this method is invoked when a trace is requested
	 * 
	 * @return
	 */
	public static long nextTrace() {
		long traceID = -1;

		switch (TRACE_SOURCE) {
		case GENERATOR_MANUAL:
			DataGenerationWorker worker = new DataGenerationWorker(false);
			traceID = worker.getTraceId();
			worker.run();
			break;
		case INSPECTIT_IMPORT:
			if (!isDataIterator.hasNext()) {
				throw new IllegalStateException();
			}
			InvocationSequenceData isd = isDataIterator.next();
			Trace trace = new IITraceImpl(isd, pIdent);
			traceID = trace.getLogicalTraceId();
			try {
				DiagnoseIT.getInstance().appendTrace(trace);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

		return traceID;
	}

	enum TraceSource {
		/**
		 * generates automatically synthetic traces using concurrent threads
		 */
		GENERATOR_CONCURRENT,
		/**
		 * generates synthetic traces when manually requested by user
		 */
		GENERATOR_MANUAL,
		/**
		 * reads real traces from inspectIT files and provides them one by one when manually
		 * requested by user
		 */
		INSPECTIT_IMPORT
	}

}
