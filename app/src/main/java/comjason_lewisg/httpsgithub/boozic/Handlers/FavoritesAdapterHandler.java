package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Handlers.TouchHelpers.ItemTouchHelperAdapter;
import comjason_lewisg.httpsgithub.boozic.Handlers.TouchHelpers.ItemTouchHelperViewHolder;
import comjason_lewisg.httpsgithub.boozic.Handlers.TouchHelpers.OnStartDragListener;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FavoritesAdapterHandler extends RecyclerView.Adapter<FavoritesAdapterHandler.ListItemViewHolder>
            implements ItemTouchHelperAdapter {
    private List<TopTensModel> items;
    private List<TopTensModel> removeItems;
    MainActivity m;

    private final OnStartDragListener mDragStartListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
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

        @Override
        public void onItemSelected() {
        }

        @Override
        public void onItemClear() {
        }

        public interface IMyViewHolderClicks {
            void onPotato(View caller, int position);
        }
    }

    // Provide a suitable constructor (depends on the kind of data set)
    public FavoritesAdapterHandler(List<TopTensModel> modeldata, MainActivity m, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        if (modeldata == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        removeItems = new ArrayList<TopTensModel>() {};
        items = modeldata;
        this.m = m;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoritesAdapterHandler.ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // create a new view
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.top_ten_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new ListItemViewHolder(itemView, new FavoritesAdapterHandler.ListItemViewHolder.IMyViewHolderClicks() {
            public void onPotato(View caller, int position) {
                Intent i = new Intent(m, ProductActivity.class);
                //if the product is in the list, it is not a new product
                i.putExtra("Found", true);
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
                i.putExtra("Rating", items.get(position).rating);
                i.putExtra("Volume", items.get(position).volume);
                i.putExtra("VolumeMeasure", items.get(position).volumeMeasure);
                i.putExtra("PBV", items.get(position).pbv);
                i.putExtra("ABP", items.get(position).abp);
                i.putExtra("PDD", items.get(position).pdd);
                i.putExtra("TD", items.get(position).td);
                i.putExtra("AvgRating", items.get(position).avgRating);

                i.putExtra("COLOR_PRIMARY_ID", m.getColorPrimaryId());
                i.putExtra("COLOR_ACCENT_ID", m.getColorAccentId());
                i.putExtra("COLOR_PRIMARY", m.getColorPrimary());
                i.putExtra("COLOR_PRIMARY_DARK", m.getColorPrimaryDark());
                i.putExtra("COLOR_ACCENT", m.getColorAccent());
                i.putExtra("COLOR_ACCENT_DARK", m.getColorAccentDark());

                m.startActivity(i);
            }
        });
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TopTensModel model = items.get(position);
        DecimalFormat df = new DecimalFormat("####0.##");

        viewHolder.label.setText(model.label);
        viewHolder.storeName.setText(model.closestStoreName + " (" + model.closestStoreDist + "mi)");
        viewHolder.price.setText(NumberFormat.getCurrencyInstance().format(model.closestPrice));
        viewHolder.volume.setText("(" + df.format(model.volume) + model.volumeMeasure + ")");
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

    @Override
    public void onItemDismiss(int position) {
        removeItems.add(items.get(position));
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<TopTensModel> getRemovedList() {
        return removeItems;
    }
}