package comjason_lewisg.httpsgithub.boozic.Handlers;


import android.app.Activity;
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
import comjason_lewisg.httpsgithub.boozic.R;

public class DialogHandler {

    public void onCreate() {}

    public void OpenFeedbackDialog(final Activity m, final int colorAccent_id, final int primarycolor) {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Send us feedback")
                .inputMaxLength(250, primarycolor)
                .input("What can we improve upon?", "", new MaterialDialog.InputCallback() {
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.length() == 0) {
                            Log.w("myApp", "There is no String");
                            OpenFeedbackDialog(m, colorAccent_id, primarycolor);
                        }
                    }
                })
                .positiveText("SEND")
                .negativeText("CANCEL")
                .widgetColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .positiveColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .negativeColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .build();

        EditText input = dialog.getInputEditText();
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES|InputType.TYPE_CLASS_TEXT);
        input.setSingleLine(false);
        input.setVerticalScrollBarEnabled(true);
        input.setBackground(null);
        input.setLines(5);
        input.setGravity(Gravity.TOP);

        dialog.show();
    }

    public void OpenLegalDialog(final Activity m, final int colorAccent_id) {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Legal and Liability")
                .content("This is where the legal documentation will go.")
                .positiveText("OK")
                .widgetColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .positiveColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .build();

        dialog.show();
    }

    public void OpenRangeDialog(final MainActivity m, final int colorAccent_id, String title, final String units) {
        int[] lowHighTmp;

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title(title)
                .customView(R.layout.custom_range, true)
                .positiveText("SET")
                .negativeText("CANCEL")
                .widgetColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .positiveColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .negativeColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
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

    public void OpenCustomMileDialog(final MainActivity m, final int colorAccent_id) {
        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Custom Mile Radius")
                .customView(R.layout.custom_mi, true)
                .positiveText("SET")
                .negativeText("CANCEL")
                .widgetColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .positiveColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .negativeColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
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

    private String searchForRes(int colorAccent_id) {
        String str = "";
      switch (colorAccent_id) {
          case 1:
              str = "ColorAccentDark";
              break;
          case 2:
              str = "ColorAccentDark2";
              break;
          case 3:
              str = "ColorAccentDark3";
              break;
          case 4:
              str = "ColorAccentDark4";
              break;
          case 5:
              str = "ColorAccentDark5";
              break;
      }
        return str;
    }

    private void setMiles(MainActivity m, EditText miles) {
        miles.setText(m.FBhandle.custommi_miles + "");
    }

    private void setUnits(String units, TextView low_curr, TextView low_units, EditText low_input, TextView high_curr,
                          TextView high_units, EditText high_input, MainActivity m) {
        switch (units) {
            case "$":
                low_curr.setVisibility(View.VISIBLE);
                low_units.setVisibility(View.GONE);
                high_curr.setVisibility(View.VISIBLE);
                high_units.setVisibility(View.GONE);

                low_input.setText(m.FBhandle.pricerange_low + "");
                high_input.setText(m.FBhandle.pricerange_high + "");

                break;
            case "%":
                low_input.setText(m.FBhandle.contentrange_low + "");
                high_input.setText(m.FBhandle.contentrange_high + "");
                break;
            case "avg":
                low_units.setText("avg");
                high_units.setText("avg");

                low_input.setText(m.FBhandle.ratingrange_low + "");
                high_input.setText(m.FBhandle.ratingrange_high + "");
                break;
        }
    }

    private void checkDialog(MainActivity m, String radius) {

        int radiuss = changeToInt(radius);

        //set the filterbutton model's low/high variables
        m.FBhandle.setCustommi(radiuss);
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
                m.FBhandle.setPriceRange(loww, highh);
                break;
            case "%":
                m.FBhandle.setContentRange(loww, highh);
                break;
            case "avg":
                m.FBhandle.setRatingRange(loww, highh);
                break;
        }
    }

    private int changeToInt(String str) {
        return Integer.parseInt(str);
    }
}