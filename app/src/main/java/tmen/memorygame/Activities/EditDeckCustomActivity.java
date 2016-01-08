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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Adapters.EditDeckAdapter;
import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.R;

public class EditDeckCustomActivity extends AppCompatActivity {

    List<String> customCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customCards = MySharedPreferences.getDeckFromFile(getApplicationContext());
        final GridView gridview = (GridView) findViewById(R.id.editDeckView);
        final EditDeckAdapter editDeckAdapter = new EditDeckAdapter(getApplicationContext(),customCards);
        gridview.setAdapter(editDeckAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                ImageView imageView = (ImageView) v;
                //TODO dialog para apagar imagem
                Toast.makeText(getApplicationContext(),customCards.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

}
