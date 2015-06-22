package comjason_lewisg.httpsgithub.boozic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class CameraActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_camera);

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
        } else {
            mFlash = false;
            mAutoFocus = true;
        }
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view

        FrameLayout linearLayout = new FrameLayout(this);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
        final ImageView flash = new ImageView(this);
        flash.setImageResource(R.drawable.flashlight_off);
        flash.setLayoutParams(new FrameLayout.LayoutParams(175,175, Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM));
        flash.setY(-200);

        linearLayout.addView(mScannerView);
        linearLayout.addView(flash);
        setContentView(linearLayout);

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlash) {
                    flash.setImageResource(R.drawable.flashlight_off);
                    mFlash = !mFlash;
                }else {
                    flash.setImageResource(R.drawable.flashlight);
                    mFlash = !mFlash;
                }
                mScannerView.setFlash(mFlash);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }



    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("TAG", rawResult.getContents()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        onBackPressed();
    }
}