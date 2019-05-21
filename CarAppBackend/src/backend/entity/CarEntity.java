package backend.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Car")
public class CarEntity {
	
	@Column(name="uuid")
	@Id
	private String id;
	
	@Column(name="brand")
	private String brand;
	
	@Column(name="registration")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp registration;
	
	@Column(name="country")
	private String country;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createdAt;
	
	@Column(name="last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp lastUpdated;

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

	public Timestamp getRegistration() {
		return registration;
	}

	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) 
			return false;
		if(!(other instanceof CarEntity))
			return false;
		if(other == this)
			return true;
		
		CarEntity o = (CarEntity) other;
		return o.id.equals(this.id);
	}
}
