package comjason_lewisg.httpsgithub.boozic;

/**
 * Created by Jason on 6/7/2015.
 */
import android.support.v7.widget.Toolbar;

public class TopBar {

    public Toolbar toolbar;                        // Declaring the Toolbar Object

    protected void onCreate() {
    }
    public void connectBar (MainActivity v) {
        toolbar = (Toolbar) v.findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        v.setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
    }
}
