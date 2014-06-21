package jp.goka.favos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import jp.goka.favos.model.Media;

import java.util.ArrayList;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class MediaAdapter extends ArrayAdapter<Media> {
	private Context mContext;
	private LayoutInflater mInflater;


	public MediaAdapter(Context context) {
		super(context, 0, new ArrayList<Media>());
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final Media media = getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(android.R.layout.simple_list_item_activated_1, null, false);
		}

		TextView name = ((TextView) convertView.findViewById(android.R.id.text1));
		name.setText(media.getUser().getUsername());

		return convertView;
	}

}
