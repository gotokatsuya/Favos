package jp.goka.favos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.volley.VolleyManager;
import jp.goka.favos.view.ParentViewPager;
import jp.goka.favos.view.RoundedImageView;

/**
 * Created by katsuyagoto on 2014/06/27.
 */
public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sendScreenName(MainActivity.class.getSimpleName());
		setContentView(R.layout.activity_main);

		final RoundedImageView roundedImageView = (RoundedImageView)findViewById(R.id.include_main_tab_profile_image);
		if(Self.find() != null){
			Self self = Self.find();
			VolleyManager.getInstance().getImageLoader().get(self.getUser().getProfilePicture(), new ImageLoader.ImageListener() {
				@Override
				public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
					roundedImageView.setImageBitmap(response.getBitmap());
				}
				@Override
				public void onErrorResponse(VolleyError error) {
				}
			});
		}

		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.include_main_tab_radio_group);
		radioGroup.check(R.id.include_main_tab_popular_button);
		setTitleUnEnableHome(getTitleFromPosition(0));


		final ParentViewPager viewPager = (ParentViewPager)findViewById(R.id.main_viewpager);
		///viewPager.setOffscreenPageLimit(4);
		FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				Fragment fragment = null;
				switch (position) {
					case 0:
						fragment = new PopularFragment();
						break;
					case 1:
						fragment = new AlbumFragment();
						break;
					case 2:
						fragment = new HomeFragment();
						break;
					case 3:
						fragment = new ProfileFragment();
						break;
				}
				return fragment;
			}

			@Override
			public int getCount() {
				return 4;
			}
		};

		viewPager.setAdapter(fragmentPagerAdapter);
		final RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId){
					case R.id.include_main_tab_popular_button:
						viewPager.setCurrentItem(0);
						break;
					case R.id.include_main_tab_album_button:
						viewPager.setCurrentItem(1);
						break;
					case R.id.include_main_tab_home_button:
						viewPager.setCurrentItem(2);
						break;
				}
			}
		};
		radioGroup.setOnCheckedChangeListener(listener);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				switch (position){
					case 0:
						sendAction(MainActivity.class.getSimpleName(), "onPageSelected", "Popular");
						radioGroup.check(R.id.include_main_tab_popular_button);
						break;
					case 1:
						sendAction(MainActivity.class.getSimpleName(), "onPageSelected", "Album");
						radioGroup.check(R.id.include_main_tab_album_button);
						break;
					case 2:
						sendAction(MainActivity.class.getSimpleName(), "onPageSelected", "Home");
						radioGroup.check(R.id.include_main_tab_home_button);
						break;
					case 3:
						sendAction(MainActivity.class.getSimpleName(), "onPageSelected", "Profile");
						radioGroup.setOnCheckedChangeListener(null);
						radioGroup.clearCheck();
						radioGroup.setOnCheckedChangeListener(listener);
						break;
				}
				setTitleUnEnableHome(getTitleFromPosition(position));
			}
		});

		roundedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				radioGroup.setOnCheckedChangeListener(null);
				radioGroup.clearCheck();
				radioGroup.setOnCheckedChangeListener(listener);
				viewPager.setCurrentItem(3);
			}
		});
	}

	private String getTitleFromPosition(int position){
		switch (position){
			case 0:
				return "Popular";
			case 1:
				return "Album";
			case 2:
				return "Home";
			case 3:
				return "Profile";
		}
		return "";
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
