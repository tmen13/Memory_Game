package tmen.memorygame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tmen.memorygame.R;

public class MainActivity extends AppCompatActivity {

    Button singlePlayerBtn, multiPlayerBtn, multiPlayerOnBtn, historicoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        singlePlayerBtn = (Button) findViewById(R.id.singlePlayerBtn);
        multiPlayerBtn = (Button) findViewById(R.id.multiPlayerBtn);
        multiPlayerOnBtn = (Button) findViewById(R.id.multiPlayerOnBtn);
        historicoBtn = (Button) findViewById(R.id.historicoBtn);

        singlePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"SinglePlayer",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), EscolheTemaActivity.class);
                startActivity(intent);
            }
        });

        multiPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"MultiPlayer",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
                startActivity(intent);
            }
        });

        multiPlayerOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"MultiPlayerOn",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MenuMultiPlayerOnActivity.class);
                startActivity(intent);
            }
        });

        historicoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Histórico",Toast.LENGTH_SHORT).show();

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
}
