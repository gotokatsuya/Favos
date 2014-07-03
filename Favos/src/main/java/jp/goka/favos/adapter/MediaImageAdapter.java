package jp.goka.favos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.listener.AdapterListener;
import jp.goka.favos.model.Media;
import jp.goka.favos.model.User;
import jp.goka.favos.request.volley.VolleyManager;
import jp.goka.favos.view.RoundedImageView;
import jp.goka.favos.view.SquaredImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class MediaImageAdapter extends ArrayAdapter<Media> {

	private LayoutInflater mInflater;
	private AdapterListener.OnLoadListener onLoadListener;

	public MediaImageAdapter(Context context, AdapterListener.OnLoadListener onLoadListener) {
		super(context, 0, new ArrayList<Media>());
		this.onLoadListener = onLoadListener;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final Media media = getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_grid_media_image, null, false);
		}

		final ImageView squaredImageView = ((ImageView) convertView.findViewById(R.id.item_grid_image));
		if(media.getImages() != null){
			VolleyManager.getInstance().getImageLoader().get(media.getImages().getThumbnail().getUrl(),new ImageLoader.ImageListener() {
				@Override
				public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
					squaredImageView.setImageBitmap(response.getBitmap());
				}
				@Override
				public void onErrorResponse(VolleyError error) {
				}
			});
		}

		if(position == getCount() -1){
			onLoadListener.load();
		}

		return convertView;
	}


	public ArrayList<String> getUrls(){
		ArrayList<String> urls = new ArrayList<String>();
		for(int i=0;i<getCount();i++){
			urls.add(getItem(i).getImages().getStandardResolution().getUrl());
		}
		return urls;
	}

}
