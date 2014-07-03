package jp.goka.favos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import jp.goka.favos.R;
import jp.goka.favos.adapter.SavedImageAdapter;
import jp.goka.favos.helper.ThreadHelper;
import jp.goka.favos.listener.AdapterListener;
import jp.goka.favos.model.SavedImage;
import jp.goka.favos.util.MediaUtils;

import java.util.ArrayList;
import java.util.List;


public class AlbumFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

	private SavedImageAdapter savedImageAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		sendScreenName(AlbumFragment.class.getSimpleName());

		setHasOptionsMenu(true);
		setTitleUnEnableHome("Album");
		savedImageAdapter = new SavedImageAdapter(getFragmentActivity(), new ArrayList<SavedImage>(),
		new AdapterListener.OnLoadListener() {
			@Override
			public void load() {
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_grid_album, null, false);
		return view;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
		swipeRefreshLayout = getSrl(view, R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
		gridView = (GridView)view.findViewById(R.id.gridView);
		gridView.setAdapter(savedImageAdapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getFragmentActivity(), DetailSavedImageActivity.class);
				intent.putExtra(DetailSavedImageActivity.KEY_POSITION, position);
				intent.putStringArrayListExtra(DetailSavedImageActivity.KEY_PATH, savedImageAdapter.getPaths());
				startActivity(intent);
			}
		});
		fetchSavedImage();
	}


	private void fetchSavedImage(){
		swipeRefreshLayout.setRefreshing(true);
		ThreadHelper.runInBackground(new Runnable() {
			@Override
			public void run() {
				final List<SavedImage> savedImages = MediaUtils.getSavedImage(getFragmentActivity());
				ThreadHelper.runOnUi(new Runnable() {
					@Override
					public void run() {
						if (!savedImageAdapter.isEmpty()) {
							savedImageAdapter.clear();
						}
						savedImageAdapter.addAll(savedImages);
						swipeRefreshLayout.setRefreshing(false);
					}
				});
			}
		});
	}


	@Override
	public void onRefresh() {
		fetchSavedImage();
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_album, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_item_slideshow:
				Intent intent = new Intent(getFragmentActivity(), SlideShowActivity.class);
				intent.putExtra(SlideShowActivity.KEY_PATHS, savedImageAdapter.getPaths());
				startActivity(intent);
				break;
		}
		return false;
	}
}
