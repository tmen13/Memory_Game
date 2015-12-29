package tmen.memorygame.Activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tmen.memorygame.Classes.Card;
import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.R;

public class TesteActivity extends AppCompatActivity {

    public static final String PREF_PLAYERNAME = "pref_nome_jogador";
    public static final String PREF_LANG = "pref_idioma";

    TextView tv;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv = (TextView)findViewById(R.id.tv1);

        bt = (Button)findViewById(R.id.imgBt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(MySharedPreferences.getSharedPref(getApplicationContext(),PREF_PLAYERNAME));
            }
        });
    }



}
