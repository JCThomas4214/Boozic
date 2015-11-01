package comjason_lewisg.httpsgithub.boozic.Handlers;


import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FilterMenuHandler {

    private MainActivity main;

    static final int TYPESBUTTONS = 0b111;
    static final int DISTANCESBUTTONS = 0b100;
    static final int ORDERBUTTONS = 0b100000;
    static final int PRICECONTRATEBUTTONS = 0b000;


    public int typesButtonPressed;
    public int distancesButtonPressed;
    public int orderButtonPressed;
    public int priceContRateButtonPressed;

    static final int CUSTOMMI_MILES = 15;
    static final int PRICE_RANGE_LOW = 0;
    static final int PRICE_RANGE_HIGH = 30;
    static final int CONTENT_RANGE_LOW = 0;
    static final int CONTENT_RANGE_HIGH = 60;
    static final int RATING_RANGE_LOW = 0;
    static final int RATING_RANGE_HIGH = 5;

    public boolean menuOpen = false;

    ImageView button_liquors;
    ImageView button_beers;
    ImageView button_wines;
    TextView button_twomi;
    TextView button_fivemi;
    TextView button_custommi;
    ImageView button_price_lowhi;
    ImageView button_price_hilow;
    ImageView button_price_range;
    ImageView button_content_lowhi;
    ImageView button_content_hilow;
    ImageView button_content_range;
    ImageView button_rating_lowhi;
    ImageView button_rating_hilow;
    ImageView button_rating_range;

    private ImageView button_liquors_ring;
    private ImageView button_beers_ring;
    private ImageView button_wines_ring;
    private ImageView button_twomi_ring;
    private ImageView button_fivemi_ring;
    private ImageView button_custommi_ring;
    private ImageView button_price_lowhi_ring;
    private ImageView button_price_hilow_ring;
    private ImageView button_price_range_ring;
    private ImageView button_content_lowhi_ring;
    private ImageView button_content_hilow_ring;
    private ImageView button_content_range_ring;
    private ImageView button_rating_lowhi_ring;
    private ImageView button_rating_hilow_ring;
    private ImageView button_rating_range_ring;

    public int custommi_miles;
    public int pricerange_low;
    public int pricerange_high;
    public int contentrange_low;
    public int contentrange_high;
    public int ratingrange_low;
    public int ratingrange_high;

    private ImageView milesPrevious;
    private ImageView orderPrevious;

    private View view;

    Animation menu_anim_in;
    Animation menu_anim_out;

    public void onCreate() {}

    public FilterMenuHandler(MainActivity m, int primaryColor) {
        main = m;
        setColor(m, primaryColor);

        button_liquors = (ImageView) m.findViewById(R.id.liquors);
        button_beers = (ImageView) m.findViewById(R.id.beers);
        button_wines = (ImageView) m.findViewById(R.id.wines);
        button_twomi = (TextView) m.findViewById(R.id.twomi);
        button_fivemi = (TextView) m.findViewById(R.id.fivemi);
        button_custommi = (TextView) m.findViewById(R.id.custommi);
        button_price_lowhi = (ImageView) m.findViewById(R.id.lowhi);
        button_price_hilow = (ImageView) m.findViewById(R.id.hilow);
        button_price_range = (ImageView) m.findViewById(R.id.pricerange);
        button_content_lowhi = (ImageView) m.findViewById(R.id.lowhicontent);
        button_content_hilow = (ImageView) m.findViewById(R.id.hilowcontent);
        button_content_range = (ImageView) m.findViewById(R.id.rangecontent);
        button_rating_lowhi = (ImageView) m.findViewById(R.id.lowhirating);
        button_rating_hilow = (ImageView) m.findViewById(R.id.hilowrating);
        button_rating_range = (ImageView) m.findViewById(R.id.rangerating);

        button_liquors_ring = (ImageView) m.findViewById(R.id.liquors_ring);
        button_beers_ring = (ImageView) m.findViewById(R.id.beers_ring);
        button_wines_ring = (ImageView) m.findViewById(R.id.wines_ring);
        button_twomi_ring = (ImageView) m.findViewById(R.id.distance_twomi_ring);
        button_fivemi_ring = (ImageView) m.findViewById(R.id.distance_fivemi_ring);
        button_custommi_ring = (ImageView) m.findViewById(R.id.distance_range_ring);
        button_price_lowhi_ring = (ImageView) m.findViewById(R.id.price_lowhi_ring);
        button_price_hilow_ring = (ImageView) m.findViewById(R.id.price_hilow_ring);
        button_price_range_ring = (ImageView) m.findViewById(R.id.price_range_ring);
        button_content_lowhi_ring = (ImageView) m.findViewById(R.id.content_lowhi_ring);
        button_content_hilow_ring = (ImageView) m.findViewById(R.id.content_hilow_ring);
        button_content_range_ring = (ImageView) m.findViewById(R.id.content_range_ring);
        button_rating_lowhi_ring = (ImageView) m.findViewById(R.id.rating_lowhi_ring);
        button_rating_hilow_ring = (ImageView) m.findViewById(R.id.rating_hilow_ring);
        button_rating_range_ring = (ImageView) m.findViewById(R.id.rating_range_ring);

        milesPrevious = button_twomi_ring;
        orderPrevious = button_price_lowhi_ring;

        view = m.findViewById(R.id.filter_menu_layout);
        menu_anim_out = fadeOutLayout(view, 0);
        menu_anim_in = fadeInLayout(view, 300);

        button_wines.setOnClickListener(clickListenerTypesFilter);
        button_liquors.setOnClickListener(clickListenerTypesFilter);
        button_beers.setOnClickListener(clickListenerTypesFilter);
        button_twomi.setOnClickListener(clickListenerDistancesFilter);
        button_fivemi.setOnClickListener(clickListenerDistancesFilter);
        button_custommi.setOnClickListener(clickListenerDistancesFilter);
        button_price_lowhi.setOnClickListener(clickListenerPricesFilter);
        button_price_hilow.setOnClickListener(clickListenerPricesFilter);
        button_price_range.setOnClickListener(clickListenerPricesFilter);
        button_content_lowhi.setOnClickListener(clickListenerContentsFilter);
        button_content_hilow.setOnClickListener(clickListenerContentsFilter);
        button_content_range.setOnClickListener(clickListenerContentsFilter);
        button_rating_lowhi.setOnClickListener(clickListenerRatingsFilter);
        button_rating_hilow.setOnClickListener(clickListenerRatingsFilter);
        button_rating_range.setOnClickListener(clickListenerRatingsFilter);
    }

    public View.OnClickListener clickListenerTypesFilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wines:
                    if ((typesButtonPressed / 4) != 1) {
                        typesButtonPressed += 0b100;
                        button_wines_ring.setVisibility(View.VISIBLE);
                    } else {
                        typesButtonPressed -= 0b100;
                        button_wines_ring.setVisibility(View.GONE);
                    }

                    break;
                case R.id.beers:
                    if ((typesButtonPressed / 2) % 2 != 1) {
                        typesButtonPressed += 0b010;
                        button_beers_ring.setVisibility(View.VISIBLE);
                    } else {
                        typesButtonPressed -= 0b010;
                        button_beers_ring.setVisibility(View.GONE);
                    }
                    break;
                case R.id.liquors:
                    if ((typesButtonPressed) % 2 != 1) {
                        typesButtonPressed += 0b001;
                        button_liquors_ring.setVisibility(View.VISIBLE);
                    } else {
                        typesButtonPressed -= 0b001;
                        button_liquors_ring.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerDistancesFilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.twomi:

                    if (milesPrevious == button_twomi_ring) {
                        if (distancesButtonPressed == 0b100) {
                            distancesButtonPressed = 0b000;
                            milesPrevious.setVisibility(View.INVISIBLE);
                        } else {
                            distancesButtonPressed = 0b100;
                            milesPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        distancesButtonPressed = 0b100;
                        milesPrevious.setVisibility(View.INVISIBLE);
                        milesPrevious = button_twomi_ring;
                        milesPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.fivemi:
                    if (milesPrevious == button_fivemi_ring) {
                        if (distancesButtonPressed == 0b010) {
                            distancesButtonPressed = 0b000;
                            milesPrevious.setVisibility(View.INVISIBLE);
                        } else {
                            distancesButtonPressed = 0b010;
                            milesPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        distancesButtonPressed = 0b010;
                        milesPrevious.setVisibility(View.INVISIBLE);
                        milesPrevious = button_fivemi_ring;
                        milesPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.custommi:
                    if (milesPrevious == button_custommi_ring) {
                        if (distancesButtonPressed == 0b001) {
                            distancesButtonPressed = 0b000;
                            milesPrevious.setVisibility(View.INVISIBLE);
                        } else {
                            distancesButtonPressed = 0b001;
                            milesPrevious.setVisibility(View.VISIBLE);
                            main.callCustommiDialog();
                        }
                    } else {
                        distancesButtonPressed = 0b001;
                        milesPrevious.setVisibility(View.INVISIBLE);
                        milesPrevious = button_custommi_ring;
                        milesPrevious.setVisibility(View.VISIBLE);
                        main.callCustommiDialog();
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerPricesFilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lowhi:
                    if (orderPrevious == button_price_lowhi_ring) {
                        if (orderButtonPressed == 0b100000) {
                            orderButtonPressed = 0b000000;
                            orderPrevious.setVisibility(View.GONE);
                        } else {
                            orderButtonPressed = 0b100000;
                            orderPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        orderButtonPressed = 0b100000;
                        orderPrevious.setVisibility(View.GONE);
                        orderPrevious = button_price_lowhi_ring;
                        orderPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.hilow:
                    if (orderPrevious == button_price_hilow_ring) {
                        if (orderButtonPressed == 0b010000) {
                            orderButtonPressed = 0b000000;
                            orderPrevious.setVisibility(View.GONE);
                        } else {
                            orderButtonPressed = 0b010000;
                            orderPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        orderButtonPressed = 0b010000;
                        orderPrevious.setVisibility(View.GONE);
                        orderPrevious = button_price_hilow_ring;
                        orderPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.pricerange:
                    if ((priceContRateButtonPressed) / 4 != 1) {
                        priceContRateButtonPressed += 0b100;
                        main.callRangeDialog("Choose Price Range", "$");
                        button_price_range_ring.setVisibility(View.VISIBLE);
                    } else {
                        priceContRateButtonPressed -= 0b100;
                        button_price_range_ring.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerContentsFilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lowhicontent:
                    if (orderPrevious == button_content_lowhi_ring) {
                        if (orderButtonPressed == 0b001000) {
                            orderButtonPressed = 0b000000;
                            orderPrevious.setVisibility(View.GONE);
                        } else {
                            orderButtonPressed = 0b001000;
                            orderPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        orderButtonPressed = 0b001000;
                        orderPrevious.setVisibility(View.GONE);
                        orderPrevious = button_content_lowhi_ring;
                        orderPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.hilowcontent:
                    if (orderPrevious == button_content_hilow_ring) {
                        if (orderButtonPressed == 0b000100) {
                            orderButtonPressed = 0b000000;
                            orderPrevious.setVisibility(View.GONE);
                        } else {
                            orderButtonPressed = 0b000100;
                            orderPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        orderButtonPressed = 0b000100;
                        orderPrevious.setVisibility(View.GONE);
                        orderPrevious = button_content_hilow_ring;
                        orderPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.rangecontent:
                    if ((priceContRateButtonPressed) / 2 % 2 != 1) {
                        priceContRateButtonPressed += 0b010;
                        main.callRangeDialog("Choose ABV Range", "%");
                        button_content_range_ring.setVisibility(View.VISIBLE);
                    } else {
                        priceContRateButtonPressed -= 0b010;
                        button_content_range_ring.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    public View.OnClickListener clickListenerRatingsFilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lowhirating:
                    if (orderPrevious == button_rating_lowhi_ring) {
                        if (orderButtonPressed == 0b000010) {
                            orderButtonPressed = 0b000000;
                            orderPrevious.setVisibility(View.GONE);
                        } else {
                            orderButtonPressed = 0b000010;
                            orderPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        orderButtonPressed = 0b000010;
                        orderPrevious.setVisibility(View.GONE);
                        orderPrevious = button_rating_lowhi_ring;
                        orderPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.hilowrating:
                    if (orderPrevious == button_rating_hilow_ring) {
                        if (orderButtonPressed == 0b000001) {
                            orderButtonPressed = 0b000000;
                            orderPrevious.setVisibility(View.GONE);
                        } else {
                            orderButtonPressed = 0b000001;
                            orderPrevious.setVisibility(View.VISIBLE);
                        }
                    } else {
                        orderButtonPressed = 0b000001;
                        orderPrevious.setVisibility(View.GONE);
                        orderPrevious = button_rating_hilow_ring;
                        orderPrevious.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.rangerating:
                    if ((priceContRateButtonPressed) % 2 != 1) {
                        priceContRateButtonPressed += 0b001;
                        main.callRangeDialog("Choose Rating Range", "avg");
                        button_rating_range_ring.setVisibility(View.VISIBLE);
                    } else {
                        priceContRateButtonPressed -= 0b001;
                        button_rating_range_ring.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    public void setFilterButtons() {

        if (typesButtonPressed / 4 == 1) {
            button_wines_ring.setVisibility(View.VISIBLE);
        }
        if (typesButtonPressed / 2 % 2 == 1) {
            button_beers_ring.setVisibility(View.VISIBLE);
        }
        if (typesButtonPressed % 2 == 1) {
            button_liquors_ring.setVisibility(View.VISIBLE);
        }
        if (distancesButtonPressed / 4 == 1) {
            button_twomi_ring.setVisibility(View.VISIBLE);
            milesPrevious = button_twomi_ring;
        }
        if (distancesButtonPressed / 2 % 2 == 1) {
            button_fivemi_ring.setVisibility(View.VISIBLE);
            milesPrevious = button_fivemi_ring;
        }
        if (distancesButtonPressed % 2 == 1) {
            button_custommi_ring.setVisibility(View.VISIBLE);
            milesPrevious = button_custommi_ring;
        }
        if (orderButtonPressed / 32 == 1) {
            button_price_lowhi_ring.setVisibility(View.VISIBLE);
            orderPrevious = button_price_lowhi_ring;
        }
        if (orderButtonPressed / 16 % 2 == 1) {
            button_price_hilow_ring.setVisibility(View.VISIBLE);
            orderPrevious = button_price_hilow_ring;
        }
        if (priceContRateButtonPressed / 4 == 1) {
            button_price_range_ring.setVisibility(View.VISIBLE);
        }
        if (orderButtonPressed / 8 % 2 == 1) {
            button_content_lowhi_ring.setVisibility(View.VISIBLE);
            orderPrevious = button_content_lowhi_ring;
        }
        if (orderButtonPressed / 4 % 2 == 1) {
            button_content_hilow_ring.setVisibility(View.VISIBLE);
            orderPrevious = button_content_hilow_ring;
        }
        if (priceContRateButtonPressed / 2 % 2 == 1) {
            button_content_range_ring.setVisibility(View.VISIBLE);
        }
        if (orderButtonPressed / 2 % 2 == 1) {
            button_rating_lowhi_ring.setVisibility(View.VISIBLE);
            orderPrevious = button_rating_lowhi_ring;
        }
        if (orderButtonPressed % 2 == 1) {
            button_rating_hilow_ring.setVisibility(View.VISIBLE);
            orderPrevious = button_rating_hilow_ring;
        }
        if (priceContRateButtonPressed % 2 == 1) {
            button_rating_range_ring.setVisibility(View.VISIBLE);
        }
    }

    private void setColor(MainActivity m, int primaryColor) {
        ImageView filter_background = (ImageView) m.findViewById(R.id.distance_range_ring_inner);
        filter_background.setColorFilter(primaryColor);
        filter_background = (ImageView) m.findViewById(R.id.distance_twomi_ring_inner);
        filter_background.setColorFilter(primaryColor);
        filter_background = (ImageView) m.findViewById(R.id.distance_fivemi_ring_inner);
        filter_background.setColorFilter(primaryColor);
    }

    public void hideFilterMenu() {
        menuOpen = false;

        view.startAnimation(menu_anim_out);
    }

    public void showFilterMenu() {
        menuOpen = true;

        View view = main.findViewById(R.id.filter_menu_layout);
        view.setVisibility(View.VISIBLE);

        view.startAnimation(menu_anim_in);
    }

    public void initiateSharedPref(SharedPreferences mPrefs) {
        //when resume, pull saves states for each button
        typesButtonPressed = mPrefs.getInt("TYPESBUTTONS", TYPESBUTTONS);
        distancesButtonPressed = mPrefs.getInt("DISTANCESBUTTONS", DISTANCESBUTTONS);
        priceContRateButtonPressed = mPrefs.getInt("PRICECONTRATEBUTTONS", PRICECONTRATEBUTTONS);
        orderButtonPressed = mPrefs.getInt("ORDERBUTTONS", ORDERBUTTONS);

        custommi_miles = mPrefs.getInt("CUSTOMMI_MILES", CUSTOMMI_MILES);
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
        ed.putInt("TYPESBUTTONS", typesButtonPressed);
        ed.putInt("DISTANCESBUTTONS", distancesButtonPressed);
        ed.putInt("PRICECONTRATEBUTTONS", priceContRateButtonPressed);
        ed.putInt("ORDERBUTTONS", orderButtonPressed);

        ed.putInt("CUSTOMMI_MILES", custommi_miles);
        ed.putInt("PRICE_RANGE_LOW", pricerange_low);
        ed.putInt("PRICE_RANGE_HIGH", pricerange_high);
        ed.putInt("CONTENT_RANGE_LOW", contentrange_low);
        ed.putInt("CONTENT_RANGE_HIGH", contentrange_high);
        ed.putInt("RATING_RANGE_LOW", ratingrange_low);
        ed.putInt("RATING_RANGE_HIGH", ratingrange_high);
    }

    private Animation fadeInLayout(final View view, long startTime) {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(main, R.anim.fade_in);
        fadeInAnimation.setStartOffset(startTime);
        fadeInAnimation.setDuration(350);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return fadeInAnimation;
    }

    private Animation fadeOutLayout(final View view, long startTime) {
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(main, R.anim.fade_out);
        fadeOutAnimation.setStartOffset(startTime);
        fadeOutAnimation.setDuration(200);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return fadeOutAnimation;
    }

    public void setCustommi(int radius) {
        custommi_miles = radius;
    }

    public void setPriceRange(int low, int high) {

        Log.v("DATA", "The low and high for setPriceRange is " + low + " and " + high);

        pricerange_low = low;
        pricerange_high = high;
    }

    public void setContentRange(int low, int high) {

        Log.v("DATA", "The low and high for setContentRange is " + low + "% and " + high);

        contentrange_low = low;
        contentrange_high = high;
    }

    public void setRatingRange(int low, int high) {

        Log.v("DATA", "The low and high for setRatingRange is " + low + " and " + high);

        ratingrange_low = low;
        ratingrange_high = high;
    }
}
