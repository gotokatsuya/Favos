package jp.goka.favos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioGroup;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.volley.VolleyManager;
import jp.goka.favos.util.MediaUtils;
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
		final RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				setCurrentTab(checkedId);
			}
		};
		radioGroup.setOnCheckedChangeListener(listener);

		if (savedInstanceState == null) {
			radioGroup.check(R.id.include_main_tab_popular_button);
			setCurrentTab(R.id.include_main_tab_popular_button);
		}

		roundedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				radioGroup.setOnCheckedChangeListener(null);
				radioGroup.clearCheck();
				radioGroup.setOnCheckedChangeListener(listener);
				setCurrentTab(R.id.include_main_tab_profile_image);
			}
		});
	}

	private void setCurrentTab(int checkedId){
		Fragment fragment = null;
		switch (checkedId){
			case R.id.include_main_tab_popular_button:
				sendAction(MainActivity.class.getSimpleName(), "onClick", "Popular");
				fragment = new PopularFragment();
				break;
			case R.id.include_main_tab_album_button:
				sendAction(MainActivity.class.getSimpleName(), "onClick", "Album");
				if(MediaUtils.existSavedImage(getApplicationContext())){
					fragment = new AlbumFragment();
				}else {
					fragment = new EmptyAlbumFragment();
				}
				break;
			case R.id.include_main_tab_home_button:
				sendAction(MainActivity.class.getSimpleName(), "onClick", "Home");
				fragment = new HomeFragment();
				break;
			case R.id.include_main_tab_profile_image:
				sendAction(MainActivity.class.getSimpleName(), "onClick", "Profile");
				fragment = new ProfileFragment();
				break;
		}

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.main_container, fragment)
				.commit();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
