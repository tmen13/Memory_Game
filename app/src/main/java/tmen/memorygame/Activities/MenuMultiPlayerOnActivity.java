package tmen.memorygame.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tmen.memorygame.R;

public class MenuMultiPlayerOnActivity extends AppCompatActivity {

    private int type = JogoActivity.MULTIPLAYERONLINE;

    Button serverBtn, clientBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_multi_player_on);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Intent intentMain = getIntent();
        if (intentMain != null) {
            type = intentMain.getIntExtra("type",JogoActivity.MULTIPLAYERONLINE);
        }
        String fontPath = "fonts/CarterOne.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        serverBtn = (Button) findViewById(R.id.serverModeBtn);
        clientBtn = (Button) findViewById(R.id.clientModeBtn);
        serverBtn.setTypeface(tf);
        clientBtn.setTypeface(tf);

        serverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EscolheTemaActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("mode", JogoActivity.SERVER);
                startActivity(intent);
            }
        });

        clientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("mode", JogoActivity.CLIENT);
                startActivity(intent);
            }
        });

    }
}
