package tmen.memorygame.Classes;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tmen.memorygame.Classes.Card;
import tmen.memorygame.R;

public class Baralho {
    private String tema;
    private List<Card> cartas = new ArrayList<>();

    static Integer[] flagIdsArray = {R.drawable.flags_argentina, R.drawable.flags_australia, R.drawable.flags_belgium, R.drawable.flags_brazil, R.drawable.flags_england, R.drawable.flags_france, R.drawable.flags_germany, R.drawable.flags_italy, R.drawable.flags_mexico, R.drawable.flags_netherlands, R.drawable.flags_portugal, R.drawable.flags_russia, R.drawable.flags_spain, R.drawable.flags_switzerland, R.drawable.flags_usa};

    public Baralho(String tema, int nivel) {
        this.tema = tema;
        switch (tema) {
            case "Bandeiras":
                //Switch nivel
                for (int i = 0; i < flagIdsArray.length; i++) {
                    cartas.add(new Card(i,flagIdsArray[i]));
                    cartas.add(new Card(i,flagIdsArray[i]));
                }
                Collections.shuffle(cartas);
                break;
            default:
                break;
        }
    }

    public String getTema(){
        return tema;
    }
/*
    public void addCarta(Card carta){
        cartas.add(carta);
    }
*/
    public List<Card> getCartas(){
        return cartas;
    }
}
