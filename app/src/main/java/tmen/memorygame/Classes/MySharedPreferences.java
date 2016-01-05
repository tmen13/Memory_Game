package tmen.memorygame.Classes;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public final class MySharedPreferences {

    public static final String PREF_PLAYERNAME = "pref_nome_jogador";
    public static final String PATH_HITORICO = "historico.dat";
    public static final String PATH_TEMA = "tema.dat";

    public static void addToSharedPref(Context c, String key, String obj){
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString(key, obj).apply();
    }

    public static String getSharedPref(Context c, String key){
        return PreferenceManager.getDefaultSharedPreferences(c).getString(key, "");
    }

    public static void saveHisticoToFile(Context c,  ArrayList<Historico> obj){
        FileOutputStream fos;
        ObjectOutputStream os;
        try {
            fos = c.openFileOutput(PATH_HITORICO, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        } catch (Exception e){
            Log.d("tmen", "saveHisticoToFile: " + e.toString());
        }
    }

    public static void saveTemaToFile(Context c,  ArrayList<Historico> obj){
        FileOutputStream fos;
        ObjectOutputStream os;
        try {
            fos = c.openFileOutput(PATH_TEMA, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        } catch (Exception e){
            Log.d("tmen", "saveTemaToFile: " + e.toString());
        }
    }

    public static ArrayList<Historico> getHistoricoFromFile(Context c){
        FileInputStream fis;
        ObjectInputStream is;
        ArrayList<Historico> historico = null;
        try {
            fis = c.openFileInput(PATH_HITORICO);
            is = new ObjectInputStream(fis);
            historico = (ArrayList<Historico>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e){
            Log.d("tmen", "getHistricoFromFile: " + e.toString());
        }
        return historico;
    }

    public static ArrayList<Tema> getTemaFromFile(Context c){
        FileInputStream fis;
        ObjectInputStream is;
        ArrayList<Tema> tema = null;
        try {
            fis = c.openFileInput(PATH_TEMA);
            is = new ObjectInputStream(fis);
            tema = (ArrayList<Tema>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e){
            Log.d("tmen", "saveTemaToFile: " + e.toString());
        }
        return tema;
    }
}
