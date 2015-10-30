package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;

public class ProductListController {
    List<TopTensModel> productList;
    JSONObject jObject = null;
    boolean playService;

    public void onCreate() {
    }

    public ProductListController(MainActivity m) {
        checkPlayServices(m);
    }

    public List<TopTensModel> callList(MainActivity m) {
        if (playService) return getListInBackground(m);
        else return null;
    }

    private void checkPlayServices(MainActivity m) {
        // Check if Google Play Service is installed in Device
        // Play services is needed to handle GCM stuffs
        if (m.checkPlayServices()) playService = true;
        else playService = false;
    }

    private List<TopTensModel> getListInBackground(final MainActivity m) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080//api/GCM/addGCMRegkey?");
                    //urlString.append("&DeviceId=").append(android_id);

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
                    response = "THERE WAS AN ERROR";
                } else {
                    try {
                        jObject = new JSONObject(response);
                    } catch (JSONException e) {
                    }
                }
            }

        }.execute();
        return parseJsonObject(jObject);
    }

    public List<TopTensModel> parseJsonObject(JSONObject jObject) {
        JSONArray jArray;

        try {
            jArray = jObject.getJSONArray("PRODUCT_LIST_ARRAY");

            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    productList.add(new TopTensModel(oneObject.getString("PRODUCT_LABEL"),
                            oneObject.getString("PRODUCT_UPDATED"),
                            oneObject.getDouble("PRODUCT_USERRATING"),
                            oneObject.getString("PRODUCT_CLOSEST_STORE"),
                            oneObject.getString("PRODUCT_CHEAPEST_STORE"),
                            oneObject.getDouble("PRODUCT_CLOSEST_DIST"),
                            oneObject.getDouble("PRODUCT_CHEAPEST_DIST"),
                            oneObject.getDouble("PRODUCT_CLOSEST_PRICE"),
                            oneObject.getDouble("PRIDUCT_CHEAPEST_PRICE"),
                            oneObject.getInt("PRODUCT_TYPE"),
                            oneObject.getBoolean("FAVORITE_PRODUCT"),
                            oneObject.getString("PRODUCT_CONTAINER"),
                            oneObject.getDouble("PRODUCT_ABV"),
                            oneObject.getInt("PRODUCT_PROOF"),
                            new int[] {oneObject.getInt("PRODUCT_RATING_FIVE"),oneObject.getInt("PRODUCT_RATING_FOUR"),
                                    oneObject.getInt("PRODUCT_RATING_THREE"),oneObject.getInt("PRODUCT_RATING_TWO"),oneObject.getInt("PRODUCT_RATING_ONE")}));
                } catch (JSONException e) {}
            }

        } catch (JSONException e) {}
        return productList;
    }
}
