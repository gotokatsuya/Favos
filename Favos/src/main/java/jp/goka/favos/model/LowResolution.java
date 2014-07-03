package jp.goka.favos.model;

import com.activeandroid.annotation.Table;

/**
 * Created by katsuyagoto on 2014/06/19.
 */

public class LowResolution extends Base {
	private String url;
	private int width;
	private int height;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
