package tmen.memorygame.Classes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.R;

public final class MySharedPreferences {

    public static final String PREF_PLAYERNAME = "pref_nome_jogador";
    public static final String PATH_HITORICO = "historico.dat";
    public static final String PATH_TEMA = "tema.dat";
    public static final String PATH_CUSTOM_DECK = "custom_deck.dat";
    public static final String PREF_TYPE_MODE = "pref_type_mode";
    public static final String PREF_CLICK_TIME = "pref_click_time";

    public static void addToSharedPref(Context c, String key, String obj){
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString(key, obj).apply();
    }

    public static String getSharedPref(Context c, String key){
        return PreferenceManager.getDefaultSharedPreferences(c).getString(key, "");
    }

    public static void saveHisticoToFile(Context c, List<Historico> obj){
        FileOutputStream fos;
        ObjectOutputStream os;
        try {
            fos = c.openFileOutput(PATH_HITORICO, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        } catch (Exception e){
            Log.d("MemoryGame", "saveHisticoToFile: " + e.toString());
        }
    }

    public static void saveTemaToFile(Context c, List<Tema> obj){
        FileOutputStream fos;
        ObjectOutputStream os;
        try {
            fos = c.openFileOutput(PATH_TEMA, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        } catch (Exception e){
            Log.d("MemoryGame", "saveTemaToFile: " + e.toString());
        }
    }

    public static void saveDeckToFile(Context c, List<String> obj){
        FileOutputStream fos;
        ObjectOutputStream os;
        try {
            fos = c.openFileOutput(PATH_CUSTOM_DECK, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        } catch (Exception e){
            Log.d("MemoryGame", "saveDeckToFile: " + e.toString());
        }
    }

    public static List<String> getDeckFromFile(Context c){
        FileInputStream fis;
        ObjectInputStream is;
        List<String> customDeck = null;
        try {
            fis = c.openFileInput(PATH_CUSTOM_DECK);
            is = new ObjectInputStream(fis);
            customDeck = (List<String>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e){
            Log.d("MemoryGame", "getDeckFromFile: " + e.toString());
        }
        return customDeck;
    }

    public static List<Historico> getHistoricoFromFile(Context c){
        FileInputStream fis;
        ObjectInputStream is;
        List<Historico> historico = null;
        try {
            fis = c.openFileInput(PATH_HITORICO);
            is = new ObjectInputStream(fis);
            historico = (List<Historico>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e){
            Log.d("MemoryGame", "getHistricoFromFile: " + e.toString());
        }
        return historico;
    }

    public static List<Tema> getTemasFromFile(Context c){
        FileInputStream fis;
        ObjectInputStream is;
        List<Tema> tema = null;
        try {
            fis = c.openFileInput(PATH_TEMA);
            is = new ObjectInputStream(fis);
            tema = (List<Tema>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e){
            Log.d("MemoryGame", "saveTemaToFile: " + e.toString());
        }
        return tema;
    }

    public static List<Tema> getTemasDefault(Context c) {
        List<Tema> temasDefault = new ArrayList<>();
        List<Tema> temasList = getTemasFromFile(c);
        for (int i = 0; i < temasList.size(); i++) {
            if (temasList.get(i).getIsDefault()) {
                temasDefault.add(temasList.get(i));
            }
        }
        return temasDefault;
    }

    public static Uri getDrawableUri(Context c, int id){ //devolve o uri a partir do id
        Uri uri = Uri.parse("android.resource://" + c.getPackageName() + "/drawable/" + id);
        return uri;
    }

    public static Drawable getDrawableFromUri(Context c, Uri uri){ //devolve uri atraves do uri
        Drawable drawable = null;
        try {
            InputStream inputStream = c.getContentResolver().openInputStream(uri);
            drawable = Drawable.createFromStream(inputStream, uri.toString());
        } catch (FileNotFoundException e) {
            Log.d("tmen", "getDrawableFromUri");
        }
        return drawable;
    }
}
