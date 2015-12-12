package tmen.memorygame.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tmen.memorygame.Classes.Card;
import tmen.memorygame.R;

public class TesteActivity extends AppCompatActivity {

    public static final String PREF_PLAYERNAME = "playername";
    public static final String PREF_LANG = "lang";

    TextView tv;
    Button bt;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        tv = (TextView)findViewById(R.id.tv1);
        bt = (Button)findViewById(R.id.imgBt);
        addToSharedPref(PREF_PLAYERNAME, "tmen13");
        Card carta = new Card(0);

        bt.setBackgroundResource(carta.getCardCover());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(preferences.getString(PREF_PLAYERNAME, ""));
            }
        });


    }

    public void addToSharedPref(String key, String obj){
        editor.putString(key, obj);
        editor.apply();
    }

    public String getSharedPref(String key){
        return preferences.getString(key, "");
    }

}
