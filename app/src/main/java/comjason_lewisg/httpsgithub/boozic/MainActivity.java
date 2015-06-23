package comjason_lewisg.httpsgithub.boozic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import comjason_lewisg.httpsgithub.boozic.Handlers.DialogHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.FloatingActionButtonHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.RefreshHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.SearchBarHandler;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public DialogHandler DHandle;
    public SearchBarHandler searchBarHandler;
    public RefreshHandler refreshHandler;

    static final int SCANNER_CODE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates a FAB for the bottom right corner of the main screen
        FloatingActionButtonHandler FAB = new FloatingActionButtonHandler();
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
}
