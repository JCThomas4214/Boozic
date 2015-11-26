package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class AdapterHandler extends RecyclerView.Adapter<AdapterHandler.ListItemViewHolder> {

    private List<TopTensModel> shownItems = new ArrayList<>();
    private int cursor;
    private int addedItems = 50;
    private boolean cursorCheck = true;
    MainActivity m;

    // Provide a reference to the views for each data item
    // Complex data shownItems may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            productInfo = (LinearLayout) itemView.findViewById(R.id.product_list_info);

            volume = (TextView) itemView.findViewById(R.id.volume_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onPotato(v, getAdapterPosition());
        }

        public interface IMyViewHolderClicks {
            void onPotato(View caller, int position);
        }
    }

    // Provide a suitable constructor (depends on the kind of data set)
    public AdapterHandler(MainActivity m, List<TopTensModel> productList) {
        this.m = m;
        this.shownItems.addAll(productList);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterHandler.ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // create a new view
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.top_ten_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new ListItemViewHolder(itemView, new AdapterHandler.ListItemViewHolder.IMyViewHolderClicks() {
            public void onPotato(View caller, int position) {
                Intent i = new Intent(m, ProductActivity.class);
                //if the product is in the list, it is not a new product
                i.putExtra("Found", 0);
                i.putExtra("Position", position);
                i.putExtra("FavoritePosition", shownItems.get(position).favoritePosition);
                //inject model variables
                i.putExtra("Label", shownItems.get(position).label);
                i.putExtra("ProductID", shownItems.get(position).productID);
                i.putExtra("UPC", shownItems.get(position).upc);
                i.putExtra("LastUpdate", shownItems.get(position).lastUpdate);
                i.putExtra("UserRating", shownItems.get(position).userRating);
                i.putExtra("ClosestStoreId", shownItems.get(position).closestStoreId);
                i.putExtra("CheapestStoreId", shownItems.get(position).cheapestStoreId);
                i.putExtra("ClosestStore", shownItems.get(position).closestStoreName);
                i.putExtra("CheapestStore", shownItems.get(position).cheapestStoreName);
                i.putExtra("ClosestStoreAddress", shownItems.get(position).closestStoreAddress);
                i.putExtra("CheapestStoreAddress", shownItems.get(position).cheapestStoreAddress);
                i.putExtra("ClosestStoreDist", shownItems.get(position).closestStoreDist);
                i.putExtra("CheapestStoreDist", shownItems.get(position).cheapestStoreDist);
                i.putExtra("ClosestPrice", shownItems.get(position).closestPrice);
                i.putExtra("CheapestPrice", shownItems.get(position).cheapestPrice);
                i.putExtra("Type", shownItems.get(position).typePic);
                i.putExtra("Favorites", shownItems.get(position).favorite);
                i.putExtra("Container", shownItems.get(position).containerType);
                i.putExtra("ContainerQty", shownItems.get(position).containerQuantity);
                i.putExtra("Volume", shownItems.get(position).volume);
                i.putExtra("VolumeMeasure", shownItems.get(position).volumeMeasure);
                i.putExtra("ABV", shownItems.get(position).abv);
                i.putExtra("ABP", shownItems.get(position).abp);
                i.putExtra("PDD", shownItems.get(position).pdd);
                i.putExtra("Rating", shownItems.get(position).rating);
                i.putExtra("PBV", shownItems.get(position).pbv);
                i.putExtra("TD", shownItems.get(position).td);
                i.putExtra("AvgRating", shownItems.get(position).avgRating);

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
        TopTensModel model = shownItems.get(position);
        DecimalFormat df = new DecimalFormat("####0.##");

        viewHolder.containerLayout.setVisibility(View.VISIBLE);
        viewHolder.abvLayout.setVisibility(View.VISIBLE);
        viewHolder.productInfo.setVisibility(View.VISIBLE);

        viewHolder.label.setText(model.label);

        if (model.containerType != null && model.typePic == 2) {
            String containerLabel = "(" + model.containerQuantity + ") " + model.containerType;
            viewHolder.container.setText(containerLabel);
        } else viewHolder.containerLayout.setVisibility(View.GONE);

        if (model.abv > 0) {
            String abv = df.format(model.abv) + "%";
            viewHolder.abv.setText(abv);
        } else {
            viewHolder.abvLayout.setVisibility(View.GONE);
        }

        if (model.avgRating == 0) {
            viewHolder.rating.setText("N/A");
        } else {
            String avgRating = model.avgRating + "";
            viewHolder.rating.setText(avgRating);
        }

        if (model.closestStoreName != null) {
            viewHolder.storeName.setText(model.closestStoreName);
            viewHolder.price.setText(NumberFormat.getCurrencyInstance().format(model.closestPrice));
        } else {
            String noStore = "No Store Price Inputted within radius";
            viewHolder.storeName.setText(noStore);
            viewHolder.price.setText("N/A");
        }

        if (model.volume != -1) {
            String volume = "(" + df.format(model.volume) + model.volumeMeasure + ")";
            viewHolder.volume.setText(volume);
        } else viewHolder.volume.setText("N/A");

        switch (model.typePic) {
            case 1:
                viewHolder.picture.setImageResource(R.mipmap.wine);
                break;
            case 2:
                viewHolder.picture.setImageResource(R.mipmap.beer);
                if (model.abv <= 0 && model.containerType == null)
                    viewHolder.productInfo.setVisibility(View.GONE);
                break;
            case 3:
                viewHolder.picture.setImageResource(R.mipmap.liquor);
                break;
            case 4:
                viewHolder.picture.setImageResource(R.mipmap.boozic_notype);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return shownItems.size();
    }

    public void startList(final List<TopTensModel> productList) {
        shownItems.clear();
        shownItems.addAll(productList);
        notifyDataSetChanged();
    }
}
