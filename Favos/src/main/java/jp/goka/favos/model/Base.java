package jp.goka.favos.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Base extends Model {

	public Base() {
		super();
	}

	@Column(name = "Identity")
	private String id;

	@Column(name = "CreatedTime")
	private long created_time;

	public String getIdentity() {
		return id;
	}

	public void setIdentity(String id) {
		this.id = id;
	}

	public long getCreatedTime() {
		return created_time;
	}

	public void setCreatedTime(long createdTime) {
		this.created_time = createdTime;
	}
}
