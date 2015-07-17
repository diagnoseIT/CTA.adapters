package org.diagnoseit.spike.trace.impl;

import org.diagnoseit.spike.trace.SubTrace;
import org.diagnoseit.spike.trace.Trace;

public class TraceImpl implements Trace{

	private SubTraceImpl root;
	
	
	
	public TraceImpl(SubTraceImpl root) {
		super();
		this.root = root;
	}



	public SubTrace getRootTrace() {
		return root;
	}
	
	
	@Override
	public String toString() {
		String strRep = "";
		
		
		return strRep;
	}

}
