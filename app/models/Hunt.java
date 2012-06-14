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
		OPEN("Open"), INVITE_ONLY("Invite-only");
		private final String label;

		private HuntType(String label) {
			this.label = label;
		}

		public String label() {
			return this.label;
		}
	}

	public enum HuntStatus {
		CREATED("Created"), IN_PROGRESS("In-progress"), COMPLETED("Completed");
		private final String label;

		private HuntStatus(String label) {
			this.label = label;
		}

		public String label() {
			return this.label;
		}
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "hunt")
	private Collection<Invitation> invitations;

	private HuntType huntType = HuntType.OPEN;
	private HuntStatus huntStatus = HuntStatus.CREATED;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organizer_id")
	private Organizer organizer;

	@ManyToMany
	private Collection<Player> players;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "hunt")
	private Collection<Target> targets;

	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

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

	public HuntStatus getHuntStatus() {
		return huntStatus;
	}

	public void setHuntStatus(HuntStatus huntStatus) {
		this.huntStatus = huntStatus;
	}

	public void setHuntType(HuntType huntType) {
		this.huntType = huntType;
	}

}
