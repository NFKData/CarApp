package com.backend.entity.dto;

import com.backend.entity.Brand;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name" })
public class BrandDto {

	private Brand brand;

	public BrandDto(Brand brand) {
		this.brand = brand;
	}

	@JsonProperty("id")
	public Integer getId() {
		return brand.getId();
	}

	@JsonProperty("name")
	public String getName() {
		return brand.getName();
	}
	
	public boolean equals(Object other) {
		return this == other || brand.equals(other) || (other instanceof BrandDto && brand.equals(((BrandDto)other).brand));
	}

}
