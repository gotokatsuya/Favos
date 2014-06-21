package jp.goka.favos.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class BaseListFragment extends ListFragment {

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


	protected TextView setEmptyItems(String text, int size) {
		TextView emptyView = new TextView(getActivity());
		emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		emptyView.setText(text);
		emptyView.setTextSize(size);
		emptyView.setVisibility(View.GONE);
		emptyView.setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		((ViewGroup) getListView().getParent()).addView(emptyView);
		return emptyView;
	}

	protected Button getBtn(View view, int id) {
		return (Button) view.findViewById(id);
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
    protected CheckBox getCb(View view, int id) {
        return (CheckBox) view.findViewById(id);
    }

}
