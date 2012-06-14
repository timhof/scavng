package controllers;

import java.util.Collection;
import java.util.List;

import models.Player;
import models.Target;

public class Players extends UserBaseController {

	public static void index() {
		Collection<Player> players = Player.all().fetch();
		System.out.println("players.size: " + players.size());
		render(players);
	}

	public static void save(Player player) {
		player.save();
		System.out.println("SAVED");
		index();
	}

	public static void remove(Long id) {
		Player player = Player.findById(id);
		player.delete();
		index();
	}

	public static void detailJSON(Long id) {
		Player player = Player.findById(id);

		renderJSON(String.format("{\"id\": \"%s\", \"email\": \"%s\"}",
				player.getId(), player.getNickname()));
	}

	public static void targetReached(Long targetId) {

		String deviceId = getDeviceIdFromRequest();

		List<Player> players = Player.find("deviceId", deviceId).fetch();
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
}
