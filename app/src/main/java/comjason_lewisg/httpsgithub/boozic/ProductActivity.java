package comjason_lewisg.httpsgithub.boozic;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.quinny898.library.persistentsearch.SearchBox;

import comjason_lewisg.httpsgithub.boozic.Handlers.ProductAdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductSearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.Models.UpdateProductModel;

public class ProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    public ProductSearchBarHandler searchBarHandler;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    int colorPrimary_id;
    int colorAccent_id;
    int primaryColor;
    int primaryColorDark;
    int accentColor;
    int accentColorDark;

    public ProductStorageModel model;
    public UpdateProductModel updatedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchColorInfo();
        //when resume, pull saves states for each button
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

        //if not a new product inject serializable objects
        if ((boolean) getIntent().getSerializableExtra("Found"))
            fetchProductInfo();
        else
            newProduct();

        //Instantiate the toolbar object
        toolbar = (Toolbar) findViewById(R.id.product_toolbar); // Attaching the layout to the toolbar object
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Call these to set up the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //connect to search bar and create new search handler
        searchBarHandler = new ProductSearchBarHandler(this, toolbar);
        SearchBox sb = (SearchBox) findViewById(R.id.product_searchbox);
        sb.setY(getStatusBarHeight());

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
            checkUpdateModel();
            finish();
            return true;
        }
        if (id == R.id.action_product_search) {
            searchBarHandler.openSearch(toolbar);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchColorInfo() {
        colorPrimary_id = (int) getIntent().getSerializableExtra("COLOR_PRIMARY_ID");
        colorAccent_id = (int) getIntent().getSerializableExtra("COLOR_ACCENT_ID");
        primaryColor = (int) getIntent().getSerializableExtra("COLOR_PRIMARY");
        primaryColorDark = (int) getIntent().getSerializableExtra("COLOR_PRIMARY_DARK");
        accentColor = (int) getIntent().getSerializableExtra("COLOR_ACCENT");
        accentColorDark = (int) getIntent().getSerializableExtra("COLOR_ACCENT_DARK");
     }

    private void fetchProductInfo() {
        //fetch extra items

        model = new ProductStorageModel((String) getIntent().getSerializableExtra("Label"),
                (String) getIntent().getSerializableExtra("UPC"),
                (int) getIntent().getSerializableExtra("ProductId"),
                (String) getIntent().getSerializableExtra("LastUpdate"),
                (double) getIntent().getSerializableExtra("UserRating"),
                (int) getIntent().getSerializableExtra("ClosestStoreId"),
                (int) getIntent().getSerializableExtra("CheapestStoreId"),
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

        updatedModel = new UpdateProductModel((String) getIntent().getSerializableExtra("Label"),
                (String) getIntent().getSerializableExtra("UPC"),
                (int) getIntent().getSerializableExtra("ProductId"),
                (double) getIntent().getSerializableExtra("Volume"),
                (String) getIntent().getSerializableExtra("VolumeMeasure"));
    }

    public void newProduct() {

        model = new ProductStorageModel((String) getIntent().getSerializableExtra("Label"),
                (String) getIntent().getSerializableExtra("UPC"),
                (int) getIntent().getSerializableExtra("ProductId"),
                null,-1,-1,-1,null,null,-1,-1,-1,-1,-1,false,null,-1,-1,new int[] {0,0,0,0,0},
                (double) getIntent().getSerializableExtra("Volume"),
                (String) getIntent().getSerializableExtra("VolumeMeasure"),
                -1,-1,-1,-1,-1);

        updatedModel = new UpdateProductModel((String) getIntent().getSerializableExtra("Label"),
                (String) getIntent().getSerializableExtra("UPC"),
                (int) getIntent().getSerializableExtra("ProductId"),
                (double) getIntent().getSerializableExtra("Volume"),
                (String) getIntent().getSerializableExtra("VolumeMeasure"));
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

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle("");
        ctl.setContentScrimColor(primaryColor);
        ctl.setStatusBarScrimColor(primaryColorDark);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    public int getAccentColorId() { return colorAccent_id; }
    public int getAccentColor() { return accentColor; }

    public void checkUpdateModel() {
        Log.v("UPDATEMODEL", "The value of container is " + updatedModel.container);
        Log.v("UPDATEMODEL", "The value of abv is " + updatedModel.abv);
        Log.v("UPDATEMODEL", "The value of StoreName and StoreDist is " + updatedModel.StoreName + " and " + updatedModel.StoreDist);
        Log.v("UPDATEMODEL", "The value of Price is " + updatedModel.Price);
    }

    public int changeToInt(String str) {
        return Integer.parseInt(str);
    }
    public double changeToDouble(String str) {
        return Double.parseDouble(str);
    }
}
