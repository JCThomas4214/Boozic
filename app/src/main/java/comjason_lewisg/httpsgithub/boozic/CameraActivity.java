package comjason_lewisg.httpsgithub.boozic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
        setContentView(mScannerView);                // Set the scanner view as the content view

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