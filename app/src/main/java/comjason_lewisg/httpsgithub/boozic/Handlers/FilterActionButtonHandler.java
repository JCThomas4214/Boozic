package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FilterActionButtonHandler extends Activity{

    private int colorAccent;
    private int primaryColor;
    private MainActivity main;

    static final boolean WINES = true;
    static final boolean BEERS = true;
    static final boolean LIQUORS = true;
    static final boolean TWOMI = true;
    static final boolean FIVEMI = false;
    static final boolean TENMI = false;
    static final boolean CUSTOMMI = false;
    static final int CUSTOMMI_LOW = 0;
    static final int CUSTOMMI_HIGH = 10;
    static final boolean HILOW_PRICE = false;
    static final boolean LOWHI_PRICE = true;
    static final boolean PRICE_RANGE = false;
    static final int PRICE_RANGE_LOW = 0;
    static final int PRICE_RANGE_HIGH = 30;
    static final boolean HILOW_CONTENT = true;
    static final boolean LOWHI_CONTENT = false;
    static final boolean CONTENT_RANGE = false;
    static final int CONTENT_RANGE_LOW = 0;
    static final int CONTENT_RANGE_HIGH = 60;
    static final boolean HILOW_RATING = true;
    static final boolean LOWHI_RATING = false;
    static final boolean RATING_RANGE = false;
    static final int RATING_RANGE_LOW = 0;
    static final int RATING_RANGE_HIGH = 5;

    public FloatingActionMenu types;
    public FloatingActionMenu distances;
    public FloatingActionMenu prices;
    public FloatingActionMenu contents;
    public FloatingActionMenu ratings;

    public boolean winescheck;
    public boolean beerscheck;
    public boolean liquorscheck;
    public boolean twomicheck;
    public boolean fivemicheck;
    public boolean tenmicheck;
    public boolean custommicheck;
    public int custommi_low;
    public int custommi_high;
    public boolean hilowpricecheck;
    public boolean lowhipricecheck;
    public boolean pricerangecheck;
    public int pricerange_low;
    public int pricerange_high;
    public boolean hilowcontentcheck;
    public boolean lowhicontentcheck;
    public boolean contentrangecheck;
    public int contentrange_low;
    public int contentrange_high;
    public boolean hilowratingcheck;
    public boolean lowhiratingcheck;
    public boolean ratingrangecheck;
    public int ratingrange_low;
    public int ratingrange_high;

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

    public FilterActionButtonHandler(MainActivity m, int primaryColor, int primaryColorDark, int accentColor, int accentColorDark) {
        setActivity(m);
        setColor(primaryColor, primaryColorDark, accentColor, accentColorDark);
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

    public void disableAll() {
        types.setEnabled(false);
        distances.setEnabled(false);
        prices.setEnabled(false);
        contents.setEnabled(false);
        ratings.setEnabled(false);
    }

    public void enableAll() {
        types.setEnabled(true);
        distances.setEnabled(true);
        prices.setEnabled(true);
        contents.setEnabled(true);
        ratings.setEnabled(true);
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
                if ((twomicheck) && (button != twomi)) {
                    twomicheck= checkclick(twomi, true);
                    return checkclick(button, false);
                }
                else if ((fivemicheck) && (button != fivemi)) {
                    fivemicheck = checkclick(fivemi, true);
                    return checkclick(button, false);
                }
                else if ((tenmicheck) && (button != tenmi)) {
                    tenmicheck = checkclick(tenmi, true);
                    return checkclick(button, false);
                }
                else if ((custommicheck) && (button != custommi)) {
                    custommicheck = checkclick(custommi, true);
                    return checkclick(button, false);
                }
                break;
            case 2:
                if ((hilowpricecheck) && (button != hilowprice)) {
                    hilowpricecheck = checkclick(hilowprice, true);
                    return checkclick(button, false);
                }
                else if ((lowhipricecheck) && (button != lowhiprice)) {
                    lowhipricecheck = checkclick(lowhiprice, true);
                    return checkclick(button, false);
                }
                else if ((pricerangecheck) && (button != pricerange)) {
                    pricerangecheck = checkclick(pricerange, true);
                    return checkclick(button, false);
                }
            break;
            case 3:
                if ((hilowcontentcheck) && (button != hilowcontent)) {
                    hilowcontentcheck = checkclick(hilowcontent, true);
                    return checkclick(button, false);
                }
                else if ((lowhicontentcheck) && (button != lowhicontent)) {
                    lowhicontentcheck = checkclick(lowhicontent, true);
                    return checkclick(button, false);
                }
                else if ((contentrangecheck) && (button != contentrange)) {
                    contentrangecheck = checkclick(contentrange, true);
                    return checkclick(button, false);
                }
                break;
            case 4:
                if ((hilowratingcheck) && (button != hilowrating)) {
                    hilowratingcheck = checkclick(hilowrating, true);
                    return checkclick(button, false);
                }
                else if ((lowhiratingcheck) && (button != lowhirating)) {
                    lowhiratingcheck = checkclick(lowhirating, true);
                    return checkclick(button, false);
                }
                else if ((ratingrangecheck) && (button != ratingrange)) {
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
            button.setColorNormal(primaryColor);
            return true;
        }
    }

    public void setFilterButtons() {
        if (winescheck) {
            wines.setColorNormal(primaryColor);
        }
        if (beerscheck) {
            beers.setColorNormal(primaryColor);
        }
        if (liquorscheck) {
            liquors.setColorNormal(primaryColor);
        }
        if (twomicheck) {
            twomi.setColorNormal(primaryColor);
        }
        if (fivemicheck) {
            fivemi.setColorNormal(primaryColor);
        }
        if (tenmicheck) {
            tenmi.setColorNormal(primaryColor);
        }
        if (custommicheck) {
            custommi.setColorNormal(primaryColor);
        }
        if (hilowpricecheck) {
            hilowprice.setColorNormal(primaryColor);
        }
        if (lowhipricecheck) {
            lowhiprice.setColorNormal(primaryColor);
        }
        if (pricerangecheck) {
            pricerange.setColorNormal(primaryColor);
        }
        if (hilowcontentcheck) {
            hilowcontent.setColorNormal(primaryColor);
        }
        if (lowhicontentcheck) {
            lowhicontent.setColorNormal(primaryColor);
        }
        if (contentrangecheck) {
            contentrange.setColorNormal(primaryColor);
        }
        if (hilowratingcheck) {
            hilowrating.setColorNormal(primaryColor);
        }
        if (lowhiratingcheck) {
            lowhirating.setColorNormal(primaryColor);
        }
        if (ratingrangecheck) {
            ratingrange.setColorNormal(primaryColor);
        }
    }

    public void setColor(int primaryColor, int primaryColorDark, int accentColor, int accentColorDark) {
        colorAccent = accentColor;
        this.primaryColor = primaryColor;

        types.setMenuButtonColorNormal(primaryColor);
        types.setMenuButtonColorPressed(primaryColorDark);

        distances.setMenuButtonColorNormal(primaryColor);
        distances.setMenuButtonColorPressed(primaryColorDark);

        prices.setMenuButtonColorNormal(primaryColor);
        prices.setMenuButtonColorPressed(primaryColorDark);

        contents.setMenuButtonColorNormal(primaryColor);
        contents.setMenuButtonColorPressed(primaryColorDark);

        ratings.setMenuButtonColorNormal(primaryColor);
        ratings.setMenuButtonColorPressed(primaryColorDark);

        wines.setColorNormal(accentColor);
        wines.setColorPressed(accentColorDark);
        beers.setColorNormal(accentColor);
        beers.setColorPressed(accentColorDark);
        liquors.setColorNormal(accentColor);
        liquors.setColorPressed(accentColorDark);

        twomi.setColorNormal(accentColor);
        twomi.setColorPressed(accentColorDark);
        fivemi.setColorNormal(accentColor);
        fivemi.setColorPressed(accentColorDark);
        tenmi.setColorNormal(accentColor);
        tenmi.setColorPressed(accentColorDark);
        custommi.setColorNormal(accentColor);
        custommi.setColorPressed(accentColorDark);

        hilowprice.setColorNormal(accentColor);
        hilowprice.setColorPressed(accentColorDark);
        lowhiprice.setColorNormal(accentColor);
        lowhiprice.setColorPressed(accentColorDark);
        pricerange.setColorNormal(accentColor);
        pricerange.setColorPressed(accentColorDark);

        hilowcontent.setColorNormal(accentColor);
        hilowcontent.setColorPressed(accentColorDark);
        lowhicontent.setColorNormal(accentColor);
        lowhicontent.setColorPressed(accentColorDark);
        contentrange.setColorNormal(accentColor);
        contentrange.setColorPressed(accentColorDark);

        hilowrating.setColorNormal(accentColor);
        hilowrating.setColorPressed(accentColorDark);
        lowhirating.setColorNormal(accentColor);
        lowhirating.setColorPressed(accentColorDark);
        ratingrange.setColorNormal(accentColor);
        ratingrange.setColorPressed(accentColorDark);
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

    public void hideFilterButtons() {
        types.hideMenuButton(true);
        distances.hideMenuButton(true);
        prices.hideMenuButton(true);
        contents.hideMenuButton(true);
        ratings.hideMenuButton(true);
    }

    public void showFilterButtons() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                types.showMenuButton(true);
            }
        }, 250);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                distances.showMenuButton(true);
            }
        }, 350);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prices.showMenuButton(true);
            }
        }, 450);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                contents.showMenuButton(true);
            }
        }, 550);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ratings.showMenuButton(true);
            }
        }, 650);
    }

    public void initiateSharedPref(SharedPreferences mPrefs) {
        //when resume, pull saves states for each button
        winescheck = mPrefs.getBoolean("WINES", WINES);
        beerscheck = mPrefs.getBoolean("BEERS", BEERS);
        liquorscheck = mPrefs.getBoolean("LIQUORS", LIQUORS);
        twomicheck = mPrefs.getBoolean("TWOMI", TWOMI);
        fivemicheck = mPrefs.getBoolean("FIVEMI", FIVEMI);
        tenmicheck = mPrefs.getBoolean("TENMI", TENMI);
        custommicheck = mPrefs.getBoolean("CUSTOMMI", CUSTOMMI);
        hilowpricecheck = mPrefs.getBoolean("HILOW_PRICE", HILOW_PRICE);
        lowhipricecheck = mPrefs.getBoolean("LOWHI_PRICE", LOWHI_PRICE);
        pricerangecheck = mPrefs.getBoolean("PRICE_RANGE", PRICE_RANGE);
        hilowcontentcheck = mPrefs.getBoolean("HILOW_CONTENT", HILOW_CONTENT);
        lowhicontentcheck = mPrefs.getBoolean("LOWHI_CONTENT", LOWHI_CONTENT);
        contentrangecheck = mPrefs.getBoolean("CONTENT_RANGE", CONTENT_RANGE);
        hilowratingcheck = mPrefs.getBoolean("HILOW_RATING", HILOW_RATING);
        lowhiratingcheck = mPrefs.getBoolean("LOWHI_RATING", LOWHI_RATING);
        ratingrangecheck = mPrefs.getBoolean("RATING_RANGE", RATING_RANGE);

        //store the previous state colors into their variables
        custommi_low = mPrefs.getInt("CUSTOMMI_LOW", CUSTOMMI_LOW);
        custommi_high = mPrefs.getInt("CUSTOMMI_HIGH", CUSTOMMI_HIGH);
        pricerange_low = mPrefs.getInt("PRICE_RANGE_LOW", PRICE_RANGE_LOW);
        pricerange_high = mPrefs.getInt("PRICE_RANGE_HIGH", PRICE_RANGE_HIGH);
        contentrange_low = mPrefs.getInt("CONTENT_RANGE_LOW", CONTENT_RANGE_LOW);
        contentrange_high = mPrefs.getInt("CONTENT_RANGE_HIGH", CONTENT_RANGE_HIGH);
        ratingrange_low = mPrefs.getInt("RATING_RANGE_LOW", RATING_RANGE_LOW);
        ratingrange_high = mPrefs.getInt("RATING_RANGE_HIGH", RATING_RANGE_HIGH);

        setFilterButtons();
    }

    public void saveSharedPref(SharedPreferences.Editor ed) {
        //store all color states into universal sharedpreference
        ed.putBoolean("WINES", winescheck);
        ed.putBoolean("BEERS", beerscheck);
        ed.putBoolean("LIQUORS", liquorscheck);
        ed.putBoolean("TWOMI", twomicheck);
        ed.putBoolean("FIVEMI", fivemicheck);
        ed.putBoolean("TENMI", tenmicheck);
        ed.putBoolean("CUSTOMMI", custommicheck);
        ed.putBoolean("HILOW_PRICE", hilowpricecheck);
        ed.putBoolean("LOWHI_PRICE", lowhipricecheck);
        ed.putBoolean("PRICE_RANGE", pricerangecheck);
        ed.putBoolean("HILOW_CONTENT", hilowcontentcheck);
        ed.putBoolean("LOWHI_CONTENT", lowhicontentcheck);
        ed.putBoolean("CONTENT_RANGE", contentrangecheck);
        ed.putBoolean("HILOW_RATING", hilowratingcheck);
        ed.putBoolean("LOWHI_RATING", lowhiratingcheck);
        ed.putBoolean("RATING_RANGE", ratingrangecheck);

        ed.putInt("CUSTOMMI_LOW", custommi_low);
        ed.putInt("CUSTOMMI_HIGH", custommi_high);
        ed.putInt("PRICE_RANGE_LOW", pricerange_low);
        ed.putInt("PRICE_RANGE_HIGH", pricerange_high);
        ed.putInt("CONTENT_RANGE_LOW", contentrange_low);
        ed.putInt("CONTENT_RANGE_HIGH", contentrange_high);
        ed.putInt("RATING_RANGE_LOW", ratingrange_low);
        ed.putInt("RATING_RANGE_HIGH", ratingrange_high);
    }
}
