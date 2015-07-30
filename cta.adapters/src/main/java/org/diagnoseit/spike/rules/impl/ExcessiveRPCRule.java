package org.diagnoseit.spike.rules.impl;

import java.util.HashMap;
import java.util.Map.Entry;

import org.diagnoseit.spike.tag.SlowMethodTag;
import org.diagnoseit.spike.trace.Callable;

public class ExcessiveRPCRule {
	
	public void execute(SlowMethodTag slowMethodTag) {
		Callable callable = slowMethodTag.getCallable();
		
		HashMap<String, Long> histo = new HashMap<String, Long>();
		
		for (Callable callee : callable.getCallees()) {
			
			if (histo.containsKey(callee.getSignature())) {
				Long sumExecTime = histo.get(callee.getSignature());
				sumExecTime = sumExecTime + callee.getExecutionTime();
				histo.replace(callee.getSignature(), sumExecTime);
			} else {
				histo.put(callee.getSignature(), callee.getExecutionTime());
			}
		}
		
		for (Entry<String,Long> entry : histo.entrySet()) {
			double fraction = ((double)entry.getValue()) / ((double)callable.getParent().getExecutionTime());
			
			if (fraction > 0.8) {
				System.out.println("Excessive RPC Calls diagnosed: " + entry.getKey());
				break;
			}
		}
		
	}

}
