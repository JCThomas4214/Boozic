package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import comjason_lewisg.httpsgithub.boozic.ProductActivity;

public class FlagProductController {

    public void onCreate() {}

    public FlagProductController() {}

    public void flagProduct(ProductActivity p, int reason) {
        flagProductInBackground(p, reason);
    }

    private void flagProductInBackground(final ProductActivity p, final int reason) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    String android_id = "" + android.provider.Settings.Secure.getString(p.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/flagProduct?");
                    //append productID
                    urlString.append("ProductId=").append(p.updatedModel.productId);
                    //append deviceID
                    urlString.append("&DeviceId=").append(android_id);
                    //append reason
                    urlString.append("&ReasonId=").append(reason);


                    Log.v("updateProductURL", urlString.toString());
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

            @Override
            protected void onPostExecute(String response) {
                if (response == null) {
                    Log.v("ERROR", "There was an error");
                }
            }
        }.execute();
    }
}
