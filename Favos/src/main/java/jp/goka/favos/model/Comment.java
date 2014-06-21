package jp.goka.favos.model;

import com.activeandroid.annotation.Table;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "Comments")
public class Comment extends Base {
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
}
