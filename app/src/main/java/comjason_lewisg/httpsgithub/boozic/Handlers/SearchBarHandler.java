package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;
import io.codetailps.animation.ReverseInterpolator;
import io.codetailps.animation.SupportAnimator;
import io.codetailps.animation.ViewAnimationUtils;

public class SearchBarHandler {

    private MainActivity m;
    public SearchSuggestHandler searchSuggestHandler;
    public SearchBox search;
    private int dist;
    private int searchButtY;
    private int searchButtX;

    public void onCreate() {}

    public SearchBarHandler(MainActivity m) {
        this.m = m;
        setActivity(m.toolbar);
    }

    public void setActivity(Toolbar t) {
        //Creates a Navigation Drawer
        //When you swipe from the left

        SearchBox search = (SearchBox) m.findViewById(R.id.searchbox);
        search.setY(m.findViewById(R.id.searchbox).getHeight() / 2);
        searchSuggestHandler = new SearchSuggestHandler();
        searchSuggestHandler.initList();
    }

    public void openSearch(TextView title) {
        //Hide the FAB button with animation
        FloatingActionButton menu = (FloatingActionButton) m.findViewById(R.id.fabtop);
        menu.hide(true);

        //connect Searchbox to search variable
        search = (SearchBox) m.findViewById(R.id.searchbox);
        //Turn buttons off

        //circular reveal and hide are determined by the length and width of the toolbar layout
        //if the filter options are present then the toolbar will be longer than typical
        //this adjusts for the difference
        if (m.toolbar.getLayoutParams().height > 300) {
            dist = 20 - (m.toolbar.getLayoutParams().height / 4);
        }
        else {
            dist = 20;
        }
        revealFromMenuItem(R.id.action_search, m, search);

        //logo is the initial String that's shown during reveal animation
        //set search bar logo to current toolbar title
        search.setLogoText((String) title.getText());

        //connect to searchbar edittext xml to change hint
        EditText text = (EditText) search.findViewById(R.id.search);
        text.setHint("Search Boozic");

        search.setSearchListener(searchListener);
        Handler handler = new Handler();

        //Lock and hide Navagation drawer and Nav drawer icon
        m.Nav.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //set a delay to remove navigation drawer burger icon
        //this makes the icon unclickable and the user doesn't see it happen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                m.Nav.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            }
        }, 400);
    }

    public SearchBox.SearchListener searchListener = new SearchBox.SearchListener() {

        @Override
        public void onSearchOpened() {
            // Use this to tint the screen
            m.backstackSearch = true;
            m.findViewById(R.id.action_search).setEnabled(false);
            if (m.filterButtonVis) m.findViewById(R.id.action_filter).setEnabled(false);
            m.hideFilterMenu();
        }

        @Override
        public void onSearchClosed() {
            // Use this to un-tint the screen
            m.backstackSearch = false;
            m.findViewById(R.id.action_search).setEnabled(true);
            if (m.filterButtonVis) m.findViewById(R.id.action_filter).setEnabled(true);
            closeSearch();
        }

        @Override
        public void onSearchTermChanged(String term) {
            // React to the search term changing
            // Called after it has updated results
        }

        @Override
        public void onSearch(String searchTerm) {
            search.setLogoText(searchTerm);
            m.Nav.navigationView.getMenu().getItem(m.Nav.titleIndex).setCheckable(false);
            //m.title.setText(searchTerm);

            //TODO: we need to connect to the backend here and query for product search to populate product activity

            //SearchSuggestHandler handles suggest dropdown
            //Only allow 4 previous searches to be shown
            //search.setSearchables(searchSuggestHandler.addSuggest(searchTerm, m));
        }

        @Override
        public void onResultClick(SearchResult result) {
            //React to a result being clicked
        }

        @Override
        public void onSearchCleared() {

        }

    };

    public void closeSearch() {
        //Turn buttons back on and unlock Nav drawer
        m.Nav.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        m.Nav.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        final FloatingActionButton menu = (FloatingActionButton) m.findViewById(R.id.fabtop);
        Handler handler = new Handler();
        //set a delay for FAB button reveal so users can see it
        //will reveal below keyboard if not
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu.show(true);
            }
        }, 600);

        hideCircularly();

        //if no characters inputted or erased
        if(search.getSearchText().isEmpty()) {
            //set Toolbar title to previously selected content and set true for Nav drawer congruency
            m.Nav.navigationView.getMenu().getItem(m.Nav.titleIndex).setCheckable(true);
            m.title.setText(m.Nav.title);
            search.setLogoText(m.Nav.title);
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
                //animation function
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
        int cx = m.findViewById(R.id.toolbar).getWidth() - (m.findViewById(R.id.action_search).getWidth() / 2) + searchButtX;
        int cy = m.findViewById(R.id.toolbar).getHeight() / 2 + dist + searchButtY;

        int finalRadius = (int) Math.max(layout.getWidth(), px);

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
                root, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(450);
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

    private void hideCircularly(){
        Display display = m.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        final FrameLayout layout = (FrameLayout) m.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        RelativeLayout root = (RelativeLayout) m.findViewById(R.id.search_root);
        display.getSize(size);
        Resources r = m.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96,
                r.getDisplayMetrics());
        int cx = m.findViewById(R.id.toolbar).getWidth() - (m.findViewById(R.id.action_search).getWidth() / 2) + searchButtX;
        int cy = m.findViewById(R.id.toolbar).getHeight() / 2 + dist + searchButtY;
        int finalRadius = (int) Math.max(layout.getWidth()*1.5, px);

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
                root, cx, cy, 0, finalRadius);
        animator.setInterpolator(new ReverseInterpolator());
        animator.setDuration(500);
        animator.start();
        animator.addListener(new SupportAnimator.AnimatorListener() {

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

    public void setSearchButtonXY(int x, int y) {
        searchButtX = x;
        searchButtY = y;
    }
}
