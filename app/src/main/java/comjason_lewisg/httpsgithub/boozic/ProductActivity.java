package comjason_lewisg.httpsgithub.boozic;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public String pathToImage;
    public String storeName;
    public BigDecimal price;
    public int typePic;
    public double distance;
    public boolean favorite;
    public String volume;
    public double rating;
    public int alcoholId;
    public long barcode;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SharedPreferences mPrefs;

    private List<TopTensModel> DataSet = new ArrayList<TopTensModel>() {
        {
            //this is where we call backend connector
            //to populate Arraylist
            add(new TopTensModel(3, "Sky Vodka", "ABC liquor", "1.75L", 1.3, BigDecimal.valueOf(18.73), true));
            add(new TopTensModel(1, "Miller Light", "Publix liquor", "2.13L", 1.8, BigDecimal.valueOf(7.23), true));
            add(new TopTensModel(1, "Bud Light", "Publix liquor", "2.13L", 1.8, BigDecimal.valueOf(8.02), true));
            add(new TopTensModel(2, "Moscato", "ABC liquor", "0.75L", 1.3, BigDecimal.valueOf(11.46), true));
            add(new TopTensModel(3, "Fireball Whiskey", "ADC Liquor", "0.75L", 1.3, BigDecimal.valueOf(12.95), true));
            add(new TopTensModel(3, "Wyborowa Wodka Rye Grain Polish Vodka", "ABC liquor", "1.75L", 1.3, BigDecimal.valueOf(26.99), true));
            add(new TopTensModel(1, "Henninger", "ABC liquor", "0.47L", 1.8, BigDecimal.valueOf(1.59), true));
            add(new TopTensModel(1, "Erie Soleil Shandy ", "ABC liquor", "0.35L", 1.8, BigDecimal.valueOf(2.29), true));
            add(new TopTensModel(2, "Domaine Gavoty Provence Rose", "ABC liquor", "0.75L", 1.3, BigDecimal.valueOf(29.99), true));
            add(new TopTensModel(3, "Ciroc French Pineapple Vodka", "ADC Liquor", "1.75L", 1.3, BigDecimal.valueOf(27.99), true));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new AdapterHandler(DataSet, this);
        mRecyclerView.setAdapter(mAdapter);

        //fetch extra items
        label = (String) getIntent().getSerializableExtra("Label");

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
        toolbar.setY(getStatusBarHeight() + 75);
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

        CollapsingToolbarLayout tmp = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        tmp.setTitle(label);
        tmp.setContentScrimColor(primaryColor);
        tmp.setStatusBarScrimColor(primaryColorDark);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //getWindow().setStatusBarColor(primaryColorDark);
        //findViewById(R.id.collapsing_toolbar).setCon(primaryColor);

    }
}
