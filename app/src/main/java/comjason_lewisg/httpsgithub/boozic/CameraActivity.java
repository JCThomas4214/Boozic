package comjason_lewisg.httpsgithub.boozic;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class CameraActivity extends Activity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private int mFlash;
    private int mAutoFocus;
    private int mSound;
    public int colorAccent;
    static final int FLASH_STATE = 0;
    static final int AUTO_FOCUS_STATE = 1;
    static final int SOUND_STATE = 1;
    static final int COLOR_ACCENT_STATE = 0;

    private SharedPreferences mPrefs;
    private ImageView flash;
    private ImageView autofocus;
    private ImageView sound;

    private Drawable flashLight_off;
    private Drawable flashLight;
    private Drawable eye_off;
    private Drawable eye;
    private Drawable bell_off;
    private Drawable bell;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Programmatically initialize the scanner view
        mScannerView = new ZBarScannerView(this);

        //add the scannerview to the layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.scanner_layout_sub);
        frameLayout.addView(mScannerView);

        //Set listener for all buttons
        flash = (ImageView) findViewById(R.id.flashlightButton);
        flash.setOnClickListener(clickListener);

        autofocus = (ImageView) findViewById(R.id.autofocusButton);
        autofocus.setOnClickListener(clickListener);

        sound = (ImageView) findViewById(R.id.soundButton);
        sound.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.flashlightButton:
                    if (mFlash == 1) {
                        flash.setImageDrawable(flashLight_off);
                        mFlash = 0;
                    }else {
                        flash.setImageDrawable(flashLight);
                        mFlash = 1;
                    }
                    mScannerView.setFlash(mFlash == 1);
                    break;
                case R.id.soundButton:
                    if (mSound == 1) {
                        sound.setImageDrawable(bell_off);
                        mSound = 0;
                    }else {
                        sound.setImageDrawable(bell);
                        mSound = 1;
                    }
                    break;
                case R.id.autofocusButton:
                    if (mAutoFocus == 1) {
                        autofocus.setImageDrawable(eye_off);
                        mAutoFocus = 0;
                    }else {
                        autofocus.setImageDrawable(eye);
                        mAutoFocus = 1;
                    }
                    mScannerView.setAutoFocus(mAutoFocus == 1);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //pull the shared preference
        mPrefs = getSharedPreferences("FLASH_STATE", MODE_PRIVATE);
        //when resume, pull saves states for each button
        mFlash = mPrefs.getInt("FLASH_STATE", FLASH_STATE);
        mAutoFocus = mPrefs.getInt("AUTO_FOCUS_STATE", AUTO_FOCUS_STATE);
        mSound = mPrefs.getInt("SOUND_STATE", SOUND_STATE);
        mPrefs = getSharedPreferences("COLOR_STATE", MODE_MULTI_PROCESS);
        colorAccent = mPrefs.getInt("COLOR_ACCENT_STATE", COLOR_ACCENT_STATE);

        setStyleCameraButtons(colorAccent);

        //depending on the state, change the icon that's seen by user
        if (mFlash == 1) {flash.setImageDrawable(flashLight);}
        else {flash.setImageDrawable(flashLight_off);}
        if (mAutoFocus == 1) {autofocus.setImageDrawable(eye);}
        else {autofocus.setImageDrawable(eye_off);}
        if (mSound == 1) {sound.setImageDrawable(bell);}
        else {sound.setImageDrawable(bell_off);}

        //set the camera actions according to the states
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        mScannerView.setFlash(mFlash == 1);
        mScannerView.setAutoFocus(mAutoFocus == 1);
    }

    @Override
    public void onPause() {
        super.onPause();
        //connect universal sharedpreference edit to ed
        SharedPreferences.Editor ed = mPrefs.edit();
        //store all button states into universal sharedpreference
        ed.putInt("FLASH_STATE", mFlash);
        ed.putInt("AUTO_FOCUS_STATE", mAutoFocus);
        ed.putInt("SOUND_STATE", mSound);
        //apply changes
        ed.apply();

        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        //if sound is enabled
        if (mSound == 1) {
            try {
                //play raw mp3
                Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.secret);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                Log.v("ERROR", "sound disabled or malfunctioned");
            }
        }
        // Do something with the result here
        Log.v("TAG", rawResult.getContents()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        //send string as integer

        //set intent
        Intent intent = this.getIntent();
        //store scanner result in intent
        intent.putExtra("RESULT", rawResult.getContents());
        //give the 'all clear' and set result intent for when mainactivity catches it
        this.setResult(RESULT_OK, intent);
        //finish activity to be destroyed
        finish();
    }

    public void setStyleCameraButtons (int colorAccent) {
        switch (colorAccent) {
            case 1:
                flashLight_off = getResources().getDrawable(R.drawable.flashlight_off, null);
                flashLight_off.setColorFilter(getResources().getColor(R.color.ColorAccent), PorterDuff.Mode.MULTIPLY);
                flashLight = getResources().getDrawable(R.drawable.flashlight, null);
                flashLight.setColorFilter(getResources().getColor(R.color.ColorAccent), PorterDuff.Mode.MULTIPLY);
                eye_off = getResources().getDrawable(R.drawable.eye_off, null);
                eye_off.setColorFilter(getResources().getColor(R.color.ColorAccent), PorterDuff.Mode.MULTIPLY);
                eye = getResources().getDrawable(R.drawable.eye, null);
                eye.setColorFilter(getResources().getColor(R.color.ColorAccent), PorterDuff.Mode.MULTIPLY);
                bell_off = getResources().getDrawable(R.drawable.bell_off, null);
                bell_off.setColorFilter(getResources().getColor(R.color.ColorAccent), PorterDuff.Mode.MULTIPLY);
                bell = getResources().getDrawable(R.drawable.bell, null);
                bell.setColorFilter(getResources().getColor(R.color.ColorAccent), PorterDuff.Mode.MULTIPLY);
                break;
            case 2:
                flashLight_off = getResources().getDrawable(R.drawable.flashlight_off, null);
                flashLight_off.setColorFilter(getResources().getColor(R.color.ColorAccent2), PorterDuff.Mode.MULTIPLY);
                flashLight = getResources().getDrawable(R.drawable.flashlight, null);
                flashLight.setColorFilter(getResources().getColor(R.color.ColorAccent2), PorterDuff.Mode.MULTIPLY);
                eye_off = getResources().getDrawable(R.drawable.eye_off, null);
                eye_off.setColorFilter(getResources().getColor(R.color.ColorAccent2), PorterDuff.Mode.MULTIPLY);
                eye = getResources().getDrawable(R.drawable.eye, null);
                eye.setColorFilter(getResources().getColor(R.color.ColorAccent2), PorterDuff.Mode.MULTIPLY);
                bell_off = getResources().getDrawable(R.drawable.bell_off, null);
                bell_off.setColorFilter(getResources().getColor(R.color.ColorAccent2), PorterDuff.Mode.MULTIPLY);
                bell = getResources().getDrawable(R.drawable.bell, null);
                bell.setColorFilter(getResources().getColor(R.color.ColorAccent2), PorterDuff.Mode.MULTIPLY);
                break;
            case 3:
                flashLight_off = getResources().getDrawable(R.drawable.flashlight_off, null);
                flashLight_off.setColorFilter(getResources().getColor(R.color.ColorAccent3), PorterDuff.Mode.MULTIPLY);
                flashLight = getResources().getDrawable(R.drawable.flashlight, null);
                flashLight.setColorFilter(getResources().getColor(R.color.ColorAccent3), PorterDuff.Mode.MULTIPLY);
                eye_off = getResources().getDrawable(R.drawable.eye_off, null);
                eye_off.setColorFilter(getResources().getColor(R.color.ColorAccent3), PorterDuff.Mode.MULTIPLY);
                eye = getResources().getDrawable(R.drawable.eye, null);
                eye.setColorFilter(getResources().getColor(R.color.ColorAccent3), PorterDuff.Mode.MULTIPLY);
                bell_off = getResources().getDrawable(R.drawable.bell_off, null);
                bell_off.setColorFilter(getResources().getColor(R.color.ColorAccent3), PorterDuff.Mode.MULTIPLY);
                bell = getResources().getDrawable(R.drawable.bell, null);
                bell.setColorFilter(getResources().getColor(R.color.ColorAccent3), PorterDuff.Mode.MULTIPLY);
                break;
            case 4:
                flashLight_off = getResources().getDrawable(R.drawable.flashlight_off, null);
                flashLight_off.setColorFilter(getResources().getColor(R.color.ColorAccent4), PorterDuff.Mode.MULTIPLY);
                flashLight = getResources().getDrawable(R.drawable.flashlight, null);
                flashLight.setColorFilter(getResources().getColor(R.color.ColorAccent4), PorterDuff.Mode.MULTIPLY);
                eye_off = getResources().getDrawable(R.drawable.eye_off, null);
                eye_off.setColorFilter(getResources().getColor(R.color.ColorAccent4), PorterDuff.Mode.MULTIPLY);
                eye = getResources().getDrawable(R.drawable.eye, null);
                eye.setColorFilter(getResources().getColor(R.color.ColorAccent4), PorterDuff.Mode.MULTIPLY);
                bell_off = getResources().getDrawable(R.drawable.bell_off, null);
                bell_off.setColorFilter(getResources().getColor(R.color.ColorAccent4), PorterDuff.Mode.MULTIPLY);
                bell = getResources().getDrawable(R.drawable.bell, null);
                bell.setColorFilter(getResources().getColor(R.color.ColorAccent4), PorterDuff.Mode.MULTIPLY);
                break;
            case 5:
                flashLight_off = getResources().getDrawable(R.drawable.flashlight_off, null);
                flashLight_off.setColorFilter(getResources().getColor(R.color.ColorAccent5), PorterDuff.Mode.MULTIPLY);
                flashLight = getResources().getDrawable(R.drawable.flashlight, null);
                flashLight.setColorFilter(getResources().getColor(R.color.ColorAccent5), PorterDuff.Mode.MULTIPLY);
                eye_off = getResources().getDrawable(R.drawable.eye_off, null);
                eye_off.setColorFilter(getResources().getColor(R.color.ColorAccent5), PorterDuff.Mode.MULTIPLY);
                eye = getResources().getDrawable(R.drawable.eye, null);
                eye.setColorFilter(getResources().getColor(R.color.ColorAccent5), PorterDuff.Mode.MULTIPLY);
                bell_off = getResources().getDrawable(R.drawable.bell_off, null);
                bell_off.setColorFilter(getResources().getColor(R.color.ColorAccent5), PorterDuff.Mode.MULTIPLY);
                bell = getResources().getDrawable(R.drawable.bell, null);
                bell.setColorFilter(getResources().getColor(R.color.ColorAccent5), PorterDuff.Mode.MULTIPLY);
                break;
        }
    }

}