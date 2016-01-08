package tmen.memorygame.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageDrawable();
        //imageView.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), defaultThemesImgsIds[position], 50, 50));

        return imageView;
    }
}
