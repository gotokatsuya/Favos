package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */

@Table(name = "Likeses")
public class Likes extends Base {

	private int count;
	private List<Like> data;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Like> getLikes() {
		return data;
	}

	public void setLikes(List<Like> likes) {
		this.data = likes;
	}

	public static Likes parse(JSONObject jsonObject){
		Likes likes = new Gson().fromJson(jsonObject.toString(), Likes.class);
		return likes;
	}
}
