package tmen.memorygame.Classes;

        import android.content.Context;

        import java.util.ArrayList;

        import tmen.memorygame.R;

/**
 * Created by Ricardo on 20/12/2015.
 */
public class Jogo {
    private Context mContext;

    private String tema;
    private int nivel;
    private Baralho baralho;
    private Card primeiraCarta, segundaCarta;

    // Jogo jogo = new Jogo(getApplicationContext);
    public Jogo (Context mContext, String tema, int nivel) {
        this.mContext = mContext;
        this.tema = tema;
        this.nivel = nivel;
        this.baralho = new Baralho(tema, nivel);
    }

    public String getTema() {
        return tema;
    }

    public int getNivel() {
        return nivel;
    }

    public Baralho getBaralho() {
        return baralho;
    }

    public Card getPrimeiraCarta() {
        return primeiraCarta;
    }

    public void setPrimeiraCarta(Card primeiraCarta) {
        this.primeiraCarta = primeiraCarta;
    }

    public Card getSegundaCarta() {
        return segundaCarta;
    }

    public void setSegundaCarta(Card segundaCarta) {
        this.segundaCarta = segundaCarta;
    }
}
