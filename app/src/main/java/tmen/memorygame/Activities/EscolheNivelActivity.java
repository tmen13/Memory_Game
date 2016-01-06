package tmen.memorygame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import tmen.memorygame.Adapters.CardAdapter;
import tmen.memorygame.Adapters.LevelAdapter;
import tmen.memorygame.Classes.Tema;
import tmen.memorygame.R;

public class EscolheNivelActivity extends AppCompatActivity {

    private int type = JogoActivity.SINGLEPLAYER;
    private Tema tema;
    private int nivelEscolhido;

    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhe_nivel);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", JogoActivity.SINGLEPLAYER);
            tema = (Tema) intent.getSerializableExtra("tema");
        }

        gridview = (GridView) findViewById(R.id.levelGridView);
        final LevelAdapter levelAdapter = new LevelAdapter(getApplicationContext(), tema);
        gridview.setAdapter(levelAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                ImageView imageView = (ImageView) v;

                Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("tema", tema);
                intent.putExtra("nivelEscolhido", position + 1);
                startActivityForResult(intent,0);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MemoryGame","onResume");
        if (gridview == null) {

        } else {
            gridview.invalidateViews();
        }
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
