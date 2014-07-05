package jp.goka.favos.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.adapter.MediaAdapter;
import jp.goka.favos.helper.ImageHelper;
import jp.goka.favos.listener.AdapterListener;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Media;
import jp.goka.favos.model.Pagination;
import jp.goka.favos.request.UserRequest;
import jp.goka.favos.request.volley.VolleyManager;
import jp.goka.favos.util.Logger;
import org.w3c.dom.Text;

import java.util.List;


public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

	private Pagination mPagination = null;
	private MediaAdapter mediaArrayAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		sendScreenName(HomeFragment.class.getSimpleName());
		setHasOptionsMenu(true);
		//setTitleUnEnableHome("Home");
		mediaArrayAdapter = new MediaAdapter(getFragmentActivity(), new AdapterListener.OnLoadListener() {
			@Override
			public void load() {
				if(swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
					fetchSelfFeed(false);
				}
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_grid_media, null, false);
		return view;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
		swipeRefreshLayout = getSrl(view, R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
		GridView gridView = getGv(view, R.id.gridView);
		gridView.setAdapter(mediaArrayAdapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getFragmentActivity(), DetailMediaImageActivity.class);
				intent.putExtra(DetailMediaImageActivity.KEY_POSITION, position);
				intent.putStringArrayListExtra(DetailMediaImageActivity.KEY_URL, mediaArrayAdapter.getUrls());
				startActivity(intent);
			}
		});
		gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Media media = mediaArrayAdapter.getItem(position);
				VolleyManager.getInstance().getImageLoader().get(media.getImages().getStandardResolution().getUrl(), new ImageLoader.ImageListener() {
					@Override
					public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
						Bitmap bitmap = response.getBitmap();
						if(bitmap != null){
							ImageHelper.saveImage(getFragmentActivity(), bitmap);
						}
					}
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
				return true;
			}
		});

		fetchSelfFeed(true);
	}

	@Override
	public void onStop(){
		super.onStop();
		mPagination = null;
	}

	private void fetchSelfFeed(final boolean reset){
		if(!reset){
			if(mPagination == null || TextUtils.isEmpty(mPagination.getNextMaxId())){
				return;
			}
		}

		swipeRefreshLayout.setRefreshing(true);
		UserRequest.selfFeed(mPagination, new Response.Listener<List<Media>>() {
			@Override
			public void onResponse(List<Media> medias) {
				if (reset && !mediaArrayAdapter.isEmpty()) {
					mediaArrayAdapter.clear();
				}
				mediaArrayAdapter.addAll(medias);
				swipeRefreshLayout.setRefreshing(false);
			}
		}, new Response.Listener<Pagination>() {
			@Override
			public void onResponse(Pagination pagination) {
				mPagination = pagination;
			}
		}, new Response.Listener<InstagramError>() {
			@Override
			public void onResponse(InstagramError response) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}


	@Override
	public void onRefresh() {
		mPagination = null;
		fetchSelfFeed(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}
}
