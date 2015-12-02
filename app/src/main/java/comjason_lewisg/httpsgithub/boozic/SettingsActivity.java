package comjason_lewisg.httpsgithub.boozic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    public Toolbar toolbar;

    static final int COLOR_STATE = 1;
    static final int PRIMARY_STATE = 0;
    static final int PRIMARY_DARK_STATE = 0;
    static final int ACCENT_STATE = 0;
    static final int ACCENT_DARK_STATE = 0;

    int colorPrimary_id;
    int primaryColor;
    int primaryColorDark;
    int accentColor;
    int accentColorDark;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("COLOR_STATE", MODE_PRIVATE);

        colorPrimary_id = mPrefs.getInt("COLOR_STATE", COLOR_STATE);
        primaryColor = mPrefs.getInt("PRIMARY_STATE", PRIMARY_STATE);
        primaryColorDark = mPrefs.getInt("PRIMARY_DARK_STATE", PRIMARY_DARK_STATE);
        accentColor = mPrefs.getInt("ACCENT_STATE", ACCENT_STATE);
        accentColorDark = mPrefs.getInt("ACCENT_DARK_STATE", ACCENT_DARK_STATE);

        switch (colorPrimary_id) {
            case 1:
                setTheme(R.style.AppTheme1);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
        }

        setContentView(R.layout.activity_settings);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(primaryColorDark);

        //Instantiate the toolbar object
        toolbar = (Toolbar) findViewById(R.id.settings_toolbar); // Attaching the layout to the toolbar object
        toolbar.setTitle("");
        toolbar.getLayoutParams().height = 170;
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Settings");
        setSupportActionBar(toolbar);

        //Call these to set up the back arrow on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //If the back button is clicked
        if (id == android.R.id.home) {
            //return to main activity
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
