package jp.goka.favos.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import jp.goka.favos.R;
import jp.goka.favos.adapter.MediaAdapter;
import jp.goka.favos.helper.ImageHelper;
import jp.goka.favos.helper.SharedPreferencesHelper;
import jp.goka.favos.helper.WindowHelper;
import jp.goka.favos.listener.AdapterListener;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Media;
import jp.goka.favos.model.Pagination;
import jp.goka.favos.request.MediaRequest;
import jp.goka.favos.request.TagRequest;
import jp.goka.favos.request.volley.VolleyManager;
import jp.goka.favos.util.Logger;

import java.util.ArrayList;
import java.util.List;


public class PopularFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

	private static final String KEY_QUERY = "key_query";

	public enum Mode{
		POPULAR, SEARCH;
	}

	private Mode mode;
	private MediaAdapter mediaArrayAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		sendScreenName(PopularFragment.class.getSimpleName());
		setHasOptionsMenu(true);
		setTitleUnEnableHome("Popular");
		mediaArrayAdapter = new MediaAdapter(getFragmentActivity(), new AdapterListener.OnLoadListener() {
			@Override
			public void load() {}
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


		String q = SharedPreferencesHelper.getString(getFragmentActivity(), KEY_QUERY);
		if(TextUtils.isEmpty(q)) {
			fetchPopular();
		}else {
			fetchTag(q);
		}
	}


	private void fetchPopular(){
		setTitleUnEnableHome("Popular");
		mode = Mode.POPULAR;
		swipeRefreshLayout.setRefreshing(true);
		MediaRequest.popular(new Response.Listener<List<Media>>() {
			@Override
			public void onResponse(List<Media> medias) {
				if(!mediaArrayAdapter.isEmpty()){
					mediaArrayAdapter.clear();
				}
				mediaArrayAdapter.addAll(medias);
				swipeRefreshLayout.setRefreshing(false);
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
		if(mode != null){
			switch (mode){
				case POPULAR:
					fetchPopular();
					break;
				case SEARCH:
					String q = SharedPreferencesHelper.getString(getFragmentActivity(), KEY_QUERY);
					fetchTag(q);
					break;
			}
		}
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_popular, menu);
		final MenuItem searchItem = menu.findItem(R.id.menu_item_search);
		final SearchView searchView = (SearchView)searchItem.getActionView();
		searchView.setIconifiedByDefault(false);
		searchView.setSubmitButtonEnabled(false);
		searchView.setQueryHint("Tag (pet, car, ...)");
		searchView.setOnQueryTextListener(this);
		searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				return true;
			}
		});
		searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean queryTextFocused) {
				if(!queryTextFocused) {
					if(searchItem != null) {
						searchItem.collapseActionView();
					}
					if(searchView != null) {
						searchView.setQuery("", false);
					}
				}
			}
		});
		int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		TextView textView = (TextView) searchView.findViewById(id);
		if(textView != null) {
			textView.setTextColor(Color.WHITE);
		}

		final MenuItem popularItem = menu.findItem(R.id.menu_item_popular);
		View pText = popularItem.getActionView();
		if(pText != null) {
			pText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					sendAction(PopularFragment.class.getSimpleName(), "onClick", "Popular");

					SharedPreferencesHelper.save(getFragmentActivity(), KEY_QUERY, "");
					if (searchItem != null) {
						searchItem.collapseActionView();
					}
					if (searchView != null) {
						searchView.setQuery("", false);
					}
					fetchPopular();
				}
			});
		}

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		if(!TextUtils.isEmpty(query)){
			sendAction(PopularFragment.class.getSimpleName(), "onQuery", query);
			SharedPreferencesHelper.save(getFragmentActivity(), KEY_QUERY, query);
			fetchTag(query);
		}
		return true;
	}

	private void fetchTag(String tag){
		setTitleUnEnableHome(tag);
		mode = Mode.SEARCH;
		swipeRefreshLayout.setRefreshing(true);
		TagRequest.recent(tag, new Response.Listener<List<Media>>() {
			@Override
			public void onResponse(List<Media> medias) {
				if (!mediaArrayAdapter.isEmpty()) {
					mediaArrayAdapter.clear();
				}
				mediaArrayAdapter.addAll(medias);
				swipeRefreshLayout.setRefreshing(false);
			}
		}, new Response.Listener<InstagramError>() {
			@Override
			public void onResponse(InstagramError response) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}


}
