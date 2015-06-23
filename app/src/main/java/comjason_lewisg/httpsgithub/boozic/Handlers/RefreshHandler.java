package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

/**
 * Created by Jason on 6/23/2015.
 */
public class RefreshHandler extends AppCompatActivity{
    public ImageView refresh;
    public Animation rotation;

    public void onCreate() {}

    public MenuItem setRefreshButton (MainActivity m, Menu menu) {
        final MenuItem item = menu.findItem(R.id.action_refresh);

        item.setActionView(R.layout.nav_refresh);
        refresh = (ImageView) item.getActionView().findViewById(R.id.refreshButton);
        rotation = AnimationUtils.loadAnimation(m, R.anim.rotation);

        return item;
    }

    public void startAnim () {
        refresh.startAnimation(rotation);
    }


}