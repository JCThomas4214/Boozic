package comjason_lewisg.httpsgithub.boozic;

/**
 * Created by Jason on 6/7/2015.
 */
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class FloatActionButton {

    ImageButton FAB;

    protected void onCreate() {}
    public void connectButton (final MainActivity m) {
        this.FAB = (ImageButton) m.findViewById(R.id.imageButton);
        this.FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this,"You have pressed the FAB",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(m, CameraActivity.class);
                m.startActivity(i);

            }
        });
    }
}
