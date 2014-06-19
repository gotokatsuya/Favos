package jp.goka.favos.model;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Caption extends Base {
	private String text;
	private From from;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}
}
