package com.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.backend.control.BrandService;
import com.backend.control.CountryService;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.CountryNotFoundException;
import com.backend.serialization.LocalDateTimeAdapter;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * Entity that represents a car
 * 
 * @author gmiralle
 */
@Entity
@Table(name = "Car")
@NamedQuery(name = "CarService.findAllCars", query = "SELECT c FROM Car c")
public class Car {

	@Column(name = "uuid")
	@Id
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id", updatable = false)
	@NotNull(message = "Brand mustn't be null")
	private Brand brand;

	@Column(name = "registration")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	@NotNull(message = "Registration mustn't be null and has to have the ISO format")
	private LocalDateTime registration;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id", updatable = false)
	@NotNull(message = "Country mustn't be null")
	private Country country;

	@Hidden
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Hidden
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.id = UUID.randomUUID().toString();
	}

	@PreUpdate
	public void preUpdate() {
		this.lastUpdated = LocalDateTime.now();
	}
	
	@PostPersist
	public void postPersist() throws NamingException, BrandNotFoundException, CountryNotFoundException {
		InitialContext ctx = new InitialContext();
		BrandService brandService = (BrandService) ctx.lookup("java:comp/env/brandService");
		CountryService countryService = (CountryService) ctx.lookup("java:comp/env/countryService");
		this.brand = brandService.getBrand(this.brand.getId());
		this.country = countryService.getCountry(this.country.getId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public LocalDateTime getRegistration() {
		return registration;
	}

	public void setRegistration(LocalDateTime registration) {
		this.registration = registration;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other instanceof Car))
			return false;
		if (other == this)
			return true;

		Car o = (Car) other;
		return o.id.equals(this.id);
	}

	@Override
	public String toString() {
		return "Car = {id:" + id + ", brand:" + brand + ", registration:" + registration + ", country:" + country
				+ ", createdAt:" + createdAt + ", lastUpdated:" + lastUpdated + "}";
	}
}
