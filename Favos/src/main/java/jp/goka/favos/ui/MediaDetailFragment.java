package jp.goka.favos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import jp.goka.favos.R;
import jp.goka.favos.model.Media;


public class MediaDetailFragment extends BaseFragment {

	public static final String ARG_ITEM_ID = "item_id";

    private Media mItem;

    public MediaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = Media.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.photo_detail)).setText(mItem.getUser().getUsername());
        }

        return rootView;
    }
}
