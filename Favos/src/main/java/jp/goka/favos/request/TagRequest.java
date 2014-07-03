package jp.goka.favos.request;

import android.util.Xml;
import com.android.volley.Response;
import jp.goka.favos.helper.ThreadHelper;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Media;
import jp.goka.favos.model.Pagination;
import jp.goka.favos.model.Tag;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class TagRequest extends BaseRequest {

	enum TagMethod{
		GET("tags/tag-name"),
		RECENT("tags/tag-name/media/recent"),
		SEARCH("tags/search");

		private String method;

		TagMethod(String method) {
			this.method = method;
		}
	}

	private static TagRequest ourInstance = new TagRequest();

	public static TagRequest getInstance() {
		return ourInstance;
	}

	public static void get(String tag_name,
						   final Response.Listener<Tag> successListener,
						   final Response.Listener<InstagramError> errorListener){
		String method = TagMethod.GET.method;
		method = method.replace("tag-name", String.valueOf(tag_name));
		getInstance().get(method, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(final JSONObject response) {
				ThreadHelper.runInBackground(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject data = response.getJSONObject("data");
							final Tag tag = Tag.parse(data);
							ThreadHelper.runOnUi(new Runnable() {
								@Override
								public void run() {
									successListener.onResponse(tag);
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

	public static void recent(String tag_name,
							  final Response.Listener<List<Media>> successListener,
							  final Response.Listener<InstagramError> errorListener){
		String method = TagMethod.RECENT.method;
		try {
			method = method.replace("tag-name", URLEncoder.encode(tag_name, "UTF-8"));
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		getInstance().get(method, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				successForMedia(response, successListener);
			}
		}, errorListener);
	}

	public static void search(String query,
							  final Response.Listener<List<Tag>> successListener,
							  final Response.Listener<InstagramError> errorListener){

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		getInstance().get(TagMethod.SEARCH.method, params, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				successForTag(response, successListener);
			}
		}, errorListener);
	}


	private static void successForTag(final JSONObject response,
								final Response.Listener<List<Tag>> successListener){
		ThreadHelper.runInBackground(new Runnable() {
			@Override
			public void run() {
				try {
					final List<Tag> tags = new ArrayList<Tag>();
					JSONArray jsonArray = response.getJSONArray("data");
					for(int i=0; i<jsonArray.length(); i++){
						Tag tag = Tag.parse(jsonArray.getJSONObject(i));
						if(tag != null) {
							tags.add(tag);
						}
					}
					ThreadHelper.runOnUi(new Runnable() {
						@Override
						public void run() {
							successListener.onResponse(tags);
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
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
