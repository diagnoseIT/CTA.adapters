/**
 * 
 */
package org.diagnoseit.spike.kieker.trace.impl.callables;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.callables.HTTPMethod;
import rocks.cta.api.core.callables.HTTPRequestProcessing;

/**
 * @author Okanovic
 *
 */
public class HttpRequestProcessingImpl extends AbstractNestingCallableImpl implements HTTPRequestProcessing {
	public HttpRequestProcessingImpl() {
		this(null, 0, null, null, null, 0, 0);
		// TODO only temporary, must be removed later
	}

	public HttpRequestProcessingImpl(AbstractNestingCallableImpl parent, long entryTime, List<Integer> labelIds, List<AdditionalInformation> additionInfos,
			SubTrace containingSubTrace, long responseTime, long exclusiveTime) {
		super(parent, entryTime, labelIds, additionInfos, containingSubTrace, responseTime, exclusiveTime);
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException();
	}

	private static final long serialVersionUID = 6495741068107006151L;

	// TODO unfinished

	@Override
	public Optional<Map<String, String>> getHTTPAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Map<String, String>> getHTTPHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Map<String, String[]>> getHTTPParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Map<String, String>> getHTTPSessionAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<HTTPMethod> getRequestMethod() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		return null;
	}

}
