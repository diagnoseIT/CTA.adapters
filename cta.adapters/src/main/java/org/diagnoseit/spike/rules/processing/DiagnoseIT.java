package org.diagnoseit.spike.rules.processing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.diagnoseit.spike.trace.Trace;

public class DiagnoseIT implements Runnable {

	private static DiagnoseIT instance;

	public static DiagnoseIT getInstance() {
		if (instance == null) {
			instance = new DiagnoseIT();
		}
		return instance;
	}

	private TraceAnalyzer traceAnalyzer;
	private BlockingQueue<Trace> inputQueue;
	private boolean run = false;

	private DiagnoseIT() {
		inputQueue = new LinkedBlockingQueue<Trace>();
		traceAnalyzer = new TraceAnalyzer();
	}

	public void appendTrace(Trace inputTrace) throws InterruptedException {
		inputQueue.put(inputTrace);
	}

	public void start() {
		run = true;
		new Thread(this).start();
	}

	public void stop() {
		run = false;
	}

	public void run() {
		while (run) {
			Trace trace;
			try {
				trace = inputQueue.poll(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				trace = null;
			}

			if (trace != null) {
				System.out.println(trace);
				traceAnalyzer.analyze(trace);
			}
		}
	}

}
