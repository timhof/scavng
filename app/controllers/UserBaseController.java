package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import util.MapKeys;

public class UserBaseController extends Controller {

	@Before
	public static void setUser() {
		User user = MapKeys.User.get(session, new User());
		if (user != null) {
			MapKeys.User.put(renderArgs, user);
		}
	}
}
