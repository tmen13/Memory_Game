package tmen.memorygame.Classes;

import java.util.ArrayList;
import java.util.List;

public class Baralho {
    private Tema tema;
    private List<Card> cartas = new ArrayList<>();

    public Baralho(Tema tema) {
        this.tema = tema;
    }

    public Tema getTema(){
        return tema;
    }

    public List<Card> getCartas(){
        return cartas;
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