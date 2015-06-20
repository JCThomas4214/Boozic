package comjason_lewisg.httpsgithub.boozic;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;

import Handlers.DialogHandler;
import Handlers.FloatingActionButtonHandler;
import Handlers.NavigationDrawerHandler;
import Handlers.SearchBarHandler;
import Handlers.SearchSuggestHandler;

public class MainActivity extends AppCompatActivity {

    private ImageView refresh;
    private Animation rotation;
    public NavigationDrawerHandler Nav;
    public Toolbar toolbar;
    public DialogHandler DHandle;
    public SearchBarHandler searchBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates a FAB for the bottom right corner of the main screen
        //FloatingActionButtonHandler FButton = new FloatingActionButtonHandler();
        //FButton.connectButton(this);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creates a Navigation Drawer
        //When you swipe from the left
        Nav = new NavigationDrawerHandler();
        Nav.connectDrawer(this,toolbar);

        DHandle = new DialogHandler();

        FrameLayout layout_MainMenu = (FrameLayout) findViewById( R.id.frame2);
        layout_MainMenu.getForeground().setAlpha(0);

        SearchBox search = (SearchBox) findViewById(R.id.searchbox);
        searchBarHandler = new SearchBarHandler();
        searchBarHandler.setActivity(this);
        //search.enableVoiceRecognition(this);
        SearchSuggestHandler searchSuggestHandler = new SearchSuggestHandler();
        search.setSearchables(searchSuggestHandler.setSuggest(this));

        FloatingActionButtonHandler FAB = new FloatingActionButtonHandler();
        FAB.setActivity(this);
    }


    //Data Handlers//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_refresh);

        item.setActionView(R.layout.nav_refresh);
        refresh = (ImageView) item.getActionView().findViewById(R.id.refreshButton);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);

        refresh.setOnClickListener(new View.OnClickListener() {
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
            refresh.startAnimation(rotation);
            return true;
        }
        if (id == R.id.action_search) {
            searchBarHandler.openSearch();
            return true;
        }
        if (id == android.R.id.home) {
            searchBarHandler.closeSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    ////////////////
}
