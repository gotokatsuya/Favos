package jp.goka.favos.application;

import android.app.Application;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import jp.goka.favos.Config;
import jp.goka.favos.request.volley.VolleyManager;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Favos extends com.activeandroid.app.Application {
	@Override
	public void onCreate() {
		super.onCreate();

		/*ActiveAndroid*/
		/*Configuration.Builder builder = new Configuration.Builder(getApplicationContext());
		builder.setCacheSize(Config.DB_CACHE_SIZE);
		builder.setDatabaseName(Config.DB_NAME);
		builder.setDatabaseVersion(Config.DB_VERSION);
		ActiveAndroid.initialize(builder.create(), Config.DEBUG_MODE);*/

		/*Volley*/
		VolleyManager.getInstance().init(getApplicationContext());
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	//	ActiveAndroid.dispose();
	}
}
