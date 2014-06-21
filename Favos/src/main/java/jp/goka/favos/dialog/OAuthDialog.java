package jp.goka.favos.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.*;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import jp.goka.favos.Config;
import jp.goka.favos.R;

/**
 * Created by katsuyagoto on 2014/06/21.
 */
public class OAuthDialog extends BaseDialogFragment {

	private static final String KEY_URL = "url";

	public interface OAuthDialogListener {
		public abstract void onComplete(String code);
		public abstract void onError(String error);
	}

	private String mUrl;
	private OAuthDialogListener mListener;

	private ProgressDialog progressDialog;
	private WebView mWebView;

	public static OAuthDialog newInstance(String url){
		OAuthDialog instance = new OAuthDialog();
		Bundle bundle = new Bundle();
		bundle.putString(KEY_URL, url);
		instance.setArguments(bundle);
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
		setStyle(style, theme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_oauth, null);
		mUrl = getArguments().getString(KEY_URL);
		progressDialog = new ProgressDialog(getFragmentActivity());
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setMessage("Loading...");
		mWebView = (WebView)view.findViewById(R.id.oauth_webview);
		setUpWebView();
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		return view;
	}

	private void setUpWebView() {
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new OAuthWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(mUrl);
	}

	public void setListener(OAuthDialogListener mListener) {
		this.mListener = mListener;
	}

	private class OAuthWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (url.startsWith(Config.CALLBACK_URL)) {
				String urls[] = url.split("=");
				if(mListener != null) {
					mListener.onComplete(urls[1]);
				}
				dismiss();
				return true;
			}
			return false;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
									String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			if(mListener != null) {
				mListener.onError(description);
			}
			dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			progressDialog.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			progressDialog.dismiss();
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();

		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int dialogWidth = (int) (metrics.widthPixels * 0.9);

		lp.width = dialogWidth;
		dialog.getWindow().setAttributes(lp);
	}

}
