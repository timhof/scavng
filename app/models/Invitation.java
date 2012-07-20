package models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Invitation extends BaseModel {

	public enum InvitationStatus {
		SENT, ACCEPTED, DECLINED, REVOKED;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hunt_id")
	Hunt hunt;

	private InvitationStatus status = InvitationStatus.SENT;

	public Hunt getHunt() {
		return hunt;
	}

	public void setHunt(Hunt hunt) {
		this.hunt = hunt;
	}

	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	public void setAccepted() {

		Player player = user.joinHunt(hunt);
		if (player != null) {

			hunt.addPlayer(player);
			hunt.save();
		}

		status = InvitationStatus.ACCEPTED;
	}
}
