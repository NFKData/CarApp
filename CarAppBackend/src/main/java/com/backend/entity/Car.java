package com.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.interceptor.Interceptors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.backend.interceptor.LogInterceptor;
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
@Interceptors(LogInterceptor.class)
public class Car {

	@Column(name = "uuid")
	@Id
	private String id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "brand")
	private Brand brand;

	@Column(name = "registration")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	@NotNull(message = "Registration mustn't be null and has to have the ISO format")
	private LocalDateTime registration;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "country")
	private Country country;

	@Hidden
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Hidden
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;

	@PrePersist
	public void prePersit() {
		this.createdAt = LocalDateTime.now();
		this.id = UUID.randomUUID().toString();
	}

	@PreUpdate
	public void preUpdate() {
		this.lastUpdated = LocalDateTime.now();
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
