package models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Player extends Model {

	private String nickname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hunt_id")
	private Hunt hunt;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "players")
	private Collection<Target> targets;

	public Collection<Target> getTargets() {
		return targets;
	}

	public void setTargets(Collection<Target> targets) {
		this.targets = targets;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Hunt getHunt() {
		return hunt;
	}

	public void setHunt(Hunt hunt) {
		this.hunt = hunt;
	}

	public int getScore() {

		int score = 0;

		for (Target target : targets) {
			score += target.getBounty();
		}
		return score;
	}
}
