package jp.goka.favos.request;

import com.android.volley.Response;
import jp.goka.favos.helper.ThreadHelper;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Media;
import jp.goka.favos.model.Pagination;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class MediaRequest extends BaseRequest {

	enum MediaMethod{
		GET("media/media-id"),
		SEARCH("media/search"),
		POPULAR("media/popular");

		private String method;

		MediaMethod(String method) {
			this.method = method;
		}
	}

	private static MediaRequest ourInstance = new MediaRequest();

	public static MediaRequest getInstance() {
		return ourInstance;
	}

	public static void get(long mediaId,
						   final Response.Listener<List<Media>> successListener,
						   final Response.Listener<InstagramError> errorListener){
		String method = MediaMethod.GET.method;
		method = method.replace("media-id", String.valueOf(mediaId));
		getInstance().get(method, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				success(response, successListener);
			}
		}, errorListener);
	}

	public static void search(final Response.Listener<List<Media>> successListener,
							  final Response.Listener<Pagination> paginationListener,
							  final Response.Listener<InstagramError> errorListener){
		getInstance().get(MediaMethod.SEARCH.method, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				success(response, successListener);
				pagination(response, paginationListener);
			}
		}, errorListener);
	}

	public static void popular(final Response.Listener<List<Media>> successListener,
							   final Response.Listener<InstagramError> errorListener){

		getInstance().get(MediaMethod.POPULAR.method, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				success(response, successListener);
			}
		},errorListener);
	}

	private static void success(final JSONObject response,
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
