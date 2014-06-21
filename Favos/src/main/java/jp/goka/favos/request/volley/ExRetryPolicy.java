package jp.goka.favos.request.volley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

public class ExRetryPolicy extends DefaultRetryPolicy {

    protected long mInterval = 1000;

	private static final int CONNECTION_TIMEOUT = 10000;
    private static final int CONNECTION_RETRY_COUNT = 0;

    public ExRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier) {
        super(initialTimeoutMs, maxNumRetries, backoffMultiplier);
    }

    public ExRetryPolicy() {
        super(CONNECTION_TIMEOUT, CONNECTION_RETRY_COUNT, DEFAULT_BACKOFF_MULT);
    }

    public ExRetryPolicy(long interval) {
        super(CONNECTION_TIMEOUT, CONNECTION_RETRY_COUNT, DEFAULT_BACKOFF_MULT);
        mInterval = interval;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {
        NetworkResponse response = error.networkResponse;
        if (response != null && response.statusCode >= 500 && response.statusCode < 600) {
            // サーバーエラー時はリトライしない
            throw error;
        }
        /*if (mInterval > 0) {
            try {
                Thread.sleep(mInterval);
            } catch (InterruptedException e) {
            }
        }*/
        //VolleyLog.d("Network Retry count : %d", getCurrentRetryCount());
        super.retry(error);
    }
}