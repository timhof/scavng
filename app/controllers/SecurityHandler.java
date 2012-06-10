package controllers;

import models.User;
import models.deadbolt.RoleHolder;
import play.mvc.Controller;
import util.MapKeys;
import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.ExternalizedRestrictionsAccessor;
import controllers.deadbolt.RestrictedResourcesHandler;

/**
 * Created by IntelliJ IDEA. User: akast Date: 2/8/12 Time: 4:23 PM To change
 * this template use File | Settings | File Templates.
 */
public class SecurityHandler extends Controller implements DeadboltHandler {

	@Override
	public void beforeRoleCheck() {

		User user = MapKeys.User.get(session, new User());
		if (user != null) {
			MapKeys.User.put(renderArgs, user);
		} else {
			Login.index();
		}
	}

	@Override
	public RoleHolder getRoleHolder() {
		return (User) renderArgs.get("user");
	}

	@Override
	public void onAccessFailure(String controllerClassName) {
		flash("nein", "nein, das ist sterk verboten!");
		Application.index();
	}

	@Override
	public ExternalizedRestrictionsAccessor getExternalizedRestrictionsAccessor() {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	@Override
	public RestrictedResourcesHandler getRestrictedResourcesHandler() {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
	}
}
