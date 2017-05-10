package com.atlas;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sagar Shedge on 14/6/16.
 */
public class AtlasApplication extends Application {
    public static SharedPreferences appPrefs;
    public static SharedPreferences.Editor appEditor;
    String TAG = "AtlasApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        appPrefs = getSharedPreferences("atlas_app_prefs", Context.MODE_PRIVATE);
        appEditor = appPrefs.edit();
    }
}
