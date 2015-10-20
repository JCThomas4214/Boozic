package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class AdapterHandler extends RecyclerView.Adapter<AdapterHandler.ListItemViewHolder> {
    private List<TopTensModel> items;
    AppCompatActivity m;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView label;
        TextView storeName;
        TextView price;
        TextView volume;
        ImageView picture;
        public IMyViewHolderClicks mListener;

        // each data item is just a string in this case
        public ListItemViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            storeName = (TextView) itemView.findViewById(R.id.txt_desc_item);
            price = (TextView) itemView.findViewById(R.id.price_item);
            picture = (ImageView) itemView.findViewById(R.id.type_image);
            volume = (TextView) itemView.findViewById(R.id.volume_item);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mListener.onPotato(v, getAdapterPosition());
        }
        public static interface IMyViewHolderClicks {
            public void onPotato(View caller, int position);
        }
    }

    // Provide a suitable constructor (depends on the kind of data set)
    public AdapterHandler(List<TopTensModel> modeldata, AppCompatActivity m) {
        if (modeldata == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modeldata;
        this.m = m;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterHandler.ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // create a new view
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.top_ten_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        AdapterHandler.ListItemViewHolder vh = new ListItemViewHolder(itemView, new AdapterHandler.ListItemViewHolder.IMyViewHolderClicks() {
            public void onPotato(View caller, int position) {
                Intent i = new Intent(m, ProductActivity.class);
                //inject model variables
                i.putExtra("Label", items.get(position).label);
                i.putExtra("LastUpdate", items.get(position).lastUpdate);
                i.putExtra("UserRating", items.get(position).userRating);
                i.putExtra("ClosestStore", items.get(position).closestStoreName);
                i.putExtra("CheapestStore", items.get(position).cheapestStoreName);
                i.putExtra("ClosestStoreDist", items.get(position).closestStoreDist);
                i.putExtra("CheapestStoreDist", items.get(position).cheapestStoreDist);
                i.putExtra("ClosestPrice", items.get(position).closestPrice);
                i.putExtra("CheapestPrice", items.get(position).cheapestPrice);
                i.putExtra("Type", items.get(position).typePic);
                i.putExtra("Favorites", items.get(position).favorite);
                i.putExtra("Container", items.get(position).container);
                i.putExtra("Volume", items.get(position).volume);
                i.putExtra("Type", items.get(position).typePic);
                i.putExtra("ABV", items.get(position).abv);
                i.putExtra("Proof", items.get(position).proof);
                i.putExtra("ABP", items.get(position).abp);
                i.putExtra("PDD", items.get(position).pdd);
                i.putExtra("Rating5", items.get(position).rating5);
                i.putExtra("Rating4", items.get(position).rating4);
                i.putExtra("Rating3", items.get(position).rating3);
                i.putExtra("Rating2", items.get(position).rating2);
                i.putExtra("Rating1", items.get(position).rating1);

                m.startActivity(i);
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TopTensModel model = items.get(position);
        viewHolder.label.setText(model.label);
        viewHolder.storeName.setText(model.closestStoreName + " (" + model.closestStoreDist + "mi)");
        viewHolder.price.setText(NumberFormat.getCurrencyInstance().format(model.closestPrice));
        viewHolder.volume.setText("(" + model.volume + "L)");
        switch (model.typePic) {
            case 1:
                viewHolder.picture.setBackgroundResource(R.mipmap.beer);
                break;
            case 2:
                viewHolder.picture.setBackgroundResource(R.mipmap.wine);
                break;
            case 3:
                viewHolder.picture.setBackgroundResource(R.mipmap.liquor);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

}
