package comjason_lewisg.httpsgithub.boozic;

/**
 * Created by Jason on 6/7/2015.
 */

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SideView {

    String TITLES[] = {"Favorites","Theme","Spending","Settings","About Us"};
    int ICONS[] = { R.drawable.ic_action_heart, R.drawable.ic_action_edit, R.drawable.ic_action_cash, R.drawable.ic_action_settings, R.drawable.ic_action_about};

    String NAME = "Jason Lewis";
    String EMAIL = "jason.lewisg@gmail.com";
    int PROFILE = R.drawable.download_20141015_232430;

    RecyclerView mRecyclerView;                     // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                  // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;      // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                            // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;            // Declaring Action Bar Drawer Toggle


    protected void onCreate(Bundle savedInstanceState) {
    }
    public void connectView (final MainActivity m, final TopBar b) {
        this.mRecyclerView = (RecyclerView) m.findViewById(R.id.RecyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        //mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);
        this.mAdapter = new MyAdapter(this.TITLES,this.ICONS,this.NAME,this.EMAIL,this.PROFILE);

        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mLayoutManager = new LinearLayoutManager(m);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);

        this.Drawer = (DrawerLayout) m.findViewById(R.id.DrawerLayout);
        //should be R.string.navigation_drawer_open and R.string.navigation_drawer_close
        this.mDrawerToggle = new ActionBarDrawerToggle(m,this.Drawer, b.toolbar,R.string.abc_action_bar_home_description, R.string.abc_action_bar_up_description) {
            @Override
            public void onDrawerOpened(View drawView) {
                super.onDrawerOpened(drawView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };//Drawer toggle object

        this.Drawer.setDrawerListener(this.mDrawerToggle);
        this.mDrawerToggle.syncState();

    }
}
