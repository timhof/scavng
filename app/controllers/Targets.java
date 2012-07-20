package controllers;

import java.util.Collection;

import models.Target;

public class Targets extends UserBaseController {

	public static void index() {
		Collection<Target> targets = Target.all().fetch();
		render(targets);
	}

	public static void save(Target target) {
		target.save();
		index();
	}

	public static void remove(Long id) {
		Target target = Target.findById(id);
		target.delete();
		index();
	}

	public static void detail(Long id) {
		Target target = Target.findById(id);
		render(target);
	}

	public static void detailJSON(Long id) {
		Target target = Target.findById(id);

		// Gson gson = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// String json = gson.toJson(client);
		// Logger.info("%s", json);
		// renderJSON(json);
		renderJSON(String.format("{\"id\": \"%s\", \"description\": \"%s\"}",
				target.getId(), target.getDescription()));
	}
}