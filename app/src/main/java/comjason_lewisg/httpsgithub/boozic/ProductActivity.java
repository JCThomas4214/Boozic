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
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductAdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductSearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;

public class ProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public ProductSearchBarHandler searchBarHandler;

    static final int COLOR_STATE = 0;
    static final int PRIMARY_STATE = 0;
    static final int PRIMARY_DARK_STATE = 0;
    static final int ACCENT_STATE = 0;
    static final int ACCENT_DARK_STATE = 0;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int primaryColor;
    private int primaryColorDark;
    private int accentColor;
    private int accentColorDark;

    public ProductStorageModel model;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("COLOR_STATE", MODE_MULTI_PROCESS);
        //when resume, pull saves states for each button
        int colorPrimary_id = mPrefs.getInt("COLOR_STATE", COLOR_STATE);
        switch (colorPrimary_id) {
            case 1:
                setTheme(R.style.AppTheme);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
        }
        setContentView(R.layout.activity_product);

        fetchProductInfo();

        //Instantiate the toolbar object
        toolbar = (Toolbar) findViewById(R.id.product_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        //Call these to set up the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //connect to search bar and create new search handler
        searchBarHandler = new ProductSearchBarHandler(this, toolbar);

        ImageView img = (ImageView) findViewById(R.id.headerimg);
        SearchBox sb = (SearchBox) findViewById(R.id.product_searchbox);
        toolbar.setY(getStatusBarHeight() + 75);
        sb.setY(getStatusBarHeight());
        img.setY(getStatusBarHeight());

        //set recyclerview for product layout
        mRecyclerView = (RecyclerView) findViewById(R.id.product_rv);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ProductAdapterHandler(model, this);
        mRecyclerView.setAdapter(mAdapter);
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

        model = new ProductStorageModel((String) getIntent().getSerializableExtra("Label"),
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
                (int[]) getIntent().getSerializableExtra("Rating"),
                (double) getIntent().getSerializableExtra("Volume"),
                (String) getIntent().getSerializableExtra("VolumeMeasure"),
                (double) getIntent().getSerializableExtra("PBV"),
                (double) getIntent().getSerializableExtra("ABP"),
                (double) getIntent().getSerializableExtra("PDD"),
                (double) getIntent().getSerializableExtra("TD"),
                (double) getIntent().getSerializableExtra("AvgRating"));
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

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }
}
