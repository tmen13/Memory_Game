package tmen.memorygame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.R;

public class CustomDeckActivity extends AppCompatActivity {

    Button selectImgBt, saveImgBt, editDeckBt;
    ImageView placeholderImageView;
    Uri targetUri = null;
    List<String> customCards = new ArrayList<>();
    Boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectImgBt = (Button)findViewById(R.id.imgBt);
        saveImgBt = (Button)findViewById(R.id.saveImgBt);
        editDeckBt = (Button)findViewById(R.id.editDeckBt);
        placeholderImageView = (ImageView)findViewById(R.id.previewImageView);

        File file = getApplicationContext().getFileStreamPath(MySharedPreferences.PATH_CUSTOM_DECK);
        if(!file.exists()) {
            Log.i("aqui", "Ficheiro nao existe!");
            //customCards = MySharedPreferences.getDeckFromFile(getApplicationContext());
            MySharedPreferences.saveDeckToFile(getApplicationContext(), customCards); //
        }
        Log.i("aqui", "Ficheiro existe!");

        customCards = MySharedPreferences.getDeckFromFile(getApplicationContext());
        if(customCards.size() == 0)
            editDeckBt.setEnabled(false);

        if(targetUri == null)
             saveImgBt.setEnabled(false);
        else
            saveImgBt.setEnabled(true);

        selectImgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on click abre galeria
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        saveImgBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        CustomDeckActivity.this);
                alertDialog.setPositiveButton(R.string.creditos_ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                });

                for(String str : customCards){
                    if(str.equals(targetUri.toString())){
                        alertDialog.setMessage(R.string.custom_add_fail);
                        found = true;
                        break;
                    }
                }
                if (!found){
                    customCards.add(targetUri.toString());
                    MySharedPreferences.saveDeckToFile(getApplicationContext(), customCards);
                    alertDialog.setMessage(R.string.custom_added);
                    found = false;
                }
                alertDialog.setTitle(R.string.app_name);
                alertDialog.show();
            }
        });

        editDeckBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditDeckCustomActivity.class);
                startActivity(intent);
            }
        });
    }

    //Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + R.drawable.animals_bear);

        /*try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Drawable yourDrawable = Drawable.createFromStream(inputStream, uri.toString());
            placeholderImageView.setBackground();
        } catch (FileNotFoundException e) {
            Log.i("tmen", e.toString());
        }*/


    //escolhe imagem da galeria e mete como fundo do butao. so para teste, usar image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), targetUri);
                BitmapDrawable bdrawable = new BitmapDrawable(getResources(),bitmap);
                if(android.os.Build.VERSION.SDK_INT < 16) {
                    placeholderImageView.setBackgroundDrawable(bdrawable);
                } else {
                    placeholderImageView.setBackground(bdrawable);
                }
                saveImgBt.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
