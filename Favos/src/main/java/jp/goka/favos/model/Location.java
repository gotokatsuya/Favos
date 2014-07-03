package jp.goka.favos.model;

import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Created by katsuyagoto on 2014/06/19.
 */

public class Location extends Base {

	private double latitude;
	private double longitude;
	private String name;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Location parse(JSONObject jsonObject){
		Location location = new Gson().fromJson(jsonObject.toString(), Location.class);
		return location;
	}
}
