package Handlers;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import com.afollestad.materialdialogs.MaterialDialog;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.R;

/**
 * Created by jlew on 6/11/15.
 */
public class DialogHandler {

    public void onCreate() {

    }

    public void OpenFeedbackDialog(MainActivity m) {


        //Create the MaterialDialog object to start initiallizing attributes
        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Send us feedback")
                .customView(R.layout.feedback_dialog, false)
                .positiveText("SEND")
                .negativeText("CANCEL")
                .positiveColorRes(R.color.ColorPrimary)
                .negativeColorRes(R.color.ColorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {
                    //onPositive is the callback for when SEND is selected
                    @Override
                    public void onPositive(MaterialDialog item) {
                        //grabs the EditText XML and places in input
                        EditText input = (EditText) item.findViewById(R.id.feedback_dialog);
                        Log.w("myApp", "The output when positive is " + input.getText());
                    }
                })
                .build();

        //Again storing XML into input to set the Hint field for the input
        //before the Material Dialog object is shown
        EditText input = (EditText) dialog.findViewById(R.id.feedback_dialog);
        input.setHint("What can we improve upon?");

        dialog.show();
    }
}
