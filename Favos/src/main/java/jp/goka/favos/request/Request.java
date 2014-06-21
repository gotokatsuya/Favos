package jp.goka.favos.request;

import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import jp.goka.favos.helper.ToastHelper;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.volley.VolleyManager;
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
public class Request extends com.android.volley.Request<JSONObject> {

	private final Response.Listener<JSONObject> mListener;
	private final Response.Listener<InstagramError> mInstagramErrorListener;
	private Map<String, String> mParams;


	public Request(int method, String url, Response.Listener<JSONObject> listener, final Response.Listener<InstagramError> errorListener) {
		super(method, url, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Logger.D("Request volley error", error.getMessage());
			}
		});
		mListener = listener;
		mInstagramErrorListener = errorListener;
		Logger.D("Request url", getUrl());
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		Logger.D("Request response", response.toString());
		/*try {
			InstagramError instagramError = InstagramError.parse(response.getJSONObject("meta"));
			if(instagramError.isError()){
				if(mInstagramErrorListener != null)
					mInstagramErrorListener.onResponse(instagramError);
			}else {
				if(mListener != null)
					mListener.onResponse(response);
			}
		}catch (JSONException e){
			e.printStackTrace();
		}*/
		if(mListener != null)
			mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getParams(){
		return mParams;
	}

	public void setParams(Map<String, String> params){
		this.mParams = params;
	}


	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		JSONObject resultJson;
		try {
			String data = new String(response.data);
			resultJson = new JSONObject(data);
		} catch (Exception e) {
			return null;
		}
		return Response.success(resultJson, HttpHeaderParser.parseCacheHeaders(response));
	}


	public void start() {
		VolleyManager.getInstance().addToRequestQueue(this);
	}

}
