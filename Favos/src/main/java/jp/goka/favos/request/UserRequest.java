package jp.goka.favos.request;

import com.android.volley.Response;
import jp.goka.favos.helper.ThreadHelper;
import jp.goka.favos.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class UserRequest extends BaseRequest {

	enum UserMethod{
		GET("users/user-id"),
		SELF_FEED("users/self/feed"),
		RECENT("users/user-id/media/recent"),
		SELF_LIKED("users/self/media/liked"),
		SEARCH("users/search");

		private String method;

		UserMethod(String method) {
			this.method = method;
		}
	}

	private static UserRequest ourInstance = new UserRequest();

	public static UserRequest getInstance() {
		return ourInstance;
	}

	public static void getSelf(final Response.Listener<Self> successListener,
						   final Response.Listener<InstagramError> errorListener){

		String method = UserMethod.GET.method;
		method = method.replace("user-id", String.valueOf(Self.find().getUser().getIdentity()));
		getInstance().get(method, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(final JSONObject response) {
				ThreadHelper.runInBackground(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject data = response.getJSONObject("data");
							User user = User.parseForSelf(data);
							Self self = Self.find();
							self.setUser(user);
							self.save();

							ThreadHelper.runOnUi(new Runnable() {
								@Override
								public void run() {
									successListener.onResponse(Self.find());
								}
							});
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}, errorListener);
	}


	public static void selfRecent(Pagination pagination,
								  final Response.Listener<List<Media>> successListener,
								  final Response.Listener<Pagination> paginationListener,
								  final Response.Listener<InstagramError> errorListener){

		HashMap<String, String> params = null;
		if(pagination != null) {
			params = new HashMap<String, String>();
			params.put("max_id", pagination.getNextMaxId());
		}

		String method = UserMethod.RECENT.method;
		method = method.replace("user-id", String.valueOf(Self.find().getUser().getIdentity()));
		getInstance().get(method, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				successForMedia(response, successListener);
				pagination(response, paginationListener);
			}
		}, errorListener);
	}

	public static void selfFeed(Pagination pagination,
								final Response.Listener<List<Media>> successListener,
								final Response.Listener<Pagination> paginationListener,
							    final Response.Listener<InstagramError> errorListener){

		HashMap<String, String> params = null;
		if(pagination != null) {
			params = new HashMap<String, String>();
			params.put("max_id", pagination.getNextMaxId());
		}

		getInstance().get(UserMethod.SELF_FEED.method, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				successForMedia(response, successListener);
				pagination(response, paginationListener);
			}
		}, errorListener);
	}

	public static void selfLiked(final Response.Listener<List<Media>> successListener,
								final Response.Listener<InstagramError> errorListener){
		getInstance().get(UserMethod.SELF_LIKED.method, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				successForMedia(response, successListener);
			}
		}, errorListener);
	}


	private static void successForMedia(final JSONObject response,
										final Response.Listener<List<Media>> successListener){
		ThreadHelper.runInBackground(new Runnable() {
			@Override
			public void run() {
				try {
					final List<Media> medias = new ArrayList<Media>();
					JSONArray jsonArray = response.getJSONArray("data");
					for(int i=0; i<jsonArray.length(); i++){
						Media media = Media.parse(jsonArray.getJSONObject(i));
						if(media != null) {
							medias.add(media);
						}
					}
					ThreadHelper.runOnUi(new Runnable() {
						@Override
						public void run() {
							successListener.onResponse(medias);
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
