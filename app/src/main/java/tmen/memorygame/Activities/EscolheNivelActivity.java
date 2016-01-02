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

import tmen.memorygame.Classes.Tema;
import tmen.memorygame.R;

public class EscolheNivelActivity extends AppCompatActivity {

    private int type = JogoActivity.SINGLEPLAYER;
    private Tema tema;
    private int nivelEscolhido;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhe_nivel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", JogoActivity.SINGLEPLAYER);
            tema = (Tema) intent.getSerializableExtra("tema");
        }

        button = (Button) findViewById(R.id.escolheNivelButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Jogo", Toast.LENGTH_SHORT).show();

                nivelEscolhido = 4;

                Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("tema",tema);
                intent.putExtra("nivelEscolhido",nivelEscolhido);
                startActivity(intent);
            }
        });
    }

}
