package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeviceIdController extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    GoogleCloudMessaging gcmObj;
    String PROJECT_NUMBER = "845607826709";
    public Context applicationContext;
    String regId="";

    public void onCreate() {

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.
    }

    public void getRegId() {
        GoogleCloudMessaging gcmObj;
        // Check if Google Play Service is installed in Device
        // Play services is needed to handle GCM stuffs
        if (checkPlayServices()) {
            // Register Device in GCM Server
            getTokenInBackground();
        }
    }

    /**
     * Get an authorization Token for GCM
     */
    public void getTokenInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token;
                try {
                    //TODO: get Project number from global variable
                    InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
                    token = instanceID.getToken("845607826709",GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                } catch (final IOException e) {
                    token = "Error :" + e.getMessage();
                }
                return token;
            }

            @Override
            protected void onPostExecute(String msg) {
                // Add token to App Server
                addToken(msg);
                // Toast.makeText(applicationContext,"Registered with GCM Server successfully.nn"+ msg, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    //This will add the token to App server in the background
    public void addToken(final String token) {
        new AsyncTask<Void, Void, String>() {

            private Exception exception;
            protected String doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080//api/GCM/addGCMRegkey?");
                    urlString.append("RegKey=").append(token);
                    urlString.append("&DeviceId=").append( android_id );

                    URL url = new URL(urlString.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            protected void onPostExecute(String response) {
                if (response == null) {
                    response = "THERE WAS AN ERROR";
                }
                else
                {
                    //Toast.makeText(applicationContext,"Regn Token Updated to database", Toast.LENGTH_SHORT).show();
                }
            }

        }.execute();
    }
}
