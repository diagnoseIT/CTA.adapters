package org.diagnoseit.spike.dummy.trace.impl;

import org.diagnoseit.spike.shared.trace.Location;

public class LocationImpl implements Location {

	private String host;
	private String runtime;
	private String application;
	private String bTransaction;

	public LocationImpl(String platformId) {
		String[] strings = platformId.split(":");
		switch (strings.length) {
		default:
			bTransaction = strings[3];
		case 3:
			application = strings[2];
		case 2:
			runtime = strings[1];
		case 1:
			host = strings[0];
			break;
		case 0:
			host = "unknown";
			runtime = "unknown";
			application = "unknown";
			bTransaction = "unknown";
			break;

		}

		
		
	}

	public String getHost() {
		return host;
	}

	public String getRuntimeEnvironment() {
		return runtime;
	}

	public String getApplication() {
		return application;
	}

	public String getBusinessTransaction() {
		return bTransaction;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((application == null) ? 0 : application.hashCode());
		result = prime * result
				+ ((bTransaction == null) ? 0 : bTransaction.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((runtime == null) ? 0 : runtime.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationImpl other = (LocationImpl) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (bTransaction == null) {
			if (other.bTransaction != null)
				return false;
		} else if (!bTransaction.equals(other.bTransaction))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (runtime == null) {
			if (other.runtime != null)
				return false;
		} else if (!runtime.equals(other.runtime))
			return false;
		return true;
	}
	
	
	

}
