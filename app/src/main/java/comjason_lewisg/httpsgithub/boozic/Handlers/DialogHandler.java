package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.ProductStorageModel;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class DialogHandler {

    public void onCreate() {
    }

    public void OpenFeedbackDialog(final MainActivity m) {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Send us feedback")
                .inputRange(0, 250, m.getColorPrimary())
                .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT)
                .input("What can we improve upon?", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        //something
                    }
                })
                .positiveText("SEND")
                .negativeText("CANCEL")
                .widgetColor(m.getColorAccent())
                .positiveColor(m.getColorAccent())
                .negativeColor(m.getColorAccent())
                .build();

        EditText input = dialog.getInputEditText();
        input.setSingleLine(false);
        input.setVerticalScrollBarEnabled(true);
        input.setBackground(null);
        input.setLines(5);
        input.setGravity(Gravity.TOP);

        dialog.show();
    }

    public void OpenLegalDialog(final MainActivity m) {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Legal and Liability")
                .content("This is where the legal documentation will go.")
                .positiveText("OK")
                .positiveColor(m.getColorAccent())
                .build();

        dialog.show();
    }

    public void OpenRangeDialog(final MainActivity m, String title, final String units) {
        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title(title)
                .customView(R.layout.custom_range, true)
                .positiveText("SET")
                .positiveColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText low_input = (EditText) view.findViewById(R.id.range_low_input);
                        String low = low_input.getText().toString();

                        EditText high_input = (EditText) view.findViewById(R.id.range_high_input);
                        String high = high_input.getText().toString();

                        checkDialog(m, units, low, high);
                    }
                })

                .build();

        View view = dialog.getCustomView();

        TextView low_curr = (TextView) view.findViewById(R.id.range_low_currency);
        TextView low_units = (TextView) view.findViewById(R.id.range_low_units);
        EditText low_input = (EditText) view.findViewById(R.id.range_low_input);

        TextView high_curr = (TextView) view.findViewById(R.id.range_high_currency);
        TextView high_units = (TextView) view.findViewById(R.id.range_high_units);
        EditText high_input = (EditText) view.findViewById(R.id.range_high_input);

        setUnits(units, low_curr, low_units, low_input, high_curr, high_units, high_input, m);

        dialog.show();
    }

    public void OpenCustomMileDialog(final MainActivity m) {
        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Custom Mile Radius")
                .customView(R.layout.custom_mi, true)
                .positiveText("SET")
                .positiveColor(m.getColorAccent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText miles = (EditText) view.findViewById(R.id.custom_mi_input);
                        String radius = miles.getText().toString();

                        checkDialog(m, radius);
                    }
                })
                .build();

        View view = dialog.getCustomView();
        EditText miles = (EditText) view.findViewById(R.id.custom_mi_input);
        setMiles(m, miles);

        dialog.show();
    }

    public void UpdateContainer(final ProductActivity p) {
        CharSequence[] items = {"custom", "handle", "fifth", "(1) bottle", "(6) bottle", "(12) bottle", "(6) can", "(12) can", "(24) can"};
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Select Container")
                .items(items)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.v("CONTAINER", "string = " + text);
                        if (text == null) UpdateContainer(p);
                        else p.updatedModel.updateContainer((String) text);
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText("CANCEL")
                .neutralText("SKIP")
                .widgetColorRes(R.color.NavUnchecked)
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateAbv(p);
                        //save selected
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateAbv(p);
                    }
                })
                .build();

        dialog.show();
    }

    public void UpdateAbv(final ProductActivity p) {
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Input ABV")
                .customView(R.layout.input_abv, true)
                .positiveText("NEXT")
                .negativeText("BACK")
                .neutralText("SKIP")
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        View view = dialog.getCustomView();

                        EditText percent = (EditText) view.findViewById(R.id.abv_dia_input);
                        p.updatedModel.updateABV(p.changeToDouble(percent.getText().toString()));

                        UpdateStore(p, true);
                        //save input
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateStore(p, true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateContainer(p);
                    }
                })
                .build();
        dialog.show();
    }

    public void UpdateStore(final ProductActivity p, final boolean cameFrom) {
        String tmp;
        if (cameFrom) tmp = "BACK";
        else tmp = "CANCEL";

        final double[] distances = {1.3, 1.8, 2.34};
        final CharSequence[] stores = {"ABC liquor", "Publix Liquor", "Walmart"};
        CharSequence[] combined = storeDistCombine(distances, stores);
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Select Store")
                .items(combined)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text == null) UpdateStore(p, cameFrom);
                        else {
                            p.updatedModel.updateStore((String) stores[which], distances[which]);
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText(tmp)
                .widgetColorRes(R.color.NavUnchecked)
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdatePrice(p, cameFrom);
                        //save selected
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (cameFrom) UpdateAbv(p);
                    }
                })
                .build();
        dialog.show();
    }

    public void UpdatePrice(final ProductActivity p, final boolean cameFrom) {
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Update Price")
                .customView(R.layout.input_price, true)
                .positiveText("DONE")
                .negativeText("BACK")
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        EditText price = (EditText) view.findViewById(R.id.price_dia_input);
                        p.updatedModel.updateStorePrice(p.changeToInt(price.getText().toString()));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateStore(p, cameFrom);
                    }
                })
                .build();
        dialog.show();
    }

    private void setMiles(MainActivity m, EditText miles) {
        miles.setText(m.FMHandle.custommi_miles + "");
    }

    private void setUnits(String units, TextView low_curr, TextView low_units, EditText low_input, TextView high_curr,
                          TextView high_units, EditText high_input, MainActivity m) {
        switch (units) {
            case "$":
                low_curr.setVisibility(View.VISIBLE);
                low_units.setVisibility(View.GONE);
                high_curr.setVisibility(View.VISIBLE);
                high_units.setVisibility(View.GONE);

                low_input.setText(m.FMHandle.pricerange_low + "");
                high_input.setText(m.FMHandle.pricerange_high + "");

                break;
            case "%":
                low_input.setText(m.FMHandle.contentrange_low + "");
                high_input.setText(m.FMHandle.contentrange_high + "");
                break;
            case "avg":
                low_units.setText("avg");
                high_units.setText("avg");

                low_input.setText(m.FMHandle.ratingrange_low + "");
                high_input.setText(m.FMHandle.ratingrange_high + "");
                break;
        }
    }

    private void checkDialog(MainActivity m, String radius) {

        int radiuss = changeToInt(radius);

        //set the filterbutton model's low/high variables
        m.FMHandle.setCustommi(radiuss);
    }

    private void checkDialog(MainActivity m, String units, String low, String high) {

        int loww = changeToInt(low);
        int highh = changeToInt(high);
        int tmp;

        //if user fucks up the input...
        if (highh < loww) {
            tmp = loww;
            loww = highh;
            highh = tmp;
        }

        //set the filterbutton model's low/high variables
        switch (units) {
            case "$":
                m.FMHandle.setPriceRange(loww, highh);
                break;
            case "%":
                m.FMHandle.setContentRange(loww, highh);
                break;
            case "avg":
                m.FMHandle.setRatingRange(loww, highh);
                break;
        }
    }

    private CharSequence[] storeDistCombine(double[] distances, CharSequence[] stores) {

        if (distances.length == stores.length) {
            CharSequence[] combine = new CharSequence[distances.length];
            for (int i = 0; i < distances.length; i++)
                combine[i] = stores[i] + " (" + distances[i] + "mi)";
            return combine;
        }
        return null;
    }

    private int changeToInt(String str) {
        return Integer.parseInt(str);
    }
}