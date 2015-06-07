package comjason_lewisg.httpsgithub.boozic;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    String TITLES[] = {"Favorites","Theme","Spending","Settings","About Us"};
    int ICONS[] = { R.drawable.ic_action_heart, R.drawable.ic_action_edit, R.drawable.ic_action_cash, R.drawable.ic_action_settings, R.drawable.ic_action_about};

    String NAME = "Jason Lewis";
    String EMAIL = "jason.lewisg@gmail.com";
    int PROFILE = R.drawable.download_20141015_232430;

    private Toolbar toolbar;                        // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                     // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                  // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;      // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                            // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;            // Declaring Action Bar Drawer Toggle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
            setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

            mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);

            mRecyclerView.setAdapter(mAdapter);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
            //should be R.string.navigation_drawer_open and R.string.navigation_drawer_close
            mDrawerToggle = new ActionBarDrawerToggle(this,Drawer, toolbar,R.string.abc_action_bar_home_description, R.string.abc_action_bar_up_description) {

                @Override
                public void onDrawerOpened(View drawView) {
                    super.onDrawerOpened(drawView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };//Drawer toggle object

            Drawer.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
