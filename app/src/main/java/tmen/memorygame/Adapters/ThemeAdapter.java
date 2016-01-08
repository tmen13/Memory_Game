package tmen.memorygame.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import tmen.memorygame.Classes.GeradorTemas;
import tmen.memorygame.Classes.MySharedPreferences;
import tmen.memorygame.Classes.Tema;
import tmen.memorygame.R;

/**
 * Created by Ricardo on 04/01/2016.
 */
public class ThemeAdapter extends BaseAdapter {

    static Integer[] defaultThemesImgsIds = {R.drawable.ic_tema_bandeiras, R.drawable.ic_tema_carros, R.drawable.ic_tema_animais, R.drawable.ic_tema_cores, R.drawable.ic_tema_clubes, R.drawable.ic_tema_custom};

    private Context mContext;
    private List<Tema> temas;

    public ThemeAdapter(Context mContext, List<Tema> temas) {
        this.mContext = mContext;
        this.temas = temas;
    }

    @Override
    public int getCount() {
        return temas.size();
    }

    @Override
    public Object getItem(int position) {
        return temas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
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

        imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), defaultThemesImgsIds[position], 50, 50));

        return imageView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 5) {
            File file = mContext.getFileStreamPath(MySharedPreferences.PATH_CUSTOM_DECK);
            if(file.exists()) {
                Log.d("MemoryGame", "Ficheiro existe!");
                if (MySharedPreferences.getDeckFromFile(mContext).size() < 2) {
                    return false;
                }
            } else {
                return false;
            }

        }
        return super.isEnabled(position);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
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
