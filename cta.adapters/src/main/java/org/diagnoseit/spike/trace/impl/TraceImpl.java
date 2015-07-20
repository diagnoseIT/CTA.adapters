package org.diagnoseit.spike.trace.impl;

import java.util.Iterator;

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
		StringBuilder strBuilder = new StringBuilder();
		for(InvocationRecord irec : root){
			int depth = irec.getStackDepth();
			for(int i = 0; i < depth ; i++){
				strBuilder.append("   ");
			}
			if(irec instanceof MethodExecution){
				strBuilder.append(((MethodExecution)irec).getOperationName() + " - " +irec.getExecutionTime()+"ms");
			}else{
				strBuilder.append("-->");
			}
			strBuilder.append("\n");
		}
		return strBuilder.toString();
	}

}
