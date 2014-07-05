package jp.goka.favos.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import jp.goka.favos.R;
import jp.goka.favos.adapter.MediaImageAdapter;
import jp.goka.favos.helper.ImageHelper;
import jp.goka.favos.helper.SharedPreferencesHelper;
import jp.goka.favos.helper.TextViewHelper;
import jp.goka.favos.listener.AdapterListener;
import jp.goka.favos.model.*;
import jp.goka.favos.request.UserRequest;
import jp.goka.favos.request.volley.VolleyManager;
import jp.goka.favos.view.HorizontalListView;
import jp.goka.favos.view.RoundedImageView;

import java.util.List;


public class ProfileFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

	private static final String KEY_REVIEW = "key_review";
	private static final int REQUEST_GOOGLE_PLAY = 1;

	private SwipeRefreshLayout swipeRefreshLayout;
	private RoundedImageView selfImage;
	private TextView selfNickname;
	private TextView selfFullName;
	private TextView selfPosts;
	private TextView selfFollowers;
	private TextView selfFollowing;
	private MediaImageAdapter mediaMyLikedAdapter;
	private MediaImageAdapter mediaMyRecentAdapter;


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		sendScreenName(ProfileFragment.class.getSimpleName());
		setHasOptionsMenu(true);
		//setTitleUnEnableHome("Profile");
		mediaMyLikedAdapter = new MediaImageAdapter(getFragmentActivity(), new AdapterListener.OnLoadListener() {
			@Override
			public void load() {

			}
		});
		mediaMyRecentAdapter = new MediaImageAdapter(getFragmentActivity(), new AdapterListener.OnLoadListener() {
			@Override
			public void load() {

			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_profile, null, false);
		return view;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
		swipeRefreshLayout = getSrl(view, R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
		selfImage = (RoundedImageView)view.findViewById(R.id.profile_self_image);
		selfNickname = getTv(view, R.id.profile_self_nickname);
		selfFullName = getTv(view, R.id.profile_self_full_name);
		selfPosts = getTv(view, R.id.profile_self_posts);
		selfFollowers = getTv(view, R.id.profile_self_followers);
		selfFollowing = getTv(view, R.id.profile_self_following);
		HorizontalListView recentListView = (HorizontalListView)view.findViewById(R.id.profile_self_recent_list);
		recentListView.setAdapter(mediaMyRecentAdapter);
		recentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getFragmentActivity(), DetailMediaImageActivity.class);
				intent.putExtra(DetailMediaImageActivity.KEY_POSITION, position);
				intent.putStringArrayListExtra(DetailMediaImageActivity.KEY_URL, mediaMyRecentAdapter.getUrls());
				startActivity(intent);
			}
		});
		recentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Media media = mediaMyRecentAdapter.getItem(position);
				VolleyManager.getInstance().getImageLoader().get(media.getImages().getStandardResolution().getUrl(), new ImageLoader.ImageListener() {
					@Override
					public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
						Bitmap bitmap = response.getBitmap();
						if (bitmap != null) {
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

		HorizontalListView likedListView = (HorizontalListView)view.findViewById(R.id.profile_self_liked_list);
		likedListView.setAdapter(mediaMyLikedAdapter);
		likedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getFragmentActivity(), DetailMediaImageActivity.class);
				intent.putExtra(DetailMediaImageActivity.KEY_POSITION, position);
				intent.putStringArrayListExtra(DetailMediaImageActivity.KEY_URL, mediaMyLikedAdapter.getUrls());
				startActivity(intent);
			}
		});
		likedListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Media media = mediaMyLikedAdapter.getItem(position);
				VolleyManager.getInstance().getImageLoader().get(media.getImages().getStandardResolution().getUrl(), new ImageLoader.ImageListener() {
					@Override
					public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
						Bitmap bitmap = response.getBitmap();
						if (bitmap != null) {
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

		final View review = view.findViewById(R.id.review_layout);
		boolean reviewed = SharedPreferencesHelper.getBoolean(getFragmentActivity(), KEY_REVIEW);
		if(reviewed){review.setVisibility(View.GONE);}
		review.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendAction(ProfileFragment.class.getSimpleName(), "onClick", "OpenReview");
				Uri uri = Uri.parse("market://details?id=jp.goka.favos");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivityForResult(intent, REQUEST_GOOGLE_PLAY);
			}
		});
		ImageView closeReview = getIv(view, R.id.close_review);
		closeReview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendAction(ProfileFragment.class.getSimpleName(), "onClick", "CloseReview");
				review.setVisibility(View.GONE);
				SharedPreferencesHelper.save(getFragmentActivity(), KEY_REVIEW, true);
			}
		});
		TextView reviewText = getTv(view, R.id.review_text);
		TextViewHelper.makeUnderLine(reviewText);

		refreshView(Self.find());
		fetchSelf();
		fetchMyLiked();
		fetchMyRecent();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public void onRefresh() {
		fetchSelf();
		fetchMyLiked();
		fetchMyRecent();
	}



	private void fetchSelf(){
		swipeRefreshLayout.setRefreshing(true);
		UserRequest.getSelf(new Response.Listener<Self>() {
			@Override
			public void onResponse(Self self) {
				refreshView(self);
				swipeRefreshLayout.setRefreshing(false);
			}
		}, new Response.Listener<InstagramError>() {
			@Override
			public void onResponse(InstagramError response) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}



	private void fetchMyLiked(){
		swipeRefreshLayout.setRefreshing(true);
		UserRequest.selfLiked(new Response.Listener<List<Media>>() {
			@Override
			public void onResponse(List<Media> medias) {
				if (!mediaMyLikedAdapter.isEmpty()) {
					mediaMyLikedAdapter.clear();
				}
				mediaMyLikedAdapter.addAll(medias);
				swipeRefreshLayout.setRefreshing(false);
			}
		}, new Response.Listener<InstagramError>() {
			@Override
			public void onResponse(InstagramError response) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}



	private void fetchMyRecent(){
		swipeRefreshLayout.setRefreshing(true);
		UserRequest.selfRecent(null, new Response.Listener<List<Media>>() {
			@Override
			public void onResponse(List<Media> medias) {
				if (!mediaMyRecentAdapter.isEmpty()) {
					mediaMyRecentAdapter.clear();
				}
				mediaMyRecentAdapter.addAll(medias);
				swipeRefreshLayout.setRefreshing(false);
			}
		}, new Response.Listener<Pagination>() {
			@Override
			public void onResponse(Pagination response) {

			}
		}, new Response.Listener<InstagramError>() {
			@Override
			public void onResponse(InstagramError response) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}



	private void refreshView(Self self){
		User user = self.getUser();
		if(user != null) {
			VolleyManager.getInstance().getImageLoader().get(user.getProfilePicture(), new ImageLoader.ImageListener() {
				@Override
				public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
					selfImage.setImageBitmap(response.getBitmap());
				}

				@Override
				public void onErrorResponse(VolleyError error) {

				}
			});
			selfNickname.setText(user.getUsername());
			selfFullName.setText(user.getFullName());
		}

		Count count = user.getCounts();
		if(count != null) {
			selfPosts.setText("" + count.getMedia());
			selfFollowers.setText("" + count.getFollowedBy());
			selfFollowing.setText("" + count.getFollows());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case REQUEST_GOOGLE_PLAY:
				break;
		}
	}

}
