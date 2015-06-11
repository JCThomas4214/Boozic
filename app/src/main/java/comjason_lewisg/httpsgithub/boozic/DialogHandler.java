package comjason_lewisg.httpsgithub.boozic;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by jlew on 6/11/15.
 */
public class DialogHandler {

    public void onCreate() {

    }

    public void OpenFeedbackDialog(MainActivity m) {

        MaterialDialog dialog = new MaterialDialog.Builder(m)
                .title("Send us feedback")
                .customView(R.layout.feedback_dialog, false)
                .positiveText("SEND")
                .negativeText("CANCEL")
                .positiveColorRes(R.color.ColorPrimary)
                .negativeColorRes(R.color.ColorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog item) {
                        EditText input = (EditText) item.findViewById(R.id.feedback_dialog);
                        Log.w("myApp", "The output when positive is " + input.getText());
                    }
                })
                .build();

        EditText input = (EditText) dialog.findViewById(R.id.feedback_dialog);
        input.setHint("What can we improve upon?");

        dialog.show();
    }
}
