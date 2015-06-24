package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.util.Log;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class ThemeHandler extends MainActivity{

    public int primaryColor;
    public int primaryColorDark;
    public int accentColor;
    public int accentColorDark;

    public void onCreate(){
    }
    public void setStylePrimary(int colorPrimary, Activity a) {
        switch (colorPrimary) {
            case 1:
                primaryColor = a.getResources().getColor(R.color.ColorPrimary);
                primaryColorDark = a.getResources().getColor(R.color.ColorPrimaryDark);
                Log.v("COLOR", "primaryColor = "+primaryColor+ " primaryColorDark = "+primaryColorDark);
                break;
            case 2:
                primaryColor = a.getResources().getColor(R.color.ColorPrimary2);
                primaryColorDark = a.getResources().getColor(R.color.ColorPrimaryDark2);
                break;
            case 3:
                primaryColor = a.getResources().getColor(R.color.ColorPrimary3);
                primaryColorDark = a.getResources().getColor(R.color.ColorPrimaryDark3);
                break;
            case 4:
                primaryColor = a.getResources().getColor(R.color.ColorPrimary4);
                primaryColorDark = a.getResources().getColor(R.color.ColorPrimaryDark4);
                break;
            case 5:
                primaryColor = a.getResources().getColor(R.color.ColorPrimary5);
                primaryColorDark = a.getResources().getColor(R.color.ColorPrimaryDark5);
                break;
        }
    }

    public void setStyleAccent(int colorAccent, Activity a) {
        switch (colorAccent) {
            case 1:
                accentColor = a.getResources().getColor(R.color.ColorAccent);
                accentColorDark = a.getResources().getColor(R.color.ColorAccentDark);
                break;
            case 2:
                accentColor = a.getResources().getColor(R.color.ColorAccent2);
                accentColorDark = a.getResources().getColor(R.color.ColorAccentDark2);
                break;
            case 3:
                accentColor = a.getResources().getColor(R.color.ColorAccent3);
                accentColorDark = a.getResources().getColor(R.color.ColorAccentDark3);
                break;
            case 4:
                accentColor = a.getResources().getColor(R.color.ColorAccent4);
                accentColorDark = a.getResources().getColor(R.color.ColorAccentDark4);
                break;
            case 5:
                accentColor = a.getResources().getColor(R.color.ColorAccent5);
                accentColorDark = a.getResources().getColor(R.color.ColorAccentDark5);
                break;
        }
    }
}
