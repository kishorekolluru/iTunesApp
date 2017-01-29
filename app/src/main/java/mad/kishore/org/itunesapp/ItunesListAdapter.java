package mad.kishore.org.itunesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekolluru on 10/24/16.
 */

public class ItunesListAdapter extends ArrayAdapter<ItunesAppItem> {
    private Context context;
    private List<ItunesAppItem> items;

    public ItunesListAdapter(Context context, int resource, List<ItunesAppItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.appIcon);
        Picasso.with(context).load(items.get(position).getIcon()).into(iv);
        ImageView priceIcon = (ImageView) convertView.findViewById(R.id.priceIcon);
        TextView name = (TextView) convertView.findViewById(R.id.itemtv1);
        name.setText(items.get(position).getName());
        TextView price = (TextView) convertView.findViewById(R.id.itemtv2);
        price.setText("Price : USD " + items.get(position).getPrice());
        priceIcon.setImageResource(getImageResource(items.get(position)));
        return convertView;
    }

    private int getImageResource(ItunesAppItem itunesAppItem) {
        double price = itunesAppItem.getPrice();
        if (price >= 0 && price < 2) {
            return R.mipmap.price_low;
        } else if (price < 6 && price >= 2) {
            return R.mipmap.price_medium;
        } else {
            return R.mipmap.price_high;
        }
    }

    public void remove(int pos) {
        items.remove(pos);
        notifyDataSetChanged();
    }
}
