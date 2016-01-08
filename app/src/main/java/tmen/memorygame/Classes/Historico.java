package tmen.memorygame.Classes;

import java.io.Serializable;

/**
 * Created by Ricardo on 04/01/2016.
 */
public class Historico implements Serializable {
    private static final long serialVersionUID = 2L;
    private int tipo;
    private int mode;
    private String tema;
    private String nomeJogador1, nomeJogador2;
    private int tentativas[] = { 0, 0 };
    private int acertadas[] = { 0, 0 };
    private int intrusosAcertados[] = { 0, 0 };
    private int vencedor;

    public Historico(int tipo, int mode, String tema, String nomeJogador1, String nomeJogador2, int[] tentativas, int[] acertadas, int[] intrusosAcertados, int vencedor) {
        this.tipo = tipo;
        this.mode = mode;
        this.tema = tema;
        this.nomeJogador1 = nomeJogador1;
        this.nomeJogador2 = nomeJogador2;
        this.tentativas = tentativas;
        this.acertadas = acertadas;
        this.intrusosAcertados = intrusosAcertados;
        this.vencedor = vencedor;
    }

    public int getTipo() {
        return tipo;
    }

    public int getMode() {
        return mode;
    }

    public String getTema() {
        return tema;
    }

    public String getNomeJogador1() {
        return nomeJogador1;
    }

    public String getNomeJogador2() {
        return nomeJogador2;
    }

    public int[] getTentativas() {
        return tentativas;
    }

    public int getTentativas(int position) {
        return tentativas[position];
    }

    public int[] getAcertadas() {
        return acertadas;
    }

    public int getAcertadas(int position) {
        return acertadas[position];
    }

    public int[] getIntrusosAcertados() {
        return intrusosAcertados;
    }

    public int getIntrusosAcertados(int position) {
        return intrusosAcertados[position];
    }

    public int getVencedor() {
        return vencedor;
    }
}
