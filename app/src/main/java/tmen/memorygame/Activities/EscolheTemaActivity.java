package tmen.memorygame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Classes.GeradorBaralhos;
import tmen.memorygame.Classes.GeradorTemas;
import tmen.memorygame.Classes.Tema;
import tmen.memorygame.R;

public class EscolheTemaActivity extends AppCompatActivity {

    private int type = JogoActivity.SINGLEPLAYER;
    private List<Tema> temas = new ArrayList<>();

    Button btn;

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
        }

        //load temas e niveis
        if (type == JogoActivity.SINGLEPLAYER) {
            temas.clear();
            temas.addAll(GeradorTemas.getTemas());
        }

        btn = (Button) findViewById(R.id.escolheTemaButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "EscolheNivel", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EscolheNivelActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("tema",temas.get(0));
                startActivity(intent);
            }
        });
    }

}
