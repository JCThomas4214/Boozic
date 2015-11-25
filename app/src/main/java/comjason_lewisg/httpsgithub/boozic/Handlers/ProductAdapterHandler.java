package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;
import comjason_lewisg.httpsgithub.boozic.SettingsActivity;

public class ProductAdapterHandler extends RecyclerView.Adapter<ProductAdapterHandler.ProductInfoHolder> {
    private ProductStorageModel item;
    ProductActivity p;
    DialogHandler DHandler;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ProductInfoHolder extends RecyclerView.ViewHolder{

        View main;
        Button updateProduct;

        TextView label;
        TextView lastUpdate;
        TextView boozicScore;
        ImageView favorite;
        ImageView flag;

        TextView closestStore;
        TextView cheapestStore;
        TextView closestStoreAddress;
        TextView cheapestStoreAddress;
        TextView closestPrice;
        TextView cheapestPrice;

        ImageView typePic;
        Drawable picBack;

        TextView container;
        TextView volume;

        TextView pbv;
        TextView abv;
        TextView proof;
        TextView abp;
        TextView td;

        RatingBar userRating;
        TextView containerLabel;
        LinearLayout favoriteFlagLayout;
        LinearLayout closestStoreLayout;
        LinearLayout cheapestStoreLayout;
        LinearLayout containerLayout;
        LinearLayout productInfoTable;
        PieChart ratingChart;
        public IMyViewHolderClicks mListener;

        // each data item is just a string in this case
        public ProductInfoHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            main = itemView;
            mListener = listener;
            updateProduct = (Button) itemView.findViewById(R.id.new_price_button);
            mListener.setButtonColor(updateProduct);

            label = (TextView) itemView.findViewById(R.id.product_label);
            typePic = (ImageView) itemView.findViewById(R.id.product_type);
            picBack = itemView.getResources().getDrawable(R.drawable.image_background, null);

            boozicScore = (TextView) itemView.findViewById(R.id.product_metascore);
            lastUpdate = (TextView) itemView.findViewById(R.id.product_last_updated);
            userRating = (RatingBar) itemView.findViewById(R.id.product_ratingBar);
            favorite = (ImageView) itemView.findViewById(R.id.favorite_button);
            mListener.checkIfFavorite(favorite);

            flag = (ImageView) itemView.findViewById(R.id.flag_button);
            favoriteFlagLayout = (LinearLayout) itemView.findViewById(R.id.favorite_flag_layout);

            containerLabel = (TextView) itemView.findViewById(R.id.container_label);
            closestStoreLayout = (LinearLayout) itemView.findViewById(R.id.closest_store_layout);
            cheapestStoreLayout = (LinearLayout) itemView.findViewById(R.id.cheapest_store_layout);
            containerLayout = (LinearLayout) itemView.findViewById(R.id.product_container_layout);
            productInfoTable = (LinearLayout) itemView.findViewById(R.id.product_info_table_table_layout);
            td = (TextView) itemView.findViewById(R.id.product_td);

            closestStore = (TextView) itemView.findViewById(R.id.product_closest_store);
            closestStoreAddress = (TextView) itemView.findViewById(R.id.closest_store_address);
            closestPrice = (TextView) itemView.findViewById(R.id.product_closest_price);

            cheapestStore = (TextView) itemView.findViewById(R.id.product_cheapest_store);
            cheapestStoreAddress = (TextView) itemView.findViewById(R.id.cheapest_store_address);
            cheapestPrice = (TextView) itemView.findViewById(R.id.product_cheapest_price);


            //TODO: impliment the favorities button here

            container = (TextView) itemView.findViewById(R.id.product_container);
            volume = (TextView) itemView.findViewById(R.id.product_volume);
            abv = (TextView) itemView.findViewById(R.id.product_abv);
            proof = (TextView) itemView.findViewById(R.id.product_proof);
            abp = (TextView) itemView.findViewById(R.id.product_abp);
            pbv = (TextView) itemView.findViewById(R.id.product_pbv);

            ratingChart = (PieChart) itemView.findViewById(R.id.rating_chart);

            productInfoTable.setOnLongClickListener(longClickListener);
            label.setOnLongClickListener(longClickListener);

            userRating.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        mListener.changeUpdateModelRating((RatingBar) v);
                    }
                    return false;
                }
            });

            favorite.setOnClickListener(clickListener);
            flag.setOnClickListener(clickListener);
            updateProduct.setOnClickListener(clickListener);
            closestStoreLayout.setOnClickListener(clickListener);
            cheapestStoreLayout.setOnClickListener(clickListener);
            mListener.setLayoutBackgroundColor(favoriteFlagLayout);
        }

        public View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.new_price_button:
                        mListener.startUpdateDialog(v);
                        break;
                    case R.id.closest_store_layout:
                        mListener.startClosestNavigation(v);
                        break;
                    case R.id.cheapest_store_layout:
                        mListener.startCheapestNavigation(v);
                        break;
                    case R.id.favorite_button:
                        mListener.toggleFavorite(favorite);
                        break;
                    case R.id.flag_button:
                        mListener.startFlagDialog();
                        break;
                }
            }
        };
        public View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch(v.getId()) {
                    case R.id.product_info_table_table_layout:
                        mListener.startProductInfoDialog(v);
                        break;
                    case R.id.product_label:
                        mListener.startProductNameDialog(v);
                        break;
                }
                return true;
            }
        };

        public interface IMyViewHolderClicks {
            void setLayoutBackgroundColor(LinearLayout favoriteFlagLayout);
            void setButtonColor(Button updateProduct);
            void checkIfFavorite(ImageView favorite);
            void toggleFavorite(ImageView favorite);
            void startUpdateDialog(View caller);
            void startFlagDialog();
            void startClosestNavigation(View caller);
            void startCheapestNavigation(View caller);
            void changeUpdateModelRating(RatingBar caller);
            void startProductInfoDialog(View caller);
            void startProductNameDialog(View caller);
            int getPrimaryColor();
            int getPrimaryColorDark();
        }
    }

    // Provide a suitable constructor (depends on the kind of data set)
    public ProductAdapterHandler(ProductStorageModel modeldata, ProductActivity p) {
        if (modeldata == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        item = modeldata;
        this.p = p;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductAdapterHandler.ProductInfoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        DHandler = new DialogHandler(p);

        // create a new view
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.product_info, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new ProductInfoHolder(itemView, new ProductAdapterHandler.ProductInfoHolder.IMyViewHolderClicks() {
            public void setLayoutBackgroundColor(LinearLayout favoriteFlagLayout) {
                favoriteFlagLayout.setBackgroundColor(p.getPrimaryColor());
            }
            public void setButtonColor(Button updateProduct) {
                Drawable setButton = p.getResources().getDrawable(R.drawable.custom_update_price_button, null);
                setButton.setColorFilter(p.getPrimaryColor(), PorterDuff.Mode.MULTIPLY);
                updateProduct.setBackground(setButton);
            }
            public void checkIfFavorite(ImageView favorite) {
                setFavorite(checkFavorites(), favorite);
            }
            public void toggleFavorite(ImageView favorite) {
                toggleFavoriteValue(favorite);
            }
            public void startUpdateDialog(View caller) {
                if (p.model.typePic == 4 && p.updatedModel.type == -1) DHandler.UpdateProductParentType(false);
                else if (p.model.containerType.equals("N/A") && (p.model.typePic == 2 || p.updatedModel.type == 2)) DHandler.UpdateContainer(false);
                else if (p.model.abv <= 0) DHandler.UpdateAbv(false, false);
                else DHandler.UpdateStore(false, false);

            }
            public void startFlagDialog() {
                DHandler.startFlagDialog();
            }
            public void startClosestNavigation(View caller) {
                p.startNavigationIntent(p.model.closestStoreName, p.model.closestStoreAddress);
            }
            public void startCheapestNavigation(View caller) {
                p.startNavigationIntent(p.model.cheapestStoreName, p.model.cheapestStoreAddress);
            }
            public void changeUpdateModelRating(final RatingBar caller) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("RATINGUPDATE", "new rating = " + caller.getRating());
                        p.updatedModel.updateRating(caller.getRating());
                    }
                }, 50);
            }
            public void startProductInfoDialog(View caller) {
                DHandler.StartProductInfoDialog();
            }
            public void startProductNameDialog(View caller) {
                DHandler.UpdateProductLabel();
            }
            public int getPrimaryColor() { return p.getPrimaryColor(); }
            public int getPrimaryColorDark() { return p.getPrimaryColorDark(); }
        });
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProductInfoHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ProductStorageModel model = item;

        DecimalFormat df = new DecimalFormat("####0.##");
        DecimalFormat monFormat = new DecimalFormat("####0.00");
        DecimalFormat pbvFormat = new DecimalFormat("####0.00#");

        viewHolder.label.setText(model.label);
        if (model.boozicScore <= 0) viewHolder.boozicScore.setText("N/A");
        else viewHolder.boozicScore.setText("" + model.boozicScore);
        if (model.lastUpdate == null) viewHolder.lastUpdate.setText("N/A");
        else viewHolder.lastUpdate.setText("" + model.lastUpdate);
        setFavorite(model.favorite, viewHolder.favorite);

        if (model.userRating > 0) viewHolder.userRating.setRating((float)model.userRating);

        LayerDrawable stars = (LayerDrawable) viewHolder.userRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(p.getAccentColor(), PorterDuff.Mode.SRC_ATOP);

        if (model.cheapestStoreName == null) {
            viewHolder.closestStoreLayout.setVisibility(View.GONE);
            viewHolder.cheapestStoreLayout.setVisibility(View.GONE);
            viewHolder.td.setText("N/A");
            /*viewHolder.cheapestStore.setText("N/A");
            viewHolder.cheapestPrice.setText("N/A");
            viewHolder.closestStoreAddress.setText("N/A");
            viewHolder.cheapestStoreAddress.setText("N/A");*/
        }
        else {
            if (model.closestStoreId == model.cheapestStoreId) {
                viewHolder.closestStoreLayout.setVisibility(View.GONE);
                viewHolder.td.setText("N/A");
            } else {
                viewHolder.closestStore.setText(model.closestStoreName);
                viewHolder.closestStoreAddress.setText(model.closestStoreAddress);
                viewHolder.closestPrice.setText("$" + monFormat.format(model.closestPrice));
                viewHolder.td.setText("$" + monFormat.format(model.td));
            }
            viewHolder.cheapestStore.setText(model.cheapestStoreName);
            viewHolder.cheapestStoreAddress.setText(model.cheapestStoreAddress);
            viewHolder.cheapestPrice.setText("$" + monFormat.format(model.cheapestPrice));
        }

        if (model.containerType.equals("N/A")) viewHolder.containerLayout.setVisibility(View.GONE);
        else {
            String tmp = "(" + model.containerQuantity + ") " + model.containerType;
            viewHolder.container.setText(tmp);
        }

        selectTypePic(model, viewHolder);

        if (model.volume <= 0) viewHolder.volume.setText("N/A");
        else viewHolder.volume.setText(df.format(model.volume) + model.volumeMeasure);

        if (model.abv <= 0) viewHolder.abv.setText("N/A");
        else viewHolder.abv.setText(df.format(model.abv) + "%");

        if (model.proof <= 0) viewHolder.proof.setText("N/A");
        else viewHolder.proof.setText("" + model.proof);

        if (model.abp <= 0) viewHolder.abp.setText("N/A");
        else viewHolder.abp.setText("$" + monFormat.format(model.abp) + "/ml");

        if (model.pbv <= 0 || model.abv <= 0) viewHolder.pbv.setText("N/A");
        else viewHolder.pbv.setText("$" + pbvFormat.format(model.pbv) + "/ml");

        if (model.avgRating <= 0) emptyChart(viewHolder);
        else setChart(model, viewHolder);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }

    public void selectTypePic(ProductStorageModel model, ProductInfoHolder viewHolder) {
        switch (model.typePic) {
            case 1:
                viewHolder.typePic.setImageResource(R.mipmap.wine);
                viewHolder.containerLayout.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.typePic.setImageResource(R.mipmap.beer);
                viewHolder.containerLabel.setText(":Total Vol");
                break;
            case 3:
                viewHolder.typePic.setImageResource(R.mipmap.liquor);
                viewHolder.containerLayout.setVisibility(View.GONE);
                break;
            case 4:
                viewHolder.typePic.setImageResource(R.mipmap.boozic_notype);
                viewHolder.containerLayout.setVisibility(View.GONE);
                break;
        }
        viewHolder.picBack.setColorFilter(p.getPrimaryColor(), PorterDuff.Mode.MULTIPLY);
        viewHolder.typePic.setBackground(viewHolder.picBack);
    }

    private int checkFavorites() {
        return item.favorite;
    }

    private void setFavorite(int set, ImageView favorite) {
        if (set == 1) {
            favorite.setImageResource(R.drawable.star);
        } else {
            favorite.setImageResource(R.drawable.star_outline);
        }
    }

    private void toggleFavoriteValue(ImageView favorite) {
        if (p.updatedModel.favorite == -1) {
            if (p.model.favorite == 0) {
                setFavorite(1, favorite);
                p.updatedModel.updateFavorite(1);
            } else {
                setFavorite(0, favorite);
                p.updatedModel.updateFavorite(0);
            }
        } else {
            if (p.updatedModel.favorite == 0) {
                setFavorite(1, favorite);
                p.updatedModel.updateFavorite(1);
            } else {
                setFavorite(0, favorite);
                p.updatedModel.updateFavorite(0);
            }
        }
    }

    public void changeParentType(int type) {
        item.typePic = type;
    }

    public void changeABV(double abv) {
        item.abv = abv;
        item.proof = 2 * (int)abv;
        item.setABP();
    }

    public void changeVolume(double volume) {
        item.volume = volume;
        item.setABP();
        item.setPBV();
    }

    public void changeContainerType(String type) {
        item.containerType = type;
    }

    public void changeContainerQty(int quantity) {
        item.containerQuantity = quantity;
    }

    public void setChart(ProductStorageModel model, ProductInfoHolder viewHolder) {
        DecimalFormat avgFormat = new DecimalFormat("0.0");
        float yData[] = {model.rating[0], model.rating[1], model.rating[2], model.rating[3], model.rating[4]};

        // config the pie chart
        viewHolder.ratingChart.setUsePercentValues(true);
        viewHolder.ratingChart.setDrawSliceText(false);
        viewHolder.ratingChart.setDescription("");

        // enable hole and config
        viewHolder.ratingChart.setDrawHoleEnabled(true);
        viewHolder.ratingChart.setHoleColorTransparent(true);
        viewHolder.ratingChart.setHoleRadius(60);
        viewHolder.ratingChart.setTransparentCircleRadius(65);
        viewHolder.ratingChart.setCenterText(avgFormat.format(model.avgRating));
        viewHolder.ratingChart.setCenterTextSize(40f);

        // set rotation
        viewHolder.ratingChart.setRotationEnabled(false);

        // add data
        addData(yData, viewHolder);

        // customize legends
        Legend l = viewHolder.ratingChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setYOffset(18);
        l.setXOffset(0);
        l.setFormSize(15);
        l.setXEntrySpace(2);
        l.setYEntrySpace(8);
    }

    private void addData(float[] yData, ProductInfoHolder viewHolder) {

        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < yData.length; i++)
            yVals.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<>();
        for (int j = 0; j < 5; j++)
            xVals.add("");

        // create pie data
        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(0);

        // add colors
        ArrayList<Integer> colors = new ArrayList<>();


        colors.add(ContextCompat.getColor(p.getApplicationContext(), R.color.ColorAccent));
        colors.add(ContextCompat.getColor(p.getApplicationContext(), R.color.ColorAccent2));
        colors.add(ContextCompat.getColor(p.getApplicationContext(), R.color.ColorAccent3));
        colors.add(ContextCompat.getColor(p.getApplicationContext(), R.color.ColorAccent4));
        colors.add(ContextCompat.getColor(p.getApplicationContext(), R.color.ColorAccent5));

        dataSet.setColors(colors);

        // instantiate pie data
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        viewHolder.ratingChart.setData(data);

        //undo all highlights
        viewHolder.ratingChart.highlightValues(null);

        //update pie chart
        viewHolder.ratingChart.invalidate();
    }

    private void emptyChart(ProductInfoHolder viewHolder) {
        DecimalFormat avgFormat = new DecimalFormat("0.0");
        float yData[] = {0,0,0,0,0};

        // config the pie chart
        viewHolder.ratingChart.setUsePercentValues(true);
        viewHolder.ratingChart.setDrawSliceText(false);
        viewHolder.ratingChart.setDescription("");

        // enable hole and config
        viewHolder.ratingChart.setDrawHoleEnabled(true);
        viewHolder.ratingChart.setHoleColorTransparent(true);
        viewHolder.ratingChart.setHoleRadius(60);
        viewHolder.ratingChart.setTransparentCircleRadius(65);
        viewHolder.ratingChart.setCenterText(avgFormat.format(0));
        viewHolder.ratingChart.setCenterTextSize(40f);

        // set rotation
        viewHolder.ratingChart.setRotationEnabled(false);

        // add data
        addData(yData, viewHolder);

        // customize legends
        Legend l = viewHolder.ratingChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setYOffset(18);
        l.setXOffset(0);
        l.setFormSize(15);
        l.setXEntrySpace(2);
        l.setYEntrySpace(8);
    }
}






