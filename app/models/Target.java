package models;

import java.io.IOException;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;

import Helper.QRHelper;

import com.google.zxing.WriterException;

@Entity
public class Target extends BaseModel {

	private String description;
	private String message;

	private int bounty;

	private float latitude;
	private float longitude;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hunt_id", nullable = false)
	private Hunt hunt;

	// Players that found this target
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Player> players;

	private String qrCodePath = null;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public Hunt getHunt() {
		return hunt;
	}

	public void setHunt(Hunt hunt) {
		this.hunt = hunt;
	}

	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getBounty() {
		return bounty;
	}

	public void setBounty(int bounty) {
		this.bounty = bounty;
	}

	@PostPersist
	public void setQRCodePath() throws IOException, WriterException {
		if (qrCodePath == null) {
			this.qrCodePath = QRHelper.createQRImage(this);
		}
	}
}
