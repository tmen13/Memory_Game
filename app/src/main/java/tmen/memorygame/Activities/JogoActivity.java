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
    ImageView primeiraImageView, segundaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Buscar tema e nivel do intent

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
                    primeiraImageView = imageView;
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setPrimeiraCarta(cardAdapter.getItem(position));
                } else if (jogoActual.getPrimeiraCarta() != null && jogoActual.getSegundaCarta() == null) { //2ªCarta
                    segundaImageView = imageView;
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setSegundaCarta(cardAdapter.getItem(position));

                    if (jogoActual.verificaJogada()) {
                        blockImageViews();
                        jogoActual.resetJogada();
                        resetImageViews();
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
                        unlockImageViews();
                        primeiraImageView.setImageResource(jogoActual.getPrimeiraCarta().getCardCover());
                        segundaImageView.setImageResource(jogoActual.getSegundaCarta().getCardCover());
                        jogoActual.resetJogada();
                        resetImageViews();
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

    public void blockImageViews(){
        //primeiraImageView.setClickable(false);
        //segundaImageView.setClickable(false);
    }

    public void unlockImageViews(){
        //primeiraImageView.setClickable(true);
        //segundaImageView.setClickable(true);
    }

    public void resetImageViews() {
        primeiraImageView = null;
        segundaImageView = null;
    }



}
