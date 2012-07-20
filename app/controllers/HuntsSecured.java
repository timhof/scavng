package controllers;

import java.util.List;

import models.Hunt;
import models.Hunt.HuntStatus;
import models.Invitation;
import models.Invitation.InvitationStatus;
import models.Organizer;
import models.Target;
import models.User;
import play.Logger;
import play.mvc.With;
import util.MapKeys;
import controllers.deadbolt.Deadbolt;

@With(Deadbolt.class)
public class HuntsSecured extends UserBaseController {

	public static void save(Hunt hunt) throws Throwable {

		Organizer organizer = getOrganizer();
		hunt.setOrganizer(organizer);
		hunt.save();
		System.out.println("SAVED");
		Hunts.index();
	}

	private static Organizer getOrganizer() {
		Organizer organizer = null;
		User user = MapKeys.User.get(session, new User());
		List<Organizer> organizers = Organizer.find("user = ?", user).fetch();
		if (organizers.isEmpty()) {
			organizer = new Organizer();
			organizer.setUser(user);
			organizer.save();
		} else {
			organizer = organizers.iterator().next();
		}
		return organizer;
	}

	public static void remove(Long id) {
		Hunt hunt = Hunt.findById(id);
		if (!userIsHuntOrganizer(hunt)) {
			Logger.info("Only hunt organizer can remove the hunt");
			Hunts.index();
		}
		hunt.delete();
		Hunts.index();
	}

	public static void saveTarget(Hunt hunt, Target target) {
		if (!userIsHuntOrganizer(hunt)) {
			Logger.info("Only hunt organizer can edit hunt targets");
			Hunts.index();
		}
		// target.setHunt(hunt);
		// target.save();
		if (!hunt.getTargets().contains(target)) {
			hunt.addTarget(target);
		}
		hunt.save();
		Hunts.detail(hunt.getId());
	}

	public static void removeTarget(Long huntId, Long targetId) {

		Hunt hunt = Hunt.findById(huntId);

		if (!userIsHuntOrganizer(hunt)) {
			Logger.info("Only hunt organizer can edit hunt targets");
			Hunts.index();
		}
		Target target = Target.findById(targetId);

		hunt.getTargets().remove(target);
		target.delete();

		Hunts.detail(hunt.getId());
	}

	public static void inviteUser(Hunt hunt, User user) {

		if (!userIsHuntOrganizer(hunt)) {
			Logger.info("Only hunt organizer can invite players");
			Hunts.index();
		}

		List<Object> users = User.find("email", user.getEmail()).fetch();
		if (users.isEmpty()) {
			user.save();
		} else {
			user = (User) users.iterator().next();
		}

		Invitation invitation = new Invitation();
		invitation.setUser(user);
		invitation.setHunt(hunt);
		invitation.save();

		hunt.getInvitations().add(invitation);

		user.getInvitations().add(invitation);

		Hunts.detail(hunt.getId());
	}

	public static void uninvite(Long huntId, Long userId) {

		Hunt hunt = Hunt.findById(huntId);

		if (!userIsHuntOrganizer(hunt)) {
			Logger.info("Only hunt organizer can uninvite players");
			Hunts.index();
		}
		User user = User.findById(userId);

		List<Invitation> invitations = Invitation.find("user = ? and hunt = ?",
				user, hunt).fetch();
		if (!invitations.isEmpty()) {
			Invitation invitation = invitations.iterator().next();
			invitation.setStatus(InvitationStatus.REVOKED);
			invitation.save();
		}

		Hunts.detail(hunt.getId());
	}

	public static void start(Long id) {
		Hunt hunt = Hunt.findById(id);

		if (!userIsHuntOrganizer(hunt)) {
			Logger.info("Only hunt organizer can start a hunt");
			Hunts.index();
		}

		hunt.setHuntStatus(HuntStatus.IN_PROGRESS);
		hunt.save();
		Hunts.index();
	}

	public static void end(Long id) {
		Hunt hunt = Hunt.findById(id);

		if (!userIsHuntOrganizer(hunt)) {
			Logger.info("Only hunt organizer can end a hunt");
			Hunts.index();
		}

		hunt.setHuntStatus(HuntStatus.COMPLETED);
		hunt.save();
		Hunts.index();
	}

	private static boolean userIsHuntOrganizer(Hunt hunt) {

		boolean userIsHuntOrganizer = true;
		User user = MapKeys.User.get(session, new User());
		if (user.getId() != hunt.getOrganizer().getUser().getId()) {
			userIsHuntOrganizer = false;
		}
		return userIsHuntOrganizer;
	}

	public static void joinHunt(Long id) {
		Hunt hunt = Hunt.findById(id);
		User user = MapKeys.User.get(session, new User());

		user.joinHunt(hunt);

		Hunts.index();
	}

}