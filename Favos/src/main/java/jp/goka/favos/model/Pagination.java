package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/28.
 */
public class Pagination extends Base {

	private String next_url;
	private String next_max_id;

	public String getNextUrl() {
		if(next_url == null){
			return "";
		}
		return next_url;
	}

	public String getNextMaxId() {
		if(next_max_id == null){
			return "";
		}
		return next_max_id;
	}

	public static Pagination parse(JSONObject jsonObject){
		Pagination pagination = new Gson().fromJson(jsonObject.toString(), Pagination.class);
		return pagination;
	}
}
