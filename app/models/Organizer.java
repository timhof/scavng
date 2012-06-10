package models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Organizer extends Model {

	@OneToOne
	private User user;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "organizer")
	private Collection<Hunt> hunts;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Hunt> getHunts() {
		return hunts;
	}

	public void setHunts(Collection<Hunt> hunts) {
		this.hunts = hunts;
	}
}
