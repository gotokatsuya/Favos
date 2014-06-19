package jp.goka.favos.model;

import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Likes extends Base {

	private int count;
	private List<Like> likes;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}
}
