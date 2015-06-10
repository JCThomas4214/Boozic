package comjason_lewisg.httpsgithub.boozic;

/**
 * Created by Jason on 6/8/2015.
 */

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class NavigationDrawer extends MainActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private int previousItemGroupId;

    protected void onCreate(){}
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
                        Toast.makeText(m.getApplicationContext(),"Top Tens Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.heart:
                        Toast.makeText(m.getApplicationContext(),"Favorites Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.edit:
                        Toast.makeText(m.getApplicationContext(),"Theme Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.cash:
                        Toast.makeText(m.getApplicationContext(),"Spending Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.settings:
                        Toast.makeText(m.getApplicationContext(),"Settings Selected",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(m, SettingsActivity.class);
                        m.startActivity(i);
                        return true;
                    case R.id.about:
                        Toast.makeText(m.getApplicationContext(),"About Us Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.feedback:
                        Toast.makeText(m.getApplicationContext(),"Help and Feedback Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(m.getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) m.findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(m,drawerLayout,t,R.string.openDrawer, R.string.closeDrawer){

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
}
