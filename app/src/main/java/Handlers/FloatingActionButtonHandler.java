package Handlers;

/**
 * Created by Jason on 6/11/2015.
 */
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import comjason_lewisg.httpsgithub.boozic.CameraActivity;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FloatingActionButtonHandler {

    ImageButton FAB;

    protected void onCreate() {}
    /*public void connectButton (final MainActivity m) {
        this.FAB = (ImageButton) m.findViewById(R.id.imageButton);
        this.FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this,"You have pressed the FAB",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(m, CameraActivity.class);
                m.startActivity(i);

            }
        });
    }*/
}
