package tmen.memorygame.Classes;

/**
 * Created by Ricardo on 04/01/2016.
 */
public class Historico {
    private int tipo;
    private String tema;
    private String nomeJogador1, nomeJogador2;
    private int tentativas[] = { 0, 0 };
    private int acertadas[] = { 0, 0 };
    private int intrusosAcertados[] = { 0, 0 };
    private String vencedor;

    public Historico(int tipo, String tema, String nomeJogador1, String nomeJogador2, int[] tentativas, int[] acertadas, int[] intrusosAcertados, String vencedor) {
        this.tipo = tipo;
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

    public int[] getAcertadas() {
        return acertadas;
    }

    public int[] getIntrusosAcertados() {
        return intrusosAcertados;
    }

    public String getVencedor() {
        return vencedor;
    }
}
