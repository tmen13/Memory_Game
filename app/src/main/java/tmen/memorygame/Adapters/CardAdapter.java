package tmen.memorygame.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import tmen.memorygame.Classes.Baralho;
import tmen.memorygame.Classes.Card;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.R;

/**
 * Created by Ricardo on 20/12/2015.
 */
public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private Jogo jogoActual; //Se so necessario baralho, alterar!

    public CardAdapter(Context mContext, Jogo jogoActual) {
        this.mContext = mContext;
        this.jogoActual = jogoActual;
    }

    public int getCount() {
        return jogoActual.getBaralho().getCartas().size(); //mThumbIds.length;
    }

    public Card getItem(int position) {
        return jogoActual.getBaralho().getCartas().get(position); //null;
    }

    public long getItemId(int position) {
        return jogoActual.getBaralho().getCartas().get(position).getCardID(); // 0;
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

        imageView.setId(position);
        imageView.setImageResource(getItem(position).getCardCover()); //(mThumbIds[position]);
        return imageView;
    }


    //private Integer[] mThumbIds = {R.drawable.flags_argentina, R.drawable.flags_australia, R.drawable.flags_belgium, R.drawable.flags_brazil, R.drawable.flags_england, R.drawable.flags_france, R.drawable.flags_germany, R.drawable.flags_italy, R.drawable.flags_mexico, R.drawable.flags_netherlands, R.drawable.flags_portugal, R.drawable.flags_russia, R.drawable.flags_spain, R.drawable.flags_switzerland, R.drawable.flags_usa};


}
