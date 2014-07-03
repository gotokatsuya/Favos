package jp.goka.favos.application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import jp.goka.favos.Config;
import jp.goka.favos.R;
import jp.goka.favos.request.volley.VolleyManager;

import java.util.HashMap;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Favos extends com.activeandroid.app.Application {

	// The following line should be changed to include the correct property id.
	// XMLファイルの中にもあるのはマルチなGLOBAL/ECOMMERCE用
	private static final String PROPERTY_ID = "UA-52520916-1";

	public static int GENERAL_TRACKER = 0;

	public enum TrackerName {
		APP_TRACKER,       // Tracker used only in this app.
		GLOBAL_TRACKER,    // Tracker used by all the apps from a company. eg: roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	// スコープを public に変更
	public synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			analytics.setDryRun(Config.DEBUG_MODE);

			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
					: analytics.newTracker(R.xml.ecommerce_tracker);
			mTrackers.put(trackerId, t);
		}
		return mTrackers.get(trackerId);
	}


	@Override
	public void onCreate() {
		super.onCreate();
		/*Volley*/
		VolleyManager.getInstance().init(getApplicationContext());
	}


	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
