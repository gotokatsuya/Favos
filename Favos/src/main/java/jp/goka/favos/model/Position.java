package jp.goka.favos.model;

import com.activeandroid.annotation.Table;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
@Table(name = "Positions")
public class Position extends Base {
	private double x;
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
