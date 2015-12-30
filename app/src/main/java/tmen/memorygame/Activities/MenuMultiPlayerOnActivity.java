package tmen.memorygame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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

        Intent intentMain = getIntent();
        if (intentMain != null) {
            type = intentMain.getIntExtra("type",JogoActivity.MULTIPLAYERONLINE);
        }

        serverBtn = (Button) findViewById(R.id.serverModeBtn);
        clientBtn = (Button) findViewById(R.id.clientModeBtn);

        serverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JogoActivity.class);
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
