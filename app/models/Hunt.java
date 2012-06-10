package models;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Hunt extends Model {

	public enum HuntType {
		OPEN, INVITE_ONLY
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "hunt")
	private Collection<Invitation> invitations;

	private final HuntType huntType = HuntType.OPEN;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organizer_id")
	private Organizer organizer;

	@ManyToMany
	private Collection<Player> players;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "hunt")
	private Collection<Target> targets;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Organizer getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}

	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}

	public Collection<Target> getTargets() {
		return targets;
	}

	public void setTargets(Collection<Target> targets) {
		this.targets = targets;
	}

	public Collection<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(Collection<Invitation> invitations) {
		this.invitations = invitations;
	}

	public HuntType getHuntType() {
		return huntType;
	}

}
