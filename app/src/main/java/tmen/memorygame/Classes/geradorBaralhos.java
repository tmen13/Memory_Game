package tmen.memorygame.Classes;

import java.util.ArrayList;
import java.util.Collections;

import tmen.memorygame.R;

public final class geradorBaralhos {

    protected static ArrayList<String> listaTemas = new ArrayList<>();
    protected static ArrayList<Baralho> listaBaralhos = new ArrayList<>();

    static Integer[] flagIdsArray = {R.drawable.flags_argentina, R.drawable.flags_australia, R.drawable.flags_belgium,
            R.drawable.flags_brazil, R.drawable.flags_england, R.drawable.flags_france, R.drawable.flags_germany,
            R.drawable.flags_italy, R.drawable.flags_mexico, R.drawable.flags_netherlands, R.drawable.flags_portugal,
            R.drawable.flags_russia, R.drawable.flags_spain, R.drawable.flags_switzerland, R.drawable.flags_usa};

    public static void criaBaralhos(){
        //baralho bandeiras
        Baralho bandeiras =  new Baralho("Bandeiras");
        for (int i = 0; i < flagIdsArray.length; i++) {
            bandeiras.addCarta(new Card(i, flagIdsArray[i]));
            bandeiras.addCarta(new Card(i, flagIdsArray[i]));
        }
        Collections.shuffle(bandeiras.getCartas());
        listaTemas.add("Bandeiras");
        listaBaralhos.add(bandeiras);
    }

    public static ArrayList<String> getTemas(){
        return listaTemas;
    }

    public static Baralho getBaralhoPorTema(String tema){
        for(int i = 0; i< listaBaralhos.size();i++){
            if (listaBaralhos.get(i).getTema().equals(tema))
                return listaBaralhos.get(i);
        }
        return null;
    }

    public static void addTema(String tema){
        listaTemas.add(tema);
    }

    public static void addBaralho(Baralho baralho){
        listaBaralhos.add(baralho);
    }
}