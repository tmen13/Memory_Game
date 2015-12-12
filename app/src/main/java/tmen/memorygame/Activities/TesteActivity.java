package tmen.memorygame.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import tmen.memorygame.Classes.Card;
import tmen.memorygame.R;

public class TesteActivity extends AppCompatActivity {

    TextView tv;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv = (TextView)findViewById(R.id.tv1);
        bt = (Button)findViewById(R.id.imgBt);

        Card carta = new Card(0);

        bt.setBackgroundResource(carta.getCardCover());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("PlayerName", "tmen13");
        editor.apply();

        tv.setText(preferences.getString("PlayerName", ""));

    }

}
