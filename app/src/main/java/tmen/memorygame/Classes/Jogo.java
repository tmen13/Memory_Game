package tmen.memorygame.Classes;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

import tmen.memorygame.Activities.JogoActivity;

public class Jogo implements Serializable{
    private transient Context mContext;
    private int tipo;
    private int mode;
    private Tema tema;
    private int nivelEscolhido;
    private Baralho baralho;
    private Card primeiraCarta, segundaCarta;

    private int jogadorActual = JogoActivity.ME;
    private String nomeJogador1, nomeJogador2;

    private int tentativas[] = { 0, 0 };
    private int acertadas[] = { 0, 0 };
    private int intrusosAcertados[] = { 0, 0 };

    public Jogo (Context mContext, int tipo, int mode, Tema tema, int nivelEscolhido, String nomeJogador1) {
        this.mContext = mContext;
        this.tipo = tipo;
        this.mode = mode;
        this.tema = tema;
        this.nivelEscolhido = nivelEscolhido;
        this.baralho = GeradorBaralhos.getBaralho(tema, nivelEscolhido);
        this.nomeJogador1 = nomeJogador1;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int getTipo() {
        return tipo;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Tema getTema() {
        return tema;
    }

    public int getNivelEscolhido() {
        return nivelEscolhido;
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

    public int getJogadorActual() {
        return jogadorActual;
    }

    public void setJogadorActual(int jogadorActual) {
        this.jogadorActual = jogadorActual;
    }

    public String getNomeJogador1() {
        return nomeJogador1;
    }

    public String getNomeJogador2() {
        return nomeJogador2;
    }

    public void setNomeJogador2(String nomeJogador2) {
        this.nomeJogador2 = nomeJogador2;
    }

    public Boolean verificaJogada() {
        Log.d("MemoryGame", String.valueOf(primeiraCarta.getCardID() == segundaCarta.getCardID()));
        return (primeiraCarta.getCardID() == segundaCarta.getCardID());
    }

    public void incrementaTentativas(int position) {
        tentativas[position]++;
    }

    public int[] getTentativas() {
        return tentativas;
    }

    public int getTentativas(int position) {
        return tentativas[position];
    }

    public void incrementaAcertadas(int position) {
        acertadas[position]++;
    }

    public int[] getAcertadas() {
        return acertadas;
    }

    public int getAcertadas(int position) {
        return acertadas[position];
    }

    public void incrementaIntrusosAcertados(int position) {
        intrusosAcertados[position]++;
    }

    public int[] getIntrusosAcertados() {
        return intrusosAcertados;
    }

    public int getIntrusosAcertados(int position){
        return intrusosAcertados[position];
    }

    public void resetJogada() {
        primeiraCarta = null;
        segundaCarta = null;
    }

    public int getProximoJogador() {
        if (jogadorActual == JogoActivity.ME) {
            return JogoActivity.OTHER;
        } else {
            return JogoActivity.ME;
        }
    }

    public Boolean verificaFinal() {
        return ((acertadas[JogoActivity.ME] + acertadas[JogoActivity.OTHER]) == (baralho.getCartas().size() / 2));
    }

    public int getVencedor() {
        return (((acertadas[JogoActivity.ME] - intrusosAcertados[JogoActivity.ME]) > (acertadas[JogoActivity.OTHER] - intrusosAcertados[JogoActivity.OTHER])) ? JogoActivity.ME : JogoActivity.OTHER);
    }

}
