package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.content.Intent;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import comjason_lewisg.httpsgithub.boozic.CameraActivity;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FloatingActionButtonHandler {

    private MainActivity m;

    public FloatingActionButton menuButton;

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
                    m.startActivityForResult(i, m.SCANNER_CODE_REQUEST);
                    break;
            }
        }
    };


}
