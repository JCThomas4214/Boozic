package comjason_lewisg.httpsgithub.boozic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;

import Handlers.DialogHandler;
//import Handlers.FloatingActionButtonHandler;
import Handlers.NavigationDrawerHandler;
import Handlers.SearchBarHandler;
import Handlers.SearchSuggestHandler;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;


public class MainActivity extends AppCompatActivity {

    private ImageView refresh;
    private Animation rotation;
    public NavigationDrawerHandler Nav;
    public Toolbar toolbar;
    public DialogHandler DHandle;
    public SearchBarHandler searchBarHandler;

    public FloatingActionMenu menu;
    public FloatingActionButton menuButton;
    public FloatingActionButton fav1;
    public FloatingActionButton fav2;
    public FloatingActionButton fav3;

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

        createCustomAnimation();

        menu = (FloatingActionMenu) findViewById(R.id.fabmenu);
        menuButton = (FloatingActionButton) findViewById(R.id.fabtop);
        fav1 = (FloatingActionButton) findViewById(R.id.fav1);
        fav2 = (FloatingActionButton) findViewById(R.id.fav2);
        fav3 = (FloatingActionButton) findViewById(R.id.fav3);

        fav1.setOnClickListener(clickListener);
        fav2.setOnClickListener(clickListener);
        fav3.setOnClickListener(clickListener);
        menu.setOnMenuToggleListener(menuClickListener);

        menuButton.setOnClickListener(clickListener);
        menuButton.setVisibility(View.GONE);

        menu.setClosedOnTouchOutside(true);


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

        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.fabmenu);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refresh.startAnimation(rotation);
            Toast.makeText(this, "You have pressed refresh", Toast.LENGTH_SHORT).show();
            System.gc();
            return true;
        }
        if (id == R.id.action_search) {
            searchBarHandler.openSearch();
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    ////////////////
    private void createCustomAnimation() {
        final FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.fabmenu);

        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu.getMenuIconView().setImageResource(menu.isOpened()
                        ? R.drawable.ic_camera : R.drawable.ic_plus);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menu.setIconToggleAnimatorSet(set);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fav1:
                    menu.close(true);
                    break;
                case R.id.fav2:
                    menu.close(true);
                    break;
                case R.id.fav3:
                    menu.close(true);
                    break;
                case R.id.fabtop:
                    Intent i = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(i);
                    break;
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener menuClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            Handler handler = new Handler();
            if (opened) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menuButton.setVisibility(View.VISIBLE);
                    }
                }, 225);
            } else
                menuButton.setVisibility(View.GONE);
        }
    };
}
