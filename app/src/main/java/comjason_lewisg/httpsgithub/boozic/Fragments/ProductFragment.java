package comjason_lewisg.httpsgithub.boozic.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Controllers.ProductTypeListController;
import comjason_lewisg.httpsgithub.boozic.Controllers.UpdateProductController;
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductAdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.ProductSearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.SearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.Models.UpdateProductModel;
import comjason_lewisg.httpsgithub.boozic.R;

public class ProductFragment extends Fragment {

    private View rootView;
    public MainActivity m;
    private OnFragmentInteractionListener mListener;

    Toolbar toolbar;
    public SearchBarHandler searchBarHandler;
    private UpdateProductController UPC;
    public ProductTypeListController PTLC;

    public List<String> stores = new ArrayList<>();
    public List<Integer> storeIDs = new ArrayList<>();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    int colorPrimary_id;
    int colorAccent_id;
    int primaryColor;
    int primaryColorDark;
    int accentColor;
    int accentColorDark;

    int found;

    public TopTensModel model;
    public UpdateProductModel updatedModel;

    public boolean hasStores = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_product, container, false);
        m = (MainActivity)getActivity();

        UPC = new UpdateProductController();
        PTLC = new ProductTypeListController();

        //if not a new product inject serializable objects
        model = m.getPreviousListItem();

        //Instantiate the toolbar object
        toolbar = (Toolbar) rootView.findViewById(R.id.product_toolbar); // Attaching the layout to the toolbar object
        toolbar.setTitle("");
        setHasOptionsMenu(true);

        //Call these to set up the back arrow on toolbar
        m.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        m.getSupportActionBar().setDisplayShowHomeEnabled(true);

        //connect to search bar and create new search handler
        searchBarHandler = new SearchBarHandler((MainActivity)getActivity());
        SearchBox sb = (SearchBox) rootView.findViewById(R.id.product_searchbox);
        sb.setY(getStatusBarHeight());

        //set recyclerview for product layout
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.product_rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ProductAdapterHandler(m, model, this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_product, menu);
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
            //checkUpdateModel();
            //if (updatedModel.updated) UPC.updateProduct(this);
            //finish();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
            return true;
        }
        if (id == R.id.action_search) {
            searchBarHandler.openSearch(m.title);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getPrimaryColor() { return m.getColorPrimary(); }
    public int getPrimaryColorDark() { return m.getColorPrimaryDark(); }
    public int getAccentColor() { return m.getColorAccent(); }
    public int getAccentColorDark() { return m.getColorAccentDark(); }


    public interface OnFragmentInteractionListener {

    }

    @Override
    public void onResume() {
        super.onResume();

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) m.findViewById(R.id.collapsing_toolbar);
        ctl.setTitle("");
        ctl.setContentScrimColor(getPrimaryColor());
        ctl.setStatusBarScrimColor(getPrimaryColorDark());

        m.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
