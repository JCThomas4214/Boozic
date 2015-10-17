package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import comjason_lewisg.httpsgithub.boozic.R;
import io.codetail.animation.ReverseInterpolator;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class ProductSearchBarHandler {

    private AppCompatActivity m;
    public SearchSuggestHandler searchSuggestHandler;
    public SearchBox search;
    private int dist;

    public void onCreate() {
    }
    public void setActivity(AppCompatActivity main, Toolbar t) {
        m = main;

        SearchBox search = (SearchBox) m.findViewById(R.id.product_searchbox);
        search.setY(m.findViewById(R.id.product_searchbox).getHeight() / 2);
        searchSuggestHandler = new SearchSuggestHandler();
        searchSuggestHandler.initList();
    }

    public void openSearch(Toolbar t) {

        //connect Searchbox to search variable
        search = (SearchBox) m.findViewById(R.id.product_searchbox);
        //Turn buttons off
        m.findViewById(R.id.action_product_search).setEnabled(false);

        revealFromMenuItem(R.id.action_product_search, m, search);

        //logo is the initial String that's shown during reveal animation
        //set search bar logo to current toolbar title
        search.setLogoText((String)t.getTitle());

        //connect to searchbar edittext xml to change hint
        EditText text = (EditText) search.findViewById(R.id.search);
        text.setHint("Search Boozic");

        search.setSearchListener(searchListener);
    }

    public SearchBox.SearchListener searchListener = new SearchBox.SearchListener() {

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

            //TODO: we need to connect to the backend here and query for product search to populate product activity

            //SearchSuggestHandler handles suggest dropdown
            //Only allow 4 previous searches to be shown
            //search.setSearchables(searchSuggestHandler.addSuggest(searchTerm, m));
        }

        @Override
        public void onSearchCleared() {

        }

    };

    public void closeSearch() {

        //connect search bar to search variable
        SearchBox search = (SearchBox) m.findViewById(R.id.product_searchbox);

        hideCircularly(m);

        //Turn buttons back on and unlock Nav drawer
        m.findViewById(R.id.action_product_search).setEnabled(true);

        //if no characters inputted or erased
        if(search.getSearchText().isEmpty()) {

        }
    }

    public void revealFromMenuItem(int id, Activity activity, SearchBox s) {
        m.findViewById(R.id.product_searchbox).setVisibility(View.VISIBLE);
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
        int cx = m.findViewById(R.id.product_toolbar).getWidth() - (m.findViewById(R.id.action_product_search).getWidth() / 2) + 20;
        int cy = m.findViewById(R.id.product_toolbar).getHeight() / 2 + dist;

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

    private void hideCircularly(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        final FrameLayout layout = (FrameLayout) activity.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        RelativeLayout root = (RelativeLayout) m.findViewById(R.id.search_root);
        display.getSize(size);
        Resources r = m.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96,
                r.getDisplayMetrics());
        int cx = m.findViewById(R.id.product_toolbar).getWidth() - (m.findViewById(R.id.action_product_search).getWidth() / 2) + 20;
        int cy = m.findViewById(R.id.product_toolbar).getHeight() / 2 + dist;
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
                m.findViewById(R.id.product_searchbox).setVisibility(View.GONE);
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
