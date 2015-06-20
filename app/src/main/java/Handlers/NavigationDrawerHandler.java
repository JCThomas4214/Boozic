package Handlers;

import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import comjason_lewisg.httpsgithub.boozic.AboutUsActivity;
import comjason_lewisg.httpsgithub.boozic.LegalActivity;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;
import comjason_lewisg.httpsgithub.boozic.SettingsActivity;

public class NavigationDrawerHandler extends MainActivity {
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    protected void onCreate(){

    }
    public void connectDrawer(final MainActivity m, final Toolbar t) {
        //Initializing NavigationView
        navigationView = (NavigationView) m.findViewById(R.id.navigation_view);

        //set the default checked item
        //In this case the first page opened, TOP TENS
        navigationView.getMenu().getItem(0).setChecked(true);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Handler handler = new Handler();
                //Checking if the item is in checked state or not, if not make it in checked state
                if(!menuItem.isChecked()) menuItem.setChecked(true);
                else menuItem.setChecked(false);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    /*
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                        Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        return true;
                    */

                    // For rest of the options we just show a toast on click
                    case R.id.lists:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(m.getApplicationContext(),"Top Tens Selected",Toast.LENGTH_SHORT).show();
                            }
                        }, 275);

                        return true;
                    case R.id.heart:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(m.getApplicationContext(),"Favorites Selected",Toast.LENGTH_SHORT).show();
                            }
                        }, 275);

                        return true;
                    case R.id.edit:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(m.getApplicationContext(),"Theme Selected",Toast.LENGTH_SHORT).show();
                            }
                        }, 275);

                        return true;
                    case R.id.cash:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(m.getApplicationContext(),"Spending Selected",Toast.LENGTH_SHORT).show();
                            }
                        }, 275);

                        return true;
                    case R.id.settings:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(m, SettingsActivity.class);
                                m.startActivity(i);
                            }
                        }, 275);

                        return true;
                    case R.id.about:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(m, AboutUsActivity.class);
                                m.startActivity(i);
                            }
                        }, 275);

                        return true;
                    case R.id.feedback:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                m.DHandle.OpenFeedbackDialog(m);
                            }
                        }, 275);

                        return true;
                    case R.id.legal:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(m, LegalActivity.class);
                                m.startActivity(i);
                            }
                        }, 275);

                        return true;
                    default:

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(m.getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                            }
                        }, 275);

                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) m.findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(m,drawerLayout,t,R.string.openDrawer, R.string.closeDrawer){
            final FloatingActionMenu menu = (FloatingActionMenu) m.findViewById(R.id.fabmenu);
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                menu.close(true);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
}
