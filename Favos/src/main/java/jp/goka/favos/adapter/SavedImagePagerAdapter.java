package jp.goka.favos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.request.volley.VolleyManager;
import uk.co.senab.photoview.PhotoView;

import java.util.ArrayList;

/**
 * Created by katsuyagoto on 2014/07/03.
 */
public class SavedImagePagerAdapter extends PagerAdapter {

	private ArrayList<String> mImagePaths;
	private LayoutInflater mInflater;

	public SavedImagePagerAdapter(Context context, ArrayList<String> imagePaths) {
		this.mImagePaths = imagePaths;
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return mImagePaths.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = mInflater.inflate(R.layout.item_photo_view, view, false);
		assert imageLayout != null;
		final PhotoView photoView = (PhotoView) imageLayout.findViewById(R.id.photo_view);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(mImagePaths.get(position), options);
		photoView.setImageBitmap(bitmap);
		view.addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}


	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
}
