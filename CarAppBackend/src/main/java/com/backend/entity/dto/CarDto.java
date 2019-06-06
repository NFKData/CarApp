package com.backend.entity.dto;

import com.backend.entity.Car;
import com.backend.serialization.LocalDateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "country", "brand", "registration" })
public class CarDto {

	private Car car;

	public CarDto(Car car) {
		this.car = car;
	}

	@JsonProperty("id")
	public String getId() {
		return car.getId();
	}

	@JsonProperty("brand")
	public BrandDto getBrand() {
		return new BrandDto(car.getBrand());
	}

	@JsonProperty("country")
	public CountryDto getCountry() {
		return new CountryDto(car.getCountry());
	}

	@JsonProperty("registration")
	public String getRegistration() throws Exception {
		return new LocalDateTimeAdapter().marshal(car.getRegistration());
	}
	
	public boolean equals(Object other) {
		return this == other || car.equals(other) || (other instanceof CarDto && car.equals(((CarDto)other).car));
	}

}
