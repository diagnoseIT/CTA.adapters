package org.diagnoseit.spike.trace.impl;

import org.diagnoseit.spike.trace.Location;

public class LocationImpl implements Location {

	private String host;
	private String container;
	
	public LocationImpl(String platformId) {
		String [] strings = platformId.split(":");
		host = strings[0];
		container = strings[1];
	}
	
	public String getHost() {
		return host;
	}

	public String getContainer() {
		return container;
	}

}
