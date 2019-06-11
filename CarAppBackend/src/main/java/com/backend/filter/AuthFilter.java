package com.backend.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.backend.entity.dto.ErrorDto;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthFilter implements ContainerRequestFilter {

	private static final ErrorDto ERROR_401 = new ErrorDto(0, "Request must include \"Authorization\" header.");
	private static final ErrorDto ERROR_403 = new ErrorDto(0, "Auth token not valid");
	private static final String JWT_SECRET = "jwt.secret";
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
			authHeader = authHeader.substring(BEARER.length());
			String secret = System.getProperty(JWT_SECRET);
			Algorithm alg = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(alg).build();
			try {
				verifier.verify(authHeader);
			} catch (JWTVerificationException e) {
				requestContext.abortWith(Response.status(Status.FORBIDDEN).entity(ERROR_403).build());
			}
		}
	}

}
