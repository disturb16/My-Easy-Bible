package com.example.jramos.myeasybible.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    public static void putString(Activity _activity,String _name, String _value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_activity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(_name, _value);
        editor.apply();
    }

    public static String getStringValue(Activity _activity, String _name){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_activity);
        return preferences.getString(_name, "");
    }
}
