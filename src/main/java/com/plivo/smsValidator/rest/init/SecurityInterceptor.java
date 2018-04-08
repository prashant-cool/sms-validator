package com.plivo.smsValidator.rest.init;

import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.plivo.smsValidator.dao.AccountDAO;
import com.plivo.smsValidator.model.Account;

@Provider
@RequestScoped
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter {
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";

	@Inject
	private Logger log;

	@Inject
	private AccountDAO dao;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		MultivaluedMap<String, String> headers = requestContext.getHeaders();
		List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
		if (authorization == null || authorization.isEmpty()) {
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Credential required").build());
			return;
		}

		String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword));

		StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		String username = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		log.info("username = " + username);

		Account account = dao.findByUsername(username);
		if (account == null || !account.getAuthId().equals(password)) {
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Invalid Credentials").build());
			return;
		}

		requestContext.getHeaders().add("LoggedinAccountId", account.getId().toString());
	}
}