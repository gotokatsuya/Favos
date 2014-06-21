package jp.goka.favos.request;

import com.android.volley.Response;
import jp.goka.favos.Config;
import jp.goka.favos.Instagram;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.volley.ExRetryPolicy;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by katsuyagoto on 2014/06/21.
 */
public class OAuthRequest extends BaseRequest {

	public static final String AUTH_URL = "https://instagram.com/oauth/authorize/?" +
			"client_id="+ Instagram.CLIENT_ID
			+"&redirect_uri="+Instagram.CALLBACK_URL
			+"&response_type=code"
			+"&scope=likes+comments+relationships";

	private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";

	private static void getAccessTokenRequest(final String code,
									  final Response.Listener<JSONObject> listener,
									  final Response.Listener<InstagramError> errorListener){
		final HashMap<String, String> param = new HashMap<String, String>();
		param.put("client_id", Instagram.CLIENT_ID);
		param.put("client_secret", Instagram.CLIENT_SECRET);
		param.put("grant_type", "authorization_code");
		param.put("code", code);
		param.put("redirect_uri", Instagram.CALLBACK_URL);
		Request request
				= new Request(
				com.android.volley.Request.Method.POST,
				TOKEN_URL,
				listener,
				errorListener) {
		};
		request.setParams(param);
		request.setRetryPolicy(new ExRetryPolicy());
		request.start();
	}

	public static void getAccessToken(final String code,
									  final Response.Listener<Self> listener,
									  final Response.Listener<InstagramError> errorListener){
		getAccessTokenRequest(code, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Self self = Self.parse(response);
				listener.onResponse(self);
			}
		}, errorListener);
	}



}
