package comjason_lewisg.httpsgithub.boozic;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import comjason_lewisg.httpsgithub.boozic.Controllers.FlagProductController;
import comjason_lewisg.httpsgithub.boozic.Controllers.NearbyStoresController;
import comjason_lewisg.httpsgithub.boozic.Controllers.ProductTypeListController;
import comjason_lewisg.httpsgithub.boozic.Controllers.UpdateProductController;
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductAdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductSearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.Models.UpdateProductModel;
import me.dm7.barcodescanner.zbar.Result;

public class ProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    public ProductSearchBarHandler searchBarHandler;
    private UpdateProductController UPC;
    public ProductTypeListController PTLC;
    public FlagProductController FPcon;

    public List<String> stores = new ArrayList<>();
    public List<Integer> storeIDs = new ArrayList<>();

    RecyclerView mRecyclerView;
    public ProductAdapterHandler mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    int colorPrimary_id;
    int colorAccent_id;
    int primaryColor;
    int primaryColorDark;
    int accentColor;
    int accentColorDark;

    int found;

    public ProductStorageModel model;
    public UpdateProductModel updatedModel;

    public boolean hasStores = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchColorInfo();
        //when resume, pull saves states for each button
        switch (colorPrimary_id) {
            case 1:
                setTheme(R.style.AppTheme1);
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

        UPC = new UpdateProductController();
        PTLC = new ProductTypeListController();
        FPcon = new FlagProductController();

        //if not a new product inject serializable objects
        found = (int) getIntent().getSerializableExtra("Found");
        if (found == 0) fetchProductInfo();
        else newProduct();

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
            if (updatedModel.updated) UPC.updateProduct(this);
            setProductActivityResults();
            finish();
            return true;
        }
        if (id == R.id.action_product_search) {
            searchBarHandler.openSearch(toolbar);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setProductActivityResults() {
        if (updatedModel.updated) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Position", updatedModel.position);
            returnIntent.putExtra("FavoritePosition", updatedModel.favoritePosition);
            returnIntent.putExtra("ProductId", updatedModel.productId);

            if (updatedModel.userRating != -1 && updatedModel.userRating != model.userRating)
                returnIntent.putExtra("UserRating", updatedModel.userRating);
            else returnIntent.putExtra("UserRating", model.userRating);

            if ((updatedModel.parentType != -1 && updatedModel.parentType != model.typePic)) {
                returnIntent.putExtra("ParentType", updatedModel.parentType);
            } else returnIntent.putExtra("ParentType", model.typePic);

            if (updatedModel.favorite != -1) returnIntent.putExtra("Favorite", updatedModel.favorite);
            else returnIntent.putExtra("Favorite", model.favorite);

            if (updatedModel.containerType != null && !updatedModel.containerType.equals(model.containerType))
                returnIntent.putExtra("ContainerType", updatedModel.containerType);
            else returnIntent.putExtra("ContainerType", model.containerType);

            if (updatedModel.containerQuantity != -1 && updatedModel.containerQuantity != model.containerQuantity)
                returnIntent.putExtra("ContainerQty", updatedModel.containerQuantity);
            else returnIntent.putExtra("ContainerQty", model.containerQuantity);

            if (updatedModel.volume != -1 && updatedModel.volume != model.volume)
                returnIntent.putExtra("Volume", updatedModel.volume);
            else returnIntent.putExtra("Volume", model.volume);

            if (updatedModel.volumeMeasure != null && !updatedModel.volumeMeasure.equals(model.volumeMeasure))
                returnIntent.putExtra("VolumeMeasure", updatedModel.volumeMeasure);
            else returnIntent.putExtra("VolumeMeasure", model.volumeMeasure);

            if (updatedModel.abv != -1 && updatedModel.abv != model.abv)
                returnIntent.putExtra("ABV", updatedModel.abv);
            else returnIntent.putExtra("ABV", model.abv);


            returnIntent.putExtra("ClosestStoreId", model.closestStoreId);
            returnIntent.putExtra("CheapestStoreId", model.cheapestStoreId);
            returnIntent.putExtra("ClosestStoreName", model.closestStoreName);
            returnIntent.putExtra("CheapestStoreName", model.cheapestStoreName);
            returnIntent.putExtra("ClosestStoreAddress", model.closestStoreAddress);
            returnIntent.putExtra("CheapestStoreAddress", model.cheapestStoreAddress);
            returnIntent.putExtra("ClosestStoreDist", model.closestStoreDist);
            returnIntent.putExtra("CheapestStoreDist", model.cheapestStoreDist);
            returnIntent.putExtra("ClosestPrice", model.closestPrice);
            returnIntent.putExtra("CheapestPrice", model.cheapestPrice);
            setResult(RESULT_OK, returnIntent);
        } else {
            setResult(RESULT_CANCELED);
        }
    }

    public void setNearByStores(List<String> stores, List<Integer> storeIDs) {
        this.stores.addAll(stores);
        this.storeIDs.addAll(storeIDs);
    }

    //start google maps navigation
    public void startNavigationIntent(String storeName, String destination) {
        Uri gmmIntentUri = Uri.parse("geo:0,0=d?q=" + destination + "(" + storeName + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        //mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
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
        //fetch latitude and longitude
        //TODO: wait for script to use distance formula
        new NearbyStoresController(this,
                (double) getIntent().getSerializableExtra("LAT"),
                (double) getIntent().getSerializableExtra("LONG"));
        //fetch extra items
        model = new ProductStorageModel((String) getIntent().getSerializableExtra("Label"),
                (String) getIntent().getSerializableExtra("UPC"),
                (int) getIntent().getSerializableExtra("ProductID"),
                (String) getIntent().getSerializableExtra("LastUpdate"),
                (double) getIntent().getSerializableExtra("UserRating"),
                (int) getIntent().getSerializableExtra("ClosestStoreId"),
                (int) getIntent().getSerializableExtra("CheapestStoreId"),
                (String) getIntent().getSerializableExtra("ClosestStore"),
                (String) getIntent().getSerializableExtra("CheapestStore"),
                (String) getIntent().getSerializableExtra("ClosestStoreAddress"),
                (String) getIntent().getSerializableExtra("CheapestStoreAddress"),
                (double) getIntent().getSerializableExtra("ClosestStoreDist"),
                (double) getIntent().getSerializableExtra("CheapestStoreDist"),
                (double) getIntent().getSerializableExtra("ClosestPrice"),
                (double) getIntent().getSerializableExtra("CheapestPrice"),
                (int) getIntent().getSerializableExtra("Type"),
                (int) getIntent().getSerializableExtra("Favorites"),
                (String) getIntent().getSerializableExtra("Container"),
                (int) getIntent().getSerializableExtra("ContainerQty"),
                (double) getIntent().getSerializableExtra("ABV"),
                (int[]) getIntent().getSerializableExtra("Rating"),
                (double) getIntent().getSerializableExtra("Volume"),
                (String) getIntent().getSerializableExtra("VolumeMeasure"),
                (double) getIntent().getSerializableExtra("PBV"),
                (double) getIntent().getSerializableExtra("ABP"),
                (double) getIntent().getSerializableExtra("PDD"),
                (double) getIntent().getSerializableExtra("TD"),
                (double) getIntent().getSerializableExtra("AvgRating"));

        try {
            int favoritePosition = (int) getIntent().getSerializableExtra("FavoritePosition");
            updatedModel = new UpdateProductModel((String) getIntent().getSerializableExtra("UPC"),
                    (int) getIntent().getSerializableExtra("ProductID"),
                    (int) getIntent().getSerializableExtra("Position"),
                    favoritePosition);
        } catch (Exception e) {
            updatedModel = new UpdateProductModel((String) getIntent().getSerializableExtra("UPC"),
                    (int) getIntent().getSerializableExtra("ProductID"),
                    (int) getIntent().getSerializableExtra("Position"));
        }
    }

    public void newProduct() {

        model = new ProductStorageModel((String) getIntent().getSerializableExtra("Label"),
                (String) getIntent().getSerializableExtra("UPC"),
                (int) getIntent().getSerializableExtra("ProductID"),
                (double) getIntent().getSerializableExtra("Volume"),
                (String) getIntent().getSerializableExtra("VolumeMeasure"));

        try {
            int favoritePosition = (int) getIntent().getSerializableExtra("FavoritePosition");
            updatedModel = new UpdateProductModel((String) getIntent().getSerializableExtra("UPC"),
                    (int) getIntent().getSerializableExtra("ProductID"),
                    (int) getIntent().getSerializableExtra("Position"),
                    favoritePosition);
        } catch (Exception e) {
            updatedModel = new UpdateProductModel((String) getIntent().getSerializableExtra("UPC"),
                    (int) getIntent().getSerializableExtra("ProductID"),
                    (int) getIntent().getSerializableExtra("Position"));
        }
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

    public int getPrimaryColor() { return primaryColor; }
    public int getPrimaryColorDark() { return primaryColorDark; }
    public int getAccentColor() { return accentColor; }
    public int getAccentColorDark() { return accentColorDark; }

    public int changeToInt(String str) {
        return Integer.parseInt(str);
    }
    public double changeToDouble(String str) {
        return Double.parseDouble(str);
    }
}
