package tmen.memorygame.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import tmen.memorygame.Classes.Tema;

/**
 * Created by Ricardo on 02/01/2016.
 */
public class LevelMultiplayerAdapter extends BaseAdapter {

    private Context mContext;
    private Tema tema;


    public LevelMultiplayerAdapter(Context mContext, Tema tema) {
        this.mContext = mContext;
        this.tema = tema;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            view = new View(mContext);
            view.setLayoutParams(new GridView.LayoutParams(85, 85));
            //view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setPadding(8, 8, 8, 8);
        } else {
            view = (View) convertView;
        }

        view.setBackgroundColor(Color.RED);

        return view;

        /*ImageView imageView;
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

        return imageView;*/
    }
}
