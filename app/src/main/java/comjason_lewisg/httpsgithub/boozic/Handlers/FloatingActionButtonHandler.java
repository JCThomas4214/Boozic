package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import comjason_lewisg.httpsgithub.boozic.CameraActivity;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FloatingActionButtonHandler extends Activity {

    private MainActivity m;

    public FloatingActionButton menuButton;

    static final int SCANNER_CODE_REQUEST = 0;

    protected void onCreate() {}

    public FloatingActionButtonHandler(MainActivity m, int primaryColor, int primaryColorDark) {
        setActivity(m);
        menuButton.setColorNormal(primaryColor);
        menuButton.setColorPressed(primaryColorDark);
    }

    public void setActivity(MainActivity main) {
        m = main;

        //createCustomAnimation();
        menuButton = (FloatingActionButton) m.findViewById(R.id.fabtop);
        menuButton.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fabtop:
                    Intent i = new Intent(m, CameraActivity.class);
                    m.startActivityForResult(i, SCANNER_CODE_REQUEST);
                    break;
            }
        }
    };


}
