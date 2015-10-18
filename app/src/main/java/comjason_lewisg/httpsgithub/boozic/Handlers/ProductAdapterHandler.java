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

import java.text.NumberFormat;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class ProductAdapterHandler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TopTensModel> item;
    AppCompatActivity m;

    TextView label;
    TextView closestStoreName;
    TextView closestStorePrice;
    TextView cheapestStoreName;
    TextView cheapestStorePrice;
    TextView volume;
    ImageView picture;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ProductInfoHolder0 extends RecyclerView.ViewHolder{


        // each data item is just a string in this case
        public ProductInfoHolder0(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            closestStoreName = (TextView) itemView.findViewById(R.id.txt_desc_item);
            closestStorePrice = (TextView) itemView.findViewById(R.id.price_item);
            picture = (ImageView) itemView.findViewById(R.id.type_image);
        }
    }

    class ProductInfoHolder1 extends RecyclerView.ViewHolder{


        // each data item is just a string in this case
        public ProductInfoHolder1(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            closestStoreName = (TextView) itemView.findViewById(R.id.txt_desc_item);
            closestStorePrice = (TextView) itemView.findViewById(R.id.price_item);
            picture = (ImageView) itemView.findViewById(R.id.type_image);
        }
    }

    class ProductInfoHolder2 extends RecyclerView.ViewHolder{


        // each data item is just a string in this case
        public ProductInfoHolder2(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.product_label);
            closestStoreName = (TextView) itemView.findViewById(R.id.product_closest_store);
            cheapestStoreName = (TextView) itemView.findViewById(R.id.product_cheapest_store);
            picture = (ImageView) itemView.findViewById(R.id.product_type);
        }
    }

    // Provide a suitable constructor (depends on the kind of data set)
    public ProductAdapterHandler(List<TopTensModel> modeldata, AppCompatActivity m) {
        if (modeldata == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        item = modeldata;
        this.m = m;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView;

        if (viewType == 0) {
            // create a new view
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.product_info_0, viewGroup, false);
            // set the view's size, margins, paddings and layout parameters
            return new ProductAdapterHandler.ProductInfoHolder0(itemView);
        }
        else if (viewType == 1) {
            // create a new view
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.top_ten_item, viewGroup, false);
            // set the view's size, margins, paddings and layout parameters
            return new ProductAdapterHandler.ProductInfoHolder1(itemView);
        }
        else {
            // create a new view
            itemView = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.top_ten_item, viewGroup, false);
            // set the view's size, margins, paddings and layout parameters
            return new ProductAdapterHandler.ProductInfoHolder2(itemView);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TopTensModel model = item.get(position);
        label.setText(model.label);
        closestStoreName.setText(model.closestStoreName + " - " + NumberFormat.getCurrencyInstance().format(model.closestPrice));
        cheapestStoreName.setText(model.cheapestStoreName + " - " + NumberFormat.getCurrencyInstance().format(model.cheapestPrice));
        volume.setText(model.volume);
        switch (model.typePic) {
            case 1:
                picture.setBackgroundResource(R.mipmap.beer);
                break;
            case 2:
                picture.setBackgroundResource(R.mipmap.wine);
                break;
            case 3:
                picture.setBackgroundResource(R.mipmap.liquor);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }

}






