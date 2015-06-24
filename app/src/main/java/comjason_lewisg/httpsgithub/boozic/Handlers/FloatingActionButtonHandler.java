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
    public FloatingActionMenu menu;
    public FloatingActionButton menuButton;
    public FloatingActionButton fav1;
    public FloatingActionButton fav2;
    public FloatingActionButton fav3;

    static final int SCANNER_CODE_REQUEST = 0;

    protected void onCreate() {

    }

    public void setActivity(MainActivity main) {
        m = main;

        createCustomAnimation();

        menu = (FloatingActionMenu) m.findViewById(R.id.fabmenu);
        menuButton = (FloatingActionButton) m.findViewById(R.id.fabtop);
        fav1 = (FloatingActionButton) m.findViewById(R.id.fav1);
        fav2 = (FloatingActionButton) m.findViewById(R.id.fav2);
        fav3 = (FloatingActionButton) m.findViewById(R.id.fav3);

        fav1.setOnClickListener(clickListener);
        fav2.setOnClickListener(clickListener);
        fav3.setOnClickListener(clickListener);
        menu.setOnMenuToggleListener(menuClickListener);

        menuButton.setOnClickListener(clickListener);
        menuButton.setVisibility(View.GONE);

        menu.setClosedOnTouchOutside(true);
    }

    private void createCustomAnimation() {
        final FloatingActionMenu menu = (FloatingActionMenu) m.findViewById(R.id.fabmenu);

        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(100);
        scaleInY.setDuration(100);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu.getMenuIconView().setImageResource(menu.isOpened()
                        ? R.drawable.camera : R.drawable.chevron_up);
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
            Handler handler = new Handler();
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
                    menu.close(true);
                    menuButton.setVisibility(View.GONE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //this is where the Scanner starts
                            Intent i = new Intent(m, CameraActivity.class);
                            m.startActivityForResult(i, SCANNER_CODE_REQUEST);
                        }
                    }, 300);
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
            } else {
                menuButton.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menuButton.setVisibility(View.GONE);
                    }
                }, 200);
            }
        }
    };


}
