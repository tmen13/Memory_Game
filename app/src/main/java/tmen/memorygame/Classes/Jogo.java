package tmen.memorygame.Classes;

import android.content.Context;
import android.util.Log;

import tmen.memorygame.Activities.JogoActivity;

public class Jogo {
    private Context mContext;
    private int tipo;
    private String tema;
    private int nivel;
    private Baralho baralho;
    //private int contaJogadas;
    //private int pontuacao;
    private Card primeiraCarta, segundaCarta;

    private int jogadorActual = JogoActivity.ME;
    private int tentativas[] = { 0, 0 };
    private int acertadas[] = { 0, 0 };
    private int intrusosAcertados[] = { 0, 0 };

    public Jogo (Context mContext, int tipo, String tema, int nivel) {
        //this.contaJogadas = 0;
        //this.pontuacao = 0;
        this.mContext = mContext;
        this.tipo = tipo;
        this.tema = tema;
        this.nivel = nivel;
        this.baralho = GeradorBaralhos.getBaralhoPorTema(tema);
    }

    /*public void incPontuacao(){
        pontuacao++;
    }

    public int getPontuacao(){
        return pontuacao;
    }*/

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

    /*public int getNumJogadas(){
        return contaJogadas;
    }

    public void incJogadas(){
        contaJogadas++;
    }*/

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

    public Boolean verificaJogada() {
        Log.d("MemoryGame", String.valueOf(primeiraCarta.getCardID() == segundaCarta.getCardID()));
        return (primeiraCarta.getCardID() == segundaCarta.getCardID());
    }

    public void incrementaTentativas(int position) {
        tentativas[position]++;
    }

    public int getTentativas(int position) {
        return tentativas[position];
    }

    public void incrementaAcertadas(int position) {
        acertadas[position]++;
    }

    public int getAcertadas(int position) {
        return acertadas[position];
    }

    public void incrementaIntrusosAcertados(int position) {
        intrusosAcertados[position]++;
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
        return (((acertadas[JogoActivity.ME] + intrusosAcertados[JogoActivity.ME]) + (acertadas[JogoActivity.OTHER] + intrusosAcertados[JogoActivity.OTHER])) == (baralho.getCartas().size() / 2));
    }

    public int getVencedor() {
        return ((acertadas[JogoActivity.ME] > acertadas[JogoActivity.OTHER]) ? JogoActivity.ME : JogoActivity.OTHER);
    }

}
