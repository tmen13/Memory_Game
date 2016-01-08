package tmen.memorygame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Adapters.HistoryAdapter;
import tmen.memorygame.Adapters.ThemeAdapter;
import tmen.memorygame.Classes.Historico;
import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.R;

public class HistoricoActivity extends AppCompatActivity {

    List<Historico> historicoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView listview = (ListView) findViewById(R.id.historicoListView);
        historicoList = new ArrayList<>();
        File file = getApplicationContext().getFileStreamPath(MySharedPreferences.PATH_HITORICO);
        if(file.exists()) {
            Log.d("MemoryGame", "Ficheiro existe!");
            historicoList.addAll(MySharedPreferences.getHistoricoFromFile(getApplicationContext()));
        }
        final HistoryAdapter historyAdapter = new HistoryAdapter(getApplicationContext(), historicoList);
        listview.setAdapter(historyAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historico, menu);
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
            case R.id.action_clear:
                historicoList.clear();
                MySharedPreferences.saveHisticoToFile(getApplicationContext(),historicoList);
                recreate();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
