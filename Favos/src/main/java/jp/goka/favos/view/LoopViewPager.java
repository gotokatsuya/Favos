package jp.goka.favos.view;

/**
 * Created by katsuyagoto on 2014/04/07.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LoopViewPager extends ViewPager {

	private static final int ALL_PAGE_COUNT = 100000;
	private int mPages;
	private int mFirstPosition;
	private int mCurrentPage;
	private int mAdapterPages;
	private LoopViewPagerListener mListener;
	private LoopOnPageChangeListener mOnPageChangeListener;
	private ListView mListView;

	public interface LoopViewPagerListener {
		public View OnInstantiateItem(int page);
		public void onPageScrollChanged(int page);
		public void onPageScroll(int position, float positionOffset, int positionOffsetPixels);
		public void onPageSelected(int position);
	}

	public LoopViewPager(Context context, int pages, LoopViewPagerListener listener) {
		super(context);

		mPages = pages;

		if(pages == 0) {
			return;
		}

		if(pages == 1) {
			mAdapterPages = 1;
		} else {
			mAdapterPages = ALL_PAGE_COUNT;
		}

		setAdapter(new LoopPagerAdapter());

		int maxSets = ALL_PAGE_COUNT / mPages;
		mFirstPosition = (maxSets / 2) * mPages;
		setCurrentItem(-1, false);

		mListener = listener;
		mOnPageChangeListener = new LoopOnPageChangeListener();
		setOnPageChangeListener(mOnPageChangeListener);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		int pos = item < 0 ? mFirstPosition : mFirstPosition + item;
		super.setCurrentItem(pos, smoothScroll);
	}

	public void setCurrentItemAfterCancelListener(int item, boolean smoothScroll) {
		setOnPageChangeListener(null);

		int pos = item < 0 ? mFirstPosition : mFirstPosition + item;
		super.setCurrentItem(pos, smoothScroll);

		setOnPageChangeListener(mOnPageChangeListener);
	}

	@Override
	public void setCurrentItem(int item) {
		int pos = item < 0 ? mFirstPosition : mFirstPosition + item;
		super.setCurrentItem(pos);
	}

	public void setCurrentItemAfterCancelListener(int item) {
		setOnPageChangeListener(null);

		int pos = item < 0 ? mFirstPosition : mFirstPosition + item;
		super.setCurrentItem(pos);

		setOnPageChangeListener(mOnPageChangeListener);
	}

	private class LoopOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			mListener.onPageScroll(position, positionOffset, positionOffsetPixels);
		}

		@Override
		public void onPageSelected(int position) {
			mCurrentPage = pos2page(position);
			mListener.onPageSelected(mCurrentPage);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if ( state == ViewPager.SCROLL_STATE_IDLE ) {
				mListener.onPageScrollChanged(mCurrentPage);
			}
		}
	}

	private int pos2page(int pos) {
		return(pos % mPages);
	}

	private class LoopPagerAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v = mListener.OnInstantiateItem(pos2page(position));
			if(v != null) {
				container.addView(v);
			}
			return(v);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView((View)object);
		}

		@Override
		public int getCount() {
			return(mAdapterPages);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}
	}


	public void setListView(ListView listView) {
		this.mListView = listView;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_UP){
			if(mListView != null){
				mListView.requestDisallowInterceptTouchEvent(false);
			}
		}
		return super.onInterceptTouchEvent(motionEvent);
	}

	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixel) {
		if (offset > 0.01 || 0.99 < offset ) {
			if(mListView != null){
				mListView.requestDisallowInterceptTouchEvent(true);
			}
		}
		super.onPageScrolled(position, offset, offsetPixel);
	}
}