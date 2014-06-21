package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */

@Table(name = "Captions")
public class Caption extends Base {
	private String text;
	private FromUser from;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public FromUser getFrom() {
		return from;
	}

	public void setFrom(FromUser from) {
		this.from = from;
	}

	public static Caption parse(JSONObject jsonObject){
		Caption caption = new Gson().fromJson(jsonObject.toString(), Caption.class);
		return caption;
	}
}
