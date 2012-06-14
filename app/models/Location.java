package models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Location extends Model {

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "location")
	private Collection<Hunt> hunts;

	private String description;

	private String streetAddress;

	private String zipCode;

	private String state;

	private String town;

	public Collection<Hunt> getHunts() {
		return hunts;
	}

	public void setHunts(Collection<Hunt> hunts) {
		this.hunts = hunts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
}
