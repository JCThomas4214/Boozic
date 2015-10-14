package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.Activity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class FilterActionButtonHandler extends Activity{

    int colorAccent;
    int colorAccentDark;

    public FloatingActionMenu types;
    public FloatingActionMenu distances;
    public FloatingActionMenu prices;
    public FloatingActionMenu contents;

    public boolean winescheck = false;
    public boolean beerscheck = false;
    public boolean liquorscheck = false;
    public boolean twomicheck = false;
    public boolean fivemicheck = false;
    public boolean tenmicheck = false;
    public boolean custommicheck = false;
    public boolean hilowpricecheck = false;
    public boolean lowhipricecheck = false;
    public boolean pricerangecheck = false;
    public boolean hilowcontentcheck = false;
    public boolean lowhicontentcheck = false;
    public boolean contentrangecheck = false;

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

    public void onCreate() {}

    public void setColor(int a, int ad) {
        colorAccent = a;
        colorAccentDark = ad;
    }

    public void setActivity(MainActivity m) {


        types = (FloatingActionMenu) m.findViewById(R.id.fabtype);
        types.setOnMenuToggleListener(typesClickListener);
        distances = (FloatingActionMenu) m.findViewById(R.id.fabdist);
        distances.setOnMenuToggleListener(distancesClickListener);
        prices = (FloatingActionMenu) m.findViewById(R.id.fabprice);
        prices.setOnMenuToggleListener(pricesClickListener);
        contents = (FloatingActionMenu) m.findViewById(R.id.fabcontent);
        contents.setOnMenuToggleListener(contentsClickListener);

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
        pricerange = (FloatingActionButton) m.findViewById(R.id.range);
        pricerange.setOnClickListener(clickListenerfilter);
        hilowcontent = (FloatingActionButton) m.findViewById(R.id.hilowcontent);
        hilowcontent.setOnClickListener(clickListenerfilter);
        lowhicontent = (FloatingActionButton) m.findViewById(R.id.lowhicontent);
        lowhicontent.setOnClickListener(clickListenerfilter);
        contentrange = (FloatingActionButton) m.findViewById(R.id.rangecontent);
        contentrange.setOnClickListener(clickListenerfilter);

        types.setClosedOnTouchOutside(true);
        distances.setClosedOnTouchOutside(true);
        prices.setClosedOnTouchOutside(true);
        contents.setClosedOnTouchOutside(true);

        distances.setIconAnimated(false);
        prices.setIconAnimated(false);
        contents.setIconAnimated(false);
    }

    public View.OnClickListener clickListenerfilter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wines:
                    winescheck = checkclick(wines, winescheck);
                    break;
                case R.id.beers:
                    beerscheck = checkclick(beers, beerscheck);
                    break;
                case R.id.liquors:
                    liquorscheck = checkclick(liquors, liquorscheck);
                    break;
                case R.id.twomi:
                    twomicheck = checkclick(twomi, twomicheck);
                    break;
                case R.id.fivemi:
                    fivemicheck = checkclick(fivemi, fivemicheck);
                    break;
                case R.id.tenmi:
                    tenmicheck = checkclick(tenmi, tenmicheck);
                    break;
                case R.id.custommi:
                    custommicheck = checkclick(custommi, custommicheck);
                    break;
                case R.id.hilow:
                    hilowpricecheck = checkclick(hilowprice, hilowpricecheck);
                    break;
                case R.id.lowhi:
                    lowhipricecheck = checkclick(lowhiprice, lowhipricecheck);
                    break;
                case R.id.custom:
                    pricerangecheck = checkclick(pricerange, pricerangecheck);
                    break;
                case R.id.hilowcontent:
                    hilowcontentcheck = checkclick(hilowcontent, hilowcontentcheck);
                    break;
                case R.id.lowhicontent:
                    lowhicontentcheck = checkclick(lowhicontent, lowhicontentcheck);
                    break;
                case R.id.rangecontent:
                    contentrangecheck = checkclick(contentrange, contentrangecheck);
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

    private void checkOpen(FloatingActionMenu menu) {
        if (types.isOpened() && (types != menu))
            types.close(true);
        else if (distances.isOpened() && (distances != menu))
            distances.close(true);
        else if (prices.isOpened() && (prices != menu))
            prices.close(true);
        else if (contents.isOpened() && (contents != menu))
            contents.close(true);

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
}
