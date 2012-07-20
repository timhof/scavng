import java.util.ArrayList;
import java.util.Collection;

import models.Hunt;
import models.Hunt.HuntStatus;
import models.Hunt.HuntType;
import models.Invitation;
import models.Organizer;
import models.Target;
import models.User;
import models.UserRole;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * Created by IntelliJ IDEA. User: rjudd Date: 12/2/11 Time: 9:17 AM
 */
@OnApplicationStart
public class Bootstrap extends Job {

	// THIS MUST MATCH THE "appId" VALUE IN npskit.plist
	public static String WOMANS_DAY_APPLICATION_ID = "womans_day_cooking_assistant";
	public static String APPFLO_COOKING_APP_ID = "appflo_cooking_assistant";

	@Override
	public void doJob() throws Exception {

		// Do not run Bootstrap in test mode
		if (Play.runingInTestMode()) {
			bootstrapTests();
		} else {
			bootstrapApp();
		}
		Logger.info("BOOTSTRAP COMPLETE");
	}

	public void bootstrapApp() throws Exception {

		if (User.findAll().size() == 0) {
			User admin = User.init("Admin", "admin@gmail.com", "admin",
					UserRole.Admin);
			admin.save();

			User timhof = User.init("Tim", "timothyimhof@gmail.com", "timhof",
					UserRole.Admin);
			timhof.save();

			User limhof = User.init("Liam", "liamimhof@gmail.com", "limhof",
					UserRole.Admin);
			limhof.save();

			User eimhof = User.init("Eli", "elijahimhof@gmail.com", "eimhof",
					UserRole.Admin);
			eimhof.save();

			Collection<User> users = new ArrayList<User>();
			users.add(timhof);
			users.add(limhof);
			users.add(eimhof);

			Organizer organizer = new Organizer();
			organizer.setUser(admin);

			for (int x = 0; x < 5; x++) {

				Hunt hunt = new Hunt();
				hunt.setDescription(String.format("Hunt %d", x));
				hunt.setHuntStatus(HuntStatus.CREATED);
				hunt.setHuntType(HuntType.OPEN);
				hunt.setOrganizer(organizer);

				for (int i = 0; i < 10; i++) {
					Target target = new Target();
					target.setBounty((int) (100 * Math.random()));
					target.setDescription(String.format("Hunt %d - Target %d",
							x, i));
					target.setMessage(String.format(
							"You found Hunt %d - Target %d", x, i));
					target.setHunt(hunt);
					System.out.format("Adding target %d\n", i);
					hunt.addTarget(target);
				}

				for (User user : users) {
					Invitation invitation = new Invitation();
					invitation.setHunt(hunt);
					invitation.setUser(user);
					hunt.addInvitation(invitation);
				}

				hunt.save();

				Collection<Invitation> invitations = hunt.getInvitations();
				for (Invitation invitation : invitations) {
					invitation.setAccepted();
					invitation.save();
				}
			}
		}
	}

	public void bootstrapTests() throws Exception {

	}

}
