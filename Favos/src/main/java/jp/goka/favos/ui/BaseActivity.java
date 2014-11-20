package jp.goka.favos.ui;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import jp.goka.favos.Config;
import jp.goka.favos.application.Favos;

public class BaseActivity extends FragmentActivity {

	public void sendScreenName(String screenName){
		Tracker t =  ((Favos)getApplication()).getTracker(Favos.TrackerName.APP_TRACKER);
		t.setScreenName(screenName);
		t.send(new HitBuilders.AppViewBuilder().build());
	}


	public void sendAction(String category, String action, String label){
		Tracker t =  ((Favos)getApplication()).getTracker(Favos.TrackerName.APP_TRACKER);
		t.send(new HitBuilders.EventBuilder()
				.setCategory(category)
				.setAction(action)
				.setLabel(label)
				.build());
	}

	@Override
	public void setTitle(CharSequence title){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
	}

	protected void setTitleUnEnableHome(CharSequence title){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
		actionBar.setDisplayShowCustomEnabled(false);
	}


	public void setTitleWithSubTitle(CharSequence title, CharSequence sub){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
		actionBar.setSubtitle(sub);
	}

	public void setTitleWithSubTitleUnEnableHome(CharSequence title, CharSequence sub){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
		actionBar.setSubtitle(sub);
	}


	@Override
	public void setTitle(int title){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
	}


	public void setTitleWithSubTitle(int title, int sub){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
		actionBar.setSubtitle(sub);
	}

	protected Button getBtn(int id) {
        return (Button) this.findViewById(id);
    }

    protected ImageButton getIBtn(int id) {
        return (ImageButton) this.findViewById(id);
	}

	protected TextView getTv(int id) {
		return (TextView) this.findViewById(id);
	}

	protected ImageView getIv(int id) {
		return (ImageView) this.findViewById(id);
	}

	protected EditText getEt(int id) {
		return (EditText) this.findViewById(id);
	}

	protected ViewPager getVp(int id) {
		return (ViewPager) this.findViewById(id);
	}

	protected ProgressBar getPb(int id) {
		return (ProgressBar) this.findViewById(id);
	}

	protected ListView getLv(int id) {
		return (ListView) this.findViewById(id);
	}

	protected ScrollView getSv(int id) {
		return (ScrollView) this.findViewById(id);
	}

	protected TextView getTv(View view, int id) {
		return (TextView) view.findViewById(id);
	}

	protected GridView getGv(int id) {
		return (GridView) findViewById(id);
	}
}
