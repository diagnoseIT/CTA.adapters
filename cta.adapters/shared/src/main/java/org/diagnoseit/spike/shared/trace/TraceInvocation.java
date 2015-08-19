package org.diagnoseit.spike.shared.trace;

/**
 * A {@link TraceInvocation} represents an invocation of another {@link SubTrace} from the current
 * {@link SubTrace}.
 * 
 * @author Alexander Wert
 *
 */
public interface TraceInvocation extends Callable {

	/**
	 * 
	 * @return the invoked {@link SubTrace}
	 */
	SubTrace getTargetTrace();

	/**
	 * 
	 * @return true if current {@link SubTrace} execution blocks until termination of the invoked
	 *         {@link SubTrace}, otherwise false.
	 */
	boolean isSync();
}
