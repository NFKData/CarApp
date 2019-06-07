package com.backend.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

	private static final String SCOPE = "openid email";
	private static final String AUTHORIZATION = "authorization";
	private static final String BEARER = "Bearer ";
	private static final String DOMAIN = "everis-carapp.eu.auth0.com";
	private static final String CLIENT_ID = "rrDYabEzebUPsoDSsRdlxmd5MESQPAck";
	private static final String CLIENT_SECRET = "bexcssRgKirgeoGHPdePPk59YfpazPbQjvKYnos126do9U3J8Uz1PAsMhUTpVZZI";
	private static final String API_AUDIENCE = "https://everis-carapp.eu.auth0.com/api/v2/";
	private static final String CONNECTION = "Username-Password-Authentication";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authHeader = requestContext.getHeaderString("Authorization");
		if (authHeader == null) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
					.entity("Request must include \"Authorization\" header.").build());
		}
	
		String[] authData = new String(Base64.getDecoder().decode((authHeader.split("\\s+"))[1].getBytes())).split(":");
		AuthAPI authApi = new AuthAPI(DOMAIN, CLIENT_ID, CLIENT_SECRET);
		try {
		TokenHolder tokenHolderLogin = authApi.login(authData[0], authData[1]).setAudience(API_AUDIENCE)
				.setScope(SCOPE).execute();
		List<String> authHeaders = new ArrayList<>();
		authHeaders.add(BEARER + tokenHolderLogin.getAccessToken());
		requestContext.getHeaders().put(AUTHORIZATION, authHeaders);
		} catch(Auth0Exception ex) {
			CreatedUser createdUser = authApi.signUp(authData[0], authData[1], CONNECTION).execute();
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity("A verification email should be sent to " + createdUser.getEmail()).build());
		}
	}

}
