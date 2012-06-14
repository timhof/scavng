package controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import models.User;
import models.User.UserStatus;
import models.UserRole;
import play.Logger;
import play.mvc.Controller;
import util.MapKeys;

/**
 * Created by IntelliJ IDEA. User: rjudd Date: 2/7/12 Time: 10:51 AM
 */
public class Login extends Controller {

	public static void index() {
		render();
	}

	public static void login(String email, String passphrase) {

		Logger.info("Loggin IN");

		User user = null;
		Collection<User> users = User.find("email = ?", email).fetch();
		if (!users.isEmpty()) {
			user = users.iterator().next();
		}
		boolean authenticated = false;
		if (user != null) {
			authenticated = user.authenticate(passphrase);
		} else {
			Logger.info("USER IS NULL");
		}
		Logger.info("Authenticated?: " + authenticated);

		if (authenticated) {
			MapKeys.User.put(session, new User(), user);
			MapKeys.User.put(renderArgs, user);

			Hunts.index();
		} else {
			index();
		}
	}

	public static void logout() {
		MapKeys.User.remove(session);
		index();
	}

	public static void register(User user) {
		render();
	}

	public static void createUser(User user) {

		// This call will get temporary user.
		// This must be executed before the new user is inserted in order
		// to avoid constraint violations (email must be unique)
		User existingUser = getTemporaryUser(user);
		if (existingUser != null) {
			user.copyTo(existingUser);
			user = existingUser;
		}
		user.setStatus(UserStatus.REGISTERED);
		user.setUserRoles(Arrays.asList(new UserRole[] { UserRole.Admin }));
		user.save();

		Application.index();
	}

	/**
	 * Get invitations that were created before this user was created.
	 * Organizers can invite players by entering their email. This does not
	 * require that the player is already a member. This function returns that
	 * user if one exists
	 * 
	 * @return
	 */
	private static User getTemporaryUser(User user) {

		User temporaryUser = null;
		List<User> users = User.find("email = ?", user.getEmail()).fetch();
		if (!users.isEmpty()) {
			assert (users.size() == 1);
			temporaryUser = users.iterator().next();
		}
		return temporaryUser;
	}
}
