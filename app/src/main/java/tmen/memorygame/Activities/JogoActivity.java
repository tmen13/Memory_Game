package tmen.memorygame.Activities;

import android.app.ProgressDialog;
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

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


import tmen.memorygame.Adapters.CardAdapter;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.Classes.GeradorBaralhos;
import tmen.memorygame.R;

public class JogoActivity extends AppCompatActivity {
    public static final int SINGLEPLAYER = 0;
    public static final int MULTIPLAYER = 1;
    public static final int MULTIPLAYERONLINE = 2;
    public static final int SERVER = 0;
    public static final int CLIENT = 1;
    public static final int ME = 0;
    public static final int OTHER = 1;
    private static final int PORT = 8899;

    int mode = SERVER;

    ProgressDialog pd = null;

    ServerSocket serverSocket=null;
    Socket socketGame = null;
    BufferedReader input;
    PrintWriter output;
    Handler procMsg = null;

    int tentativas[] = { 0, 0 };
    int acertadas[] = { 0, 0 };

    Jogo jogoActual;
    //List<String> listaTemas;

    TextView jogadasTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jogadasTextView = (TextView)findViewById(R.id.numJogadasTV);
        jogadasTextView.setText("0");

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
                    jogadasTextView.setText(Integer.toString(jogoActual.getNumJogadas()));
                } else {
                    Log.e("MemoryGameJogoActivity", "Erro");
                }
            }
        });
    }

}