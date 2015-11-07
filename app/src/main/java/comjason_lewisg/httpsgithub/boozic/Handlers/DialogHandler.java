package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blackcat.currencyedittext.CurrencyEditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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

        EditText low_input = (EditText) view.findViewById(R.id.range_low_input);
        EditText high_input = (EditText) view.findViewById(R.id.range_high_input);

        setUnits(units, low_input, high_input, m);

        if (units.equals("$")) {
            low_input.addTextChangedListener(makeTextWatcher(low_input, units));
            high_input.addTextChangedListener(makeTextWatcher(high_input, units));
        } else if (units.equals("%")) {
            low_input.addTextChangedListener(makeTextWatcher(low_input, units));
            high_input.addTextChangedListener(makeTextWatcher(high_input, units));
        } else {
            low_input.addTextChangedListener(makeTextWatcher(low_input, units));
            high_input.addTextChangedListener(makeTextWatcher(high_input, units));
        }

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
        CharSequence[] items = {"(1) bottle", "(6) bottle", "(12) bottle", "(6) can", "(12) can", "(24) can"};
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
                        UpdateAbv(p, true);
                        //save selected
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateAbv(p, true);
                    }
                })
                .build();

        dialog.show();
    }

    public void UpdateAbv(final ProductActivity p, final boolean isBeer) {
        String tmp;
        if (isBeer) tmp = "BACK";
        else tmp = "CANCEL";

        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Input ABV")
                .customView(R.layout.input_abv, true)
                .positiveText("NEXT")
                .negativeText(tmp)
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

                        UpdateStore(p, true, isBeer);
                        //save input
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateStore(p, true, isBeer);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (isBeer) UpdateContainer(p);
                    }
                })
                .build();

        View view = dialog.getCustomView();
        EditText input = (EditText) view.findViewById(R.id.abv_dia_input);
        input.addTextChangedListener(makeTextWatcher(input, "%"));

        dialog.show();
    }

    public void UpdateStore(final ProductActivity p, final boolean cameFrom, final boolean isBeer) {
        String tmp;
        if (cameFrom) tmp = "BACK";
        else tmp = "CANCEL";

        final double[] distances = {1.3, 1.8, 2.34};
        final CharSequence[] stores = {"ABC liquor", "Publix Liquor", "Walmart"};
        MaterialDialog dialog = new MaterialDialog.Builder(p)
                .title("Select Store")
                .items(stores)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (text == null) UpdateStore(p, cameFrom, isBeer);
                        else {
                            p.updatedModel.updateStore((String) stores[which], distances[which]);
                        }
                        return true;
                    }
                })
                .positiveText("NEXT")
                .negativeText(tmp)
                .neutralText("NOT FOUND")
                .widgetColorRes(R.color.NavUnchecked)
                .positiveColor(p.getAccentColor())
                .negativeColor(p.getAccentColor())
                .neutralColor(p.getAccentColor())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdatePrice(p, cameFrom, isBeer);
                        //save selected
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (cameFrom) UpdateAbv(p, isBeer);
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO: places and distance API
                    }
                })
                .build();
        dialog.show();
    }

    public void UpdatePrice(final ProductActivity p, final boolean cameFrom, final boolean isBeer) {
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
                        p.updatedModel.updateStorePrice(p.changeToDouble(price.getText().toString().replace("$", "")));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateStore(p, cameFrom, isBeer);
                    }
                })
                .build();

        View view = dialog.getCustomView();
        final EditText price = (EditText) view.findViewById(R.id.price_dia_input);
        price.addTextChangedListener(makeTextWatcher(price, "$"));

        dialog.show();
    }

    private TextWatcher makeTextWatcher(final EditText text, final String units) {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            private String current = "";
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(current)){
                    text.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[ avg,$,%,.]", "");
                    String formatted;

                    if (units.equals("$")) {
                        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                        formatted = NumberFormat.getCurrencyInstance().format(parsed);
                        current = formatted;
                        text.setText(formatted);
                        text.setSelection(current.length());
                    }
                    else if (units.equals("%")) {
                        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMinimumFractionDigits(2);
                        formatted = nf.format(parsed);
                        current = formatted;
                        text.setText(formatted + "%");
                        text.setSelection(current.length());
                    } else {
                        Integer parsed;
                        if (cleanString.isEmpty()) parsed = new Integer("0");
                        else parsed = new Integer(cleanString);

                        if (parsed % 10 >= 5) parsed = 5;
                        else parsed = parsed % 10;

                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMinimumIntegerDigits(1);
                        formatted = nf.format(parsed);
                        current = formatted;
                        text.setText(formatted + " avg");
                        text.setSelection(current.length());
                    }

                    text.addTextChangedListener(this);
                }
            }
        };

        return tw;
    }


    private void setMiles(MainActivity m, EditText miles) {
        miles.setText(m.FMHandle.custommi_miles + "");
    }

    private void setUnits(String units, EditText low_input, EditText high_input, MainActivity m) {

        DecimalFormat formatter = new DecimalFormat("#0.00");

        switch (units) {
            case "$":
                low_input.setText("$" + formatter.format(m.FMHandle.pricerange_low));
                high_input.setText("$" + formatter.format(m.FMHandle.pricerange_high));
                break;
            case "%":
                low_input.setText(formatter.format(m.FMHandle.contentrange_low) + "%");
                high_input.setText(formatter.format(m.FMHandle.contentrange_high) + "%");
                break;
            case "avg":
                low_input.setText(m.FMHandle.ratingrange_low + " avg");
                high_input.setText(m.FMHandle.ratingrange_high + " avg");
                break;
        }
    }

    private void checkDialog(MainActivity m, String radius) {
        //set the filterbutton model's low/high variables
        m.FMHandle.setCustommi(changeToInt(radius));
    }

    private void checkDialog(MainActivity m, String units, String low, String high) {

        int lowInt;
        int highInt;
        int tmpInt;
        double lowDouble;
        double highDouble;
        double tmpDouble;

        //set the filterbutton model's low/high variables
        switch (units) {
            case "$":
                lowDouble = changeToDouble(low.replace("$", ""));
                highDouble = changeToDouble(high.replace("$", ""));
                //if user fucks up the input...
                if (highDouble < lowDouble) {
                    tmpDouble = lowDouble;
                    lowDouble = highDouble;
                    highDouble = tmpDouble;
                }
                m.FMHandle.setPriceRange((float)lowDouble, (float)highDouble);
                break;
            case "%":
                lowDouble = changeToDouble(low.replace("%", ""));
                highDouble = changeToDouble(high.replace("%", ""));
                //if user fucks up the input...
                if (highDouble < lowDouble) {
                    tmpDouble = lowDouble;
                    lowDouble = highDouble;
                    highDouble = tmpDouble;
                }
                m.FMHandle.setContentRange((float)lowDouble, (float)highDouble);
                break;
            case "avg":
                lowInt = changeToInt(low.replace(" avg",""));
                highInt = changeToInt(high.replace(" avg",""));
                //if user fucks up the input...
                if (highInt < lowInt) {
                    tmpInt = lowInt;
                    lowInt = highInt;
                    highInt = tmpInt;
                }
                m.FMHandle.setRatingRange(lowInt, highInt);
                break;
        }
    }

    private int changeToInt(String str) {
        return Integer.parseInt(str);
    }
    public double changeToDouble(String str) {
        return Double.parseDouble(str);
    }
}