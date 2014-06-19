package jp.goka.favos.model;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class UsersInPhoto extends Base {
	private Position position;
	private User user;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
