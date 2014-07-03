package jp.goka.favos.ui.top;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Response;
import jp.goka.favos.R;
import jp.goka.favos.dialog.OAuthDialog;
import jp.goka.favos.model.InstagramError;
import jp.goka.favos.model.Self;
import jp.goka.favos.request.OAuthRequest;
import jp.goka.favos.ui.BaseActivity;
import jp.goka.favos.ui.MainActivity;
import jp.goka.favos.util.ZoomOutPageTransformer;
import jp.goka.favos.view.ViewIndicator;

/**
 * Created by katsuyagoto on 2014/06/21.
 */
public class TopActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		sendScreenName(TopActivity.class.getSimpleName());
		setContentView(R.layout.activity_top);

		Self self = Self.find();
		if(self != null && self.hasAccessToken()){
			startActivity(new Intent(TopActivity.this, MainActivity.class));
			finish();
		}

		getBtn(R.id.top_login_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendAction(TopActivity.class.getSimpleName(), "onClick", "Login");
				showOAuthDialog();
			}
		});
	}

	private void showOAuthDialog(){
		OAuthDialog oAuthDialog = OAuthDialog.newInstance(OAuthRequest.AUTH_URL);
		oAuthDialog.setListener(new OAuthDialog.OAuthDialogListener() {
			@Override
			public void onComplete(String code) {
				OAuthRequest.getAccessToken(code, new Response.Listener<Self>() {
					@Override
					public void onResponse(Self self) {
						startActivity(new Intent(TopActivity.this, MainActivity.class));
						finish();
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
