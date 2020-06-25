package com.stupendous.jumbledwords;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    SharedPreferences sharedPreferences;
    private static SharedPrefs mInstance;
    public static SharedPrefs getInstance(){
        if(mInstance == null)
            mInstance = new SharedPrefs();
        return mInstance;
    }

    public void initialize(Context ctx){
        sharedPreferences = ctx.getSharedPreferences("JumbledWords", Context.MODE_PRIVATE);
    }
    public void writeStringPreference(String key, String value){
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(key, value);
        e.apply();
    }

    public String getStringPreference(String key, String defaultValue) {
       return sharedPreferences.getString(key, defaultValue);
    }

    public void writeLongPreference(String key, long value){
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putLong(key, value);
        e.apply();
    }

    public long getLongPreference(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }
    public void writeIntPreference(String key, int value){
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putInt(key, value);
        e.apply();
    }

    public int getIntPreference(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
    public void clearPreferenceValue(String key){
        sharedPreferences.edit().remove(key).apply();
    }

    public void writeStringPreference(Context context, String key, String value) {
        if(sharedPreferences == null)
            initialize(context);
        writeStringPreference(key, value);
    }

    public boolean getBooleanPreference(String key, boolean defaultValue){
        return sharedPreferences.getBoolean(key,defaultValue);
    }
    public void writeBooleanPreference( String key, Boolean value) {
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putBoolean(key, value);
        e.apply();
    }
    public void clearAllPreferences(){
        sharedPreferences.edit().clear().apply();
    }

}
