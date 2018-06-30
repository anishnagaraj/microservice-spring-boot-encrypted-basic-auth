package com.anishnagaraj.poc.microservice.security;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Component
public class EncryptedBasicAuthFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptedBasicAuthFilter.class);

	/**
	 * This is an insecure algorithm used for testing; DO NOT USE IT in
	 * production.
	 */
	private static final String DECRYPTOR_ALGORITHM = "PBEWITHSHA1ANDRC4_40";

	private static final String PASSWORD = "Password123!";

	protected static final String AUTH_CONFIG_JSON = "config/auth-config.json";
	
    private static final String AUTH_INFO_USERNAME = "username";
    private static final String AUTH_INFO_PASSWORD = "password";

	protected JsonObject appConfigJsonObject;

	protected AuthConfig authConfig;

	@Autowired
	CryptoProvider cryptoProvider;

	@PostConstruct
	void onPostConstruct() {
		try {
			//initialize the decryptor with the provided algorithm and password
			cryptoProvider.initialiseDecryptor(DECRYPTOR_ALGORITHM, PASSWORD);
			
			//initialize the hash digester with the provided hashing algorithm and hash iterations
			Gson gson = new Gson();
			final JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(AUTH_CONFIG_JSON));
			appConfigJsonObject = jsonElement.getAsJsonObject();
			authConfig = gson.fromJson(new FileReader(AUTH_CONFIG_JSON), AuthConfig.class);
			cryptoProvider.initialiseDigester(authConfig.getHashAlgorithm(), authConfig.getHashIterations());
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			LOGGER.error("Error while readihng auth-config.json", e);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain)
			throws ServletException, IOException {
		try {

			final String userName = httpRequest.getHeader(AUTH_INFO_USERNAME);
			final String encryptedPassword = httpRequest.getHeader(AUTH_INFO_PASSWORD);

			final Optional<UserWithHashedPassword> userMatch = authConfig.getUsers().parallelStream()
					.filter(userConfig -> userConfig.getUsername().equals(userName)
							&& cryptoProvider.matchesHash(userConfig.getPasswordHash(),
									cryptoProvider.decrypt(encryptedPassword), userConfig.getPasswordSalt()))
					.findFirst();

			if (userMatch.isPresent()) {
				chain.doFilter(httpRequest, httpResponse);
			} else {
				httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
				httpResponse.getWriter().write("Unauthorized");
				httpResponse.getWriter().close();
			}
		} finally {
			MDC.clear();
		}

	}

}
