package comjason_lewisg.httpsgithub.boozic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.transition.Fade;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import comjason_lewisg.httpsgithub.boozic.Controllers.DeviceIdController;
import comjason_lewisg.httpsgithub.boozic.Controllers.FavoritesListController;
import comjason_lewisg.httpsgithub.boozic.Controllers.FeedbackController;
import comjason_lewisg.httpsgithub.boozic.Controllers.ProductListController;
import comjason_lewisg.httpsgithub.boozic.Controllers.RemoveFromFavoritesController;
import comjason_lewisg.httpsgithub.boozic.Controllers.UPCFindProductController;
import comjason_lewisg.httpsgithub.boozic.Fragments.FavoritesFragment;
import comjason_lewisg.httpsgithub.boozic.Fragments.ThemeFragment;
import comjason_lewisg.httpsgithub.boozic.Fragments.TopTensFragment;
import comjason_lewisg.httpsgithub.boozic.Handlers.AdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.AnimateToolbarHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.DialogHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.FilterMenuHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.FloatingActionButtonHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.NavigationDrawerHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.SearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.ThemeHandler;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;

import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ThemeFragment.OnDataPass, TopTensFragment.OnPass,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public Toolbar toolbar;
    public TextView title;
    public DialogHandler DHandle;

    public DeviceIdController DIDcon;
    public ProductListController PLcon;
    public FavoritesListController FLcon;
    public RemoveFromFavoritesController RFFcon;
    public UPCFindProductController upcFPC;
    public FeedbackController FC;

    public SearchBarHandler searchBarHandler;
    public NavigationDrawerHandler Nav;
    public ThemeHandler themeHandler;
    public FloatingActionButtonHandler FAB;
    public FilterMenuHandler FMHandle;
    public MenuItem item = null;
    public boolean filterButtonVis = true;
    public static Activity activity;

    static final int SCANNER_CODE_REQUEST = 0;

    static final int COLOR_STATE = 1;
    static final int COLOR_ACCENT_STATE = 1;
    static final int PRIMARY_STATE = -15374912;
    static final int PRIMARY_DARK_STATE = -15906911;
    static final int ACCENT_STATE = -26624;
    static final int ACCENT_DARK_STATE = -291840;

    public int TBwidth;
    public int TBheight;
    private double expandConst;
    private double srinkConst;

    private int colorPrimary_id = 1;
    private int colorAccent_id = 1;

    private int primaryColor = -15374912;
    private int primaryColorDark = -15906911;
    private int accentColor = -26624;
    private int accentColorDark = -291840;

    private Location mLastLocation;
    public double latitude;
    public double longitude;

    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private SharedPreferences mPrefs;
    private boolean firstStartRefresh = true;

    private boolean backstack;
    public boolean backstackSearch;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public Context applicationContext;

    private TopTensModel previousListItem;
    DrawerLayout drawer;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        //pull the shared preference
        mPrefs = getSharedPreferences("COLOR_STATE", MODE_PRIVATE);
        //when resume, pull saves states for each button
        colorPrimary_id = mPrefs.getInt("COLOR_STATE", COLOR_STATE);
        colorAccent_id = mPrefs.getInt("COLOR_ACCENT_STATE", COLOR_ACCENT_STATE);

        //store the previous state colors into their variables
        primaryColor = mPrefs.getInt("PRIMARY_STATE", PRIMARY_STATE);
        primaryColorDark = mPrefs.getInt("PRIMARY_DARK_STATE", PRIMARY_DARK_STATE);
        accentColor = mPrefs.getInt("ACCENT_STATE", ACCENT_STATE);
        accentColorDark = mPrefs.getInt("ACCENT_DARK_STATE", ACCENT_DARK_STATE);

        latitude = mPrefs.getFloat("LAST_LATITUDE", (float) 0.00);
        longitude = mPrefs.getFloat("LAST_LONGITUDE", (float) 0.00);

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
        applicationContext = getApplicationContext();

        buildGoogleApiClient();
        checkPlayServices();
        createLocationRequest();

        DIDcon = new DeviceIdController(this);
        PLcon = new ProductListController();
        FLcon = new FavoritesListController(this);
        RFFcon = new RemoveFromFavoritesController(this);
        upcFPC = new UPCFindProductController();
        FC = new FeedbackController();

        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        TBwidth = size.x;
        TBheight = size.y;

        //Create a Dialog Handler for Feedback
        DHandle = new DialogHandler();

        Log.v("STATE", "onCreate color id = " + colorPrimary_id);
        themeHandler = new ThemeHandler();

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        frame = (FrameLayout) findViewById(R.id.frame);

        //connect to search bar and create new search handler
        searchBarHandler = new SearchBarHandler(this);
        Nav = new NavigationDrawerHandler(this,toolbar);

        //change the FAB icons depending on state color
        findViewById(R.id.toolbar).setBackgroundColor(primaryColor);

        //Creates a FAB for the bottom right corner of the main screen
        FAB = new FloatingActionButtonHandler(this, accentColor, accentColorDark);

        //Create instance for Filter Menu
        FMHandle = new FilterMenuHandler(this, primaryColor);
        FMHandle.initiateSharedPref(mPrefs);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Log.v("SCREEN", "Screen size is large");
                setLarge();
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                Log.v("SCREEN", "Screen size is Normal");
                setNormal();
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                Log.v("SCREEN", "Screen size is Small");
                setSmall();
                break;
            default:
                Log.v("SCREEN", "Screen size is Xlarge");
                setXLarge();
        }
    }

    public void setLarge() {
        SearchBox search = (SearchBox) findViewById(R.id.searchbox);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) search.getLayoutParams();
        layoutParams.leftMargin = (2);
        layoutParams.rightMargin = (2);
        layoutParams.topMargin = (1);
        search.setLayoutParams(layoutParams);
        searchBarHandler.setSearchButtonXY(-10, -10);

        changeFilterMenuWidth(250);

        boolean hasSoftKeys = hasSoftKeys(this);

        layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        if (hasSoftKeys) {
            layoutParams.height = (int)(TBheight * 0.08);
            toolbar.setLayoutParams(layoutParams);

            expandConst = 0.27;
            srinkConst = 0.08;
        }
        else {
            layoutParams.height = (int)(TBheight * 0.07);
            toolbar.setLayoutParams(layoutParams);

            expandConst = 0.25;
            srinkConst = 0.07;
        }
    }
    public void setNormal() {
        searchBarHandler.setSearchButtonXY(20, 0);

        boolean hasSoftKeys = hasSoftKeys(this);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        if (hasSoftKeys) {
            layoutParams.height = (int)(TBheight * 0.10);
            toolbar.setLayoutParams(layoutParams);
            expandConst = 0.38;
            srinkConst = 0.10;
        }
        else {
            layoutParams.height = (int)(TBheight * 0.09);
            toolbar.setLayoutParams(layoutParams);
            expandConst = 0.35;
            srinkConst = 0.09;
        }
    }
    public void setSmall() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height = 42;
        toolbar.setLayoutParams(layoutParams);
        //searchBarHandler.search.setTop(20);
    }
    public void setXLarge() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height = 42;
        toolbar.setLayoutParams(layoutParams);
        //searchBarHandler.search.setTop(20);
    }
    private void changeFilterMenuWidth(int x) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.types_submenu);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.width = (x);
        relativeLayout.setLayoutParams(layoutParams);

        relativeLayout = (RelativeLayout) findViewById(R.id.distances_submenu);
        layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.width = (x);
        relativeLayout.setLayoutParams(layoutParams);

        relativeLayout = (RelativeLayout) findViewById(R.id.prices_submenu);
        layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.width = (x);
        relativeLayout.setLayoutParams(layoutParams);

        relativeLayout = (RelativeLayout) findViewById(R.id.contents_submenu);
        layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.width = (x);
        relativeLayout.setLayoutParams(layoutParams);

        relativeLayout = (RelativeLayout) findViewById(R.id.ratings_submenu);
        layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.width = (x);
        relativeLayout.setLayoutParams(layoutParams);
    }
    public static boolean hasSoftKeys(MainActivity m){
        boolean hasSoftwareKeys = true;

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Display d = m.getWindowManager().getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasSoftwareKeys =  (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        }else{
            boolean hasMenuKey = ViewConfiguration.get(m).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            hasSoftwareKeys = !hasMenuKey && !hasBackKey;
        }
        return hasSoftwareKeys;
    }

    public void setPreviousListItem(TopTensModel item) {
        previousListItem = item;
    }

    public TopTensModel getPreviousListItem() {
        return previousListItem;
    }


    public void startFragment(Fragment fragment, boolean backstack, String tag) {
        this.backstack = backstack;
        frame.setVisibility(View.VISIBLE);

        fragment.setEnterTransition(new Fade().setStartDelay(350));
        fragment.setExitTransition(new Slide(Gravity.BOTTOM));
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame3, fragment, tag);
        fragmentTransaction.commit();
    }

    public void startProductFragment(Fragment fragment, boolean backstack, String tag) {
        this.backstack = backstack;
        frame.setVisibility(View.GONE);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.product_frame, fragment, tag);
        fragmentTransaction.addToBackStack("ProductList");
        fragmentTransaction.commit();
    }

    public void themeChange() {
        finish();
        startActivity(getIntent());
    }

    public Location getLastLocation() {
        return mLastLocation;
    }

    public int getColorPrimaryId() { return colorPrimary_id; }
    public int getColorAccentId() { return colorAccent_id; }
    public int getColorPrimary() { return primaryColor; }
    public int getColorPrimaryDark() { return primaryColorDark; }
    public int getColorAccent() { return accentColor; }
    public int getColorAccentDark() { return accentColorDark; }

    public void callRangeDialog(String title, String units) {
        DHandle.OpenRangeDialog(this, title, units);
    }

    public void callCustommiDialog() {
        DHandle.OpenCustomMileDialog(this);
    }

    public void callProductRefresh(AdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        PLcon.callList(this, FMHandle, mAdapter, swipeRefreshLayout, latitude, longitude);
    }

    public void showFilterMenu() {
        if (!FMHandle.menuOpen) {
            item.setIcon(R.drawable.filter);
            FMHandle.showFilterMenu();

            AnimateToolbarHandler anim = new AnimateToolbarHandler(toolbar, (int)(TBheight * expandConst));
            anim.setDuration(350);
            toolbar.startAnimation(anim);
        }
    }

    public void hideFilterMenu() {
        if(FMHandle.menuOpen){
            item.setIcon(R.drawable.filter_outline);
            FMHandle.hideFilterMenu();

            AnimateToolbarHandler anim = new AnimateToolbarHandler(toolbar, (int) (TBheight * srinkConst));
            anim.setDuration(500);
            toolbar.startAnimation(anim);
        }
    }

    //Data Handlers//
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        item = menu.findItem(R.id.action_filter);

        return true;
    }

    ////////LOCATION SERVICES////////////
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(30000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        //initial query to populate list
        if (firstStartRefresh) {
            Nav.topTensFragment.askForProductListrefresh(Nav.topTensFragment.getmAdapter(), Nav.topTensFragment.getSwipeRefreshLayout());
            firstStartRefresh = false;
        }
    }

    /**
     * Method to verify google play services on the device
     * */
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.
    }
    ////////////////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.action_search) {
            searchBarHandler.openSearch(title);
            return true;
        }
        if (id == R.id.action_filter) {
            if (FMHandle.menuOpen) {
                hideFilterMenu();
            } else {
                showFilterMenu();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //Recieves the result from Camera Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SCANNER_CODE_REQUEST) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                Log.v("CAM RESULT", data.getExtras().getString("RESULT"));
                upcFPC.callProduct(this, data.getExtras().getString("RESULT"), latitude, longitude);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
        //connect universal sharedpreference edit to ed
        SharedPreferences.Editor ed = mPrefs.edit();
        //store all color states into universal sharedpreference
        ed.putInt("COLOR_STATE", colorPrimary_id);
        ed.putInt("COLOR_ACCENT_STATE", colorAccent_id);
        ed.putInt("PRIMARY_STATE", primaryColor);
        ed.putInt("PRIMARY_DARK_STATE", primaryColorDark);
        ed.putInt("ACCENT_STATE", accentColor);
        ed.putInt("ACCENT_DARK_STATE", accentColorDark);
        ed.putFloat("LAST_LATITUDE", (float) latitude);
        ed.putFloat("LAST_LONGITUDE", (float) longitude);

        FMHandle.saveSharedPref(ed);
        //apply changes
        ed.apply();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
        hideFilterMenu();
    }

    @Override
    public void onBackPressed() {
        /*if (backstackSearch) {
            searchBarHandler.closeSearch();
        } else*/ if (backstack) {
            MenuItem toptens = Nav.navigationView.getMenu().getItem(0);
            Nav.delay = 0;
            Nav.NavListener.onNavigationItemSelected(toptens);
            Nav.setMenuItenChecked(0);
            Nav.delay = 320;
            this.backstack = false;
        } else {
            super.onBackPressed();
        }
    }

    //Override ThemeHandler.OnDataPass interface functions
    @Override
    public void PassColorPrimaryId(int primary_id) {
        colorPrimary_id = primary_id;
    }

    @Override
    public void PassColorAccentId(int accent_id) {
        colorAccent_id = accent_id;
    }

    @Override
    public void PassColorPrimary(int colorPrimary, int colorPrimaryDark) {
        primaryColor = colorPrimary;
        primaryColorDark = colorPrimaryDark;
    }

    @Override
    public void PassColorAccent(int colorAccent, int colorAccentDark) {
        accentColor = colorAccent;
        accentColorDark = colorAccentDark;
    }

    @Override
    public void ApplyTheme () {
        themeChange();
    }

    @Override
    public int AskForColorPrimaryId () { return colorPrimary_id; }

    @Override
    public int AskForColorAccentId () { return colorAccent_id; }

    @Override
    public int AskForColorPrimary () {
        return primaryColor;
    }

    @Override
    public int AskForColorPrimaryDark () {
        return primaryColorDark;
    }

    @Override
    public int AskForColorAccent () {
        return accentColor;
    }

    @Override
    public int AskForColorAccentDark () {
        return accentColorDark;
    }

    @Override
    public void CloseMenu () { hideFilterMenu(); }

    @Override
    public void AskToHideFilterButtons () {
        if (item != null) item.setVisible(false);
        filterButtonVis = false;
        hideFilterMenu();
    }

    @Override
    public void AskForProductListrefresh (AdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        callProductRefresh(mAdapter, swipeRefreshLayout);
    }

    @Override
    public List<TopTensModel> AskForProductList() {
        return PLcon.getProductList();
    }

    @Override
    public void AskToShowFilterButtons () {
        if (item != null) item.setVisible(true);
        filterButtonVis = true;
    }
}
