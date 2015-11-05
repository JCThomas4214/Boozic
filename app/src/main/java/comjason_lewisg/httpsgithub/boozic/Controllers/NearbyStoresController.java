package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.MainActivity;

public class NearbyStoresController {
    public List<String> storeList = new ArrayList<>();
    public List<Double> distanceList = new ArrayList<>();
    //public List<Integer> storeIdList = new ArrayList<>();

    public void onCreate() {}

    public NearbyStoresController() {}

    public void callList(MainActivity m, double latitude, double longitude) {
        if (m.checkPlayServices()) {
            getListInBackground(m, latitude, longitude);
        }
    }

    private void getListInBackground(final MainActivity m, final double latitude, final double longitude) {

        storeList.clear();
        distanceList.clear();
        //storeIdList.clear();

        new AsyncTask<Void, Void, JSONArray>() {

            @Override
            protected JSONArray doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/Location/getStoresInRadius?");
                    //append location
                    urlString.append("latitude=").append(latitude).append("&longitude=").append(longitude);
                    urlString.append("&Radius=10");

                    Log.v("NearbyStoresURL", urlString.toString());
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
                        return new JSONArray(stringBuilder.toString());
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONArray jsonData) {
                for (int i = 0; i < jsonData.length(); i++) {
                    try {
                        storeList.add(jsonData.getJSONObject(i).getString("StoreName"));
                        distanceList.add(jsonData.getJSONObject(i).getDouble("Distance"));
                        //storeIdList.add(jsonData.getJSONObject(i).getInt("StoreID"));
                    } catch (JSONException e) {
                        Log.e("ERROR", e.getMessage(), e);
                    }
                }
            }
        }.execute();
    }
}
