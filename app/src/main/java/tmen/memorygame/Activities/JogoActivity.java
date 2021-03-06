package tmen.memorygame.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


import tmen.memorygame.Adapters.CardAdapter;
import tmen.memorygame.Classes.Historico;
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
    ObjectOutputStream outputObjects;
    ObjectInputStream inputObjects;

    Handler handler = null;

    Jogo jogoActual, jogoActualRecebido;
    private GridView gridview;
    private CardAdapter cardAdapter;

    TextView nomeJogador1TextView, tentativasJogador1TextView, acertadasJogador1TextView, intrusosJogador1TextView;
    TextView jogadorActualTextView;
    TextView nomeJogador2TextView, tentativasJogador2TextView, acertadasJogador2TextView, intrusosJogador2TextView;

    LinearLayout infoJogador1, infoJogadorActual, infoJogador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MemoryGame", "onCreate");
        setContentView(R.layout.activity_jogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        infoJogador1 = (LinearLayout)findViewById(R.id.infoJogador1);
        infoJogadorActual = (LinearLayout)findViewById(R.id.infoJogadorActual);
        infoJogador2 = (LinearLayout)findViewById(R.id.infoJogador2);

        nomeJogador1TextView = (TextView)findViewById(R.id.nomeJogador1TextView);
        tentativasJogador1TextView = (TextView)findViewById(R.id.tentativasJogador1TextView);
        acertadasJogador1TextView = (TextView)findViewById(R.id.acertadasJogador1TextView);
        intrusosJogador1TextView = (TextView)findViewById(R.id.intrusosJogador1TextView);

        jogadorActualTextView = (TextView)findViewById(R.id.jogadorActualTextView);

        nomeJogador2TextView = (TextView)findViewById(R.id.nomeJogador2TextView);
        tentativasJogador2TextView = (TextView)findViewById(R.id.tentativasJogador2TextView);
        acertadasJogador2TextView = (TextView)findViewById(R.id.acertadasJogador2TextView);
        intrusosJogador2TextView = (TextView)findViewById(R.id.intrusosJogador2TextView);

        nomeJogador1TextView.setText(MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_PLAYERNAME));
        jogadorActualTextView.setText(nomeJogador1TextView.getText());

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", SINGLEPLAYER);
            if (type == SINGLEPLAYER) {
                tema = (Tema) intent.getSerializableExtra("tema");
                nivelEscolhido = intent.getIntExtra("nivelEscolhido",1);
            }
            if (type == MULTIPLAYER) {
                tema = (Tema) intent.getSerializableExtra("tema");
                nivelEscolhido = intent.getIntExtra("nivelEscolhido",5);
            }

            if (type == MULTIPLAYERONLINE) {
                mode = intent.getIntExtra("mode", SERVER);
                if (mode != CLIENT) {
                    tema = (Tema) intent.getSerializableExtra("tema");
                    nivelEscolhido = intent.getIntExtra("nivelEscolhido",5);
                }
            }
        }

        handler = new Handler();

        if (mode != CLIENT) {
            String str = "Type " + type + " " + "Mode " + mode + " " + "Tema " + tema.getNome() + " " + "Nivel " + tema.getNivelActual();
            Log.d("MemoryGame",str);
        } else {
            String str = "Type " + type + " " + "Mode " + mode;
            Log.d("MemoryGame",str);
        }

        if (mode != CLIENT) {
            initializeGameAndGridView(null);
        }

        if (type == MULTIPLAYER) {
            nomeJogador2Dialog();
        }
    }

    private void initializeGameAndGridView(Jogo jogoActualRecebido) {
        if (jogoActualRecebido == null) {
            Log.d("MemoryGame","NomeJogador1:" + nomeJogador1TextView.getText().toString());
            jogoActual = new Jogo(getApplicationContext(), type, mode, tema, nivelEscolhido, nomeJogador1TextView.getText().toString());
        } else {
            jogoActual = jogoActualRecebido;
            jogoActualRecebido = null;
            jogoActual.setmContext(getApplicationContext());
            jogoActual.setJogadorActual(OTHER);
            jogoActual.setNomeJogador2(nomeJogador1TextView.getText().toString());
        }

        gridview = (GridView) findViewById(R.id.tabuleiroGridView);
        cardAdapter = new CardAdapter(getApplicationContext(), jogoActual, type);
        gridview.setAdapter(cardAdapter);

        if (MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_TYPE_MODE).equals("type_mode_simple") || MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_TYPE_MODE).equals("type_mode_double")) {
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                Boolean waitSecondTouch = false;
                int positionSelected = -1;
                int positionPost = -1;

                @Override
                public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                    long DOUBLE_CLICK_TIME_DELTA = Long.parseLong(MySharedPreferences.getSharedPref(parent.getContext(),MySharedPreferences.PREF_CLICK_TIME));//milliseconds

                    if (MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_TYPE_MODE).equals("type_mode_double")) {
                        Log.d("MemoryGame","WaitSecondTouch: " + waitSecondTouch + "positionSelected: " + positionSelected);

                        if (waitSecondTouch == false) {
                            positionSelected = position;
                            waitSecondTouch = true;
                        } else {
                            if (positionSelected == position) {
                                action(v, position);
                            }
                            positionSelected = -1;
                            waitSecondTouch = false;
                        }


                        if (waitSecondTouch == true && DOUBLE_CLICK_TIME_DELTA != 0) {
                            positionPost = position;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (positionPost == positionSelected) {
                                        waitSecondTouch = false;
                                        positionSelected = -1;
                                        Log.d("MemoryGame", "WaitSecondTouch: " + waitSecondTouch + "positionSelected: " + positionSelected);
                                    }
                                }
                            }, DOUBLE_CLICK_TIME_DELTA);
                        }
                    }

                    if (MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_TYPE_MODE).equals("type_mode_simple"))
                    {
                        action(v, position);
                    }
                }
            });

        } else if (MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_TYPE_MODE).equals("type_mode_long")) {
            gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                    action(v, position);
                    return false;
                }
            });
        }


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
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (type) {
            case SINGLEPLAYER:
                infoJogadorActual.setVisibility(LinearLayout.GONE);
                infoJogador2.setVisibility(LinearLayout.GONE);
                break;
            case MULTIPLAYER:
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
        Log.d("MemoryGame","onPause");
        try {
            commThread.interrupt();
            if (socketGame != null)
                socketGame.close();
            if (output != null)
                output.close();
            if (input != null)
                input.close();
            if (outputObjects != null)
                outputObjects.close();
            if (inputObjects != null)
                inputObjects.close();
        } catch (Exception e) {
            Log.i("MemoryGame", e.toString());
        }
        input = null;
        output = null;
        inputObjects = null;
        outputObjects = null;
        socketGame = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                showAlert(0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlert(0);
    } //desistir do jogo

    private void showAlert(int flag) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                JogoActivity.this);
        if(flag == 0) {
            alertDialog.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertDialog.setNegativeButton(R.string.nao, null);
            alertDialog.setMessage(R.string.quit_game);
            alertDialog.setTitle(R.string.app_name);
        } else if (flag == 1){ // jogo acabou
            alertDialog.setPositiveButton(R.string.creditos_ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            String vencedor;
            if(jogoActual.getVencedor() == ME){
                vencedor = nomeJogador1TextView.getText().toString();
            } else {
                vencedor = nomeJogador2TextView.getText().toString();
            }
            alertDialog.setMessage(getString(R.string.winner) + ": " + vencedor);
            alertDialog.setTitle(R.string.end_game);

            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
        }

        alertDialog.show();
    }

    void action (View v, final int position) {
        ImageView imageView = (ImageView) v;

        if (jogoActual.getPrimeiraCarta() == null && jogoActual.getSegundaCarta() == null) { //1ªCarta
            cardAdapter.setPosPrimeiraImageView(position);
            //imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
            if (jogoActual.getBaralho().getTema().getIsDefault()) {
                imageView.setImageBitmap(decodeSampledBitmapFromResource(getApplicationContext().getResources(), cardAdapter.getItem(position).getCardFront(), 50, 50));
            } else {
                try {
                    imageView.setImageBitmap(scaleDown(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(cardAdapter.getItem(position).getCardFrontStr())), 100, true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            jogoActual.setPrimeiraCarta(cardAdapter.getItem(position));
            if (type == MULTIPLAYERONLINE) {
                moveMyPlayer(position);
            }
        } else if (jogoActual.getPrimeiraCarta() != null && jogoActual.getSegundaCarta() == null) { //2ªCarta
            cardAdapter.setPosSegundaImageView(position);
            //imageView.setImageResource(cardAdapter.getItem(position).getCardFront());
            if (jogoActual.getBaralho().getTema().getIsDefault()) {
                imageView.setImageBitmap(decodeSampledBitmapFromResource(getApplicationContext().getResources(), cardAdapter.getItem(position).getCardFront(), 50, 50));
            } else {
                try {
                    imageView.setImageBitmap(scaleDown(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(cardAdapter.getItem(position).getCardFrontStr())), 100, true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            jogoActual.setSegundaCarta(cardAdapter.getItem(position));

            if (type == MULTIPLAYERONLINE) {
                moveMyPlayer(position);
            }

            if (jogoActual.verificaJogada()) {
                if (type == SINGLEPLAYER) {
                    jogoActual.incrementaAcertadas(ME);
                    jogoActual.incrementaTentativas(ME);
                    if (jogoActual.getPrimeiraCarta().getTema() != jogoActual.getBaralho().getTema().getNome() && jogoActual.getSegundaCarta().getTema() != jogoActual.getBaralho().getTema().getNome()) {
                        jogoActual.incrementaIntrusosAcertados(ME);
                        intrusosJogador1TextView.setText(String.valueOf(jogoActual.getIntrusosAcertados(ME)));
                    }
                    tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                    acertadasJogador1TextView.setText(String.valueOf(jogoActual.getAcertadas(ME)));
                }
                if (type == MULTIPLAYER || type == MULTIPLAYERONLINE) {
                    jogoActual.incrementaAcertadas(jogoActual.getJogadorActual());
                    jogoActual.incrementaTentativas(jogoActual.getJogadorActual());
                    if (jogoActual.getPrimeiraCarta().getTema() != jogoActual.getBaralho().getTema().getNome() && jogoActual.getSegundaCarta().getTema() != jogoActual.getBaralho().getTema().getNome()) {
                        jogoActual.incrementaIntrusosAcertados(jogoActual.getJogadorActual());
                        intrusosJogador1TextView.setText(String.valueOf(jogoActual.getIntrusosAcertados(ME)));
                        intrusosJogador2TextView.setText(String.valueOf(jogoActual.getIntrusosAcertados(OTHER)));
                    }
                    tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                    acertadasJogador1TextView.setText(String.valueOf(jogoActual.getAcertadas(ME)));
                    tentativasJogador2TextView.setText(String.valueOf(jogoActual.getTentativas(OTHER)));
                    acertadasJogador2TextView.setText(String.valueOf(jogoActual.getAcertadas(OTHER)));
                }

                cardAdapter.getImageViewsBloqueadas().add(cardAdapter.getPosPrimeiraImageView());
                cardAdapter.getImageViewsBloqueadas().add(cardAdapter.getPosSegundaImageView());
                jogoActual.resetJogada();
                cardAdapter.resetPosImageViews();
            } else {
                if (type == SINGLEPLAYER) {
                    jogoActual.incrementaTentativas(ME);
                    tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                }

                if (type == MULTIPLAYER || type == MULTIPLAYERONLINE) {
                    jogoActual.incrementaTentativas(jogoActual.getJogadorActual());
                    jogoActual.setJogadorActual(jogoActual.getProximoJogador());
                    tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                    tentativasJogador2TextView.setText(String.valueOf(jogoActual.getTentativas(OTHER)));
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        jogoActual.resetJogada();
                        cardAdapter.resetPosImageViews();
                        gridview.invalidateViews();

                        if (type == MULTIPLAYER || type == MULTIPLAYERONLINE) {
                            if (jogoActual.getJogadorActual() == ME) {
                                jogadorActualTextView.setText(nomeJogador1TextView.getText());
                            } else {
                                jogadorActualTextView.setText(nomeJogador2TextView.getText());
                            }
                        }
                    }
                }, 1250);
            }

            if (jogoActual.verificaFinal()) {
                if (type == SINGLEPLAYER) {
                    List<Tema> temas = MySharedPreferences.getTemasFromFile(getApplicationContext());
                    for (int i = 0; i < temas.size(); i++) {
                        Log.d("MemoryGame", "TemaNome: " + temas.get(i).getNome());
                        if (temas.get(i).getNome().equals(tema.getNome())) {
                            Log.d("MemoryGame", "NivelActual:" + temas.get(i).getNivelActual() + " NivelEscolhido:" + nivelEscolhido);
                            if (temas.get(i).getNivelActual() == nivelEscolhido && nivelEscolhido < tema.getNumNiveis()) {
                                temas.get(i).setNivelActual(temas.get(i).getNivelActual() + 1);
                                tema.setNivelActual(tema.getNivelActual() + 1);

                                MySharedPreferences.saveTemaToFile(getApplicationContext(), temas);

                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("tema", tema);
                                setResult(1, returnIntent);
                                //finish();
                            }
                        }
                    }
                }

                saveToHistory();

                showAlert(1);
            }
        } else {
            Log.e("MemoryGameJogoActivity", "Erro");
        }
    }

    void nomeJogador2Dialog() {
        final EditText nomeJogador2EditText = new EditText(this);
        nomeJogador2EditText.setText(R.string.player_2);
        nomeJogador2EditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(
                JogoActivity.this);

        alertDialog.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                jogoActual.setNomeJogador2(nomeJogador2EditText.getText().toString());
                nomeJogador2TextView.setText(jogoActual.getNomeJogador2());
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        alertDialog.setMessage(R.string.player_2_name);
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setView(nomeJogador2EditText);
        alertDialog.show();
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
                    Log.d("MemoryGame","[Servidor]Conectei com o cliente");
                    sendGameInfo(jogoActual);
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
        edtIP.setText("192.168.1.0");
        AlertDialog ad = new AlertDialog.Builder(this).setTitle(R.string.clientdlg_title)
                .setMessage(R.string.server_ip).setView(edtIP)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
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
                    Log.d("MemoryGame", "SocketGame: " + socketGame);
                } catch (Exception e) {
                    Log.d("MemoryGame", "Catch");
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
                Log.d("MemoryGame","[Cliente]Conectei com o servidor");
                receiveGameInfo();
                commThread.start();
            }
        });
        t.start();
    }

    void sendGameInfo(final Jogo jogoActualX) {
        try {
            if(outputObjects == null)
                outputObjects = new ObjectOutputStream(socketGame.getOutputStream());
            if (inputObjects == null)
                inputObjects = new ObjectInputStream(socketGame.getInputStream());

            Log.d("MemoryGame", "Sending game info: " + jogoActualX);

            outputObjects.writeObject(jogoActualX);
            outputObjects.flush();

            final String nomeJogador2Recebido = (String) inputObjects.readObject();

            Log.d("MemoryGame", "Received name: " + nomeJogador2Recebido);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    jogoActual.setNomeJogador2(nomeJogador2Recebido);
                    nomeJogador2TextView.setText(jogoActual.getNomeJogador2());
                }
            });

        } catch (Exception e) {
            Log.d("MemoryGame", "Error sending game info");
            Log.d("MemoryGame", "Exception:" + e);
        }
    }

    void receiveGameInfo() {
        try {
            if (inputObjects == null)
                inputObjects = new ObjectInputStream(socketGame.getInputStream());
            if (outputObjects == null)
                outputObjects = new ObjectOutputStream(socketGame.getOutputStream());

            final Jogo jogoActualX = (Jogo) inputObjects.readObject();

            Log.d("MemoryGame", "Received: " + jogoActualX);

            jogoActualX.setMode(JogoActivity.CLIENT);
            jogoActualX.setJogadorActual(JogoActivity.OTHER);
            tema = jogoActualX.getTema();
            nivelEscolhido = jogoActualX.getNivelEscolhido();

            Log.d("MemoryGame", "Sending name: " + nomeJogador1TextView.getText().toString());

            outputObjects.writeObject(nomeJogador1TextView.getText().toString());
            //output.flush();

            handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initializeGameAndGridView(jogoActualX);
                        nomeJogador2TextView.setText(jogoActualX.getNomeJogador1());
                        jogadorActualTextView.setText(jogoActualX.getNomeJogador1());
                    }
                });

        } catch (Exception e) {
            Log.d("MemoryGame", "Exception:" + e);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Toast.makeText(getApplicationContext(),
                            R.string.error_receive_game, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
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
                            moveOtherPlayer(move);
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Toast.makeText(getApplicationContext(),
                                R.string.game_ended, Toast.LENGTH_SHORT)
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

    void moveOtherPlayer(int move) {
        if (cardAdapter.getPosPrimeiraImageView() == null && cardAdapter.getPosSegundaImageView() == null) {
            cardAdapter.setPosPrimeiraImageView(move);
            jogoActual.setPrimeiraCarta(cardAdapter.getItem(move));
            gridview.invalidateViews();
        } else if (cardAdapter.getPosPrimeiraImageView() != null && cardAdapter.getPosSegundaImageView() == null) {
            cardAdapter.setPosSegundaImageView(move);
            jogoActual.setSegundaCarta(cardAdapter.getItem(move));
            gridview.invalidateViews();

            if (jogoActual.verificaJogada()) {
                jogoActual.incrementaAcertadas(jogoActual.getJogadorActual());
                jogoActual.incrementaTentativas(jogoActual.getJogadorActual());

                if (jogoActual.getPrimeiraCarta().getTema() != jogoActual.getBaralho().getTema().getNome() && jogoActual.getSegundaCarta().getTema() != jogoActual.getBaralho().getTema().getNome()) {
                    jogoActual.incrementaIntrusosAcertados(jogoActual.getJogadorActual());
                    intrusosJogador1TextView.setText(String.valueOf(jogoActual.getIntrusosAcertados(ME)));
                    intrusosJogador2TextView.setText(String.valueOf(jogoActual.getIntrusosAcertados(OTHER)));
                }

                tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                acertadasJogador1TextView.setText(String.valueOf(jogoActual.getAcertadas(ME)));
                tentativasJogador2TextView.setText(String.valueOf(jogoActual.getTentativas(OTHER)));
                acertadasJogador2TextView.setText(String.valueOf(jogoActual.getAcertadas(OTHER)));

                cardAdapter.getImageViewsBloqueadas().add(cardAdapter.getPosPrimeiraImageView());
                cardAdapter.getImageViewsBloqueadas().add(cardAdapter.getPosSegundaImageView());
                jogoActual.resetJogada();
                cardAdapter.resetPosImageViews();
            } else {
                jogoActual.incrementaTentativas(jogoActual.getJogadorActual());
                jogoActual.setJogadorActual(jogoActual.getProximoJogador());
                tentativasJogador1TextView.setText(String.valueOf(jogoActual.getTentativas(ME)));
                tentativasJogador2TextView.setText(String.valueOf(jogoActual.getTentativas(OTHER)));


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        jogoActual.resetJogada();
                        cardAdapter.resetPosImageViews();
                        gridview.invalidateViews();


                        if (jogoActual.getJogadorActual() == ME) {
                            jogadorActualTextView.setText(nomeJogador1TextView.getText());
                        } else {
                            jogadorActualTextView.setText(nomeJogador2TextView.getText());
                        }

                    }
                }, 750);
            }


            if (jogoActual.verificaFinal()) {
                saveToHistory();
                showAlert(1);
            }
        }
    }

    void moveMyPlayer(final int move) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("MemoryGame", "Sending a move: " + move);
                    output.println(move);
                    output.flush();
                } catch (Exception e) {
                    Log.d("MemoryGame", "Error sending a move");
                }
            }
        });
        t.start();
    }

    void saveToHistory() {
        Historico historico = new Historico(jogoActual.getTipo(), jogoActual.getMode(), jogoActual.getTema().getNome(), jogoActual.getNomeJogador1(), jogoActual.getNomeJogador2(), jogoActual.getTentativas(), jogoActual.getAcertadas(), jogoActual.getIntrusosAcertados(), jogoActual.getVencedor());
        List<Historico> historicoArrayList = new ArrayList<>();
        File file = getApplicationContext().getFileStreamPath(MySharedPreferences.PATH_HITORICO);
        if(file.exists()) {
            Log.d("MemoryGame", "Ficheiro existe!");
            historicoArrayList.addAll(MySharedPreferences.getHistoricoFromFile(getApplicationContext()));
        }
        historicoArrayList.add(historico);
        MySharedPreferences.saveHisticoToFile(getApplicationContext(), historicoArrayList);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
}