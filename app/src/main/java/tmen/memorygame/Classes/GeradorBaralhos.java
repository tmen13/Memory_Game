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

    static Integer[] animaisIdsArrray = {R.drawable.animals_bear, R.drawable.animals_cat,R.drawable.animals_dog,
            R.drawable.animals_eagle, R.drawable.animals_fox, R.drawable.animals_giraffes, R.drawable.animals_leopard,
            R.drawable.animals_lion, R.drawable.animals_monkey, R.drawable.animals_moose, R.drawable.animals_panda,
            R.drawable.animals_parrot, R.drawable.animals_rabbits, R.drawable.animals_tiger, R.drawable.animals_x};

    static Integer[] carsIdsArray = {R.drawable.car_a,R.drawable.car_b, R.drawable.car_bmw, R.drawable.car_bmwamouflage,
            R.drawable.car_bugattiveyronsupersport,R.drawable.car_ferrari, R.drawable.car_ferrarienzo, R.drawable.car_hennesseyvenomgt,
            R.drawable.car_lamborghinicabrera, R.drawable.car_lamborghinitron,R.drawable.car_lamborghiniveneno, R.drawable.car_mercedes,
            R.drawable.car_x,R.drawable.car_y,R.drawable.car_z };

    static Integer[] clubesIdsArrays = {R.drawable.clube_academica, R.drawable.clube_belenenses, R.drawable.clube_benfica, R.drawable.clube_braga,
            R.drawable.clube_estoril, R.drawable.clube_maritimo, R.drawable.clube_moreirense, R.drawable.clube_nacional,R.drawable.clube_pacos,
            R.drawable.clube_porto, R.drawable.clube_rioave, R.drawable.clube_scp, R.drawable.clube_setubal, R.drawable.clube_tondela, R.drawable.clube_vitoria};

    static Integer[] coresIdsArray = {R.drawable.cor_amarelo, R.drawable.cor_azul, R.drawable.cor_branco, R.drawable.cor_castanho,
            R.drawable.cor_cinza, R.drawable.cor_dourado, R.drawable.cor_laranja, R.drawable.cor_lilas, R.drawable.cor_lima,
            R.drawable.cor_preto, R.drawable.cor_rosa, R.drawable.cor_roxo, R.drawable.cor_turquesa, R.drawable.cor_verde, R.drawable.cor_vermelho};

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
            case "Animais":
                if (numPares <= animaisIdsArrray.length) {
                    for (int i = 0; i < numPares; i++) {
                        baralho.addCarta(new Card(i, animaisIdsArrray[i], baralho.getTema().getNome()));
                        baralho.addCarta(new Card(i, animaisIdsArrray[i], baralho.getTema().getNome()));
                    }
                }
                break;
            case "Carros":
                if (numPares <= carsIdsArray.length) {
                    for (int i = 0; i < numPares; i++) {
                        baralho.addCarta(new Card(i, carsIdsArray[i], baralho.getTema().getNome()));
                        baralho.addCarta(new Card(i, carsIdsArray[i], baralho.getTema().getNome()));
                    }
                }
                break;
            case "Cores":
                if (numPares <= coresIdsArray.length) {
                    for (int i = 0; i < numPares; i++) {
                        baralho.addCarta(new Card(i, coresIdsArray[i], baralho.getTema().getNome()));
                        baralho.addCarta(new Card(i, coresIdsArray[i], baralho.getTema().getNome()));
                    }
                }
                break;
            case "Clubes":
                if (numPares <= clubesIdsArrays.length) {
                    for (int i = 0; i < numPares; i++) {
                        baralho.addCarta(new Card(i, clubesIdsArrays[i], baralho.getTema().getNome()));
                        baralho.addCarta(new Card(i, clubesIdsArrays[i], baralho.getTema().getNome()));
                    }
                }
                break;
        }

        Collections.shuffle(baralho.getCartas());
        return baralho;
    }
}