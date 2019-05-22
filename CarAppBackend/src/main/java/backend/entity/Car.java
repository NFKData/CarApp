package backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import backend.serialization.LocalDateTimeAdapter;

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

	@Column(name = "brand")
	@NotNull(message = "Brand mustn't be null")
	private String brand;

	@Column(name = "registration")
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	@NotNull(message = "Registration mustn't be null and has to have the format dd/MM/yyyy@HH:mm:ss.SSS")
	private LocalDateTime registration;

	@Column(name = "country")
	@NotNull(message = "Country mustn't be null")
	private String country;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public LocalDateTime getRegistration() {
		return registration;
	}

	public void setRegistration(LocalDateTime registration) {
		this.registration = registration;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
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
		return "{id:" + id + ", brand:" + brand + ", registration:" + registration + ", country:" + country
				+ ", createdAt:" + createdAt + ", lastUpdated:" + lastUpdated + "}";
	}
}
