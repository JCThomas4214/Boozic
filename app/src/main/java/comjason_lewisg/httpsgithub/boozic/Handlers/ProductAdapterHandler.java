package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class ProductAdapterHandler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ProductStorageModel item;
    ProductActivity m;

    View main;

    TextView label;
    TextView lastUpdate;
    TextView boozicScore;

    TextView closestStore;
    TextView cheapestStore;
    TextView closestPrice;
    TextView cheapestPrice;

    ImageView typePic;

    TextView container;
    TextView volume;

    TextView pbv;
    TextView abv;
    TextView proof;
    TextView abp;
    TextView pdd;
    TextView td;

    RatingBar userRating;
    LinearLayout closestStoreLayout;
    PieChart ratingChart;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ProductInfoHolder extends RecyclerView.ViewHolder{


        // each data item is just a string in this case
        public ProductInfoHolder(View itemView) {
            super(itemView);
            main = itemView;

            label = (TextView) itemView.findViewById(R.id.product_label);
            typePic = (ImageView) itemView.findViewById(R.id.product_type);
            boozicScore = (TextView) itemView.findViewById(R.id.product_metascore);
            lastUpdate = (TextView) itemView.findViewById(R.id.product_last_updated);
            userRating = (RatingBar) itemView.findViewById(R.id.product_ratingBar);

            closestStoreLayout = (LinearLayout) itemView.findViewById(R.id.closest_store_layout);
            pdd = (TextView) itemView.findViewById(R.id.product_pdd);
            td = (TextView) itemView.findViewById(R.id.product_td);

            closestStore = (TextView) itemView.findViewById(R.id.product_closest_store);
            closestPrice = (TextView) itemView.findViewById(R.id.product_closest_price);

            cheapestStore = (TextView) itemView.findViewById(R.id.product_cheapest_store);
            cheapestPrice = (TextView) itemView.findViewById(R.id.product_cheapest_price);


            //TODO: impliment the favorities button here

            container = (TextView) itemView.findViewById(R.id.product_container);
            volume = (TextView) itemView.findViewById(R.id.product_volume);
            abv = (TextView) itemView.findViewById(R.id.product_abv);
            proof = (TextView) itemView.findViewById(R.id.product_proof);
            abp = (TextView) itemView.findViewById(R.id.product_abp);
            pbv = (TextView) itemView.findViewById(R.id.product_pbv);

            ratingChart = (PieChart) itemView.findViewById(R.id.rating_chart);
            LayerDrawable stars = (LayerDrawable) userRating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }
    }

    // Provide a suitable constructor (depends on the kind of data set)
    public ProductAdapterHandler(ProductStorageModel modeldata, ProductActivity m) {
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

        // create a new view
        itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.product_info, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new ProductAdapterHandler.ProductInfoHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ProductStorageModel model = item;

        DecimalFormat df = new DecimalFormat("####0.##");
        DecimalFormat monFormat = new DecimalFormat("####0.00");
        DecimalFormat pbvFormat = new DecimalFormat("####0.00#");

        label.setText(model.label);
        lastUpdate.setText("" + model.lastUpdate);
        userRating.setRating((float) model.userRating);

        if (model.closestStoreName.equals(model.cheapestStoreName)) {
            closestStoreLayout.setVisibility(View.GONE);
            pdd.setText("N/A");
            td.setText("N/A");
        }
        else {
            closestStore.setText("(" + model.closestStoreDist + "mi) " + model.closestStoreName);
            closestPrice.setText("$"+ monFormat.format(model.closestPrice));
            pdd.setText("$" + monFormat.format(model.pdd));
            td.setText("$" + monFormat.format(model.td));
        }

        cheapestStore.setText("(" + model.cheapestStoreDist + "mi) " + model.cheapestStoreName);
        cheapestPrice.setText("$" + monFormat.format(model.cheapestPrice));

        selectTypePic(model);

        container.setText(model.container);
        volume.setText(df.format(model.volume) + model.volumeMeasure);
        abv.setText(df.format(model.abv) + "%");
        proof.setText("" + model.proof);
        abp.setText("$" + monFormat.format(model.abp) + "/ml");
        pbv.setText("$" + pbvFormat.format(model.pbv) + "/ml");

        setChart(model);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }

    public void selectTypePic(ProductStorageModel model) {
        switch (model.typePic) {
            case 1:
                typePic.setBackgroundResource(R.mipmap.beer);
                break;
            case 2:
                typePic.setBackgroundResource(R.mipmap.wine);
                break;
            case 3:
                typePic.setBackgroundResource(R.mipmap.liquor);
                break;
        }
    }

    public void setChart(ProductStorageModel model) {
        DecimalFormat avgFormat = new DecimalFormat("0.0");
        float yData[] = {model.rating[0], model.rating[1], model.rating[2], model.rating[3], model.rating[4]};

        // config the pie chart
        ratingChart.setUsePercentValues(true);
        ratingChart.setDrawSliceText(false);
        ratingChart.setDescription("");

        // enable hole and config
        ratingChart.setDrawHoleEnabled(true);
        ratingChart.setHoleColorTransparent(true);
        ratingChart.setHoleRadius(60);
        ratingChart.setTransparentCircleRadius(65);
        ratingChart.setCenterText(avgFormat.format(model.avgRating));
        ratingChart.setCenterTextSize(40f);

        // set rotation
        ratingChart.setRotationEnabled(false);

        // add data
        addData(yData);

        // customize legends
        Legend l = ratingChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setYOffset(18);
        l.setXOffset(0);
        l.setFormSize(15);
        l.setXEntrySpace(2);
        l.setYEntrySpace(8);
    }

    private void addData(float[] yData) {

        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < yData.length; i++)
            yVals.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<>();
        for (int j = 0; j < 5; j++)
            xVals.add("");

        // create pie data
        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(1);
        dataSet.setSelectionShift(0);

        // add colors
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(main.getResources().getColor(R.color.ColorAccent));
        colors.add(main.getResources().getColor(R.color.ColorAccent2));
        colors.add(main.getResources().getColor(R.color.ColorAccent3));
        colors.add(main.getResources().getColor(R.color.ColorAccent4));
        colors.add(main.getResources().getColor(R.color.ColorAccent5));

        dataSet.setColors(colors);

        // instantiate pie data
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        ratingChart.setData(data);

        //undo all highlights
        ratingChart.highlightValues(null);

        //update pie chart
        ratingChart.invalidate();
    }
}






