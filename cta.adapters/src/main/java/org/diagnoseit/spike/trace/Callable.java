package org.diagnoseit.spike.trace;

import java.util.List;

/**
 * A {@link Callable} represents a node in a {@link SubTrace}, hence, stands for any callable
 * behaviour (e.g. operation execution).
 * 
 * @author Alexander Wert
 *
 */

public interface Callable {

	/**
	 * 
	 * @return a list of all {@link Callable} instances invoked by the current {@link Callable}. The
	 *         list contains the {@link Callable} instances in the order they have been called.
	 */
	List<Callable> getCallees();

	/**
	 * Returns the parent {@link Callable} of the current {@link Callable} within the tree structure
	 * of the corresponding {@link SubTrace}.
	 * 
	 * If the current {@link Callable} is the root of the current {@link SubTrace} that has been
	 * called by another {@link SubTrace} then this method returns the {@link TraceInvocation}
	 * instance of the (other) calling {@link SubTrace}.
	 * 
	 * If the current {@link Callable} is the root and the current {@link SubTrace} is the root of
	 * the entire logical {@link Trace} then this method returns null.
	 * 
	 * @return the parent
	 */
	Callable getParent();

	/**
	 * 
	 * @return returns the {@link SubTrace} this {@link Callable} belongs to.
	 */
	SubTrace getContainingTrace();

	/**
	 * 
	 * @return execution time (or duration) in milliseconds
	 */
	long getExecutionTime();

	/**
	 * 
	 * @return entry timestamp to the {@link Callable} in milliseconds
	 */
	long getEntryTime();

	/**
	 * 
	 * @return exit timestamp when leaving the {@link Callable} in milliseconds
	 */
	long getExitTime();

	/**
	 * Returns the full qualified signature of the corresponding operation (including all
	 * full-qualified types of the parameters)
	 * 
	 * Example:
	 * 
	 * For operation "org.my.Class.myMethod(org.my.Parameter)" this method would return
	 * "org.my.Class.myMethod(org.my.Parameter)"
	 * 
	 * @return full qualified signature
	 */
	String getSignature();

	/**
	 * Returns the simple name of the corresponding operation.
	 * 
	 * Example:
	 * 
	 * For operation "org.my.Class.myMethod(org.my.Parameter)" this method would return "myMethod"
	 * 
	 * @return simple name
	 */
	String getSimpleName();

	/**
	 * Returns the full name of the corresponding operation including the package name
	 * 
	 * Example:
	 * 
	 * For operation "org.my.Class.myMethod(org.my.Parameter)" this method would return
	 * "org.my.Class.myMethod"
	 * 
	 * @return full name
	 */
	String getFullName();

	/**
	 * 
	 * @return true if this {@link Callable} is a constructor
	 */
	boolean isConstructor();

	/**
	 * Lables convey simple additional information to for individual {@link Callable} instances.
	 * 
	 * @return a list of labels
	 */
	List<String> getLables();

	/**
	 * Checks whether this {@link Callable} is labled with the given value.
	 * 
	 * @param label
	 *            the label value to check for
	 * @return true if label is attached to this {@link Callable}, otherwise false
	 */
	boolean hasLabel(String label);

	/**
	 * Returns the position (i.e. index) of this {@link Callable} in the corresponding
	 * {@link SubTrace}. The order of the indexes is analgous to the original execution order of the
	 * {@link Callable} instances. The root element of the corresponding {@link SubTrace} has an
	 * index of 0.
	 * 
	 * @return the position
	 */
	long getPosition();

	/**
	 * Returns the depth of this {@link Callable} relative to the root of the correspoding
	 * {@link SubTrace}. The root has a depth of 0.
	 * 
	 * @return the depth
	 */
	long getDepth();

	/**
	 * 
	 * @return a list of all additional information objects
	 */
	List<AdditionalInformation> getAdditionalInformation();

	/**
	 * Returns a list of all additional information objects of the provided type.
	 * 
	 * @param type
	 *            the {@link AdditionalInformation} type for which the information should be
	 *            retrieved
	 * @return list of additional information objects of the provided type
	 */
	<T> List<T> getAdditionalInformation(Class<T> type);

}
