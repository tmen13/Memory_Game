package tmen.memorygame.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tmen.memorygame.Activities.JogoActivity;
import tmen.memorygame.Classes.Card;
import tmen.memorygame.Classes.Jogo;
import tmen.memorygame.R;

/**
 * Created by Ricardo on 20/12/2015.
 */
public class CardAdapter extends BaseAdapter {
    private Context mContext;
    List<Integer> posImageViewsBloqueadas = new ArrayList<>();
    Integer posPrimeiraImageView, posSegundaImageView;
    private Jogo jogoActual; //Se so necessario baralho, alterar!
    int type;


    public CardAdapter(Context mContext, Jogo jogoActual, int type) {
        this.mContext = mContext;
        this.jogoActual = jogoActual;
        this.type = type;
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
            imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), getItem(position).getCardFront(), 50, 50));
            //imageView.setImageResource(getItem(position).getCardFront());
        } else {
            imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(),getItem(position).getCardCover(), 50, 50));
            //imageView.setImageResource(getItem(position).getCardCover());
        }

        if (posPrimeiraImageView != null) {
            if (posPrimeiraImageView == position) {
                imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), getItem(position).getCardFront(), 50, 50));
                //imageView.setImageResource(getItem(position).getCardFront());
            }
        }

        if (posSegundaImageView != null) {
            if (posSegundaImageView == position) {
                imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), getItem(position).getCardFront(), 50, 50));
                //imageView.setImageResource(getItem(position).getCardFront());
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

        if (type == JogoActivity.MULTIPLAYERONLINE) {
            if (jogoActual.getJogadorActual() != JogoActivity.ME) {
                enabled = false;
            }
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


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


}
