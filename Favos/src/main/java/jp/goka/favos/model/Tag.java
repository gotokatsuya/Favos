package jp.goka.favos.model;

import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/07/02.
 */
public class Tag extends Base {
	private int media_count;
	private String name;

	public int getMediaCount() {
		return media_count;
	}

	public void setMediaCount(int media_count) {
		this.media_count = media_count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Tag parse(JSONObject jsonObject){
		return new Gson().fromJson(jsonObject.toString(), Tag.class);
	}

}
