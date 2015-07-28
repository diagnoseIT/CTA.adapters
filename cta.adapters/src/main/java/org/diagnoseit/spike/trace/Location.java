package org.diagnoseit.spike.trace;

public interface Location {
	String getHost();
	String getRuntimeEnvironment();
	String getApplication();
	String getBusinessTransaction();
}
