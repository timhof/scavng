package models;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import models.deadbolt.RoleHolder;
import play.Logger;
import play.db.jpa.Model;
import sun.misc.BASE64Encoder;

@Entity
public class User extends Model implements RoleHolder, MementoBridge<User> {

	public enum UserStatus {
		UNREGISTERED, REGISTERED, REGISTERED_WITH_DEVICE
	}

	@Column(unique = true)
	private String email;

	private String passwordSalt;

	private String passwordHash;

	private String name;
	private String deviceId;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	public Collection<UserRole> userRoles;

	private UserStatus status = UserStatus.UNREGISTERED;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "user")
	private Collection<Invitation> invitations;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "user")
	private Collection<Player> players;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Invitation> getInvitations() {
		if (this.invitations == null) {
			this.invitations = new HashSet<Invitation>();
		}
		return invitations;
	}

	public void setInvitations(Collection<Invitation> invitations) {
		this.invitations = invitations;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}

	@Override
	public List<UserRole> getRoles() {
		return new ArrayList<UserRole>(userRoles);
	}

	public void setUserRoles(Collection<UserRole> roles) {
		this.userRoles = roles;
	}

	public void addRole(UserRole r) {
		if (userRoles == null) {
			userRoles = new HashSet<UserRole>(5);
		}
		userRoles.add(r);
	}

	public void setPassphrase(String passphrase) {

		// Don't set password if passphrase is null or empty string
		if (passphrase == null || "".equals(passphrase.trim())) {
			Logger.info("PASSPHASE IS EMPTY");
		} else {
			try {
				if (this.passwordSalt == null
						|| this.passwordSalt.length() == 0) {
					this.passwordSalt = new BASE64Encoder().encode(Hash
							.generateSalt());
				}
				passwordHash = Hash.SHA1(passphrase, this.passwordSalt);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// public boolean validatePassphrase(String passphraseCandidate) {
	// if (this.passwordSalt == null || this.passwordHash == null
	// || this.passwordSalt.length() == 0
	// || this.passwordHash.length() == 0) {
	// return false;
	// }
	// String hashedPassphraseCandidate;
	// try {
	// hashedPassphraseCandidate = Hash.SHA1(passphraseCandidate,
	// this.passwordSalt);
	// if (hashedPassphraseCandidate == null
	// || !hashedPassphraseCandidate.equals(this.passwordHash)) {
	// return false;
	// }
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// return true;
	// }

	public boolean authenticate(String passphraseCandidate) {
		boolean authenticated = false;
		if (passwordHash != null && passwordSalt != null) {
			String passHashCandidate;
			try {
				passHashCandidate = Hash
						.SHA1(passphraseCandidate, passwordSalt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if (passHashCandidate != null
					&& passwordHash.equalsIgnoreCase(passHashCandidate)) {
				authenticated = true;
			}
		}
		return authenticated;
	}

	@Override
	public String toMemento(User t) {
		if (t == null)
			return null;
		return t.id.toString();
	}

	@Override
	public User fromMemento(String m) {
		if (m == null)
			return null;
		Long i = Long.parseLong(m);
		return User.findById(i);
	}

}
