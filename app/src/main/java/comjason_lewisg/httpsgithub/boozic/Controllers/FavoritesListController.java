package comjason_lewisg.httpsgithub.boozic.Controllers;


import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Fragments.FavoritesFragment;
import comjason_lewisg.httpsgithub.boozic.Handlers.FavoritesAdapterHandler;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.R;

public class FavoritesListController {

    public List<TopTensModel> favoritesList = new ArrayList<>();
    MainActivity m;

    FavoritesAdapterHandler mAdapter;

    public void onCreate() {}

    public FavoritesListController(MainActivity m) {
        this.m = m;
    }

    public void getFavorites(FavoritesAdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        this.mAdapter = mAdapter;
        getFavoritesList(m, m.latitude, m.longitude, swipeRefreshLayout);
    }

    public void getFavoritesList(final MainActivity m, final double latitude, final double longitude, final SwipeRefreshLayout swipeRefreshLayout) {


        new AsyncTask<Void, Void, JSONArray>() {

            @Override
            protected JSONArray doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    String android_id = Settings.Secure.getString(m.applicationContext.getContentResolver(),Settings.Secure.ANDROID_ID);
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/getFavourites?");
                    //append location
                    urlString.append("latitude=").append(latitude).append("&longitude=").append(longitude);
                    //append Device id
                    urlString.append("&DeviceId=").append( android_id );

                    Log.v("URL", urlString.toString());
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
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONArray jsonData) {
                parseJson(jsonData);
                if (jsonData != null) mAdapter.setList(favoritesList);
                swipeRefreshLayout.setRefreshing(false);
            }
        }.execute();
    }

    private void parseJson(JSONArray jsonArray) {
        //store the previous product list position and productID into a hashmap
        //because product list postion is important for synchronous updates
        int ID;
        int position;
        TopTensModel tmp;
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int j = 0; j < favoritesList.size(); j++) {
            tmp = favoritesList.get(j);
            hmap.put(tmp.productID, tmp.position);
        }

        favoritesList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                ID = oneObject.getInt("ProductID");
                //re inject the previous product list positions from productID
                try {
                    position = hmap.get(ID);
                } catch (Exception e) {
                    position = -1;
                }
                favoritesList.add(new TopTensModel(oneObject, position));
            }
            catch (JSONException e) {}
        }
    }

}
