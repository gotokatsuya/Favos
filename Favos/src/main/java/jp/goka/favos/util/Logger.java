package jp.goka.favos.util;

import android.util.Log;
import jp.goka.favos.Config;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class Logger {

	public static void D(String tag, String msg){
		if(Config.DEBUG_MODE){
			Log.d(tag, msg);
		}
	}

	public static void E(String tag, String msg){
		if(Config.DEBUG_MODE){
			Log.e(tag, msg);
		}
	}
}
