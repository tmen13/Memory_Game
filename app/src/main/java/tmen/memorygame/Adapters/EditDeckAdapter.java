package tmen.memorygame.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import tmen.memorygame.Classes.MySharedPreferences;

/**
 * Created by Tony on 08/01/2016.
 */
public class EditDeckAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> customDeck;

    public EditDeckAdapter(Context applicationContext, List<String> customCards) {
        this.mContext = applicationContext;
        this.customDeck = customCards;
    }

    @Override
    public int getCount() {
        return customDeck.size();
    }

    @Override
    public Object getItem(int position) {
        return customDeck.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Uri uri;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        uri = Uri.parse(customDeck.get(position));
       /* try {
            URL url = new URL(uri.toString());

            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(url.openStream(), false);
            Bitmap region = decoder.decodeRegion(new Rect(10, 10, 50, 50), null);

            imageView.setImageBitmap(region);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        imageView.setImageURI(uri);
        return imageView;
    }
}
