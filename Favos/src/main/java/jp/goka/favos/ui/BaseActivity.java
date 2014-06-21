/*
 * Copyright 2014 eureka Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.goka.favos.ui;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import jp.goka.favos.Config;

public class BaseActivity extends FragmentActivity {

    @Override
    public void onStart() {
        super.onStart();
		if(!Config.DEBUG_MODE) {
		//	EasyTracker.getInstance(this).activityStart(this);
		}
    }


    @Override
    public void onStop() {
        super.onStop();
		if(!Config.DEBUG_MODE) {
		//	EasyTracker.getInstance(this).activityStop(this);
		}
    }

	@Override
	public void onResume(){
		super.onResume();
		//com.facebook.AppEventsLogger.activateApp(this, Config.FB_APP_ID);
	}

	@Override
	public void setTitle(CharSequence title){
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