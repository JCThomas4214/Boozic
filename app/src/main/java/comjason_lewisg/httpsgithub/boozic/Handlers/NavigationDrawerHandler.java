package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import comjason_lewisg.httpsgithub.boozic.Fragments.FavoritesFragment;
import comjason_lewisg.httpsgithub.boozic.Fragments.SpendingFragment;
import comjason_lewisg.httpsgithub.boozic.Fragments.ThemeFragment;
import comjason_lewisg.httpsgithub.boozic.Fragments.TopTensFragment;
import comjason_lewisg.httpsgithub.boozic.LegalActivity;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;
import comjason_lewisg.httpsgithub.boozic.SettingsActivity;

public class NavigationDrawerHandler {
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public String title;
    public int titleIndex;
    public int delay;

    public TopTensFragment topTensFragment;
    public FavoritesFragment favoritesFragment;
    public SpendingFragment spendingFragment;
    public ThemeFragment themeFragment;
    public android.support.v4.app.FragmentTransaction fragmentTransaction;

    private MainActivity m;

    protected void onCreate(){

    }
    public void connectDrawer(final MainActivity m, final Toolbar t) {
        //set initial title to Boozic
        title = (String) t.getTitle();
        titleIndex = 0;
        delay = 255;
        this.m = m;

        topTensFragment = new TopTensFragment();

        favoritesFragment = new FavoritesFragment();

        spendingFragment = new SpendingFragment();

        themeFragment = new ThemeFragment();

        m.startFragment(fragmentTransaction, topTensFragment, false);

        //Initializing NavigationView
        navigationView = (NavigationView) m.findViewById(R.id.navigation_view);

        //set the default checked item
        //In this case the first page opened, TOP TENS
        navigationView.getMenu().getItem(0).setChecked(true);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(NavListener);

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) m.findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(m,drawerLayout,t,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public NavigationView.OnNavigationItemSelectedListener NavListener = new NavigationView.OnNavigationItemSelectedListener() {

        // This method will trigger on item Click of navigation menu
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            Handler handler = new Handler();
            //Checking if the item is in checked state or not, if not make it in checked state
            navigationView.getMenu().getItem(titleIndex).setCheckable(true);
            if(!menuItem.isChecked()) menuItem.setChecked(true);
            else menuItem.setChecked(false);

            //Closing drawer on item click
            drawerLayout.closeDrawers();

            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()){
                // For rest of the options we just show a toast on click
                case R.id.lists:
                    m.toolbar.setTitle("Boozic");
                    title = (String) m.toolbar.getTitle();
                    titleIndex = 0;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.startFragment(fragmentTransaction, topTensFragment, false);
                        }
                    }, delay);
                    return true;
                case R.id.heart:
                    m.toolbar.setTitle("Favorites");
                    title = (String) m.toolbar.getTitle();
                    titleIndex = 1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.startFragment(fragmentTransaction, favoritesFragment, true);
                        }
                    }, delay);
                    return true;
                case R.id.cash:
                    m.toolbar.setTitle("Spending");
                    title = (String) m.toolbar.getTitle();
                    titleIndex = 2;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.startFragment(fragmentTransaction, spendingFragment, true);
                        }
                    }, delay);
                    return true;
                case R.id.edit:
                    m.toolbar.setTitle("Themes");
                    title = (String) m.toolbar.getTitle();
                    titleIndex = 3;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.startFragment(fragmentTransaction, themeFragment, true);
                        }
                    }, delay);

                    return true;
                case R.id.settings:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(m, SettingsActivity.class);
                            m.startActivity(i);
                        }
                    }, delay);

                    return true;
                case R.id.about:

                    return true;
                case R.id.feedback:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.DHandle.OpenFeedbackDialog(m, m.getColorAccentId());
                        }
                    }, delay);

                    return true;
                case R.id.legal:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(m, LegalActivity.class);
                            m.startActivity(i);
                        }
                    }, delay);

                    return true;
                default:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(m.getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        }
                    }, delay);

                    return true;

            }
        }
    };
}
