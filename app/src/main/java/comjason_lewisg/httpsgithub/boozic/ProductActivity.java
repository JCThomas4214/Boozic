package comjason_lewisg.httpsgithub.boozic;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.quinny898.library.persistentsearch.SearchBox;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import comjason_lewisg.httpsgithub.boozic.Handlers.AdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductSearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public ProductSearchBarHandler searchBarHandler;

    static final int COLOR_STATE = 0;
    static final int PRIMARY_STATE = 0;
    static final int PRIMARY_DARK_STATE = 0;
    static final int ACCENT_STATE = 0;
    static final int ACCENT_DARK_STATE = 0;

    private int primaryColor;
    private int primaryColorDark;
    private int accentColor;
    private int accentColorDark;

    public TopTensModel model;

    public PieChart ratingChart;
    private String[] xData = {"", "", "", "", ""};

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        fetchProductInfo();

        //Instantiate the toolbar object
        toolbar = (Toolbar) findViewById(R.id.product_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        //Call these to set up the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //connect to search bar and create new search handler
        searchBarHandler = new ProductSearchBarHandler();
        searchBarHandler.setActivity(this, toolbar);

        ImageView img = (ImageView) findViewById(R.id.headerimg);
        SearchBox sb = (SearchBox) findViewById(R.id.product_searchbox);
        toolbar.setY(getStatusBarHeight() + 75);
        sb.setY(getStatusBarHeight());
        img.setY(getStatusBarHeight());
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_product, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            //return to main activity
            finish();
            return true;
        }
        if (id == R.id.action_product_search) {
            searchBarHandler.openSearch(toolbar);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchProductInfo() {
        //fetch extra items

        model = new TopTensModel((String) getIntent().getSerializableExtra("Label"),
                (String) getIntent().getSerializableExtra("LastUpdate"),
                (double) getIntent().getSerializableExtra("UserRating"),
                (String) getIntent().getSerializableExtra("ClosestStore"),
                (String) getIntent().getSerializableExtra("CheapestStore"),
                (double) getIntent().getSerializableExtra("ClosestStoreDist"),
                (double) getIntent().getSerializableExtra("CheapestStoreDist"),
                (double) getIntent().getSerializableExtra("ClosestPrice"),
                (double) getIntent().getSerializableExtra("CheapestPrice"),
                (int) getIntent().getSerializableExtra("Type"),
                (boolean) getIntent().getSerializableExtra("Favorites"),
                (String) getIntent().getSerializableExtra("Container"),
                (double) getIntent().getSerializableExtra("ABV"),
                (int) getIntent().getSerializableExtra("Proof"),
                (int[]) getIntent().getSerializableExtra("Rating"));

        setProductInfo();
    }

    public void setProductInfo() {
        DecimalFormat df = new DecimalFormat("####0.##");
        DecimalFormat monFormat = new DecimalFormat("####0.00");
        DecimalFormat pbvFormat = new DecimalFormat("####0.00#");

        TextView text = (TextView) findViewById(R.id.product_label);
        text.setText(model.label);

        text = (TextView) findViewById(R.id.product_last_updated);
        text.setText("" + model.lastUpdate);

        RatingBar uRating = (RatingBar) findViewById(R.id.product_ratingBar);
        uRating.setRating((float) model.userRating);

        LinearLayout closestStoreLayout = (LinearLayout) findViewById(R.id.closest_store_layout);
        if (model.closestStoreName.equals(model.cheapestStoreName)) {
            closestStoreLayout.setVisibility(View.GONE);
            text = (TextView) findViewById(R.id.product_pdd);
            text.setText("N/A");
            text = (TextView) findViewById(R.id.product_td);
            text.setText("N/A");
        }
        else {
            text = (TextView) findViewById(R.id.product_closest_store);
            text.setText("(" + model.closestStoreDist + "mi) " + model.closestStoreName);
            text = (TextView) findViewById(R.id.product_closest_price);
            text.setText("$"+ monFormat.format(model.closestPrice));
            text = (TextView) findViewById(R.id.product_pdd);
            text.setText("$" + monFormat.format(model.pdd));
            text = (TextView) findViewById(R.id.product_td);
            text.setText("$" + monFormat.format(model.td));
        }

        text = (TextView) findViewById(R.id.product_cheapest_store);
        text.setText("(" + model.cheapestStoreDist + "mi) " + model.cheapestStoreName);
        text = (TextView) findViewById(R.id.product_cheapest_price);
        text.setText("$" + monFormat.format(model.cheapestPrice));



        selectTypePic();

        //TODO: impliment the favorities button here

        text = (TextView) findViewById(R.id.product_container);
        text.setText(model.container);

        text = (TextView) findViewById(R.id.product_volume);
        text.setText(df.format(model.volume) + model.volumeMeasure);

        text = (TextView) findViewById(R.id.product_abv);
        text.setText(df.format(model.abv) + "%");

        text = (TextView) findViewById(R.id.product_proof);
        text.setText("" + model.proof);

        text = (TextView) findViewById(R.id.product_abp);
        text.setText("$" + monFormat.format(model.abp) + "/ml");

        text = (TextView) findViewById(R.id.product_pbv);
        text.setText("$" + pbvFormat.format(model.pbv) + "/ml");

        setChart();
    }

    public void selectTypePic() {
        ImageView img = (ImageView) findViewById(R.id.product_type);
        switch (model.typePic) {
            case 1:
                img.setBackgroundResource(R.mipmap.beer);
                break;
            case 2:
                img.setBackgroundResource(R.mipmap.wine);
                break;
            case 3:
                img.setBackgroundResource(R.mipmap.liquor);
                break;
        }
    }

    public void setChart() {
        DecimalFormat avgFormat = new DecimalFormat("0.0");
        //float yData[] = {model.rating5, model.rating4, model.rating3, model.rating2, model.rating1};
        float yData[] = {model.rating[0], model.rating[1], model.rating[2], model.rating[3], model.rating[4]};

        ratingChart = (PieChart) findViewById(R.id.rating_chart);

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

        for (int o = 0; o < xData.length; o++)
            xVals.add(xData[o]);

        // create pie data
        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(0);

        // add colors
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(R.color.ColorAccent));
        colors.add(getResources().getColor(R.color.ColorAccent2));
        colors.add(getResources().getColor(R.color.ColorAccent3));
        colors.add(getResources().getColor(R.color.ColorAccent4));
        colors.add(getResources().getColor(R.color.ColorAccent5));

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

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        //pull the shared preference
        mPrefs = getSharedPreferences("COLOR_STATE", MODE_MULTI_PROCESS);
        //when resume, pull saves states for each button

        primaryColor = mPrefs.getInt("PRIMARY_STATE", PRIMARY_STATE);
        primaryColorDark = mPrefs.getInt("PRIMARY_DARK_STATE", PRIMARY_DARK_STATE);
        accentColor = mPrefs.getInt("ACCENT_STATE", ACCENT_STATE);
        accentColorDark = mPrefs.getInt("ACCENT_DARK_STATE", ACCENT_DARK_STATE);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(model.label);
        ctl.setContentScrimColor(primaryColor);
        ctl.setStatusBarScrimColor(primaryColorDark);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.product_ratingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        /*Drawable setButton = getResources().getDrawable(R.drawable.custom_product_button, null);
        setButton.setColorFilter(primaryColor, PorterDuff.Mode.MULTIPLY);
        Button btn = (Button) findViewById(R.id.new_price_button);
        btn.setBackground(setButton);*/

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }
}
