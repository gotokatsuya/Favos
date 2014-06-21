package jp.goka.favos.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPreferencesHelper {

    public static void save(Context context, String key, Object value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if(value instanceof Double){
			editor.putLong(key, Double.doubleToRawLongBits((Double)value));
		}
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

	public static boolean getBooleanInitTrue(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key, true);
	}

    public static int getInteger(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(key, 0L);
    }

    public static float getFloat(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getFloat(key, 0f);
    }

	public static double getDouble(Context context, final String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToLongBits(0.0)));
	}
}
