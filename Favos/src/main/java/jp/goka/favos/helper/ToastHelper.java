package jp.goka.favos.helper;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by katsuyagoto on 2014/06/19.
 */
public class ToastHelper {

	private static Toast toast;

	public static void shortMessage(Context context, String msg) {
		toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void longMessage(Context context, String msg) {
		toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}