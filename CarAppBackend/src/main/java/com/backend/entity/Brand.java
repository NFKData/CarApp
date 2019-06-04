package com.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@NamedQuery(name = "BrandService.findAllBrands", query = "SELECT b FROM Brand b")
@Table(name = "Brand")
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private String id;
	
	@NotNull(message = "Name cannot be null")
	@Column(name = "name")
	private String name;
	
	@Hidden
	@Column(name = "createdAt")
	private LocalDateTime createdAt;
	
	@Hidden
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;
	
	@OneToMany(mappedBy = "brand")
	private List<Car> cars;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
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

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	public boolean equals(Object other) {
		if(!(other instanceof Brand))
			return false;
		if(other == this)
			return true;
		Brand o = (Brand) other;
		return id.equals(o.id);
	}
	
}
