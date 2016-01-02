package tmen.memorygame.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Classes.Card;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.Classes.Tema;

/**
 * Created by Ricardo on 02/01/2016.
 */
public class LevelAdapter extends BaseAdapter{

    private Context mContext;
    private Tema tema;


    public LevelAdapter(Context mContext, Tema tema) {
        this.mContext = mContext;
        this.tema = tema;
    }

    public int getCount() {
        Log.d("MemoryGame","" + tema.getNumNiveis());
        return tema.getNumNiveis();
    }


    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setBackgroundColor(Color.RED);

        return imageView;
    }

    public boolean isEnabled(int position) {
        Boolean enabled = false;

        if (position < tema.getNivelActual()) {
            enabled = true;
        }

        return enabled;
    }
}
