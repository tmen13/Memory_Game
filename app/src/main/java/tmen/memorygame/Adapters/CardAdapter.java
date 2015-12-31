package tmen.memorygame.Adapters;

import android.content.Context;
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

/**
 * Created by Ricardo on 20/12/2015.
 */
public class CardAdapter extends BaseAdapter {
    private Context mContext;
    List<Integer> posImageViewsBloqueadas = new ArrayList<>();
    Integer posPrimeiraImageView, posSegundaImageView;
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

        if (posImageViewsBloqueadas.contains(position)) {
            imageView.setImageResource(getItem(position).getCardFront());
        } else {
            imageView.setImageResource(getItem(position).getCardCover());
        }

        if (posPrimeiraImageView != null) {
            if (posPrimeiraImageView == position) {
                imageView.setImageResource(getItem(position).getCardFront());
            }
        }

        if (posSegundaImageView != null) {
            if (posSegundaImageView == position) {
                imageView.setImageResource(getItem(position).getCardFront());
            }
        }

        return imageView;
    }

    public boolean isEnabled(int position) {
        Boolean enabled = true;

        if (posPrimeiraImageView == null && posSegundaImageView == null) {
            enabled = true;
        }

        if (posPrimeiraImageView != null) {
            if (position == posPrimeiraImageView) {
                enabled = false;
            }
        }

        if (posSegundaImageView != null) {
            if (position == posSegundaImageView) {
                enabled = false;
            }
        }

        if (posPrimeiraImageView != null && posSegundaImageView != null) {
            enabled = false;
        }

        if (posImageViewsBloqueadas.contains(position)) {
            enabled = false;
        }

        return enabled;
    }

    public void resetPosImageViews() {
        posPrimeiraImageView = null;
        posSegundaImageView = null;
    }

    public List<Integer> getImageViewsBloqueadas() {
        return posImageViewsBloqueadas;
    }

    public Integer getPosPrimeiraImageView() {
        return posPrimeiraImageView;
    }

    public void setPosPrimeiraImageView(Integer posPrimeiraImageView) {
        this.posPrimeiraImageView = posPrimeiraImageView;
    }

    public Integer getPosSegundaImageView() {
        return posSegundaImageView;
    }

    public void setPosSegundaImageView(Integer posSegundaImageView) {
        this.posSegundaImageView = posSegundaImageView;
    }
}
