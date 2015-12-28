package tmen.memorygame.Classes;

import android.content.Context;
import android.util.Log;

public class Jogo {
    private Context mContext;
    private String tema;
    private int nivel;
    private Baralho baralho;
    private int contaJogadas;
    private int pontuacao;
    private Card primeiraCarta, segundaCarta;

    // Jogo jogo = new Jogo(getApplicationContext);
    public Jogo (Context mContext, String tema, int nivel) {
        this.contaJogadas = 0;
        this.pontuacao = 0;
        this.mContext = mContext;
        this.tema = tema;
        this.nivel = nivel;
        this.baralho = GeradorBaralhos.getBaralhoPorTema(tema);
    }

    public void incPontuacao(){
        pontuacao++;
    }

    public int getPontuacao(){
        return pontuacao;
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

    public int getNumJogadas(){
        return contaJogadas;
    }

    public void incJogadas(){
        contaJogadas++;
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

    public Boolean verificaJogada() {
        Log.d("MemoryGame", String.valueOf(primeiraCarta.getCardID() == segundaCarta.getCardID()));
        return (primeiraCarta.getCardID() == segundaCarta.getCardID());
    }

    public void resetJogada() {
        primeiraCarta = null;
        segundaCarta = null;
    }
}
