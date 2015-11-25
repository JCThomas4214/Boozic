package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private List<TopTensModel> items = new ArrayList<>();
    private List<TopTensModel> removeItems = new ArrayList<>();
    MainActivity m;

    static View view;

    private final OnStartDragListener mDragStartListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        TextView label;
        TextView storeName;
        TextView price;
        TextView volume;
        TextView rating;
        TextView abv;
        TextView container;
        ImageView picture;
        LinearLayout containerLayout;
        LinearLayout abvLayout;
        LinearLayout ratingLayout;
        LinearLayout productInfo;
        public IMyViewHolderClicks mListener;

        // each data item is just a string in this case
        public ListItemViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            storeName = (TextView) itemView.findViewById(R.id.txt_desc_item);
            price = (TextView) itemView.findViewById(R.id.price_item);
            rating = (TextView) itemView.findViewById(R.id.list_RATING);
            abv = (TextView) itemView.findViewById(R.id.list_ABV);
            container = (TextView) itemView.findViewById(R.id.list_container);
            picture = (ImageView) itemView.findViewById(R.id.type_image);
            containerLayout = (LinearLayout) itemView.findViewById(R.id.product_list_container);
            abvLayout = (LinearLayout) itemView.findViewById(R.id.product_list_abv);
            ratingLayout = (LinearLayout) itemView.findViewById(R.id.product_list_rating);
            productInfo = (LinearLayout) itemView.findViewById(R.id.product_list_info);

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
        view = m.findViewById(R.id.frame3);
        items.addAll(modeldata);
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
                i.putExtra("Found", 0);
                i.putExtra("Position", items.get(position).position);
                i.putExtra("FavoritePosition", position);
                //inject model variables
                i.putExtra("Label", items.get(position).label);
                i.putExtra("ProductID", items.get(position).productID);
                i.putExtra("UPC", items.get(position).upc);
                i.putExtra("LastUpdate", items.get(position).lastUpdate);
                i.putExtra("UserRating", items.get(position).userRating);
                i.putExtra("ClosestStoreId", items.get(position).closestStoreId);
                i.putExtra("CheapestStoreId", items.get(position).cheapestStoreId);
                i.putExtra("ClosestStore", items.get(position).closestStoreName);
                i.putExtra("CheapestStore", items.get(position).cheapestStoreName);
                i.putExtra("ClosestStoreAddress", items.get(position).closestStoreAddress);
                i.putExtra("CheapestStoreAddress", items.get(position).cheapestStoreAddress);
                i.putExtra("ClosestStoreDist", items.get(position).closestStoreDist);
                i.putExtra("CheapestStoreDist", items.get(position).cheapestStoreDist);
                i.putExtra("ClosestPrice", items.get(position).closestPrice);
                i.putExtra("CheapestPrice", items.get(position).cheapestPrice);
                i.putExtra("Type", items.get(position).typePic);
                i.putExtra("Favorites", 1);
                i.putExtra("Container", items.get(position).containerType);
                i.putExtra("ContainerQty", items.get(position).containerQuantity);
                i.putExtra("Volume", items.get(position).volume);
                i.putExtra("VolumeMeasure", items.get(position).volumeMeasure);
                i.putExtra("ABV", items.get(position).abv);
                i.putExtra("ABP", items.get(position).abp);
                i.putExtra("PDD", items.get(position).pdd);
                i.putExtra("Rating", items.get(position).rating);
                i.putExtra("PBV", items.get(position).pbv);
                i.putExtra("TD", items.get(position).td);
                i.putExtra("AvgRating", items.get(position).avgRating);

                i.putExtra("LAT", m.getLastLocation().getLatitude());
                i.putExtra("LONG", m.getLastLocation().getLongitude());

                i.putExtra("COLOR_PRIMARY_ID", m.getColorPrimaryId());
                i.putExtra("COLOR_ACCENT_ID", m.getColorAccentId());
                i.putExtra("COLOR_PRIMARY", m.getColorPrimary());
                i.putExtra("COLOR_PRIMARY_DARK", m.getColorPrimaryDark());
                i.putExtra("COLOR_ACCENT", m.getColorAccent());
                i.putExtra("COLOR_ACCENT_DARK", m.getColorAccentDark());

                m.startActivityForResult(i, m.PRODUCT_INFO_REQUEST);
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

        if (model.avgRating == 0) {
            viewHolder.rating.setText("N/A");
        }
        else {
            String avgRating = model.avgRating + "";
            viewHolder.rating.setText(avgRating);
        }

        String abv = df.format(model.abv) + "%";
        viewHolder.abv.setText(abv);

        String container = "(" + model.containerQuantity + ") " + model.containerType;
        viewHolder.container.setText(container);

        viewHolder.label.setText(model.label);
        if (model.closestStoreName != null) {
            viewHolder.storeName.setText(model.closestStoreName);
            viewHolder.price.setText(NumberFormat.getCurrencyInstance().format(model.closestPrice));
        }
        else {
            viewHolder.storeName.setVisibility(View.GONE);
            viewHolder.price.setText("N/A");
        }

        if (model.volume != -1) {
            String volume = "(" + df.format(model.volume) + model.volumeMeasure + ")";
            viewHolder.volume.setText(volume);
        }
        else viewHolder.volume.setText("N/A");

        switch (model.typePic) {
            case 1:
                viewHolder.picture.setImageResource(R.mipmap.wine);
                if (model.abv == -1) viewHolder.productInfo.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.picture.setImageResource(R.mipmap.beer);
                viewHolder.containerLayout.setVisibility(View.VISIBLE);
                if (model.abv == -1) viewHolder.abvLayout.setVisibility(View.GONE);
                break;
            case 3:
                viewHolder.picture.setImageResource(R.mipmap.liquor);
                if (model.abv == -1) viewHolder.productInfo.setVisibility(View.GONE);
                break;
            case 4:
                viewHolder.picture.setImageResource(R.mipmap.boozic_notype);
                if (model.abv == -1) viewHolder.productInfo.setVisibility(View.GONE);
                break;
        }
    }

    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();

        int PLPosition;
        for (int i = position; i < items.size(); i++) {
            PLPosition = items.get(i).position;
            items.get(position).favoritePosition = i;
            m.PLcon.getProductList().get(PLPosition).favoritePosition = i;
        }
    }

    public void removeItem(int position) {
        TopTensModel tmp = items.get(position);
        removeItems.add(tmp);
        items.remove(position);
        notifyDataSetChanged();
    }

    public void setList(List<TopTensModel> list) {
        items.clear();
        items.addAll(list);
        this.notifyDataSetChanged();
    }

    public void clearRemoveList() {
        removeItems.clear();
    }

    @Override
    public void onItemDismiss(int position) {
        TopTensModel tmp = items.get(position);
        removeItems.add(tmp);
        //change favorite from product in existing product list
        m.PLcon.getProductList().get(tmp.position).favorite = 0;
        Log.v("Remove Fav", "remove item " + position);
        remove(position);
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