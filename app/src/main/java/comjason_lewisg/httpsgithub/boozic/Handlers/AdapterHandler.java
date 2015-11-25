package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private List<TopTensModel> allItems = new ArrayList<>();
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
        ImageView picture;
        Drawable picBack;
        public IMyViewHolderClicks mListener;

        // each data item is just a string in this case
        public ListItemViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            storeName = (TextView) itemView.findViewById(R.id.txt_desc_item);
            price = (TextView) itemView.findViewById(R.id.price_item);
            picture = (ImageView) itemView.findViewById(R.id.type_image);
            picBack = itemView.getResources().getDrawable(R.drawable.image_background, null);

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

        if (!productList.isEmpty()) {
            allItems.addAll(productList);

            cursor = 25;
            cursorCheck = true;

            if (productList.size() > cursor) {
                shownItems.addAll(productList.subList(0, cursor));
            } else shownItems.addAll(productList);
        }
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
                break;
            case 2:
                viewHolder.picture.setImageResource(R.mipmap.beer);
                break;
            case 3:
                viewHolder.picture.setImageResource(R.mipmap.liquor);
                break;
            case 4:
                viewHolder.picture.setImageResource(R.mipmap.boozic_notype);
                break;
        }
        viewHolder.picBack.setColorFilter(m.getColorPrimary(), PorterDuff.Mode.MULTIPLY);
        viewHolder.picture.setBackground(viewHolder.picBack);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return shownItems.size();
    }

    public void startList(final List<TopTensModel> productList) {
        allItems.clear();
        shownItems.clear();
        allItems.addAll(productList);

        cursor = 25;
        cursorCheck = true;

        if (productList.size() > cursor) {
            shownItems.addAll(productList.subList(0,cursor));
        } else shownItems.addAll(productList);
        notifyList();
    }

    public void startList(final List<TopTensModel> productList, final SwipeRefreshLayout swipeRefreshLayout) {
        allItems.clear();
        shownItems.clear();
        allItems.addAll(productList);

        cursor = 25;
        cursorCheck = true;

        if (productList.size() > cursor) {
            shownItems.addAll(productList.subList(0,cursor));
        } else shownItems.addAll(productList);

        notifyList();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void addList(List<TopTensModel> productList) {
        allItems.addAll(productList);
    }

    public void addItem(TopTensModel item) {
        shownItems.add(item);
        this.notifyDataSetChanged();
    }

    public void addWithScroll() {

        final int size = allItems.size();

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                if (size <= cursor) cursorCheck = false;
                else cursorCheck = true;
            }
            @Override
            protected Boolean doInBackground(Void... params) {
                if (cursorCheck) {
                    if (cursor+addedItems < size) {
                        shownItems.addAll(allItems.subList(cursor, cursor+addedItems));
                        cursor += addedItems;
                    }
                    else {
                        shownItems.addAll(allItems.subList(cursor, size));
                        cursor = size;
                    }
                }
                return cursorCheck;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                notifyList();
            }
        }.execute();
    }

    public void notifyList() {
        this.notifyDataSetChanged();
    }

    public void clearData() {
        int size = this.allItems.size();
        int showSize = this.shownItems.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (i < showSize) this.shownItems.remove(0);
                this.allItems.remove(0);
            }

            this.notifyItemRangeRemoved(0, showSize);
        }
    }

    public List<TopTensModel> getProductList() {
        return shownItems;
    }

    public boolean getCursorCheck() {
        return cursorCheck;
    }
}
