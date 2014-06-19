package jp.goka.favos.model;

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
}
