package tmen.memorygame.Classes;

import java.util.ArrayList;

import tmen.memorygame.Classes.Card;

public class Baralho {
    private String tema;
    private ArrayList<Card> cartas = new ArrayList<>();

    public Baralho(String tema) {
        this.tema = tema;
    }

    public String getTema(){
        return tema;
    }

    public void addCarta(Card carta){
        cartas.add(carta);
    }

    public ArrayList<Card> getCartas(){
        return cartas;
    }
}
