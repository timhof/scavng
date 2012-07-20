package unit.models;

import java.util.List;

import models.Hunt;
import models.Organizer;
import models.Target;
import models.User;
import models.UserRole;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class TargetTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@After
	public void cleanDatabase() {
		Fixtures.deleteDatabase();
	}

	/**
	 * Create Target with Hunt field will succeed.
	 */
	@Test
	public void createTargetWithHunt() {

		Hunt hunt = createTestHunt();

		Target target = createTestTarget();

		hunt.addTarget(target);

		hunt.save();

		List<Target> targets = Target.findAll();
		assertTrue(targets.size() > 0);
	}

	/**
	 * Hunt is required field. Saving a Target without a Hunt will fail.
	 */
	@Test
	public void createTargetWithoutHunt() {

		Target target = createTestTarget();

		boolean exceptionCaught = false;
		try {
			target.save();
		} catch (Exception e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);

		List<Target> targets = Target.findAll();
		assertFalse(targets.size() > 0);
	}

	private Hunt createTestHunt() {
		User user = User.init("test", "test@test.com", "test", UserRole.Admin);
		Organizer organizer = new Organizer(user);
		Hunt hunt = new Hunt("Test Hunt", organizer);
		return hunt;
	}

	private Target createTestTarget() {
		Target target = new Target();
		target.setBounty((int) (100 * Math.random()));
		target.setDescription("Test Target");
		target.setMessage("Found test hunt");

		return target;
	}
}
