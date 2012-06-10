package controllers;

import java.util.Collection;

import models.User;
import models.User.UserStatus;
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

	public static void register(User user) {
		render();
	}

	public static void createUser(User user) {
		user.setStatus(UserStatus.REGISTERED);
		user.save();
		index();
	}

	public static void logout() {
		// MapKeys.User.remove(session);
		// MapKeys.Client.remove(session);
		index();
	}

}
