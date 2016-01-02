package tmen.memorygame.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricardo on 02/01/2016.
 */
public final class GeradorTemas {

    public static List<Tema> getTemasDefault() {
        List<Tema> temasDefault = new ArrayList<>();
        temasDefault.add(new Tema(true,"Bandeiras",6,1));
        return temasDefault;
    }

    public static List<Tema> getTemasPersonalizados() {
        List<Tema> temasPersonalizados = new ArrayList<>();
        //preencher do ficheiro, flag isDefault a false..
        return  temasPersonalizados;
    }

    public static List<Tema> getTemas () {
        List<Tema> temas = new ArrayList<>();
        temas.addAll(getTemasDefault());
        temas.addAll(getTemasPersonalizados());
        return temas;
    }
}
