package comjason_lewisg.httpsgithub.boozic.Handlers;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class ThemeHandler {

    public void onCreate(){
    }
    public void setStyle(int colorPrimary, int colorAccent, MainActivity m) {
        switch (colorPrimary) {
            case 1:
                m.findViewById(R.id.toolbar).setBackgroundColor(m.getResources().getColor(R.color.ColorPrimary));
                m.FAB.menu.setMenuButtonColorNormal(m.getResources().getColor(R.color.ColorPrimary));
                m.FAB.menu.setMenuButtonColorPressed(m.getResources().getColor(R.color.ColorPrimaryDark));
                m.FAB.menuButton.setColorNormal(m.getResources().getColor(R.color.ColorPrimary));
                setStyleAccent(colorAccent, m);
                break;
            case 2:
                m.findViewById(R.id.toolbar).setBackgroundColor(m.getResources().getColor(R.color.ColorPrimary2));
                m.FAB.menu.setMenuButtonColorNormal(m.getResources().getColor(R.color.ColorPrimary2));
                m.FAB.menu.setMenuButtonColorPressed(m.getResources().getColor(R.color.ColorPrimaryDark2));
                m.FAB.menuButton.setColorNormal(m.getResources().getColor(R.color.ColorPrimary2));
                setStyleAccent(colorAccent, m);
                break;
            case 3:
                m.findViewById(R.id.toolbar).setBackgroundColor(m.getResources().getColor(R.color.ColorPrimary3));
                m.FAB.menu.setMenuButtonColorNormal(m.getResources().getColor(R.color.ColorPrimary3));
                m.FAB.menu.setMenuButtonColorPressed(m.getResources().getColor(R.color.ColorPrimaryDark3));
                m.FAB.menuButton.setColorNormal(m.getResources().getColor(R.color.ColorPrimary3));
                setStyleAccent(colorAccent, m);
                break;
            case 4:
                m.findViewById(R.id.toolbar).setBackgroundColor(m.getResources().getColor(R.color.ColorPrimary4));
                m.FAB.menu.setMenuButtonColorNormal(m.getResources().getColor(R.color.ColorPrimary4));
                m.FAB.menu.setMenuButtonColorPressed(m.getResources().getColor(R.color.ColorPrimaryDark4));
                m.FAB.menuButton.setColorNormal(m.getResources().getColor(R.color.ColorPrimary4));
                setStyleAccent(colorAccent, m);
                break;
            case 5:
                m.findViewById(R.id.toolbar).setBackgroundColor(m.getResources().getColor(R.color.ColorPrimary5));
                m.FAB.menu.setMenuButtonColorNormal(m.getResources().getColor(R.color.ColorPrimary5));
                m.FAB.menu.setMenuButtonColorPressed(m.getResources().getColor(R.color.ColorPrimaryDark5));
                m.FAB.menuButton.setColorNormal(m.getResources().getColor(R.color.ColorPrimary5));
                setStyleAccent(colorAccent, m);
                break;
        }
    }

    private void setStyleAccent(int colorAccent, MainActivity m) {
        switch (colorAccent) {
            case 1:
                m.FAB.fav1.setColorNormal(m.getResources().getColor(R.color.ColorAccent));
                m.FAB.fav1.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark));
                m.FAB.fav2.setColorNormal(m.getResources().getColor(R.color.ColorAccent));
                m.FAB.fav2.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark));
                m.FAB.fav3.setColorNormal(m.getResources().getColor(R.color.ColorAccent));
                m.FAB.fav3.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark));

                /**/
                break;
            case 2:
                m.FAB.fav1.setColorNormal(m.getResources().getColor(R.color.ColorAccent2));
                m.FAB.fav1.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark2));
                m.FAB.fav2.setColorNormal(m.getResources().getColor(R.color.ColorAccent2));
                m.FAB.fav2.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark2));
                m.FAB.fav3.setColorNormal(m.getResources().getColor(R.color.ColorAccent2));
                m.FAB.fav3.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark2));
                break;
            case 3:
                m.FAB.fav1.setColorNormal(m.getResources().getColor(R.color.ColorAccent3));
                m.FAB.fav1.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark3));
                m.FAB.fav2.setColorNormal(m.getResources().getColor(R.color.ColorAccent3));
                m.FAB.fav2.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark3));
                m.FAB.fav3.setColorNormal(m.getResources().getColor(R.color.ColorAccent3));
                m.FAB.fav3.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark3));
                break;
            case 4:
                m.FAB.fav1.setColorNormal(m.getResources().getColor(R.color.ColorAccent4));
                m.FAB.fav1.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark4));
                m.FAB.fav2.setColorNormal(m.getResources().getColor(R.color.ColorAccent4));
                m.FAB.fav2.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark4));
                m.FAB.fav3.setColorNormal(m.getResources().getColor(R.color.ColorAccent4));
                m.FAB.fav3.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark4));
                break;
            case 5:
                m.FAB.fav1.setColorNormal(m.getResources().getColor(R.color.ColorAccent5));
                m.FAB.fav1.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark5));
                m.FAB.fav2.setColorNormal(m.getResources().getColor(R.color.ColorAccent5));
                m.FAB.fav2.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark5));
                m.FAB.fav3.setColorNormal(m.getResources().getColor(R.color.ColorAccent5));
                m.FAB.fav3.setColorPressed(m.getResources().getColor(R.color.ColorAccentDark5));
                break;
        }
    }
}
