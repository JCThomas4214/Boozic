package comjason_lewisg.httpsgithub.boozic.Handlers;


import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

public class DialogHandler {

    public void onCreate() {

    }

    public void OpenFeedbackDialog(final MainActivity m, final int colorAccent_id) {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Send us feedback")
                .inputMaxLength(400, R.color.ColorPrimaryDark)
                .input("What can we improve upon?", "", new MaterialDialog.InputCallback() {
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.length() == 0) {
                            Log.w("myApp", "There is no String");
                            OpenFeedbackDialog(m, colorAccent_id);
                        }
                    }
                })
                .positiveText("SEND")
                .negativeText("CANCEL")
                .widgetColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .positiveColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/" + searchForRes(colorAccent_id), null, null))
                .negativeColorRes(m.getResources().getIdentifier("comjason_lewisg.httpsgithub.boozic:color/"+searchForRes(colorAccent_id), null, null))
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

    public void OpenLegalDialog(final MainActivity m, final int colorAccent_id) {

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
}