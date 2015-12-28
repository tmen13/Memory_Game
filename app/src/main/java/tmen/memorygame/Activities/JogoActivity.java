package tmen.memorygame.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


import tmen.memorygame.Adapters.CardAdapter;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.Classes.GeradorBaralhos;
import tmen.memorygame.R;

public class JogoActivity extends AppCompatActivity {

    Jogo jogoActual;
    List<String> listaTemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final TextView tvJogadas = (TextView)findViewById(R.id.numJogadasTV);
        tvJogadas.setText("0");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Buscar tema e nivel ao intent
        GeradorBaralhos.criaBaralhos();
        //listaTemas = GeradorBaralhos.getTemas();
        jogoActual = new Jogo(getApplicationContext(),"Bandeiras",0);

        GridView gridview = (GridView) findViewById(R.id.tabuleiroGridView);
        final CardAdapter cardAdapter = new CardAdapter(getApplicationContext(), jogoActual);
        gridview.setAdapter(cardAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ImageView imageView = (ImageView) v;

                if (jogoActual.getPrimeiraCarta() == null && jogoActual.getSegundaCarta() == null) { //1ªCarta
                    cardAdapter.setPrimeiraImageView(imageView);
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setPrimeiraCarta(cardAdapter.getItem(position));
                } else if (jogoActual.getPrimeiraCarta() != null && jogoActual.getSegundaCarta() == null) { //2ªCarta
                    cardAdapter.setSegundaImageView(imageView);
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setSegundaCarta(cardAdapter.getItem(position));

                    if (jogoActual.verificaJogada()) {
                        cardAdapter.getImageViewsBloqueadas().add(Integer.parseInt(cardAdapter.getPrimeiraImageView().getTag().toString()));
                        cardAdapter.getImageViewsBloqueadas().add(Integer.parseInt(cardAdapter.getSegundaImageView().getTag().toString()));
                        jogoActual.resetJogada();
                        cardAdapter.resetImageViews();
                        jogoActual.incPontuacao();

                    } else {

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cardAdapter.getPrimeiraImageView().setImageResource(jogoActual.getPrimeiraCarta().getCardCover());
                                cardAdapter.getSegundaImageView().setImageResource(jogoActual.getSegundaCarta().getCardCover());
                                jogoActual.resetJogada();
                                cardAdapter.resetImageViews();
                            }
                        }, 1500);

                    }
                    jogoActual.incJogadas();
                    tvJogadas.setText(Integer.toString(jogoActual.getNumJogadas()));
                } else {
                    Log.e("MemoryGameJogoActivity", "Erro");
                }
            }
        });
    }

}