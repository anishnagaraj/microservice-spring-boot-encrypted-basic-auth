
package com.anishnagaraj.poc.microservice.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "username", "passwordHash", "passwordSalt", "authorisation", "roles" })
public class UserWithHashedPassword {

	@JsonProperty("username")
	private String username;

	@JsonProperty("passwordHash")
	private String passwordHash;

	@JsonProperty("passwordSalt")
	private String passwordSalt;

	@JsonProperty("authorisation")
	private Authorisation authorisation;

	@JsonProperty("roles")
	private List<String> roles = null;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("passwordHash")
	public String getPasswordHash() {
		return passwordHash;
	}

	@JsonProperty("passwordHash")
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@JsonProperty("passwordSalt")
	public String getPasswordSalt() {
		return passwordSalt;
	}

	@JsonProperty("passwordSalt")
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	@JsonProperty("authorisation")
	public Authorisation getAuthorisation() {
		return authorisation;
	}

	@JsonProperty("authorisation")
	public void setAuthorisation(Authorisation authorisation) {
		this.authorisation = authorisation;
	}

	@JsonProperty("roles")
	public List<String> getRoles() {
		return roles;
	}

	@JsonProperty("roles")
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
