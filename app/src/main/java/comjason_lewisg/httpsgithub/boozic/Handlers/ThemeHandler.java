package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class ThemeHandler {

    public int primaryColor;
    public int primaryColorDark;
    public int accentColor;
    public int accentColorDark;

    public void onCreate() {}

    public void setStylePrimary(int colorPrimary, Activity a) {
        switch (colorPrimary) {
            case 1:
                primaryColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimary);
                primaryColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimaryDark);
                break;
            case 2:
                primaryColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimary2);
                primaryColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimaryDark2);
                break;
            case 3:
                primaryColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimary3);
                primaryColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimaryDark3);
                break;
            case 4:
                primaryColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimary4);
                primaryColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimaryDark4);
                break;
            case 5:
                primaryColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimary5);
                primaryColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorPrimaryDark5);
                break;
        }
    }

    public void setStyleAccent(int colorAccent, Activity a) {
        switch (colorAccent) {
            case 1:
                accentColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccent);
                accentColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccentDark);
                break;
            case 2:
                accentColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccent2);
                accentColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccentDark2);
                break;
            case 3:
                accentColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccent3);
                accentColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccentDark3);
                break;
            case 4:
                accentColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccent4);
                accentColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccentDark4);
                break;
            case 5:
                accentColor = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccent5);
                accentColorDark = ContextCompat.getColor(a.getApplicationContext(), R.color.ColorAccentDark5);
                break;
        }
    }
}
