package tmen.memorygame.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import tmen.memorygame.Classes.Card;
import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.R;

public class TesteActivity extends AppCompatActivity {

    public static final String PREF_PLAYERNAME = "pref_nome_jogador";

    TextView tv;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv = (TextView)findViewById(R.id.tv1);
        bt = (Button)findViewById(R.id.imgBt);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + R.drawable.animals_bear);

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Drawable yourDrawable = Drawable.createFromStream(inputStream, uri.toString());
            bt.setBackgroundDrawable(yourDrawable);
        } catch (FileNotFoundException e) {
            Log.i("tmen", e.toString());
        }


        tv.setText(MySharedPreferences.getSharedPref(getApplicationContext(), MySharedPreferences.PREF_TYPE_MODE));


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on click abre galeria
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                //tv.setText(MySharedPreferences.getSharedPref(getApplicationContext(), PREF_PLAYERNAME));
            }
        });
    }



    //escolhe imagem da galeria e mete como fundo do butao. so para teste, usar image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            tv.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), targetUri);
                BitmapDrawable bdrawable = new BitmapDrawable(getResources(),bitmap);
                if(android.os.Build.VERSION.SDK_INT < 16) {
                    bt.setBackgroundDrawable(bdrawable);
                } else {
                    bt.setBackground(bdrawable);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
