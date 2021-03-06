package controllers;

import java.util.Collection;
import java.util.List;

import models.Invitation;
import models.Invitation.InvitationStatus;
import models.Player;
import models.Target;
import models.User;

public class Users extends UserBaseController {

	public static void index() {
		Collection<User> users = User.all().fetch();
		System.out.println("users.size: " + users.size());
		render(users);
	}

	public static void save(User user) {
		user.save();
		System.out.println("SAVED");
		index();
	}

	public static void remove(Long id) {
		User user = User.findById(id);
		user.delete();
		index();
	}

	public static void detail(Long id) {
		User user = User.findById(id);

		render(user);
	}

	public static void detailJSON(Long id) {
		User user = User.findById(id);

		renderJSON(String.format("{\"id\": \"%s\", \"email\": \"%s\"}",
				user.getId(), user.getEmail()));
	}

	public static void targetReached(Long targetId) {

		String deviceId = getDeviceIdFromRequest();

		List<Player> players = User.find("deviceId", deviceId).fetch();
		if (!players.isEmpty()) {
			Player player = players.iterator().next();
			Target target = Target.findById(targetId);

			player.getTargets().add(target);
			player.save();
		}
	}

	/**
	 * Get deviceId from request
	 * 
	 * @return
	 */
	private static String getDeviceIdFromRequest() {
		return "deviceid";
	}

	public static void acceptInvitation(Long invitationId) {

		Invitation invitation = Invitation.findById(invitationId);
		invitation.setAccepted();

		invitation.save();

		detail(invitation.getUser().getId());
	}

	public static void declineInvitation(Long invitationId) {
		Invitation invitation = Invitation.findById(invitationId);

		invitation.setStatus(InvitationStatus.DECLINED);
		invitation.save();
		detail(invitation.getUser().getId());
	}

}
