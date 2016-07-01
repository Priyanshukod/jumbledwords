package com.stupendous.jumbledwords;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ashish123 on 27/6/15.
 */
public class AppPreferences {

    private static final String APP_SHARED_PREFERENCE = "app_preferences";


    public static void setSharedPreference(Context ctx, String Key, String Value) {
        SharedPreferences pref = ctx.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Key, Value);
        editor.apply();
    }


    public static void setBooleanSharedPreference(Context ctx, String key, boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setIntSharedPreference(Context ctx, String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static String getSharedPreference(Context ctx, String Key) {
        SharedPreferences pref = ctx.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);

        if (pref.contains(Key)) {

            return pref.getString(Key, "");
        } else
            return null;
    }

    public static String getSharedPreference(Context ctx, String Key,String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);

        if (pref.contains(Key)) {

            return pref.getString(Key, "");
        } else
            return defaultValue;
    }


    public static boolean getBooleanSharedPreference(Context ctx, String Key, Boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        if (pref.contains(Key)) {
            return pref.getBoolean(Key, defaultValue);
        } else
            return defaultValue;
    }

    public static int getIntSharedPreference(Context ctx, String Key, int defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        if (pref.contains(Key)) {
            return pref.getInt(Key, defaultValue);
        } else
            return defaultValue;
    }

}
