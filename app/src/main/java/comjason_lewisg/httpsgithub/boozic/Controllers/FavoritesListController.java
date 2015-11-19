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
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONArray jsonData) {
                parseJson(jsonData);

                mAdapter.setList(favoritesList);
                swipeRefreshLayout.setRefreshing(false);
            }
        }.execute();
    }

    private void parseJson(JSONArray jsonArray) {
        favoritesList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                favoritesList.add(new TopTensModel(oneObject,i));
            }
            catch (JSONException e) {}
        }
    }

}
