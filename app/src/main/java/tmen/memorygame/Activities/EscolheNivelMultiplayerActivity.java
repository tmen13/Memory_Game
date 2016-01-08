package tmen.memorygame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import tmen.memorygame.Adapters.LevelAdapter;
import tmen.memorygame.Adapters.LevelMultiplayerAdapter;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.Classes.Tema;
import tmen.memorygame.R;

public class EscolheNivelMultiplayerActivity extends AppCompatActivity {

    private int type = JogoActivity.MULTIPLAYER;
    private int mode = JogoActivity.SERVER;
    private Tema tema;
    private int nivelEscolhido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhe_nivel_multiplayer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", JogoActivity.MULTIPLAYER);
            tema = (Tema) intent.getSerializableExtra("tema");
            if (type == JogoActivity.MULTIPLAYERONLINE) {
                mode = intent.getIntExtra("mode",JogoActivity.SERVER);
            }
        }

        final GridView gridview = (GridView) findViewById(R.id.levelMultiplayerGridView);
        final LevelMultiplayerAdapter levelMultiplayerAdapter = new LevelMultiplayerAdapter(getApplicationContext(), tema);
        gridview.setAdapter(levelMultiplayerAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                ImageView imageView = (ImageView) v;

                if (type == JogoActivity.MULTIPLAYER) {
                    Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("tema", tema);
                    if (position == 1) {
                        intent.putExtra("nivelEscolhido", 6); //com intrusos
                    } else {
                        intent.putExtra("nivelEscolhido", 1); //sem intrusos
                    }
                    startActivity(intent);
                } else if (type == JogoActivity.MULTIPLAYERONLINE) {
                    Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("mode", mode);
                    intent.putExtra("tema", tema);
                    if (position == 1) {
                        intent.putExtra("nivelEscolhido", 6); //com intrusos
                    } else {
                        intent.putExtra("nivelEscolhido", 1); //sem intrusos
                    }
                    startActivity(intent);
                }
            }
        });
    }

}
