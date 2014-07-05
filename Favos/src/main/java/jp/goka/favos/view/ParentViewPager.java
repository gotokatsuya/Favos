package jp.goka.favos.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import jp.goka.favos.util.Logger;

/**
 * Created by katsuyagoto on 2014/06/27.
 */
public class ParentViewPager extends ViewPager {

	public ParentViewPager(Context context) {
		super(context);
	}

	public ParentViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if(v != this && v instanceof HorizontalListView) {
			if(((HorizontalListView) v).getAdapter() != null){
				if(dx > 5 || dx < -5) {
					((HorizontalListView) v).requestDisallowInterceptTouchEvent(true);
				}
				return true;
			}
		}
		return super.canScroll(v, checkV, dx, x, y);
	}

}
