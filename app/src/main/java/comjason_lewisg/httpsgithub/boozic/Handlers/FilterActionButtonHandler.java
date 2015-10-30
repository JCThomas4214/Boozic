package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FilterActionButtonHandler extends Activity{
/*
    private int colorAccent;
    private int primaryColor;
    private MainActivity main;
    public boolean typeMenuOpened = false;
    public boolean distantsMenuOpened = false;
    public boolean contentsMenuOpened = false;
    public boolean pricesMenuOpened = false;
    public boolean ratingsMenuOpened = false;

    static final int TYPESBUTTONS = 0b111;
    static final int DISTANCESBUTTONS = 0b1000;
    static final int PRICESBUTTONS = 0b100;
    static final int CONTENTSBUTTONS = 0b010;
    static final int RATINGSBUTTONS = 0b010;

    static final int CUSTOMMI_MILES = 15;
    static final int PRICE_RANGE_LOW = 0;
    static final int PRICE_RANGE_HIGH = 30;
    static final int CONTENT_RANGE_LOW = 0;
    static final int CONTENT_RANGE_HIGH = 60;
    static final int RATING_RANGE_LOW = 0;
    static final int RATING_RANGE_HIGH = 5;

    static final boolean TYPESMENUOPENED = false;
    static final boolean DISTANCESMENUOPENED = false;
    static final boolean PRICESMENUOPENED = false;
    static final boolean CONTENTSMENUOPENED = false;
    static final boolean RATINGSMENUOPENED = false;

    public FloatingActionMenu types;
    public FloatingActionMenu distances;
    public FloatingActionMenu prices;
    public FloatingActionMenu contents;
    public FloatingActionMenu ratings;

    public int custommi_miles;
    public int pricerange_low;
    public int pricerange_high;
    public int contentrange_low;
    public int contentrange_high;
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

    public int typesButtonPressed = 0b111;
    public int distancesButtonPressed = 0b1000;
    public int pricesButtonPressed = 0b010;
    public int contentsButtonPressed = 0b100;
    public int ratingsButtonPressed = 0b100;

    public FloatingActionButton milesPrevious;
    public FloatingActionButton pricePrevious;
    public FloatingActionButton abvPrevious;
    public FloatingActionButton ratingPrevious;

    public FloatingActionMenu previousMenu;
    public FloatingActionMenu currentMenu;
    boolean startMenu;

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
        wines.setOnClickListener(clickListenerTypesfilter);
        beers = (FloatingActionButton) m.findViewById(R.id.beers);
        beers.setOnClickListener(clickListenerTypesfilter);
        liquors = (FloatingActionButton) m.findViewById(R.id.liquors);
        liquors.setOnClickListener(clickListenerTypesfilter);
        twomi = (FloatingActionButton) m.findViewById(R.id.twomi);
        twomi.setOnClickListener(clickListenerDistancesfilter);
        fivemi = (FloatingActionButton) m.findViewById(R.id.fivemi);
        fivemi.setOnClickListener(clickListenerDistancesfilter);
        tenmi = (FloatingActionButton) m.findViewById(R.id.tenmi);
        tenmi.setOnClickListener(clickListenerDistancesfilter);
        custommi = (FloatingActionButton) m.findViewById(R.id.custommi);
        custommi.setOnClickListener(clickListenerDistancesfilter);
        hilowprice = (FloatingActionButton) m.findViewById(R.id.hilow);
        hilowprice.setOnClickListener(clickListenerPricesfilter);
        lowhiprice = (FloatingActionButton) m.findViewById(R.id.lowhi);
        lowhiprice.setOnClickListener(clickListenerPricesfilter);
        pricerange = (FloatingActionButton) m.findViewById(R.id.pricerange);
        pricerange.setOnClickListener(clickListenerPricesfilter);
        hilowcontent = (FloatingActionButton) m.findViewById(R.id.hilowcontent);
        hilowcontent.setOnClickListener(clickListenerContentsfilter);
        lowhicontent = (FloatingActionButton) m.findViewById(R.id.lowhicontent);
        lowhicontent.setOnClickListener(clickListenerContentsfilter);
        contentrange = (FloatingActionButton) m.findViewById(R.id.rangecontent);
        contentrange.setOnClickListener(clickListenerContentsfilter);
        hilowrating = (FloatingActionButton) m.findViewById(R.id.hilowrating);
        hilowrating.setOnClickListener(clickListenerRatingsfilter);
        lowhirating = (FloatingActionButton) m.findViewById(R.id.lowhirating);
        lowhirating.setOnClickListener(clickListenerRatingsfilter);
        ratingrange = (FloatingActionButton) m.findViewById(R.id.rangerating);
        ratingrange.setOnClickListener(clickListenerRatingsfilter);

        distances.setIconAnimated(false);
        prices.setIconAnimated(false);
        contents.setIconAnimated(false);
        ratings.setIconAnimated(false);

        previousMenu = null;
        currentMenu = null;
        startMenu = true;

        adjustFBPosition();
    }

    public View.OnClickListener clickListenerTypesfilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wines:
                    if ((typesButtonPressed / 4) != 1) {
                        typesButtonPressed += 0b100;
                        wines.setColorNormal(primaryColor);
                    } else {
                        typesButtonPressed -= 0b100;
                        wines.setColorNormal(colorAccent);
                    }

                    break;
                case R.id.beers:
                    if ((typesButtonPressed / 2) % 2 != 1) {
                        typesButtonPressed += 0b010;
                        beers.setColorNormal(primaryColor);
                    } else {
                        typesButtonPressed -= 0b010;
                        beers.setColorNormal(colorAccent);
                    }
                    break;
                case R.id.liquors:
                    if ((typesButtonPressed) % 2 != 1) {
                        typesButtonPressed += 0b001;
                        liquors.setColorNormal(primaryColor);
                    } else {
                        typesButtonPressed -= 0b001;
                        liquors.setColorNormal(colorAccent);
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerDistancesfilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.twomi:

                    if (milesPrevious == twomi) {
                        if (distancesButtonPressed == 0b1000) {
                            distancesButtonPressed = 0b0000;
                            milesPrevious.setColorNormal(colorAccent);
                        } else {
                            distancesButtonPressed = 0b1000;
                            milesPrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        distancesButtonPressed = 0b1000;
                        milesPrevious.setColorNormal(colorAccent);
                        milesPrevious = twomi;
                        milesPrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.fivemi:
                    if (milesPrevious == fivemi) {
                        if (distancesButtonPressed == 0b0100) {
                            distancesButtonPressed = 0b0000;
                            milesPrevious.setColorNormal(colorAccent);
                        } else {
                            distancesButtonPressed = 0b0100;
                            milesPrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        distancesButtonPressed = 0b0100;
                        milesPrevious.setColorNormal(colorAccent);
                        milesPrevious = fivemi;
                        milesPrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.tenmi:
                    if (milesPrevious == tenmi) {
                        if (distancesButtonPressed == 0b0010) {
                            distancesButtonPressed = 0b0000;
                            milesPrevious.setColorNormal(colorAccent);
                        } else {
                            distancesButtonPressed = 0b0010;
                            milesPrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        distancesButtonPressed = 0b0010;
                        milesPrevious.setColorNormal(colorAccent);
                        milesPrevious = tenmi;
                        milesPrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.custommi:
                    if (milesPrevious == custommi) {
                        if (distancesButtonPressed == 0b0001) {
                            distancesButtonPressed = 0b0000;
                            milesPrevious.setColorNormal(colorAccent);
                        } else {
                            distancesButtonPressed = 0b0001;
                            milesPrevious.setColorNormal(primaryColor);
                            closeMenu();
                            main.callCustommiDialog();
                        }
                    } else {
                        distancesButtonPressed = 0b0001;
                        milesPrevious.setColorNormal(colorAccent);
                        milesPrevious = custommi;
                        milesPrevious.setColorNormal(primaryColor);
                        closeMenu();
                        main.callCustommiDialog();
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerPricesfilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hilow:
                    if (pricePrevious == hilowprice) {
                        if (pricesButtonPressed == 0b100) {
                            pricesButtonPressed = 0b000;
                            pricePrevious.setColorNormal(colorAccent);
                        } else {
                            pricesButtonPressed = 0b100;
                            pricePrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        pricesButtonPressed = 0b100;
                        pricePrevious.setColorNormal(colorAccent);
                        pricePrevious = hilowprice;
                        pricePrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.lowhi:
                    if (pricePrevious == lowhiprice) {
                        if (pricesButtonPressed == 0b010) {
                            pricesButtonPressed = 0b000;
                            pricePrevious.setColorNormal(colorAccent);
                        } else {
                            pricesButtonPressed = 0b010;
                            pricePrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        pricesButtonPressed = 0b010;
                        pricePrevious.setColorNormal(colorAccent);
                        pricePrevious = lowhiprice;
                        pricePrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.pricerange:
                    if (pricePrevious == pricerange) {
                        if (pricesButtonPressed == 0b001) {
                            pricesButtonPressed = 0b000;
                            pricePrevious.setColorNormal(colorAccent);
                        } else {
                            pricesButtonPressed = 0b001;
                            pricePrevious.setColorNormal(primaryColor);
                            closeMenu();
                            main.callRangeDialog("Choose Price Range", "$");
                        }
                    } else {
                        pricesButtonPressed = 0b001;
                        pricePrevious.setColorNormal(colorAccent);
                        pricePrevious = pricerange;
                        pricePrevious.setColorNormal(primaryColor);
                        closeMenu();
                        main.callRangeDialog("Choose Price Range", "$");
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerContentsfilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hilowcontent:
                    if (abvPrevious == hilowcontent) {
                        if (contentsButtonPressed == 0b100) {
                            contentsButtonPressed = 0b000;
                            abvPrevious.setColorNormal(colorAccent);
                        } else {
                            contentsButtonPressed = 0b100;
                            abvPrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        contentsButtonPressed = 0b100;
                        abvPrevious.setColorNormal(colorAccent);
                        abvPrevious = hilowcontent;
                        abvPrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.lowhicontent:
                    if (abvPrevious == lowhicontent) {
                        if (contentsButtonPressed == 0b010) {
                            contentsButtonPressed = 0b000;
                            abvPrevious.setColorNormal(colorAccent);
                        } else {
                            contentsButtonPressed = 0b010;
                            abvPrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        contentsButtonPressed = 0b010;
                        abvPrevious.setColorNormal(colorAccent);
                        abvPrevious = lowhicontent;
                        abvPrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.rangecontent:
                    if (abvPrevious == contentrange) {
                        if (contentsButtonPressed == 0b001) {
                            contentsButtonPressed = 0b000;
                            abvPrevious.setColorNormal(colorAccent);
                        } else {
                            contentsButtonPressed = 0b001;
                            abvPrevious.setColorNormal(primaryColor);
                            closeMenu();
                            main.callRangeDialog("Choose ABV Range", "%");
                        }
                    } else {
                        contentsButtonPressed = 0b001;
                        abvPrevious.setColorNormal(colorAccent);
                        abvPrevious = contentrange;
                        abvPrevious.setColorNormal(primaryColor);
                        closeMenu();
                        main.callRangeDialog("Choose ABV Range", "%");
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerRatingsfilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hilowrating:
                    if (ratingPrevious == hilowrating) {
                        if (ratingsButtonPressed == 0b100) {
                            ratingsButtonPressed = 0b000;
                            ratingPrevious.setColorNormal(colorAccent);
                        } else {
                            ratingsButtonPressed = 0b100;
                            ratingPrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        ratingsButtonPressed = 0b100;
                        ratingPrevious.setColorNormal(colorAccent);
                        ratingPrevious = hilowrating;
                        ratingPrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.lowhirating:
                    if (ratingPrevious == lowhirating) {
                        if (ratingsButtonPressed == 0b010) {
                            ratingsButtonPressed = 0b000;
                            ratingPrevious.setColorNormal(colorAccent);
                        } else {
                            ratingsButtonPressed = 0b010;
                            ratingPrevious.setColorNormal(primaryColor);
                        }
                    } else {
                        ratingsButtonPressed = 0b010;
                        ratingPrevious.setColorNormal(colorAccent);
                        ratingPrevious = lowhirating;
                        ratingPrevious.setColorNormal(primaryColor);
                    }
                    break;
                case R.id.rangerating:
                    if (ratingPrevious == ratingrange) {
                        if (ratingsButtonPressed == 0b001) {
                            ratingsButtonPressed = 0b000;
                            ratingPrevious.setColorNormal(colorAccent);
                        } else {
                            ratingsButtonPressed = 0b001;
                            ratingPrevious.setColorNormal(primaryColor);
                            closeMenu();
                            main.callRangeDialog("Choose Rating Range", "avg");
                        }
                    } else {
                        ratingsButtonPressed = 0b001;
                        ratingPrevious.setColorNormal(colorAccent);
                        ratingPrevious = ratingrange;
                        ratingPrevious.setColorNormal(primaryColor);
                        closeMenu();
                        main.callRangeDialog("Choose Rating Range", "avg");
                    }
                    break;
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener typesClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                if (!startMenu && currentMenu != null) currentMenu.close(true);
                currentMenu = types;
                startMenu = false;
            } else {
                previousMenu = types;
                if (currentMenu == previousMenu) startMenu = true;
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener distancesClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                if (!startMenu && currentMenu != null) currentMenu.close(true);
                currentMenu = distances;
                startMenu = false;
            } else {
                previousMenu = distances;
                if (currentMenu == previousMenu) startMenu = true;
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener pricesClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                if (!startMenu && currentMenu != null) currentMenu.close(true);
                currentMenu = prices;
                startMenu = false;
            } else {
                previousMenu = prices;
                if (currentMenu == previousMenu) startMenu = true;
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener contentsClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                if (!startMenu && currentMenu != null) currentMenu.close(true);
                currentMenu = contents;
                startMenu = false;
            } else {
                previousMenu = contents;
                if (currentMenu == previousMenu) startMenu = true;
            }
        }
    };

    private FloatingActionMenu.OnMenuToggleListener ratingsClickListener = new FloatingActionMenu.OnMenuToggleListener() {
        @Override
        public void onMenuToggle(boolean opened) {
            if (opened) {
                if (!startMenu && currentMenu != null) currentMenu.close(true);
                currentMenu = ratings;
                startMenu = false;
            } else {
                previousMenu = ratings;
                if (currentMenu == previousMenu) startMenu = true;
            }
        }
    };

    public void closeMenu() {
        if (currentMenu != null) {
            currentMenu.close(true);
            currentMenu = null;
            System.gc();
        }
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

    private boolean checkclick(FloatingActionButton button, boolean check) {

        if (check) {
            button.setColorNormal(colorAccent);
            return false;
        }
        else {
            button.setColorNormal(primaryColor);
            if (button == custommi) {
                distances.close(true);
                main.callCustommiDialog();
            }
            else if (button == pricerange) {
                prices.close(true);
                main.callRangeDialog("Choose Price Range", "$");
            }
            else if (button == contentrange) {
                contents.close(true);
                main.callRangeDialog("Choose ABV Range", "%");
            }
            else if (button == ratingrange) {
                ratings.close(true);
                main.callRangeDialog("Choose Rating Range", "avg");
            }
            return true;
        }
    }

    public void setFilterButtons() {
        custommi.setLabelText(custommi_miles + "mi");
        pricerange.setLabelText("$" + pricerange_low + " to $" + pricerange_high);
        contentrange.setLabelText(contentrange_low + "% to " + contentrange_high + "%");
        ratingrange.setLabelText(ratingrange_low + " to " + ratingrange_high);

        if (typesButtonPressed / 4 == 1) {
            wines.setColorNormal(primaryColor);
        }
        if (typesButtonPressed / 2 % 2 == 1) {
            beers.setColorNormal(primaryColor);
        }
        if (typesButtonPressed % 2 == 1) {
            liquors.setColorNormal(primaryColor);
        }
        if (distancesButtonPressed / 8 == 1) {
            twomi.setColorNormal(primaryColor);
            milesPrevious = twomi;
        }
        if (distancesButtonPressed / 4 % 2 == 1) {
            fivemi.setColorNormal(primaryColor);
            milesPrevious = fivemi;
        }
        if (distancesButtonPressed / 2 % 2 == 1) {
            tenmi.setColorNormal(primaryColor);
            milesPrevious = tenmi;
        }
        if (distancesButtonPressed % 2 == 1) {
            custommi.setColorNormal(primaryColor);
            milesPrevious = custommi;
        }
        if (pricesButtonPressed / 4 == 1) {
            hilowprice.setColorNormal(primaryColor);
            pricePrevious = hilowprice;
        }
        if (pricesButtonPressed / 2 % 2 == 1) {
            lowhiprice.setColorNormal(primaryColor);
            pricePrevious = lowhiprice;
        }
        if (pricesButtonPressed % 2 == 1) {
            pricerange.setColorNormal(primaryColor);
            pricePrevious = pricerange;
        }
        if (contentsButtonPressed / 4 == 1) {
            hilowcontent.setColorNormal(primaryColor);
            abvPrevious = hilowcontent;
        }
        if (contentsButtonPressed / 2 % 2 == 1) {
            lowhicontent.setColorNormal(primaryColor);
            abvPrevious = lowhicontent;
        }
        if (contentsButtonPressed % 2 == 1) {
            contentrange.setColorNormal(primaryColor);
            abvPrevious = contentrange;
        }
        if (ratingsButtonPressed / 4 == 1) {
            hilowrating.setColorNormal(primaryColor);
            ratingPrevious = hilowrating;
        }
        if (ratingsButtonPressed / 2 % 2 == 1) {
            lowhirating.setColorNormal(primaryColor);
            ratingPrevious = lowhirating;
        }
        if (ratingsButtonPressed % 2 == 1) {
            ratingrange.setColorNormal(primaryColor);
            ratingPrevious = ratingrange;
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
        distances.setX(25 + segments - 5);
        prices.setX(25 + 2 * segments);
        contents.setX(-25 - segments + 15);
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
        typesButtonPressed = mPrefs.getInt("TYPESBUTTONS", TYPESBUTTONS);
        distancesButtonPressed = mPrefs.getInt("DISTANCESBUTTONS", DISTANCESBUTTONS);
        pricesButtonPressed = mPrefs.getInt("PRICESBUTTONS", PRICESBUTTONS);
        contentsButtonPressed = mPrefs.getInt("CONTENTSBUTTONS", CONTENTSBUTTONS);
        ratingsButtonPressed = mPrefs.getInt("RATINGSBUTTONS", RATINGSBUTTONS);

        custommi_miles = mPrefs.getInt("CUSTOMMI_MILES", CUSTOMMI_MILES);
        pricerange_low = mPrefs.getInt("PRICE_RANGE_LOW", PRICE_RANGE_LOW);
        pricerange_high = mPrefs.getInt("PRICE_RANGE_HIGH", PRICE_RANGE_HIGH);
        contentrange_low = mPrefs.getInt("CONTENT_RANGE_LOW", CONTENT_RANGE_LOW);
        contentrange_high = mPrefs.getInt("CONTENT_RANGE_HIGH", CONTENT_RANGE_HIGH);
        ratingrange_low = mPrefs.getInt("RATING_RANGE_LOW", RATING_RANGE_LOW);
        ratingrange_high = mPrefs.getInt("RATING_RANGE_HIGH", RATING_RANGE_HIGH);

        typeMenuOpened = mPrefs.getBoolean("TYPESMENUOPENED", TYPESMENUOPENED);
        distantsMenuOpened = mPrefs.getBoolean("DISTANCESMENUOPENED", DISTANCESMENUOPENED);
        pricesMenuOpened = mPrefs.getBoolean("PRICESMENUOPENED", PRICESMENUOPENED);
        contentsMenuOpened = mPrefs.getBoolean("CONTENTSMENUOPENED", CONTENTSMENUOPENED);
        ratingsMenuOpened = mPrefs.getBoolean("RATINGSMENUOPENED", RATINGSMENUOPENED);

        setFilterButtons();
    }

    public void saveSharedPref(SharedPreferences.Editor ed) {
        //store all color states into universal sharedpreference
        ed.putInt("TYPESBUTTONS", typesButtonPressed);
        ed.putInt("DISTANCESBUTTONS", distancesButtonPressed);
        ed.putInt("PRICESBUTTONS", pricesButtonPressed);
        ed.putInt("CONTENTSBUTTONS", contentsButtonPressed);
        ed.putInt("RATINGSBUTTONS", ratingsButtonPressed);

        ed.putInt("CUSTOMMI_MILES", custommi_miles);
        ed.putInt("PRICE_RANGE_LOW", pricerange_low);
        ed.putInt("PRICE_RANGE_HIGH", pricerange_high);
        ed.putInt("CONTENT_RANGE_LOW", contentrange_low);
        ed.putInt("CONTENT_RANGE_HIGH", contentrange_high);
        ed.putInt("RATING_RANGE_LOW", ratingrange_low);
        ed.putInt("RATING_RANGE_HIGH", ratingrange_high);

        ed.putBoolean("TYPESMENUOPENED", typeMenuOpened);
        ed.putBoolean("DISTANCESMENUOPENED", distantsMenuOpened);
        ed.putBoolean("PRICESMENUOPENED", pricesMenuOpened);
        ed.putBoolean("CONTENTSMENUOPENED", contentsMenuOpened);
        ed.putBoolean("RATINGSMENUOPENED", ratingsMenuOpened);
    }

    public void setCustommi(int radius) {
        custommi_miles = radius;
        custommi.setLabelText(radius + "mi");
        custommi.setLabelVisibility(View.VISIBLE);
    }

    public void setPriceRange(int low, int high) {

        Log.v("DATA", "The low and high for setPriceRange is " + low + " and " + high);

        pricerange_low = low;
        pricerange_high = high;
        pricerange.setLabelText("$" + low + " to $" + high);
        pricerange.setLabelVisibility(View.VISIBLE);
    }

    public void setContentRange(int low, int high) {

        Log.v("DATA", "The low and high for setContentRange is " + low + "% and " + high);

        contentrange_low = low;
        contentrange_high = high;
        contentrange.setLabelText(low + "% to " + high + "%");
        contentrange.setLabelVisibility(View.VISIBLE);
    }

    public void setRatingRange(int low, int high) {

        Log.v("DATA", "The low and high for setRatingRange is " + low + " and " + high);

        ratingrange_low = low;
        ratingrange_high = high;
        ratingrange.setLabelText(low + " to " + high);
        ratingrange.setLabelVisibility(View.VISIBLE);
    }*/
}
