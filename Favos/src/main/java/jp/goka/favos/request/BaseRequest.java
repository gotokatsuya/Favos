package jp.goka.favos.request;

import com.android.volley.Response;
import jp.goka.favos.helper.ThreadHelper;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Pagination;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.volley.ExRetryPolicy;
import jp.goka.favos.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class BaseRequest {

	protected void post(
			final String methodName,
			final HashMap<String, String> params,
			final Response.Listener<JSONObject> listener,
			final Response.Listener<InstagramError> errorListener){

		Request request
				= new Request(
				com.android.volley.Request.Method.POST,
				appendAccessToken(buildMethodUrl(methodName)),
				listener,
				errorListener) {
		};
		request.setParams(params);
		request.setRetryPolicy(new ExRetryPolicy());
		request.start();
	}

	protected void get(
			final String methodName,
			final HashMap<String, String> params,
			final Response.Listener<JSONObject> listener,
			final Response.Listener<InstagramError> errorListener){

		Request request
				= new Request(
				com.android.volley.Request.Method.GET,
				appendParams(appendAccessToken(buildMethodUrl(methodName)), params),
				listener,
				errorListener) {
		};
		request.setRetryPolicy(new ExRetryPolicy());
		request.start();
	}

	private static String getAPIBaseUrl() {
		return "https://api.instagram.com/v1/";
	}

	private static String buildMethodUrl(String method) {
		StringBuilder builder = new StringBuilder(getAPIBaseUrl());
		builder.append(method);
		builder.append("?");
		return builder.toString();
	}

	protected static String appendParams(String url, HashMap<String, String> params){
		if(params == null){
			return url;
		}
		StringBuilder builder = new StringBuilder(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.append('&');
			builder.append(entry.getKey());
			builder.append('=');
			builder.append(entry.getValue());
		}
		return builder.toString();
	}

	protected static String appendAccessToken(String url){
		StringBuilder builder = new StringBuilder(url);

		Self self = Self.find();
		if (self != null && self.hasAccessToken()) {
			builder.append("access_token");
			builder.append("=");
			builder.append(self.getAccessToken());
		}
		return builder.toString();
	}



	protected static void pagination(final JSONObject response,
								   final Response.Listener<Pagination> paginationListener){
		ThreadHelper.runInBackground(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject jsonObject = response.getJSONObject("pagination");
					final Pagination pagination = Pagination.parse(jsonObject);
					ThreadHelper.runOnUi(new Runnable() {
						@Override
						public void run() {
							paginationListener.onResponse(pagination);
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
