package jp.goka.favos.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.adapter.MediaImagePagerAdapter;
import jp.goka.favos.helper.ImageHelper;
import jp.goka.favos.request.volley.VolleyManager;
import jp.goka.favos.util.DepthPageTransformer;
import jp.goka.favos.view.HackedViewPager;

import java.util.ArrayList;

/**
 * Created by katsuyagoto on 2014/06/27.
 */
public class DetailMediaImageActivity extends BaseActivity {

	public static final String KEY_URL = "key_url";
	public static final String KEY_POSITION = "key_position";

	private HackedViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sendScreenName(DetailMediaImageActivity.class.getSimpleName());

		setContentView(R.layout.activity_image_pager);

		final ArrayList<String> urls = getIntent().getStringArrayListExtra(KEY_URL);
		int position = getIntent().getIntExtra(KEY_POSITION, 0);

		sendAction(DetailMediaImageActivity.class.getSimpleName(), "MediaImageSize", String.valueOf(urls.size()));

		viewPager = (HackedViewPager)findViewById(R.id.view_pager);
		viewPager.setAdapter(new MediaImagePagerAdapter(this, urls));
		viewPager.setCurrentItem(position);
		viewPager.setPageTransformer(true, new DepthPageTransformer());
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				setLabel();
			}
		});
		setLabel();

		ImageButton save = (ImageButton) findViewById(R.id.activity_image_preview_button_save);
		ImageButton cancel = (ImageButton) findViewById(R.id.activity_image_preview_button_cancel);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = viewPager.getCurrentItem();
				VolleyManager.getInstance().getImageLoader().get(urls.get(position), new ImageLoader.ImageListener() {
					@Override
					public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
						Bitmap bitmap = response.getBitmap();
						if(bitmap != null){
							ImageHelper.saveImage(getApplicationContext(), bitmap);
						}
					}
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setLabel() {
		TextView index = (TextView) findViewById(R.id.activity_image_index);
		index.setText((viewPager.getCurrentItem() + 1) + "/" + viewPager.getAdapter().getCount());
	}

}
