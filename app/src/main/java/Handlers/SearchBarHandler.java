package Handlers;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.quinny898.library.persistentsearch.SearchBox;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;
import io.codetail.animation.ReverseInterpolator;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class SearchBarHandler {

    private MainActivity m;
    public NavigationDrawerHandler Nav;

    public void onCreate() {

    }
    public void setActivity(MainActivity main, Toolbar t) {
        m = main;
        //Creates a Navigation Drawer
        //When you swipe from the left
        Nav = new NavigationDrawerHandler();
        Nav.connectDrawer(m,t);

        SearchBox search = (SearchBox) m.findViewById(R.id.searchbox);
        SearchSuggestHandler searchSuggestHandler = new SearchSuggestHandler();
        search.setSearchables(searchSuggestHandler.setSuggest(m));
    }

    public void openSearch() {

        //Hide the FAB button with animation
        FloatingActionMenu menu = (FloatingActionMenu) m.findViewById(R.id.fabmenu);
        menu.hideMenuButton(true);

        //define Searchbox constant
        SearchBox search = (SearchBox) m.findViewById(R.id.searchbox);
        //Turn buttons off
        m.findViewById(R.id.action_search).setEnabled(false);
        m.findViewById(R.id.action_refresh).setEnabled(false);

        //m.toolbar.setClickable(false);

        revealFromMenuItem(R.id.action_search, m, search);

        search.setLogoText("");
        EditText text = (EditText) search.findViewById(R.id.search);
        text.setHint("Search Boozic");
        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
                // Hamburger has been clicked
                Toast.makeText(m, "Menu click",
                        Toast.LENGTH_LONG).show();
            }

        });
        search.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen

            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch();
            }

            @Override
            public void onSearchTermChanged() {
                // React to the search term changing
                // Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
                Nav.navigationView.getMenu().getItem(Nav.titleIndex).setCheckable(false);
                m.toolbar.setTitle(searchTerm);
            }

            @Override
            public void onSearchCleared() {

            }

        });
        FrameLayout layout_MainMenu = (FrameLayout) m.findViewById(R.id.frame2);
        layout_MainMenu.getForeground().setAlpha(180);

        //Lock and hide Navagation drawer and Nav drawer icon
        Nav.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Nav.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            }
        }, 400);
    }

    public void closeSearch() {
        final FloatingActionMenu menu = (FloatingActionMenu) m.findViewById(R.id.fabmenu);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu.showMenuButton(true);
            }
        }, 600);

        SearchBox search = (SearchBox) m.findViewById(R.id.searchbox);

        hideCircularly(m);

        FrameLayout layout_MainMenu = (FrameLayout) m.findViewById(R.id.frame2);
        layout_MainMenu.getForeground().setAlpha(0);
        Nav.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //Turn buttons back on
        m.findViewById(R.id.action_search).setEnabled(true);
        m.findViewById(R.id.action_refresh).setEnabled(true);
        Nav.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        //if no characters inputted or erased
        if(search.getSearchText().isEmpty()) {
            //set Toolbar title to previously selected content and set true for Nav drawer congruency
            Nav.navigationView.getMenu().getItem(Nav.titleIndex).setCheckable(true);
            m.toolbar.setTitle(Nav.title);
        }
    }

    public void revealFromMenuItem(int id, Activity activity, SearchBox s) {
        m.findViewById(R.id.searchbox).setVisibility(View.VISIBLE);
        View menuButton = activity.findViewById(id);
        if (menuButton != null) {
            FrameLayout layout = (FrameLayout) activity.getWindow().getDecorView()
                    .findViewById(android.R.id.content);
            if (layout.findViewWithTag("searchBox") == null) {
                int[] location = new int[2];
                menuButton.getLocationInWindow(location);
                revealFrom(activity, s);
            }
        }
    }

    private void revealFrom(Activity a, final SearchBox s) {
        FrameLayout layout = (FrameLayout) a.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        RelativeLayout root = (RelativeLayout) s.findViewById(R.id.search_root);
        Resources r = m.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96,
                r.getDisplayMetrics());
        int cx = m.findViewById(R.id.toolbar).getWidth() - (m.findViewById(R.id.action_refresh).getWidth() + (m.findViewById(R.id.action_search).getWidth() / 2)) + 20;
        int cy = m.findViewById(R.id.toolbar).getHeight() / 2 + 20;

        int finalRadius = (int) Math.max(layout.getWidth(), px);

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
                root, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.addListener(new SupportAnimator.AnimatorListener() {

            @Override
            public void onAnimationCancel() {

            }

            @Override
            public void onAnimationEnd() {
                s.toggleSearch();
            }

            @Override
            public void onAnimationRepeat() {

            }

            @Override
            public void onAnimationStart() {

            }

        });
        animator.start();
    }

    public void hideCircularly(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        final FrameLayout layout = (FrameLayout) activity.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        RelativeLayout root = (RelativeLayout) m.findViewById(R.id.search_root);
        display.getSize(size);
        Resources r = m.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96,
                r.getDisplayMetrics());
        int cx = m.findViewById(R.id.toolbar).getWidth() - (m.findViewById(R.id.action_refresh).getWidth() + (m.findViewById(R.id.action_search).getWidth() / 2)) + 20;
        int cy = m.findViewById(R.id.toolbar).getHeight() / 2 + 20;
        int finalRadius = (int) Math.max(layout.getWidth()*1.5, px);

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
                root, cx, cy, 0, finalRadius);
        animator.setInterpolator(new ReverseInterpolator());
        animator.setDuration(500);
        animator.start();
        animator.addListener(new SupportAnimator.AnimatorListener(){

            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
                m.findViewById(R.id.searchbox).setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel() {

            }

            @Override
            public void onAnimationRepeat() {

            }

        });
    }
}
