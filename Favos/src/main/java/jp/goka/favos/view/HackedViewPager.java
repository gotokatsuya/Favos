package jp.goka.favos.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by katsuyagoto on 2014/03/05.
 */
public class HackedViewPager extends ViewPager {

	private boolean isLocked;

	public HackedViewPager(Context context) {
		super(context);
		isLocked = false;
	}

	public HackedViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		isLocked = false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!isLocked) {
			try {
				return super.onInterceptTouchEvent(ev);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isLocked) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	public void toggleLock() {
		isLocked = !isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isLocked() {
		return isLocked;
	}

}