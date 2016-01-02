package tmen.memorygame.Classes;

import android.content.Context;
import android.preference.PreferenceManager;

public final class MySharedPreferences {

    public static final String PREF_PLAYERNAME = "pref_nome_jogador";
    public static final String PREF_LANG = "pref_idioma";

    public static void addToSharedPref(Context c, String key, String obj){
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString(key, obj).apply();
    }

    public static String getSharedPref(Context c, String key){
        return PreferenceManager.getDefaultSharedPreferences(c).getString(key, "");
    }
}
