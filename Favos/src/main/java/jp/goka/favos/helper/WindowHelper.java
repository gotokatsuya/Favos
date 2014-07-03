package jp.goka.favos.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by katsuyagoto on 2014/03/23.
 */
public class WindowHelper {

	public static void hideSoftInputFromWindow(Activity activity){
		try {
			InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}catch (Exception e){
		}
	}

	public static int getDisplayWidth(Context context){
		int measuredWidth = 0;
		Point point = new Point();
		WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
			w.getDefaultDisplay().getSize(point);
			measuredWidth = point.x;
		}else{
			Display d = w.getDefaultDisplay();
			measuredWidth = d.getWidth();
		}
		return measuredWidth;
	}

	public static int getDisplayHeight(Context context){
		int measuredHeight = 0;
		Point point = new Point();
		WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
			w.getDefaultDisplay().getSize(point);
			measuredHeight = point.y;
		}else{
			Display d = w.getDefaultDisplay();
			measuredHeight = d.getHeight();
		}
		return measuredHeight;
	}
}
