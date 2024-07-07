package com.example.rickymorty.utils;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static final String Name = "ricky&morty";
    public static void log(String log, String value) {
        Log.d(log, value);
    }


    public static SharedPreferences.Editor getSharedPreferenceEdit() {

        if (editor == null) {
            editor = getSharedPreference().edit();
        }
        return editor;

    }

    public static SharedPreferences getSharedPreference() {

        if (preferences == null) {
            preferences = App.instance().getSharedPreferences(Name, Context.MODE_PRIVATE);
        }
        return preferences;
    }
    public static void hideKeyboard(Context context, View view) {

        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
