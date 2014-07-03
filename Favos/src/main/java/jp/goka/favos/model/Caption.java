package jp.goka.favos.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */

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
		Caption find = Caption.findByIdentity(caption.getIdentity());
		if(find == null){
			caption.save();
		}else {
			find = caption;
			find.save();
		}
		return caption;
	}

	public static Caption findByIdentity(String identity){
		Caption caption = new Select()
				.from(Caption.class)
				.where("Identity = ?", identity)
				.executeSingle();
		return caption;
	}
}
