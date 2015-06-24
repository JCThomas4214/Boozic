package comjason_lewisg.httpsgithub.boozic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import comjason_lewisg.httpsgithub.boozic.Fragments.ThemeFragment;
import comjason_lewisg.httpsgithub.boozic.Handlers.DialogHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.FloatingActionButtonHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.RefreshHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.SearchBarHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.ThemeHandler;

public class MainActivity extends AppCompatActivity implements ThemeFragment.OnDataPass {

    public Toolbar toolbar;
    public DialogHandler DHandle;
    public SearchBarHandler searchBarHandler;
    public RefreshHandler refreshHandler;
    public ThemeHandler themeHandler;
    public FloatingActionButtonHandler FAB;

    static final int SCANNER_CODE_REQUEST = 0;

    static final int COLOR_STATE = 0;
    static final int COLOR_ACCENT_STATE = 0;
    static final int PRIMARY_STATE = 0;
    static final int PRIMARY_DARK_STATE = 0;
    static final int ACCENT_STATE = 0;
    static final int ACCENT_DARK_STATE = 0;

    private int colorPrimary_id;
    private int colorAccent_id;

    private int primaryColor;
    private int primaryColorDark;
    private int accentColor;
    private int accentColorDark;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("COLOR_STATE", MODE_MULTI_PROCESS);
        //when resume, pull saves states for each button
        colorPrimary_id = mPrefs.getInt("COLOR_STATE", COLOR_STATE);
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

        setContentView(R.layout.activity_main);

        //Creates a FAB for the bottom right corner of the main screen
        FAB = new FloatingActionButtonHandler();
        FAB.setActivity(this);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create a Dialog Handler for Feedback
        DHandle = new DialogHandler();

        //Create a new RefreshHandler for refresh button
        refreshHandler = new RefreshHandler();

        //connect to search bar and create new search handler
        searchBarHandler = new SearchBarHandler();
        searchBarHandler.setActivity(this, toolbar);
        //search.enableVoiceRecognition(this);

        Log.v("STATE", "onCreate color id = " + colorPrimary_id);
        themeHandler = new ThemeHandler();

    }

    public void themeChange() {
        finish();
        startActivity(getIntent());
    }

    public int getColorAccentId() {
        return colorAccent_id;
    }

    //Data Handlers//
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = refreshHandler.setRefreshButton(this, menu);
        refreshHandler.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refreshHandler.startAnim();
            return true;
        }
        if (id == R.id.action_search) {
            searchBarHandler.openSearch(toolbar);
            return true;
        }
        if (id == android.R.id.home) {
            searchBarHandler.closeSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    ////////////////
    //Recieves the result from Camera Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SCANNER_CODE_REQUEST) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                Log.v("RESULT", data.getExtras().getString("RESULT"));
                //TODO PING LOCATION HERE
                //TODO SEND TO BACKEND HERE
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //pull the shared preference
        mPrefs = getSharedPreferences("COLOR_STATE", MODE_MULTI_PROCESS);
        //when resume, pull saves states for each button
        colorPrimary_id = mPrefs.getInt("COLOR_STATE", COLOR_STATE);
        colorAccent_id = mPrefs.getInt("COLOR_ACCENT_STATE", COLOR_ACCENT_STATE);
        Log.v("STATE", "in resume, color id = " + colorPrimary_id);

        primaryColor = mPrefs.getInt("PRIMARY_STATE", PRIMARY_STATE);
        primaryColorDark = mPrefs.getInt("PRIMARY_DARK_STATE", PRIMARY_DARK_STATE);
        accentColor = mPrefs.getInt("ACCENT_STATE", ACCENT_STATE);
        accentColorDark = mPrefs.getInt("ACCENT_DARK_STATE", ACCENT_DARK_STATE);

        findViewById(R.id.toolbar).setBackgroundColor(primaryColor);
        FAB.menu.setMenuButtonColorNormal(primaryColor);
        FAB.menu.setMenuButtonColorPressed(primaryColorDark);
        FAB.menuButton.setColorNormal(primaryColor);

        FAB.fav1.setColorNormal(accentColor);
        FAB.fav1.setColorPressed(accentColorDark);
        FAB.fav2.setColorNormal(accentColor);
        FAB.fav2.setColorPressed(accentColorDark);
        FAB.fav3.setColorNormal(accentColor);
        FAB.fav3.setColorPressed(accentColorDark);
    }

    @Override
    public void onPause() {
        super.onPause();
        //connect universal sharedpreference edit to ed
        SharedPreferences.Editor ed = mPrefs.edit();
        //store all button states into universal sharedpreference
        Log.v("STATE", "in pause, color id = " + colorPrimary_id);
        ed.putInt("COLOR_STATE", colorPrimary_id);
        ed.putInt("COLOR_ACCENT_STATE", colorAccent_id);

        ed.putInt("PRIMARY_STATE", primaryColor);
        ed.putInt("PRIMARY_DARK_STATE", primaryColorDark);
        ed.putInt("ACCENT_STATE", accentColor);
        ed.putInt("ACCENT_DARK_STATE", accentColorDark);
        //apply changes
        ed.apply();
    }

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
}
