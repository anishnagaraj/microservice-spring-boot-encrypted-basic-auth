
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
@JsonPropertyOrder({ "requireHttpAuth", "hashAlgorithm", "hashIterations", "users" })
public class AuthConfig {

	@JsonProperty("requireHttpAuth")
	private Boolean requireHttpAuth;

	@JsonProperty("hashAlgorithm")
	private String hashAlgorithm;

	@JsonProperty("hashIterations")
	private Integer hashIterations;

	@JsonProperty("users")
	private List<UserWithHashedPassword> users;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("requireHttpAuth")
	public Boolean getRequireHttpAuth() {
		return requireHttpAuth;
	}

	@JsonProperty("requireHttpAuth")
	public void setRequireHttpAuth(Boolean requireHttpAuth) {
		this.requireHttpAuth = requireHttpAuth;
	}

	@JsonProperty("hashAlgorithm")
	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	@JsonProperty("hashAlgorithm")
	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	@JsonProperty("hashIterations")
	public Integer getHashIterations() {
		return hashIterations;
	}

	@JsonProperty("hashIterations")
	public void setHashIterations(Integer hashIterations) {
		this.hashIterations = hashIterations;
	}

	@JsonProperty("users")
	public List<UserWithHashedPassword> getUsers() {
		return users;
	}

	@JsonProperty("users")
	public void setUsers(List<UserWithHashedPassword> users) {
		this.users = users;
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
