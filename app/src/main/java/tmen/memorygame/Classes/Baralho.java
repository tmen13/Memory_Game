package tmen.memorygame.Classes;

import java.util.ArrayList;
import java.util.List;

public class Baralho {
    private String tema;
    private int nivelActual;
    private List<Card> cartas = new ArrayList<>();

    public Baralho(String tema) {
        this.tema = tema;
    }

    public String getTema(){
        return tema;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public List<Card> getCartas(){
        return cartas;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public void addCarta(Card carta){
        cartas.add(carta);
    }

    public void removeCarta(int id){
        for(int i = 0; i< cartas.size();i++){
            if (cartas.get(i).cardID == id)
                cartas.remove(i);
        }
    }


}