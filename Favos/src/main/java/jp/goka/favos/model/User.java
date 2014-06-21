package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "Users")
public class User extends Base {
	private String username;
	private String website;
	private String profile_picture;
	private String full_name;
	private String bio;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public static User parse(JSONObject jsonObject){
		User user = new Gson().fromJson(jsonObject.toString(), User.class);
		return user;
	}
}
