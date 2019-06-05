package com.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Hidden;

@Entity
@NamedQuery(name = "CountryService.findAllCountries", query = "SELECT c FROM Country c")
@Table(name = "Country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "country_id")
	private Integer id;
	
	@NotNull(message = "Name mustn't be null")
	@Column(name = "name")
	private String name;
	
	@Hidden
	@Column(name = "createdAt")
	private LocalDateTime createdAt;
	
	@Hidden
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
	@Hidden
	private List<Car> cars;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.lastUpdated = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public List<Car> getCars() {
		return cars;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public boolean equals(Object other) {
		if(!(other instanceof Country))
			return false;
		if(other == this)
			return true;
		Country o = (Country) other;
		return id.equals(o.id);
	}
}
