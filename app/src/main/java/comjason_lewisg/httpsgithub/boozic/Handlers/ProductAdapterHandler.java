package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

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
        public IMyViewHolderClicks mListener;

        // each data item is just a string in this case
        public ProductInfoHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            main = itemView;
            mListener = listener;
            updateProduct = (Button) itemView.findViewById(R.id.new_price_button);

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

            updateProduct.setOnClickListener(clickListener);
        }

        public View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.new_price_button:
                        mListener.startUpdateDialog(v);
                        break;
                }
            }
        };

        public interface IMyViewHolderClicks {
            void startUpdateDialog(View caller);
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

        DHandler = new DialogHandler();

        // create a new view
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.product_info, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new ProductInfoHolder(itemView, new ProductAdapterHandler.ProductInfoHolder.IMyViewHolderClicks() {
            public void startUpdateDialog(View caller) {

                //TODO: conditional statements for each important attribute containing Dialog to fill
                Log.v("CLICK", "update price button clicked in " + item.label + " product view" );
                if (p.model.container == null) DHandler.UpdateContainer(p);
                else if (p.model.abv == -1) DHandler.UpdateAbv(p);
                else DHandler.UpdateStore(p, false);

            }
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
        if (model.lastUpdate == null) viewHolder.lastUpdate.setText("N/A");
        else viewHolder.lastUpdate.setText("" + model.lastUpdate);

        if (model.userRating == -1) viewHolder.userRating.setRating(0);
        else viewHolder.userRating.setRating((float) model.userRating);
        LayerDrawable stars = (LayerDrawable) viewHolder.userRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(p.getAccentColor(), PorterDuff.Mode.SRC_ATOP);

        if (model.cheapestStoreName == null) {
            viewHolder.closestStoreLayout.setVisibility(View.GONE);
            viewHolder.pdd.setText("N/A");
            viewHolder.td.setText("N/A");
            viewHolder.cheapestStore.setText("N/A");
            viewHolder.cheapestPrice.setText("N/A");
        }
        else {
            if (model.closestStoreName.equals(model.cheapestStoreName)) {
                viewHolder.closestStoreLayout.setVisibility(View.GONE);
                viewHolder.pdd.setText("N/A");
                viewHolder.td.setText("N/A");
            } else {
                viewHolder.closestStore.setText("(" + model.closestStoreDist + "mi) " + model.closestStoreName);
                viewHolder.closestPrice.setText("$" + monFormat.format(model.closestPrice));
                viewHolder.pdd.setText("$" + monFormat.format(model.pdd));
                viewHolder.td.setText("$" + monFormat.format(model.td));
            }
            viewHolder.cheapestStore.setText("(" + model.cheapestStoreDist + "mi) " + model.cheapestStoreName);
            viewHolder.cheapestPrice.setText("$" + monFormat.format(model.cheapestPrice));
        }

        if (model.typePic == -1) viewHolder.typePic.setBackgroundResource(R.mipmap.ic_launcher);
        else selectTypePic(model, viewHolder);

        if (model.container == null) viewHolder.container.setText("N/A");
        else viewHolder.container.setText(model.container);

        if (model.volume == -1) viewHolder.volume.setText("N/A");
        else viewHolder.volume.setText(df.format(model.volume) + model.volumeMeasure);

        if (model.abv == -1) viewHolder.abv.setText("N/A");
        else viewHolder.abv.setText(df.format(model.abv) + "%");

        if (model.proof == -1) viewHolder.proof.setText("N/A");
        else viewHolder.proof.setText("" + model.proof);

        if (model.abp == -1) viewHolder.abp.setText("N/A");
        else viewHolder.abp.setText("$" + monFormat.format(model.abp) + "/ml");

        if (model.pbv == -1) viewHolder.pbv.setText("N/A");
        else viewHolder.pbv.setText("$" + pbvFormat.format(model.pbv) + "/ml");

        if (model.avgRating == -1) emptyChart(viewHolder);
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
                viewHolder.typePic.setBackgroundResource(R.mipmap.beer);
                break;
            case 2:
                viewHolder.typePic.setBackgroundResource(R.mipmap.wine);
                break;
            case 3:
                viewHolder.typePic.setBackgroundResource(R.mipmap.liquor);
                break;
        }
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






