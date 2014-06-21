package jp.goka.favos.request;

import com.android.volley.Response;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.volley.ExRetryPolicy;
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
			final Response.Listener<JSONObject> listener,
			final Response.Listener<InstagramError> errorListener){

		Request request
				= new Request(
				com.android.volley.Request.Method.POST,
				appendAccessToken(buildMethodUrl(methodName)),
				listener,
				errorListener) {
		};
		request.setRetryPolicy(new ExRetryPolicy());
		request.start();
	}

	protected void get(
			final String methodName,
			final Response.Listener<JSONObject> listener,
			final Response.Listener<InstagramError> errorListener){

		Request request
				= new Request(
				com.android.volley.Request.Method.GET,
				appendAccessToken(buildMethodUrl(methodName)),
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


}
