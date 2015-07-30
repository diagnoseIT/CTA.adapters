package org.diagnoseit.spike.tag;

import org.diagnoseit.spike.trace.Callable;

public class SlowMethodTag implements Tag{
	
	private Callable callable;

	public Callable getCallable() {
		return callable;
	}

	public void setCallable(Callable callable) {
		this.callable = callable;
	}

}
