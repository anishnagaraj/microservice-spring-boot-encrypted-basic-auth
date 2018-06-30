
package com.anishnagaraj.poc.microservice.security;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "required", "identityHeader" })
public class Authorisation {

	@JsonProperty("required")
	private Boolean required;

	@JsonProperty("identityHeader")
	private String identityHeader;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("required")
	public Boolean getRequired() {
		return required;
	}

	@JsonProperty("required")
	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonProperty("identityHeader")
	public String getIdentityHeader() {
		return identityHeader;
	}

	@JsonProperty("identityHeader")
	public void setIdentityHeader(String identityHeader) {
		this.identityHeader = identityHeader;
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
