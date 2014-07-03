package jp.goka.favos.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/07/02.
 */
@Table(name = "Counts")
public class Count extends Base {

	@Column(name = "Media")
	private int media;

	@Column(name = "Follows")
	private int follows;

	@Column(name = "FollowedBy")
	private int followed_by;

	public int getMedia() {
		return media;
	}

	public void setMedia(int media) {
		this.media = media;
	}

	public int getFollows() {
		return follows;
	}

	public void setFollows(int follows) {
		this.follows = follows;
	}

	public int getFollowedBy() {
		return followed_by;
	}

	public void setFollowed_by(int followed_by) {
		this.followed_by = followed_by;
	}

	public static Count parse(JSONObject jsonObject){
		Count count = new Gson().fromJson(jsonObject.toString(), Count.class);
		return count;
	}
}
