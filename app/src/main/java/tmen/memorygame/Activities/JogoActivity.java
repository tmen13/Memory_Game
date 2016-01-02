package tmen.memorygame.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;


import tmen.memorygame.Adapters.CardAdapter;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.Classes.GeradorBaralhos;
import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.Classes.Tema;
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

    Tema tema;
    int nivelEscolhido;
    int type = SINGLEPLAYER;
    int mode = SERVER;

    ProgressDialog pd = null;

    ServerSocket serverSocket=null;
    Socket socketGame = null;
    BufferedReader input;
    PrintWriter output;
    Handler handler = null;

    Jogo jogoActual;
    //List<String> listaTemas;

    //TextView jogadasTextView;
    TextView nomeJogador1TextView, tentativasJogador1TextView, acertadasJogador1TextView, intrusosJogador1TextView;
    TextView nomeJogador2TextView, tentativasJogador2TextView, acertadasJogador2TextView, intrusosJogador2TextView;

    LinearLayout infoJogadorActual, infoJogador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MemoryGame", "onCreate");
        setContentView(R.layout.activity_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //jogadasTextView = (TextView)findViewById(R.id.numJogadasTV);
        //jogadasTextView.setText("0");

        infoJogadorActual = (LinearLayout)findViewById(R.id.infoJogadorActual);
        infoJogador2 = (LinearLayout)findViewById(R.id.infoJogador2);

        nomeJogador1TextView = (TextView)findViewById(R.id.nomeJogador1TextView);
        tentativasJogador1TextView = (TextView)findViewById(R.id.tentativasJogador1TextView);
        acertadasJogador1TextView = (TextView)findViewById(R.id.acertadasJogador1TextView);
        intrusosJogador1TextView = (TextView)findViewById(R.id.intrusosJogador1TextView);

        nomeJogador2TextView = (TextView)findViewById(R.id.nomeJogador2TextView);
        tentativasJogador2TextView = (TextView)findViewById(R.id.tentativasJogador2TextView);
        acertadasJogador2TextView = (TextView)findViewById(R.id.acertadasJogador2TextView);
        intrusosJogador2TextView = (TextView)findViewById(R.id.intrusosJogador2TextView);

        nomeJogador1TextView.setText(MySharedPreferences.getSharedPref(getApplicationContext(), SettingsActivity.PREF_PLAYERNAME));

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", SINGLEPLAYER);
            if (type == SINGLEPLAYER) {
                tema = (Tema) intent.getSerializableExtra("tema");
                nivelEscolhido = intent.getIntExtra("nivelEscolhido",1);
            }
            if (type == MULTIPLAYER) {
                tema = (Tema) intent.getSerializableExtra("tema");
            }

            if (type == MULTIPLAYERONLINE) {
                mode = intent.getIntExtra("mode", SERVER);
                tema = (Tema) intent.getSerializableExtra("tema");
            }
        }

        handler = new Handler();

        String str = "Type " + type + " " + "Mode " + mode + " " + "Tema " + tema.getNome() + " " + "Nivel " + tema.getNivelActual();
        Log.d("MemoryGame",str);

        //GeradorBaralhos.criaBaralhos();
        //listaTemas = GeradorBaralhos.getTemas();
        jogoActual = new Jogo(getApplicationContext(), type, tema, nivelEscolhido);

        final GridView gridview = (GridView) findViewById(R.id.tabuleiroGridView);
        final CardAdapter cardAdapter = new CardAdapter(getApplicationContext(), jogoActual);
        gridview.setAdapter(cardAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                ImageView imageView = (ImageView) v;

                if (jogoActual.getPrimeiraCarta() == null && jogoActual.getSegundaCarta() == null) { //1ªCarta
                    cardAdapter.setPosPrimeiraImageView(position);
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setPrimeiraCarta(cardAdapter.getItem(position));
                } else if (jogoActual.getPrimeiraCarta() != null && jogoActual.getSegundaCarta() == null) { //2ªCarta
                    cardAdapter.setPosSegundaImageView(position);
                    imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
                    jogoActual.setSegundaCarta(cardAdapter.getItem(position));

                    if (jogoActual.verificaJogada()) {
                        cardAdapter.getImageViewsBloqueadas().add(cardAdapter.getPosPrimeiraImageView());
                        cardAdapter.getImageViewsBloqueadas().add(cardAdapter.getPosSegundaImageView());
                        jogoActual.resetJogada();
                        cardAdapter.resetPosImageViews();
                        if (type == SINGLEPLAYER) {
                            jogoActual.incrementaAcertadas(ME);
                            jogoActual.incrementaTentativas(ME);
                            tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                            acertadasJogador1TextView.setText(String.valueOf(jogoActual.getAcertadas(ME)));
                        }
                        if (type == MULTIPLAYER) {
                            jogoActual.incrementaAcertadas(jogoActual.getJogadorActual());
                            jogoActual.incrementaTentativas(jogoActual.getJogadorActual());

                            tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                            acertadasJogador1TextView.setText(String.valueOf(jogoActual.getAcertadas(ME)));
                            tentativasJogador2TextView.setText(String.valueOf(jogoActual.getTentativas(OTHER)));
                            acertadasJogador2TextView.setText(String.valueOf(jogoActual.getAcertadas(OTHER)));

                        }
                        //jogoActual.incPontuacao();

                    } else {
                        if (type == SINGLEPLAYER) {
                            jogoActual.incrementaTentativas(ME);
                            tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                        }

                        if (type == MULTIPLAYER) {
                            jogoActual.incrementaTentativas(jogoActual.getJogadorActual());
                            jogoActual.setJogadorActual(jogoActual.getProximoJogador());
                            tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                            tentativasJogador2TextView.setText(String.valueOf(jogoActual.getTentativas(OTHER)));
                        }

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //ImageView primeiraImageView = (ImageView) gridview.getChildAt(cardAdapter.getPosPrimeiraImageView());
                                //primeiraImageView.setImageResource(jogoActual.getPrimeiraCarta().getCardCover());
                                //ImageView segundaImageView = (ImageView) gridview.getChildAt(cardAdapter.getPosSegundaImageView());
                                //segundaImageView.setImageResource(jogoActual.getPrimeiraCarta().getCardCover());
                                jogoActual.resetJogada();
                                cardAdapter.resetPosImageViews();
                                gridview.invalidateViews();
                            }
                        }, 750);
                    }
                    //jogoActual.incJogadas();
                    //jogadasTextView.setText(Integer.toString(jogoActual.getNumJogadas()));

                    if (jogoActual.verificaFinal()) {
                        finish();
                        Toast.makeText(getApplicationContext(),"Fim do Jogo. Vencedor:" + jogoActual.getVencedor(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("MemoryGameJogoActivity", "Erro");
                }
            }
        });

        if (type == SINGLEPLAYER) {
            switch (nivelEscolhido) {
                case 1:
                    gridview.setNumColumns(2); //2x2
                    break;
                case 2:
                    gridview.setNumColumns(2); //3x2
                    break;
                case 3:
                    gridview.setNumColumns(3); //4x3
                    break;
                case 4:
                    gridview.setNumColumns(4); //5x4
                    break;
                case 5:
                    gridview.setNumColumns(5); //6x5
                    break;
                case 6:
                    gridview.setNumColumns(5); //6x5 c/ intruso
                    break;
                default:
                    break;
            }
        } else {
            gridview.setNumColumns(5); //6x5
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MemoryGame","onResume");
        switch (type) {
            case SINGLEPLAYER:
                infoJogadorActual.setVisibility(LinearLayout.GONE);
                infoJogador2.setVisibility(LinearLayout.GONE);
                break;
            case MULTIPLAYER:
                nomeJogador2Dialog();
                break;
            case MULTIPLAYERONLINE:
                if (mode == SERVER)
                    server();
                else  // CLIENT
                    clientDlg();
                break;
            default:
                break;
        }
    }

    protected void onPause() {
        super.onPause();
        try {
            commThread.interrupt();
            if (socketGame != null)
                socketGame.close();
            if (output != null)
                output.close();
            if (input != null)
                input.close();
        } catch (Exception e) {
        }
        input = null;
        output = null;
        socketGame = null;
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                doExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        doExit();
    }

    private void doExit() {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(
                JogoActivity.this);

        alertDialog.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setNegativeButton(R.string.nao, null);
        alertDialog.setMessage(R.string.confima_sair);
        alertDialog.setTitle(R.string.app_name);
        alertDialog.show();
    }

    void nomeJogador2Dialog() {
        final EditText nomeJogador2EditText = new EditText(this);
        nomeJogador2EditText.setText("Jogador2");

        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(
                JogoActivity.this);

        alertDialog.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                nomeJogador2TextView.setText(nomeJogador2EditText.getText().toString());
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        alertDialog.setMessage("Nome Jogador 2");
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setView(nomeJogador2EditText);
        alertDialog.show();






        /*AlertDialog ad = new AlertDialog.Builder(this).setTitle(R.string.app_name)
                .setMessage("Nome Jogador 2").setView(nomeJogador2EditText)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nomeJogador2TextView.setText(nomeJogador2EditText.getText().toString());
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).create();
        ad.show();*/
    }

    void server() {
        // WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        // String ip = Formatter.formatIpAddress(wm.getConnectionInfo()
        // .getIpAddress());
        String ip = getLocalIpAddress();
        pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.serverdlg_msg) + "\n(IP: " + ip
                + ")");
        pd.setTitle(R.string.serverdlg_title);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    serverSocket = null;
                }
            }
        });
        pd.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                    socketGame = serverSocket.accept();
                    serverSocket.close();
                    serverSocket=null;
                    commThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    socketGame = null;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        if (socketGame == null)
                            finish();
                    }
                });
            }
        });
        t.start();
    }

    void clientDlg() {
        final EditText edtIP = new EditText(this);
        edtIP.setText("10.0.2.2");
        AlertDialog ad = new AlertDialog.Builder(this).setTitle(R.string.clientdlg_title)
                .setMessage("Server IP").setView(edtIP)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        client(edtIP.getText().toString(), PORT); // to test with emulators: PORTaux);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).create();
        ad.show();
    }

    void client(final String strIP, final int Port) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("MemoryGame", "Connecting to the server  " + strIP);
                    socketGame = new Socket(strIP, Port);
                } catch (Exception e) {
                    socketGame = null;
                }
                if (socketGame == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                    return;
                }
                commThread.start();
            }
        });
        t.start();
    }

    Thread commThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(
                        socketGame.getInputStream()));
                output = new PrintWriter(socketGame.getOutputStream());
                while (!Thread.currentThread().isInterrupted()) {
                    String read = input.readLine();
                    final int move = Integer.parseInt(read);
                    Log.d("MemoryGame", "Received: " + move);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //moveOtherPlayer(move);
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Toast.makeText(getApplicationContext(),
                                "The game was finished", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        }
    });

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}