package jp.goka.favos.request.volley;

import android.content.Context;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class VolleyManager {
	public static final String TAG = VolleyManager.class.getSimpleName();

	private static VolleyManager ourInstance = new VolleyManager();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static Context mContext;

	public static VolleyManager getInstance() {
		return ourInstance;
	}

	private VolleyManager() {
	}

	public void init(Context context) {
		mContext = context;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mContext);
		}
		return mRequestQueue;
	}

	public ImageLoader getImageLoader(){
		getRequestQueue();
		if(mImageLoader == null){
			mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
		}
		return mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
