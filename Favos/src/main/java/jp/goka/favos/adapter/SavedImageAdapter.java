package jp.goka.favos.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import jp.goka.favos.R;
import jp.goka.favos.listener.AdapterListener;
import jp.goka.favos.model.SavedImage;
import jp.goka.favos.request.task.GetThumbnailTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class SavedImageAdapter extends ArrayAdapter<SavedImage> {

	private LayoutInflater mInflater;
	private AdapterListener.OnLoadListener onLoadListener;

	public SavedImageAdapter(Context context, List<SavedImage> items, AdapterListener.OnLoadListener onLoadListener) {
		super(context, 0, items);
		this.onLoadListener = onLoadListener;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final SavedImage savedImage = (SavedImage)getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_grid_image, null, false);
		}
		final ImageView squaredImageView = ((ImageView) convertView.findViewById(R.id.item_grid_image));
		GetThumbnailTask task = new GetThumbnailTask(
				getContext(),
				squaredImageView,
				savedImage.getMediaId(),
				savedImage.getOrientation()
		);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		if(position == getCount() -1){
			onLoadListener.load();
		}

		return convertView;
	}


	public ArrayList<String> getPaths(){
		ArrayList<String> paths = new ArrayList<String>();
		for(int i=0;i<getCount();i++){
			paths.add(((SavedImage) getItem(i)).getPath());
		}
		return paths;
	}

}
