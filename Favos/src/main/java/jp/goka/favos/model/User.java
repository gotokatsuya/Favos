package jp.goka.favos.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import jp.goka.favos.util.Logger;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "Users")
public class User extends Base {

	@Column(name = "UserName")
	private String username;

	@Column(name = "WebSite")
	private String website;

	@Column(name = "ProfilePicture")
	private String profile_picture;

	@Column(name = "FullName")
	private String full_name;

	@Column(name = "Bio")
	private String bio;

	@Column(name = "Count")
	private Count counts;

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

	public Count getCounts() {
		return counts;
	}

	public void setCounts(Count counts) {
		this.counts = counts;
	}

	public static User parse(JSONObject jsonObject){
		User user = new Gson().fromJson(jsonObject.toString(), User.class);
		return user;
	}

	public static User parseForSelf(JSONObject jsonObject){
		User user = new Gson().fromJson(jsonObject.toString(), User.class);
		Count count = user.getCounts();
		if(count != null){
			count.save();
			user.setCounts(count);
		}
		user.save();
		return user;
	}
}
