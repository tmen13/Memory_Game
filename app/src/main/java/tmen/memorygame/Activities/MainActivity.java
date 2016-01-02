package tmen.memorygame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.R;

public class MainActivity extends AppCompatActivity {

    Button singlePlayerBtn, multiPlayerBtn, multiPlayerOnBtn, historicoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLocale(MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_LANG));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String fontPath = "fonts/CarterOne.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        singlePlayerBtn = (Button) findViewById(R.id.singlePlayerBtn);
        multiPlayerBtn = (Button) findViewById(R.id.multiPlayerBtn);
        multiPlayerOnBtn = (Button) findViewById(R.id.multiPlayerOnBtn);
        historicoBtn = (Button) findViewById(R.id.historicoBtn);

        singlePlayerBtn.setTypeface(tf);
        multiPlayerBtn.setTypeface(tf);
        multiPlayerOnBtn.setTypeface(tf);
        historicoBtn.setTypeface(tf);

        singlePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EscolheTemaActivity.class);
                intent.putExtra("type",JogoActivity.SINGLEPLAYER);
                startActivity(intent);
            }
        });

        multiPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EscolheTemaActivity.class);
                intent.putExtra("type",JogoActivity.MULTIPLAYER);
                startActivity(intent);
            }
        });

        multiPlayerOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuMultiPlayerOnActivity.class);
                intent.putExtra("type",JogoActivity.MULTIPLAYERONLINE);
                startActivity(intent);
            }
        });

        historicoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoricoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        doExit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_teste:
                Intent intentTeste = new Intent(this, TesteActivity.class);
                startActivity(intentTeste);
                break;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.action_creditos:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doExit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);

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


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        Log.i("aqui", newConfig.locale.getCountry().toString());
        super.onConfigurationChanged(newConfig);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getApplicationContext().getResources().getDisplayMetrics());
        Log.i("aqui", config.locale.getCountry().toString());
        onConfigurationChanged(config);

    }
}
