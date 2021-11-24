package com.hanix.randomgame.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefUtil {

    private static final String PREF_NAME = "Pref";
    private static final String KEY_FCM_TOKEN_ID = "KEY_FCM_TOKEN_ID";

    private PrefUtil() {
    }

    private static Editor getEditor(Context context) {
        return getPref(context).edit();
    }

    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /*******  기본 SharedPreferences 시작 *******/
    public static void setBoolean(Context context, String key, boolean value) {
        Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences pref = getPref(context);
        return pref.getBoolean(key, false);
    }

    public static void setString(Context context, String key, String value) {
        Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences pref = getPref(context);
        return pref.getString(key, "");
    }

    public static void setInt(Context context, String key, int value) {
        Editor editor = getEditor(context);
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences pref = getPref(context);
        return pref.getInt(key, 0);
    }
    //*******  기본 SharedPreferences 끝 *******/

}
