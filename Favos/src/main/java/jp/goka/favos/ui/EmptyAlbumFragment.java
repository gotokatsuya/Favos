package jp.goka.favos.ui;

import android.os.Bundle;
import android.view.*;
import jp.goka.favos.R;


public class EmptyAlbumFragment extends BaseFragment{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		sendScreenName(EmptyAlbumFragment.class.getSimpleName());
		setHasOptionsMenu(true);
		setTitleUnEnableHome("Album");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_empty_album, null, false);
		return view;
	}
}
