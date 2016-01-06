package tmen.memorygame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Adapters.LevelMultiplayerAdapter;
import tmen.memorygame.Adapters.ThemeAdapter;
import tmen.memorygame.Classes.GeradorBaralhos;
import tmen.memorygame.Classes.GeradorTemas;
import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.Classes.Tema;
import tmen.memorygame.R;

public class EscolheTemaActivity extends AppCompatActivity {

    private int type = JogoActivity.SINGLEPLAYER;
    private int mode = JogoActivity.SERVER;
    private List<Tema> temas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhe_tema);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentMain = getIntent();
        if (intentMain != null) {
            type = intentMain.getIntExtra("type",JogoActivity.SINGLEPLAYER);
            if (type == JogoActivity.MULTIPLAYERONLINE) {
                mode = intentMain.getIntExtra("mode",JogoActivity.SERVER);
            }
        }

        //load temas e niveis
        File file = getApplicationContext().getFileStreamPath(MySharedPreferences.PATH_TEMA);
        if(!file.exists()) {
            Log.d("MemoryGame","Ficheiro nao existe!");
            temas.addAll(GeradorTemas.getTemas()); //
            MySharedPreferences.saveTemaToFile(getApplicationContext(), temas); //
        }

        temas.clear();
        if (type == JogoActivity.MULTIPLAYERONLINE) {
            temas.addAll(MySharedPreferences.getTemasDefault(getApplicationContext()));
        } else {
            temas.addAll(MySharedPreferences.getTemasFromFile(getApplicationContext()));
        }

        for (int i = 0; i < temas.size(); i++) {
            Log.d("MemoryGame", "Tema:" + temas.get(i).getNome() + " NumNiveis:" + temas.get(i).getNumNiveis() + " NivelActual:" + temas.get(i).getNivelActual() + " isDefault: " + temas.get(i).getIsDefault());
        }

        final GridView gridview = (GridView) findViewById(R.id.escolheTemaGridView);
        final ThemeAdapter themeAdapter = new ThemeAdapter(getApplicationContext(), temas);
        gridview.setAdapter(themeAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                ImageView imageView = (ImageView) v;

                if (type == JogoActivity.SINGLEPLAYER) {
                    Intent intent = new Intent(getApplicationContext(), EscolheNivelActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("tema", temas.get(position));
                    startActivity(intent);
                } else if (type == JogoActivity.MULTIPLAYER) {
                    Intent intent = new Intent(getApplicationContext(), EscolheNivelMultiplayerActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("tema", temas.get(position));
                    startActivity(intent);
                } else if (type == JogoActivity.MULTIPLAYERONLINE) {
                    Intent intent = new Intent(getApplicationContext(), EscolheNivelMultiplayerActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("mode", mode);
                    intent.putExtra("tema", temas.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
