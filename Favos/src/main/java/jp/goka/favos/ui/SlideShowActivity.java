package jp.goka.favos.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import jp.goka.favos.R;
import jp.goka.favos.util.DepthPageTransformer;
import jp.goka.favos.view.KenBurnsView;
import jp.goka.favos.view.LoopViewPager;

import java.util.List;

public class SlideShowActivity extends BaseActivity {

	public static final String KEY_URLS = "key_urls";
	public static final String KEY_PATHS = "key_paths";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sendScreenName(SlideShowActivity.class.getSimpleName());
		setContentView(R.layout.activity_slideshow_kenburns);
		init();
	}

	private void init(){
		List<String> urls = getIntent().getStringArrayListExtra(KEY_URLS);
		List<String> paths = getIntent().getStringArrayListExtra(KEY_PATHS);

		//KenBurnsView
		final KenBurnsView kenBurnsView = (KenBurnsView)findViewById(R.id.ken_burns_view);
		kenBurnsView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		if(urls != null && !urls.isEmpty()){
			kenBurnsView.initUrls(urls);
		}else if(paths != null && !paths.isEmpty()){
			kenBurnsView.initFiles(paths);
		}

		//LoopViewListener
		LoopViewPager.LoopViewPagerListener listener = new LoopViewPager.LoopViewPagerListener() {
			@Override
			public View OnInstantiateItem(int page) {
				TextView counterText = new TextView(getApplicationContext());
				counterText.setPadding(16, 16, 0, 0);
				counterText.setText(page+1 + "");
				counterText.setTextColor(Color.WHITE);
				return counterText;
			}
			@Override
			public void onPageScroll(int position, float positionOffset, int positionOffsetPixels) {}
			@Override
			public void onPageSelected(int position) {
				kenBurnsView.forceSelected(position);
			}
			@Override
			public void onPageScrollChanged(int page){}
		};

		//LoopView
		int pages = 0;
		if(urls != null && !urls.isEmpty()){
			pages = urls.size();
		}else if(paths != null && !paths.isEmpty()){
			pages = paths.size();
		}

		sendAction(SlideShowActivity.class.getSimpleName(), "AlbumSize", String.valueOf(pages));

		LoopViewPager loopViewPager = new LoopViewPager(this, pages, listener);
		loopViewPager.setPageTransformer(true, new DepthPageTransformer());
		FrameLayout viewPagerFrame = (FrameLayout)findViewById(R.id.view_pager_frame);
		viewPagerFrame.addView(loopViewPager);

		kenBurnsView.setPager(loopViewPager);
	}

}
