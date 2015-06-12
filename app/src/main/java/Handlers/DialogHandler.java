package Handlers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.reflect.Field;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

/**
 * Created by jlew on 6/11/15.
 */
public class DialogHandler {

    public void onCreate() {

    }

    public void OpenFeedbackDialog(final MainActivity m) {

        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Send us feedback")
                .inputMaxLength(400, R.color.ColorPrimaryDark)
                .input("What can we improve upon?", "", new MaterialDialog.InputCallback() {
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.length() == 0) {
                            Log.w("myApp", "There is no String");
                            OpenFeedbackDialog(m);
                        }
                    }
                })
                .positiveText("SEND")
                .negativeText("CANCEL")
                .widgetColorRes(R.color.ColorPrimary)
                .positiveColorRes(R.color.ColorPrimary)
                .negativeColorRes(R.color.ColorPrimary)
                .build();

        EditText input = (EditText) dialog.getInputEditText();
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES|InputType.TYPE_CLASS_TEXT);
        input.setSingleLine(false);
        input.setVerticalScrollBarEnabled(true);
        input.setBackground(null);
        input.setLines(7);
        input.setGravity(Gravity.TOP);

        dialog.show();
    }
}