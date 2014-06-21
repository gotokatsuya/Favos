package jp.goka.favos.model;

import com.activeandroid.annotation.Table;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "FromUsers")
public class FromUser extends Base {
	private String username;
	private String profile_picture;
	private String full_name;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePicture() {
		return profile_picture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profile_picture = profilePicture;
	}

	public String getFullName() {
		return full_name;
	}

	public void setFullName(String fullName) {
		this.full_name = fullName;
	}
}
