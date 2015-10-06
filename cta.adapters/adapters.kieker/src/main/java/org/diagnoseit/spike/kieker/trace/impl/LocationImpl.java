/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.io.Serializable;

import kieker.tools.traceAnalysis.systemModel.Execution;
import rocks.cta.api.core.Location;
import rocks.cta.api.core.Trace;
import rocks.cta.api.utils.StringUtils;

/**
 * Implementation of Location interface.
 * 
 * @author Okanovic
 *
 */
public class LocationImpl implements Location, Serializable {
	private static final long serialVersionUID = 8959194364402329417L;
	private String host = Trace.UNKOWN;
	private String runTimeEnvironment = Trace.UNKOWN;
	private String application = Trace.UNKOWN;
	private String businessTransaction = Trace.UNKOWN;
	private String nodeType = Trace.UNKOWN;

	public LocationImpl() {
	}

	public LocationImpl(String host, String runTimeEnvironment, String application, String businessTransaction, String nodeType) {
		this.host = host;
		this.runTimeEnvironment = runTimeEnvironment;
		this.application = application;
		this.businessTransaction = businessTransaction;
		this.nodeType = nodeType;
	}

	public LocationImpl(Execution receivingExecution) {
		this(receivingExecution.getAllocationComponent().getExecutionContainer().getName(), Trace.UNKOWN, Trace.UNKOWN, Trace.UNKOWN, Trace.UNKOWN);
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public String getRuntimeEnvironment() {
		return getRunTimeEnvironment();
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
	public String getNodeType() {
		return nodeType;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getRunTimeEnvironment() {
		return runTimeEnvironment;
	}

	public void setRunTimeEnvironment(String runTimeEnvironment) {
		this.runTimeEnvironment = runTimeEnvironment;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setBusinessTransaction(String businessTransaction) {
		this.businessTransaction = businessTransaction;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location))
			return false;
		LocationImpl otherLocation = (LocationImpl) obj;
		return this.host.equals(otherLocation.host) && this.runTimeEnvironment.equals(otherLocation.runTimeEnvironment) && this.application.equals(otherLocation.application)
				&& this.businessTransaction.equals(otherLocation.businessTransaction) && this.nodeType.equals(otherLocation.nodeType);
	}
}