package org.diagnoseit.spike.rules.impl;

import java.util.Iterator;

import org.diagnoseit.spike.result.ProblemInstance;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.OperationInvocation;
import org.diagnoseit.spike.trace.Trace;

public class SlowMethodRule {

	private static final double THRESHOLD = 0.8d;

	public void execute(Trace trace) {
		Callable suspectCallable = null;

		for (Iterator<Callable> rootIt = trace.getRootTrace().iterator(); rootIt
				.hasNext();) {
			Callable c = rootIt.next();

			double fraction = c.getExecutionTime()
					/ c.getParent().getExecutionTime();

			if (fraction > THRESHOLD) {
				suspectCallable = c;
			}
		}

		createResult(suspectCallable);

	}

	private void createResult(Callable suspect) {
		ProblemInstance probInstance = new ProblemInstance();
		probInstance.setSignature(((OperationInvocation) suspect)
				.getOperationName());
		//TODO: Where to put the problem instance?
	}
}
