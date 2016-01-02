package tmen.memorygame.Classes;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Collections;

        import tmen.memorygame.R;

public final class GeradorBaralhos {

    static Integer[] flagIdsArray = {R.drawable.flags_argentina, R.drawable.flags_australia, R.drawable.flags_belgium,
            R.drawable.flags_brazil, R.drawable.flags_england, R.drawable.flags_france, R.drawable.flags_germany,
            R.drawable.flags_italy, R.drawable.flags_mexico, R.drawable.flags_netherlands, R.drawable.flags_portugal,
            R.drawable.flags_russia, R.drawable.flags_spain, R.drawable.flags_switzerland, R.drawable.flags_usa};

    public static Baralho getBaralho(Tema tema, int nivelEscolhido){ //baralhosdefault
        Baralho baralho =  new Baralho(tema);
        int numPares = 2;

        switch (nivelEscolhido) {
            case 1:
                numPares = 2;
                break;
            case 2:
                numPares = 3;
                break;
            case 3:
                numPares = 6;
                break;
            case 4:
                numPares = 10;
                break;
            case 5:
                numPares = 15;
                break;
            case 6:
                numPares = 15;
                break;
        }

        switch (tema.getNome()) {
            case "Bandeiras":
                if (numPares <= flagIdsArray.length) {
                    for (int i = 0; i < numPares; i++) {
                        baralho.addCarta(new Card(i, flagIdsArray[i], baralho.getTema().getNome()));
                        baralho.addCarta(new Card(i, flagIdsArray[i], baralho.getTema().getNome()));
                    }
                }
                break;
        }

        Collections.shuffle(baralho.getCartas());
        return baralho;
    }
}