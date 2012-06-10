package util;

import models.MementoBridge;
import models.User;
import play.libs.Crypto;
import play.mvc.Scope;

/**
 * Created by IntelliJ IDEA. User: akast Date: 3/21/12 Time: 1:42 PM To change
 * this template use File | Settings | File Templates.
 */
public class MapKeys<T> {
	public static MapKeys<User> User = new MapKeys<User>("user", true);

	String keyName;
	boolean encrypted;

	private MapKeys(String keyName) {
		this.keyName = keyName;
	}

	private MapKeys(String keyName, boolean encrypt) {
		this.keyName = keyName;
		this.encrypted = encrypt;
	}

	public void put(Scope.RenderArgs renderArgs, T t) {
		renderArgs.put(keyName, t);
	}

	public T get(Scope.RenderArgs renderArgs) {
		T t = (T) renderArgs.get(keyName);
		return t;
	}

	public void put(Scope.Session session, String t) {
		if (encrypted)
			t = Crypto.encryptAES(t);
		session.put(keyName, t);
	}

	public void remove(Scope.Session session) {
		session.remove(keyName);
	}

	public String get(Scope.Session session) {
		String answer = session.get(keyName);
		if (encrypted && answer != null)
			answer = Crypto.decryptAES(answer);

		return answer;
	}

	public void put(Scope.Session session, MementoBridge<T> bridge, T t) {
		put(session, bridge.toMemento(t));
	}

	public T get(Scope.Session session, MementoBridge<T> bridge) {
		String memento = get(session);
		T answer = bridge.fromMemento(memento);
		return answer;
	}

}
