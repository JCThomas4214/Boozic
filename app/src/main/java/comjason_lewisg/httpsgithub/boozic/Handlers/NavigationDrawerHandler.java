package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import comjason_lewisg.httpsgithub.boozic.Fragments.FavoritesFragment;
import comjason_lewisg.httpsgithub.boozic.Fragments.ThemeFragment;
import comjason_lewisg.httpsgithub.boozic.Fragments.TopTensFragment;
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
    public ThemeFragment themeFragment;

    private MainActivity m;

    protected void onCreate(){}

    public NavigationDrawerHandler(MainActivity m, Toolbar t) {
        connectDrawer(m,t);
    }

    public void connectDrawer(final MainActivity m, final Toolbar t) {
        //set initial title to Boozic
        title = m.getString(R.string.app_name);
        titleIndex = 0;
        delay = 320;
        this.m = m;

        topTensFragment = new TopTensFragment();

        favoritesFragment = new FavoritesFragment();

        /*spendingFragment = new SpendingFragment();*/

        themeFragment = new ThemeFragment();

        int screenSize = m.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                break;
            default:
        }

        m.startFragment(topTensFragment, false);

        //Initializing NavigationView
        navigationView = (NavigationView) m.findViewById(R.id.navigation_view);
        setNavTheme();

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
                /*m.FBhandle.closeMenu();*/
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public NavigationView.OnNavigationItemSelectedListener NavListener = new NavigationView.OnNavigationItemSelectedListener() {

        // This method will trigger on item Click of navigation menu
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            Handler handler = new Handler();

            //Closing drawer on item click
            drawerLayout.closeDrawers();

            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()){
                // For rest of the options we just show a toast on click
                case R.id.lists:
                    m.title.setText("Boozic");
                    title = (String) m.title.getText();
                    titleIndex = 0;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.startFragment(topTensFragment, false);
                        }
                    }, delay);
                    return true;
                case R.id.heart:
                    m.title.setText("Favorites");
                    title = (String) m.title.getText();
                    titleIndex = 1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.startFragment(favoritesFragment, true);
                        }
                    }, delay);
                    return true;
                case R.id.edit:
                    m.title.setText("Themes");
                    title = (String) m.title.getText();
                    titleIndex = 3;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.startFragment(themeFragment, true);
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

                    //link to facebook page
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //put about
                        }
                    }, delay);

                    return true;
                case R.id.feedback:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.DHandle.OpenFeedbackDialog(m);
                        }
                    }, delay);

                    return true;
                case R.id.legal:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m.DHandle.OpenLegalDialog(m);
                        }
                    }, delay);

                    return true;
                default:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(m.getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }, delay);

                    return true;

            }
        }
    };

    private void setNavTheme() {
        ColorStateList cList;

        int navUnchecked = ContextCompat.getColor(m.getApplicationContext(), R.color.NavUnchecked);
        int navUncheckedText = ContextCompat.getColor(m.getApplicationContext(), R.color.NavUncheckedText);

        int cPrimary = ContextCompat.getColor(m.getApplicationContext(), R.color.ColorPrimary);
        int cPrimary2 = ContextCompat.getColor(m.getApplicationContext(), R.color.ColorPrimary2);
        int cPrimary3 = ContextCompat.getColor(m.getApplicationContext(), R.color.ColorPrimary3);
        int cPrimary4 = ContextCompat.getColor(m.getApplicationContext(), R.color.ColorPrimary4);
        int cPrimary5 = ContextCompat.getColor(m.getApplicationContext(), R.color.ColorPrimary5);

        switch (m.getColorPrimaryId()) {
            case 1:
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUnchecked,
                                cPrimary
                        }
                );
                navigationView.setItemIconTintList(cList);
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked},
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUncheckedText,
                                cPrimary
                        }
                );
                navigationView.setItemTextColor(cList);
                break;
            case 2:
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUnchecked,
                                cPrimary2
                        }
                );
                navigationView.setItemIconTintList(cList);
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUncheckedText,
                                cPrimary2
                        }
                );
                navigationView.setItemTextColor(cList);
                break;
            case 3:
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUnchecked,
                                cPrimary3
                        }
                );
                navigationView.setItemIconTintList(cList);
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUncheckedText,
                                cPrimary3
                        }
                );
                navigationView.setItemTextColor(cList);
                break;
            case 4:
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUnchecked,
                                cPrimary4
                        }
                );
                navigationView.setItemIconTintList(cList);
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUncheckedText,
                                cPrimary4
                        }
                );
                navigationView.setItemTextColor(cList);
                break;
            case 5:
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUnchecked,
                                cPrimary5
                        }
                );
                navigationView.setItemIconTintList(cList);
                cList = new ColorStateList(
                        new int[][]{
                                new int[] {-android.R.attr.state_checked}, // enabled
                                new int[] {android.R.attr.state_checked}
                        },
                        new int[] {
                                navUncheckedText,
                                cPrimary5
                        }
                );
                navigationView.setItemTextColor(cList);
                break;
        }
    }

    public void setMenuItenChecked(int index) {
        navigationView.getMenu().getItem(index).setChecked(true);
    }
}
