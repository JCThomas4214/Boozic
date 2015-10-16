package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FilterActionButtonHandler extends Activity{

    private int colorAccent;
    private int colorAccentDark;
    private MainActivity main;

    public FloatingActionMenu types;
    public FloatingActionMenu distances;
    public FloatingActionMenu prices;
    public FloatingActionMenu contents;
    public FloatingActionMenu ratings;

    public boolean winescheck = true;
    public boolean beerscheck = true;
    public boolean liquorscheck = true;
    public boolean twomicheck = true;
    public boolean fivemicheck = false;
    public boolean tenmicheck = false;
    public boolean custommicheck = false;
    public boolean hilowpricecheck = false;
    public boolean lowhipricecheck = true;
    public boolean pricerangecheck = false;
    public boolean hilowcontentcheck = true;
    public boolean lowhicontentcheck = false;
    public boolean contentrangecheck = false;
    public boolean hilowratingcheck = true;
    public boolean lowhiratingcheck = false;
    public boolean ratingrangecheck = false;

    public FloatingActionButton wines;
    public FloatingActionButton beers;
    public FloatingActionButton liquors;
    public FloatingActionButton twomi;
    public FloatingActionButton fivemi;
    public FloatingActionButton tenmi;
    public FloatingActionButton custommi;
    public FloatingActionButton hilowprice;
    public FloatingActionButton lowhiprice;
    public FloatingActionButton pricerange;
    public FloatingActionButton hilowcontent;
    public FloatingActionButton lowhicontent;
    public FloatingActionButton contentrange;
    public FloatingActionButton hilowrating;
    public FloatingActionButton lowhirating;
    public FloatingActionButton ratingrange;

    public void onCreate() {}

    public void setColor(int a, int ad) {
        colorAccent = a;
        colorAccentDark = ad;
    }

    public void setActivity(MainActivity m) {
        main = m;

        types = (FloatingActionMenu) m.findViewById(R.id.fabtype);
        types.setOnMenuToggleListener(typesClickListener);
        distances = (FloatingActionMenu) m.findViewById(R.id.fabdist);
        distances.setOnMenuToggleListener(distancesClickListener);
        prices = (FloatingActionMenu) m.findViewById(R.id.fabprice);
        prices.setOnMenuToggleListener(pricesClickListener);
        contents = (FloatingActionMenu) m.findViewById(R.id.fabcontent);
        contents.setOnMenuToggleListener(contentsClickListener);
        ratings = (FloatingActionMenu) m.findViewById(R.id.fabrating);
        ratings.setOnMenuToggleListener(ratingsClickListener);

        wines = (FloatingActionButton) m.findViewById(R.id.wines);
        wines.setOnClickListener(clickListenerfilter);
        beers = (FloatingActionButton) m.findViewById(R.id.beers);
        beers.setOnClickListener(clickListenerfilter);
        liquors = (FloatingActionButton) m.findViewById(R.id.liquors);
        liquors.setOnClickListener(clickListenerfilter);
        twomi = (FloatingActionButton) m.findViewById(R.id.twomi);
        twomi.setOnClickListener(clickListenerfilter);
        fivemi = (FloatingActionButton) m.findViewById(R.id.fivemi);
        fivemi.setOnClickListener(clickListenerfilter);
        tenmi = (FloatingActionButton) m.findViewById(R.id.tenmi);
        tenmi.setOnClickListener(clickListenerfilter);
        custommi = (FloatingActionButton) m.findViewById(R.id.custommi);
        custommi.setOnClickListener(clickListenerfilter);
        hilowprice = (FloatingActionButton) m.findViewById(R.id.hilow);
        hilowprice.setOnClickListener(clickListenerfilter);
        lowhiprice = (FloatingActionButton) m.findViewById(R.id.lowhi);
        lowhiprice.setOnClickListener(clickListenerfilter);
        pricerange = (FloatingActionButton) m.findViewById(R.id.pricerange);
        pricerange.setOnClickListener(clickListenerfilter);
        hilowcontent = (FloatingActionButton) m.findViewById(R.id.hilowcontent);
        hilowcontent.setOnClickListener(clickListenerfilter);
        lowhicontent = (FloatingActionButton) m.findViewById(R.id.lowhicontent);
        lowhicontent.setOnClickListener(clickListenerfilter);
        contentrange = (FloatingActionButton) m.findViewById(R.id.rangecontent);
        contentrange.setOnClickListener(clickListenerfilter);
        hilowrating = (FloatingActionButton) m.findViewById(R.id.hilowrating);
        hilowrating.setOnClickListener(clickListenerfilter);
        lowhirating = (FloatingActionButton) m.findViewById(R.id.lowhirating);
        lowhirating.setOnClickListener(clickListenerfilter);
        ratingrange = (FloatingActionButton) m.findViewById(R.id.rangerating);
        ratingrange.setOnClickListener(clickListenerfilter);

        distances.setIconAnimated(false);
        prices.setIconAnimated(false);
        contents.setIconAnimated(false);
        ratings.setIconAnimated(false);

        adjustFBPosition();
    }

    public View.OnClickListener clickListenerfilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wines:
                    winescheck = checkclick(wines, winescheck, 0);
                    break;
                case R.id.beers:
                    beerscheck = checkclick(beers, beerscheck, 0);
                    break;
                case R.id.liquors:
                    liquorscheck = checkclick(liquors, liquorscheck, 0);
                    break;
                case R.id.twomi:
                    twomicheck = checkclick(twomi, twomicheck, 1);
                    break;
                case R.id.fivemi:
                    fivemicheck = checkclick(fivemi, fivemicheck, 1);
                    break;
                case R.id.tenmi:
                    tenmicheck = checkclick(tenmi, tenmicheck, 1);
                    break;
                case R.id.custommi:
                    custommicheck = checkclick(custommi, custommicheck, 1);
                    break;
                case R.id.hilow:
                    hilowpricecheck = checkclick(hilowprice, hilowpricecheck, 2);
                    break;
                case R.id.lowhi:
                    lowhipricecheck = checkclick(lowhiprice, lowhipricecheck, 2);
                    break;
                case R.id.pricerange:
                    pricerangecheck = checkclick(pricerange, pricerangecheck, 2);
                    break;
                case R.id.hilowcontent:
                    hilowcontentcheck = checkclick(hilowcontent, hilowcontentcheck, 3);
                    break;
                case R.id.lowhicontent:
                    lowhicontentcheck = checkclick(lowhicontent, lowhicontentcheck, 3);
                    break;
                case R.id.rangecontent:
                    contentrangecheck = checkclick(contentrange, contentrangecheck, 3);
                    break;
                case R.id.hilowrating:
                    hilowratingcheck = checkclick(hilowrating, hilowratingcheck, 4);
                    break;
                case R.id.lowhirating:
                    lowhiratingcheck = checkclick(lowhirating, lowhiratingcheck, 4);
                    break;
                case R.id.rangerating:
                    ratingrangecheck = checkclick(ratingrange, ratingrangecheck, 4);
                    break;
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener typesClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                checkOpen(types);
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener distancesClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                checkOpen(distances);
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener pricesClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                checkOpen(prices);
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener contentsClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                checkOpen(contents);
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener ratingsClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                checkOpen(ratings);
            }
        }
    };

    public void closeAll() {
        types.close(true);
        distances.close(true);
        prices.close(true);
        contents.close(true);
        ratings.close(true);
    }

    private void checkOpen(FloatingActionMenu menu) {
        if (types.isOpened() && (types != menu))
            types.close(true);
        else if (distances.isOpened() && (distances != menu))
            distances.close(true);
        else if (prices.isOpened() && (prices != menu))
            prices.close(true);
        else if (contents.isOpened() && (contents != menu))
            contents.close(true);
        else if (ratings.isOpened() && (ratings != menu))
            ratings.close(true);
    }


    private boolean checkclick(FloatingActionButton button, boolean check, int menu) {

        switch (menu) {
            case 0:
                return checkclick(button, check);
            case 1:
                if ((twomicheck == true) && (button != twomi)) {
                    twomicheck= checkclick(twomi, true);
                    return checkclick(button, false);
                }
                else if ((fivemicheck == true) && (button != fivemi)) {
                    fivemicheck = checkclick(fivemi, true);
                    return checkclick(button, false);
                }
                else if ((tenmicheck == true) && (button != tenmi)) {
                    tenmicheck = checkclick(tenmi, true);
                    return checkclick(button, false);
                }
                else if ((custommicheck == true) && (button != custommi)) {
                    custommicheck = checkclick(custommi, true);
                    return checkclick(button, false);
                }
                break;
            case 2:
                if ((hilowpricecheck == true) && (button != hilowprice)) {
                    hilowpricecheck = checkclick(hilowprice, true);
                    return checkclick(button, false);
                }
                else if ((lowhipricecheck == true) && (button != lowhiprice)) {
                    lowhipricecheck = checkclick(lowhiprice, true);
                    return checkclick(button, false);
                }
                else if ((pricerangecheck == true) && (button != pricerange)) {
                    pricerangecheck = checkclick(pricerange, true);
                    return checkclick(button, false);
                }
            break;
            case 3:
                if ((hilowcontentcheck == true) && (button != hilowcontent)) {
                    hilowcontentcheck = checkclick(hilowcontent, true);
                    return checkclick(button, false);
                }
                else if ((lowhicontentcheck == true) && (button != lowhicontent)) {
                    lowhicontentcheck = checkclick(lowhicontent, true);
                    return checkclick(button, false);
                }
                else if ((contentrangecheck == true) && (button != contentrange)) {
                    contentrangecheck = checkclick(contentrange, true);
                    return checkclick(button, false);
                }
                break;
            case 4:
                if ((hilowratingcheck == true) && (button != hilowrating)) {
                    hilowratingcheck = checkclick(hilowrating, true);
                    return checkclick(button, false);
                }
                else if ((lowhiratingcheck == true) && (button != lowhirating)) {
                    lowhiratingcheck = checkclick(lowhirating, true);
                    return checkclick(button, false);
                }
                else if ((ratingrangecheck == true) && (button != ratingrange)) {
                    ratingrangecheck = checkclick(ratingrange, true);
                    return checkclick(button, false);
                }
                break;
        }
        return true;
    }

    private boolean checkclick(FloatingActionButton button, boolean check) {

        if (check) {
            button.setColorNormal(colorAccent);
            return false;
        }
        else {
            button.setColorNormal(colorAccentDark);
            return true;
        }
    }

    private void adjustFBPosition() {

        int tbwidth = main.TBwidth - 25;
        int segments = tbwidth / 5;

        types.setX(25);
        distances.setX(25 + segments);
        prices.setX(25 + 2 * segments);
        contents.setX(25 + 3 * segments);
        ratings.setX(-25);
    }
}
