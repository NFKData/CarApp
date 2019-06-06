package com.backend.entity.dto;

import com.backend.entity.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name" })
public class CountryDto {

	private Country country;

	public CountryDto(Country country) {
		this.country = country;
	}

	@JsonProperty("id")
	public Integer getId() {
		return country.getId();
	}

	@JsonProperty("name")
	public String getName() {
		return country.getName();
	}
	
	public boolean equals(Object other) {
		return this == other || country.equals(other) || (other instanceof CountryDto && country.equals(((CountryDto)other).country));
	}

}
