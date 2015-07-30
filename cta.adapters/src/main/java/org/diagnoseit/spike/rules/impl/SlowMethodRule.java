package org.diagnoseit.spike.rules.impl;

import org.diagnoseit.spike.result.ProblemInstance;
import org.diagnoseit.spike.tag.SlowMethodTag;
import org.diagnoseit.spike.trace.Callable;
import org.diagnoseit.spike.trace.Trace;

public class SlowMethodRule {

	private static final double THRESHOLD = 0.8;
	
	private SlowMethodTag slowMethodTag = null;

	public void execute(Trace trace) {
		
		if (trace == null) {
			throw new IllegalArgumentException("Pass a valid trace instance to diagnose.");
		}
		
		Callable suspectCallable = null;
		Callable callableParent = trace.getRoot().getRoot();
		boolean continueSearch = false;

		do {
			continueSearch = false;
			for (Callable callable : callableParent.getCallees()) {
				
				double execTimeParent = (double) callable.getParent().getExecutionTime();
				double execTimeChild = (double) callable.getExecutionTime();

				double fraction = execTimeChild	/ execTimeParent;

				if (fraction > THRESHOLD) {
					suspectCallable = callable;
					continueSearch = true;
					callableParent = callable;
					break;
				}
			}

		} while (continueSearch);
		
		if (suspectCallable != null) {
			System.out.println("Slow Method diagnosed: " + suspectCallable.getSignature());
//			System.out.println(suspectCallable.toString());
	
			SlowMethodTag slowMethodTag = new SlowMethodTag();
			slowMethodTag.setCallable(suspectCallable);
			
			this.slowMethodTag = slowMethodTag;
	
//			createProblemInstance(suspectCallable);
			
		}


	}

	private void createProblemInstance(Callable suspect) {
		ProblemInstance probInstance = new ProblemInstance();
		probInstance.setCallable(suspect);

		// TODO: Where to put the problem instance?
	}

	public SlowMethodTag getSlowMethodTag() {
		return slowMethodTag;
	}
	
	public boolean hasTag() {
		return slowMethodTag != null;
	}

}
