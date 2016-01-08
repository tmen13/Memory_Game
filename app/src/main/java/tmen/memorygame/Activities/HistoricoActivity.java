package tmen.memorygame.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView listview = (ListView) findViewById(R.id.historicoListView);
        List<Historico> historicoList = new ArrayList<>();
        File file = getApplicationContext().getFileStreamPath(MySharedPreferences.PATH_HITORICO);
        if(file.exists()) {
            Log.d("MemoryGame", "Ficheiro existe!");
            historicoList.addAll(MySharedPreferences.getHistoricoFromFile(getApplicationContext()));
        }
        final HistoryAdapter historyAdapter = new HistoryAdapter(getApplicationContext(), historicoList);
        listview.setAdapter(historyAdapter);
    }
}
