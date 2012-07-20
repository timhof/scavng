package models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Hunt extends BaseModel {

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

	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
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

	public Hunt() {

	}

	public Hunt(String description, Organizer organizer) {
		this.description = description;
		this.organizer = organizer;
	}

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
		if (players == null) {
			players = new ArrayList<Player>();
		}
		return players;
	}

	public void addPlayer(Player player) {
		getPlayers().add(player);
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}

	public Collection<Target> getTargets() {
		if (this.targets == null) {
			this.targets = new ArrayList<Target>();
		}
		return targets;
	}

	public void setTargets(Collection<Target> targets) {
		this.targets = targets;
	}

	public void addTarget(Target target) {
		target.setHunt(this);
		this.getTargets().add(target);
	}

	public Collection<Invitation> getInvitations() {
		if (invitations == null) {
			this.invitations = new ArrayList<Invitation>();
		}
		return invitations;
	}

	public void addInvitation(Invitation invitation) {
		this.getInvitations().add(invitation);
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

	public boolean hasPlayerForUser(User user) {
		boolean hasPlayer = false;
		if (user != null) {
			for (Player player : players) {
				if (player.getUser().equals(user)) {
					hasPlayer = true;
				}
			}
		}
		return hasPlayer;
	}
}
