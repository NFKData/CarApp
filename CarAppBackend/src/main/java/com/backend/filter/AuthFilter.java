package com.backend.filter;

import java.io.IOException;
import java.text.ParseException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.backend.entity.dto.ErrorDto;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthFilter implements ContainerRequestFilter {

	private static final ErrorDto ERROR_401 = new ErrorDto(0, "Request must include \"Authorization\" header.");
	private static final ErrorDto ERROR_403 = new ErrorDto(0, "Auth token not valid");
	private static final String JWT_SECRET = "JWT_SECRET";
	private static final String BEARER = "Bearer ";
	private static final String OPTIONS = "OPTIONS";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (requestContext.getMethod().equals(OPTIONS)) {
			return;
		}
		String authHeader = requestContext.getHeaderString("Authorization");
		if (authHeader == null) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(ERROR_401).build());
		} else {
			verify(requestContext, authHeader.substring(BEARER.length()), System.getenv(JWT_SECRET).getBytes());
		}
	}

	private void verify(ContainerRequestContext requestContext, String token, byte[] secret) {
		try {
			JWSVerifier verifier = new MACVerifier(secret);
			SignedJWT.parse(token).verify(verifier);
		} catch (JOSEException | ParseException e) {
			requestContext.abortWith(Response.status(Status.FORBIDDEN).entity(ERROR_403).build());
		}
	}

}
