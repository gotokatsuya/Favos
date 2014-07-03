package jp.goka.favos.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import jp.goka.favos.R;
import jp.goka.favos.adapter.SavedImagePagerAdapter;
import jp.goka.favos.helper.ImageHelper;
import jp.goka.favos.util.DepthPageTransformer;
import jp.goka.favos.view.HackedViewPager;

import java.util.ArrayList;

/**
 * Created by katsuyagoto on 2014/06/27.
 */
public class DetailSavedImageActivity extends BaseActivity {

	public static final String KEY_PATH = "key_path";
	public static final String KEY_POSITION = "key_position";

	private HackedViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sendScreenName(DetailSavedImageActivity.class.getSimpleName());

		setContentView(R.layout.activity_image_pager);

		final ArrayList<String> paths = getIntent().getStringArrayListExtra(KEY_PATH);
		int position = getIntent().getIntExtra(KEY_POSITION, 0);

		sendAction(DetailSavedImageActivity.class.getSimpleName(), "SavedImageSize", String.valueOf(paths.size()));

		viewPager = (HackedViewPager)findViewById(R.id.view_pager);
		viewPager.setAdapter(new SavedImagePagerAdapter(this, paths));
		viewPager.setCurrentItem(position);
		viewPager.setPageTransformer(true, new DepthPageTransformer());
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				setLabel();
			}
		});
		setLabel();

		ImageButton delete = (ImageButton) findViewById(R.id.activity_image_preview_button_save);
		delete.setImageResource(R.drawable.trash);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = viewPager.getCurrentItem();
				ImageHelper.delete(DetailSavedImageActivity.this, paths.get(position));
				setResult(Activity.RESULT_OK);
				finish();
			}
		});

		ImageButton cancel = (ImageButton) findViewById(R.id.activity_image_preview_button_cancel);
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
