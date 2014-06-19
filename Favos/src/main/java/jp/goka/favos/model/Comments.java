package jp.goka.favos.model;

import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Comments extends Base {

	private int count;
	private List<Comment> comments;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
