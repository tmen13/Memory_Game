package tmen.memorygame.Activities;

import android.content.Intent;
import android.os.Bundle;
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

                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
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

                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
                startActivity(intent);
            }
        });

        historicoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Histórico",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), TesteActivity.class);
                startActivity(intent);
            }
        });

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id) {
            case R.id.action_teste:
                Intent intent = new Intent(this, TesteActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
