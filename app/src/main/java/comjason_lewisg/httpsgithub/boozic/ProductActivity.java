package comjason_lewisg.httpsgithub.boozic;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import java.text.NumberFormat;
import java.util.ArrayList;
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

    public String label;
    public String closestStoreName;
    public String cheapestStoreName;
    public BigDecimal closestPrice;
    public BigDecimal cheapestPrice;
    public int typePic;
    public double distance;
    public boolean favorite;
    public String volume;
    public double rating;
    public int alcoholId;
    public long barcode;

    public PieChart ratingChart;
    private float[] yData = {145, 112, 98, 45, 16};
    private String[] xData = {"5", "4", "3", "2", "1"};

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
        label = (String) getIntent().getSerializableExtra("Label");
        typePic = (int) getIntent().getSerializableExtra("Type");
        closestStoreName = (String) getIntent().getSerializableExtra("ClosestStore");
        cheapestStoreName = (String) getIntent().getSerializableExtra("CheapestStore");
        closestPrice = (BigDecimal) getIntent().getSerializableExtra("ClosestPrice");
        closestPrice = (BigDecimal) getIntent().getSerializableExtra("ClosestPrice");

        setProductInfo();
    }

    public void setProductInfo() {

        TextView text = (TextView) findViewById(R.id.product_label);
        text.setText(label);

        selectTypePic();

        text = (TextView) findViewById(R.id.product_closest_store);
        text.setText(closestStoreName + " - " + NumberFormat.getCurrencyInstance().format(closestPrice));

        text = (TextView) findViewById(R.id.product_cheapest_store);
        text.setText(closestStoreName + " - " + NumberFormat.getCurrencyInstance().format(closestPrice));

        setChart();
    }

    public void selectTypePic() {
        ImageView img = (ImageView) findViewById(R.id.product_type);
        switch (typePic) {
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

        // set rotation
        ratingChart.setRotationEnabled(false);

        // add data
        addData();

        // customize legends
        Legend l = ratingChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setYOffset(18);
        l.setXOffset(0);
        l.setFormSize(15);
        l.setXEntrySpace(2);
        l.setYEntrySpace(8);
    }

    private void addData() {
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
        ctl.setTitle(label);
        ctl.setContentScrimColor(primaryColor);
        ctl.setStatusBarScrimColor(primaryColorDark);

        /*Drawable setButton = getResources().getDrawable(R.drawable.custom_product_button, null);
        setButton.setColorFilter(primaryColor, PorterDuff.Mode.MULTIPLY);
        Button btn = (Button) findViewById(R.id.new_price_button);
        btn.setBackground(setButton);*/

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }
}
