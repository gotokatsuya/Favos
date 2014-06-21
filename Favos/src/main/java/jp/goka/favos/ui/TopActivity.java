package jp.goka.favos.ui;


import android.content.Intent;
import android.os.Bundle;
import com.android.volley.Response;
import jp.goka.favos.dialog.OAuthDialog;
import jp.goka.favos.helper.ToastHelper;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.OAuthRequest;
import jp.goka.favos.util.Logger;

/**
 * Created by katsuyagoto on 2014/06/21.
 */
public class TopActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		OAuthDialog oAuthDialog = OAuthDialog.newInstance(OAuthRequest.AUTH_URL);
		oAuthDialog.setListener(new OAuthDialog.OAuthDialogListener() {
			@Override
			public void onComplete(String code) {
				OAuthRequest.getAccessToken(code, new Response.Listener<Self>() {
					@Override
					public void onResponse(Self self) {
						startActivity(new Intent(TopActivity.this, MediaListActivity.class));
					}
				}, new Response.Listener<InstagramError>() {
					@Override
					public void onResponse(InstagramError response) {

					}
				});
			}

			@Override
			public void onError(String error) {
			}
		});
		oAuthDialog.show(getSupportFragmentManager(), OAuthDialog.class.getSimpleName());
	}

}
