package tmen.memorygame.Classes;

import java.io.Serializable;

/**
 * Created by Ricardo on 02/01/2016.
 */
public class Tema implements Serializable {
    Boolean isDefault;
    String nome;
    int numNiveis;
    int nivelActual;

    public Tema(Boolean isDefault, String nome, int numNiveis, int nivelActual) {
        this.isDefault = isDefault;
        this.nome = nome;
        this.numNiveis = numNiveis;
        this.nivelActual = nivelActual;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public String getNome() {
        return nome;
    }

    public int getNumNiveis() {
        return numNiveis;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }
}
