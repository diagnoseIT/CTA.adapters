/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import rocks.cta.api.core.Location;


/**
 * @author Okanovic
 *
 */
public class LocationImpl implements Location {
	String host;
	String runtimeEnvironment;
	String application;
	String businessTransaction;

	public LocationImpl(String host, String runtimeEnvironment, String application, String businessTransaction) {
		super();
		this.host = host;
		this.runtimeEnvironment = runtimeEnvironment;
		this.application = application;
		this.businessTransaction = businessTransaction;
	}

	public LocationImpl(AbstractMessage message) {
		this(message.getReceivingExecution().getAllocationComponent().getExecutionContainer().getName(), UNKOWN, UNKOWN, UNKOWN);
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public String getRuntimeEnvironment() {
		return runtimeEnvironment;
	}

	@Override
	public String getApplication() {
		return application;
	}

	@Override
	public String getBusinessTransaction() {
		return businessTransaction;
	}

	@Override
	public String toString() {
		return host + ":" + runtimeEnvironment + ":" + application + ":" + businessTransaction;
	}

	@Override
	public boolean equals(Object obj) {
		return host.equals(((Location) obj).getHost())
				&& runtimeEnvironment.equals(((Location) obj).getRuntimeEnvironment())
				&& application.equals(((Location) obj).getApplication())
				&& businessTransaction.equals(((Location) obj).getBusinessTransaction());
	}
}