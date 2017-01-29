package mad.kishore.org.itunesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.itemTextAppearance;
import static android.R.attr.name;

/**
 * Created by kishorekolluru on 10/24/16.
 */

public class ItunesRecyclerAdapter extends RecyclerView.Adapter<ItunesRecyclerAdapter.ItunesViewHolder>{
    Context context;
    List<ItunesAppItem> items;
    public ItunesRecyclerAdapter(Context context, List<ItunesAppItem> items){
        this.items = items;
        this.context = context;
    }
    public static class ItunesViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public TextView tvPrice;
        public ImageView icon;
        public ImageView priceIcon;
        ItunesRecyclerAdapter adapter;

        public ItunesViewHolder(View itemView, ItunesRecyclerAdapter adapter){
            super(itemView);
            this.adapter = adapter;
            Log.d("debug", "Holder created");
            tvName = (TextView) itemView.findViewById(R.id.rectv1);
            tvPrice = (TextView) itemView.findViewById(R.id.rectv2);
            priceIcon = (ImageView) itemView.findViewById(R.id.recpriceIcon);
            icon = (ImageView) itemView.findViewById(R.id.recappIcon);
        }

    }
    @Override
    public ItunesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("debug", "on create View invoked");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ItunesViewHolder holder = new ItunesViewHolder(v, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItunesViewHolder holder, int position) {
        Log.d("debug", "in on bnd view holder");
        ImageView iv =holder.icon;
        Picasso.with(iv.getContext()).load(items.get(position).getIcon()).into(iv);
        ImageView priceIcon =holder.priceIcon;
        Picasso.with(priceIcon.getContext()).load(getImageResource(items.get(position))).into(priceIcon);
        TextView name = holder.tvName;
        TextView price = holder.tvPrice;
        name.setText(items.get(position).getName());
        price.setText(String.valueOf(items.get(position).getPrice()));
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
    @Override
    public int getItemCount() {
        return items.size();
    }
}
