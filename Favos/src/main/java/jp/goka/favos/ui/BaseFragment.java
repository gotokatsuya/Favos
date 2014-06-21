package jp.goka.favos.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;

public class BaseFragment extends Fragment {

	private FragmentActivity mFragmentActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mFragmentActivity = (FragmentActivity)activity;
	}

	protected FragmentActivity getFragmentActivity(){
		if(mFragmentActivity != null) {
			return mFragmentActivity;
		}else {
			return getActivity();
		}
	}

	protected ActionBar getActionBar(){
		if(mFragmentActivity != null) {
			return mFragmentActivity.getActionBar();
		}else {
			return getActivity().getActionBar();
		}
	}

	protected void setTitle(int title){
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

	protected void setTitleUnEnableHome(int title){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
	}


	protected void setTitleUnEnableHome(CharSequence title){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
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


	protected void setTitle(CharSequence title){
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

	public void setSubTitle(CharSequence sub){
		ActionBar actionBar = getActionBar();
		if(actionBar == null){
			return;
		}

		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setSubtitle(sub);
	}


	protected Button getBtn(View view, int id) {
		return (Button) view.findViewById(id);
	}
	protected CheckBox getCbx(View view, int id) {
		return (CheckBox) view.findViewById(id);
	}
	protected ImageButton getIBtn(View view, int id) {
		return (ImageButton) view.findViewById(id);
	}
	protected TextView getTv(View view, int id) {
		return (TextView) view.findViewById(id);
	}
	protected ImageView getIv(View view, int id) {
		return (ImageView) view.findViewById(id);
	}
	protected EditText getEt(View view, int id) {
		return (EditText) view.findViewById(id);
	}
	protected ViewPager getVp(View view, int id) {
		return (ViewPager) view.findViewById(id);
	}
	protected ProgressBar getPb(View view, int id) {
		return (ProgressBar) view.findViewById(id);
	}
	protected ListView getLv(View view, int id) {
		return (ListView) view.findViewById(id);
	}
	protected GridView getGv(View view, int id) {
		return (GridView) view.findViewById(id);
	}
	protected View getV(View view, int id) {
		return (View) view.findViewById(id);
	}
	protected ScrollView getSv(View view, int id) {
		return (ScrollView) view.findViewById(id);
	}
}
