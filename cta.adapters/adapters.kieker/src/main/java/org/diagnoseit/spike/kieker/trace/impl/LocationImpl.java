/**
 *
 */
package org.diagnoseit.spike.kieker.trace.impl;

import java.io.Serializable;
import java.util.Optional;

import kieker.tools.traceAnalysis.systemModel.Execution;
import rocks.cta.api.core.Location;
import rocks.cta.api.core.Trace;
import rocks.cta.api.utils.StringUtils;

import javax.swing.text.html.Option;

/**
 * Implementation of Location interface.
 *
 * @author Okanovic
 */
public class LocationImpl implements Location, Serializable {
    private static final long serialVersionUID = 8959194364402329417L;
    private static final String UNKNOWN = "UNKNOWN";
    private String host = UNKNOWN;
    private String runTimeEnvironment = UNKNOWN;
    private String application = UNKNOWN;
    private String businessTransaction = UNKNOWN;
    private String nodeType = UNKNOWN;

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
        this(receivingExecution.getAllocationComponent().getExecutionContainer().getName(), UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Optional<String> getRuntimeEnvironment() {
        return runTimeEnvironment.equals(UNKNOWN) ? Optional.empty() : Optional.ofNullable(runTimeEnvironment);
    }

    @Override
    public Optional<String> getApplication() {
        return application.equals(UNKNOWN) ? Optional.empty() : Optional.ofNullable(application);
    }

    @Override
    public Optional<String> getBusinessTransaction() {
        return businessTransaction.equals(UNKNOWN) ? Optional.empty() : Optional.ofNullable(businessTransaction);
    }

    @Override
    public Optional<String> getNodeType() {
        return nodeType.equals(UNKNOWN) ? Optional.empty() : Optional.ofNullable(nodeType);
    }

    @Override
    public String toString() {
        return StringUtils.getStringRepresentation(this);
    }

    public void setHost(String host) {
        this.host = host;
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