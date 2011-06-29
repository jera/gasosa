package br.com.jera.gasosa;

import android.app.Activity;
import android.content.SharedPreferences;

public class Preferences {

	private static final String PREFS_NAME = "gasosa";

	public static String readString(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(key, null);
	}

	public static boolean readBoolean(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		return settings.getBoolean(key, false);
	}

	public static void write(Activity activity, String key, Object value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof String) {
			editor.putString(key, (String) value);
		}
		editor.commit();
	}

}
