package com.shuffl.niched.common;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static void save(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_FILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void save(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_FILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(Constants.SP_FILE, Context.MODE_PRIVATE).getString(key, "");
    }

    public static int getInt(Context context, String key) {
        return context.getSharedPreferences(Constants.SP_FILE, Context.MODE_PRIVATE).getInt(key, 0);
    }
}