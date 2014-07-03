package jp.goka.favos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
public class MediaImagePagerAdapter extends PagerAdapter {

	private ArrayList<String> mImageUrls;
	private LayoutInflater mInflater;

	public MediaImagePagerAdapter(Context context, ArrayList<String> imageUrls) {
		this.mImageUrls = imageUrls;
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return mImageUrls.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = mInflater.inflate(R.layout.item_photo_view, view, false);
		assert imageLayout != null;
		final PhotoView photoView = (PhotoView) imageLayout.findViewById(R.id.photo_view);
		VolleyManager.getInstance().getImageLoader().get(mImageUrls.get(position), new ImageLoader.ImageListener() {
			@Override
			public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
				Bitmap bitmap = response.getBitmap();
				if(bitmap != null){
					photoView.setImageBitmap(bitmap);
				}
			}
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
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
