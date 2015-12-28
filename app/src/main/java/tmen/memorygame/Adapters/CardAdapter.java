package tmen.memorygame.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Classes.Card;
import tmen.memorygame.Classes.Jogo;

/**
 * Created by Ricardo on 20/12/2015.
 */
public class CardAdapter extends BaseAdapter {
    private Context mContext;
    List<Integer> posImageViewsBloqueadas = new ArrayList<>();
    ImageView primeiraImageView, segundaImageView;
    private Jogo jogoActual; //Se so necessario baralho, alterar!


    public CardAdapter(Context mContext, Jogo jogoActual) {
        this.mContext = mContext;
        this.jogoActual = jogoActual;
    }

    public int getCount() {
        return jogoActual.getBaralho().getCartas().size();
    }

    public Card getItem(int position) {
        return jogoActual.getBaralho().getCartas().get(position);
    }

    public long getItemId(int position) {
        return jogoActual.getBaralho().getCartas().get(position).getCardID();
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

        imageView.setTag(String.valueOf(position));
        imageView.setImageResource(getItem(position).getCardCover());
        return imageView;
    }

    public boolean isEnabled(int position) {
        Boolean enabled = true;

        if (primeiraImageView == null && segundaImageView == null) {
            enabled = true;
        }

        if (primeiraImageView != null) {
            if (String.valueOf(position) == primeiraImageView.getTag()) {
                enabled = false;
            }
        }

        if (segundaImageView != null) {
            if (String.valueOf(position) == primeiraImageView.getTag()) {
                enabled = false;
            }
        }

        if (primeiraImageView != null && segundaImageView != null) {
            enabled = false;
        }

        if (posImageViewsBloqueadas.contains(position)) {
            enabled = false;
        }

        return enabled;
    }

    public void resetImageViews() {
        primeiraImageView = null;
        segundaImageView = null;
    }

    public List<Integer> getImageViewsBloqueadas() {
        return posImageViewsBloqueadas;
    }

    public ImageView getPrimeiraImageView() {
        return primeiraImageView;
    }

    public void setPrimeiraImageView(ImageView primeiraImageView) {
        this.primeiraImageView = primeiraImageView;
    }

    public ImageView getSegundaImageView() {
        return segundaImageView;
    }

    public void setSegundaImageView(ImageView segundaImageView) {
        this.segundaImageView = segundaImageView;
    }

}
