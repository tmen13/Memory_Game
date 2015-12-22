package tmen.memorygame.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

import tmen.memorygame.Adapters.CardAdapter;
import tmen.memorygame.Classes.Baralho;
import tmen.memorygame.Classes.Card;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.R;

public class JogoActivity extends AppCompatActivity {

    Jogo jogoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Buscar tema e nivel ao intent

        jogoActual = new Jogo(getApplicationContext(),"Bandeiras",0);

        GridView gridview = (GridView) findViewById(R.id.tabuleiroGridView);
        final CardAdapter cardAdapter = new CardAdapter(getApplicationContext(), jogoActual);
        gridview.setAdapter(cardAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(JogoActivity.this, "Pos:" + position + " " + "Id:" + cardAdapter.getItemId(position),
                        Toast.LENGTH_SHORT).show();
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
                        //cardAdapter.blockImageViews();
                        jogoActual.resetJogada();
                        cardAdapter.resetImageViews();
                        //incrementar contador de acertadas
                    } else {
                        //Aguarda 2seg antes de virar cartas, etc;
                        /*
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        */
                        //cardAdapter.unlockImageViews();
                        cardAdapter.getPrimeiraImageView().setImageResource(jogoActual.getPrimeiraCarta().getCardCover());
                        cardAdapter.getSegundaImageView().setImageResource(jogoActual.getSegundaCarta().getCardCover());
                        jogoActual.resetJogada();
                        cardAdapter.resetImageViews();
                    }
                    //incrementar contador de jogadas
                } else {
                    Log.e("MemoryGameJogoActivity","Erro");
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }





}
