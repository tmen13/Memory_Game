package tmen.memorygame.Classes;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Tony on 28/12/2015.
 */
public final class MySharedPreferences {

    public static void addToSharedPref(Context c, String key, String obj){
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString(key, obj).apply();
    }

    public static String getSharedPref(Context c, String key){
        return PreferenceManager.getDefaultSharedPreferences(c).getString(key, "");
    }
}
