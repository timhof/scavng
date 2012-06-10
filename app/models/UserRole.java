package models;


/**
 * Created by IntelliJ IDEA. User: akast Date: 2/8/12 Time: 2:10 PM To change
 * this template use File | Settings | File Templates.
 */

public enum UserRole implements models.deadbolt.Role {

	SuperAdmin(), Admin(), Editor(), Viewer();

	private UserRole() {
	}

	@Override
	public String getRoleName() {
		return this.name();
	}
}
