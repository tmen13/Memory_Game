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
import android.widget.Toast;

import java.util.List;

import tmen.memorygame.Adapters.CardAdapter;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.Classes.geradorBaralhos;
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
        geradorBaralhos.criaBaralhos();
        listaTemas = geradorBaralhos.getTemas();
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
                    //cardAdapter.setPosPrimeiraImageView(position);
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setPrimeiraCarta(cardAdapter.getItem(position));
                } else if (jogoActual.getPrimeiraCarta() != null && jogoActual.getSegundaCarta() == null) { //2ªCarta
                    cardAdapter.setSegundaImageView(imageView);
                    //cardAdapter.setPosSegundaImageView(position);
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setSegundaCarta(cardAdapter.getItem(position));

                    if (jogoActual.verificaJogada()) {
                        cardAdapter.blockImageViews();
                        cardAdapter.getImageViewsBloqueadas().add(Integer.parseInt(cardAdapter.getPrimeiraImageView().getTag().toString()));
                        cardAdapter.getImageViewsBloqueadas().add(Integer.parseInt(cardAdapter.getSegundaImageView().getTag().toString()));
                        jogoActual.resetJogada();
                        cardAdapter.resetImageViews();
                        cardAdapter.resetPosImageViews();
                        jogoActual.incPontuacao();

                     } else {
                        //Aguarda 1seg antes de virar cartas, etc;
                       /* try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } */
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
                        //cardAdapter.unlockImageViews();

                        //cardAdapter.resetPosImageViews();
                    }
                    jogoActual.incJogadas();
                    tvJogadas.setText(Integer.toString(jogoActual.getNumJogadas()));
                } else {
                    Log.e("MemoryGameJogoActivity","Erro");
                }
            }
        });
    }

}