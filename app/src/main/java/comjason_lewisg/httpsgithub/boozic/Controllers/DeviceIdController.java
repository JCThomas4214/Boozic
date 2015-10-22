package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import comjason_lewisg.httpsgithub.boozic.MainActivity;

public class DeviceIdController {

    public void onCreate() {}

    public DeviceIdController(MainActivity m) {
        getRegId(m);
    }

    public void getRegId(MainActivity m) {
        GoogleCloudMessaging gcmObj;
        // Check if Google Play Service is installed in Device
        // Play services is needed to handle GCM stuffs
        if (m.checkPlayServices()) {
            // Register Device in GCM Server
            getTokenInBackground(m);
        }
    }

    //Get an authorization Token for GCM
    private void getTokenInBackground(final MainActivity m) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token;
                try {
                    //TODO: get Project number from global variable
                    InstanceID instanceID = InstanceID.getInstance(m.applicationContext);
                    token = instanceID.getToken("845607826709",GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                } catch (final IOException e) {
                    token = "Error :" + e.getMessage();
                }
                return token;
            }

            @Override
            protected void onPostExecute(String msg) {
                // Add token to App Server
                addToken(msg, m);
                // Toast.makeText(applicationContext,"Registered with GCM Server successfully.nn"+ msg, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    //This will add the token to App server in the background
    private void addToken(final String token, final MainActivity m) {
        new AsyncTask<Void, Void, String>() {

            private Exception exception;
            protected String doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    String android_id = Settings.Secure.getString(m.applicationContext.getContentResolver(),Settings.Secure.ANDROID_ID);
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
