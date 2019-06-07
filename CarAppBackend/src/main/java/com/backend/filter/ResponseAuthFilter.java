package com.backend.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class ResponseAuthFilter implements ContainerResponseFilter {

	private static final String AUTHORIZATION = "Authorization";
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		List<Object> authHeaders = new ArrayList<>();
		authHeaders.add(requestContext.getHeaderString(AUTHORIZATION));
		responseContext.getHeaders().put(AUTHORIZATION, authHeaders);
		
	}

}
