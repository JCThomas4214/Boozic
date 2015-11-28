package comjason_lewisg.httpsgithub.boozic.Controllers;


import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.FrameLayout;

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
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

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
                if (jsonData != null) {
                    parseJson(jsonData);
                    mAdapter.setList(favoritesList);
                }
                else {
                    //toast no store information available
                    FrameLayout frame = (FrameLayout) m.findViewById(R.id.frame3);
                    Crouton.makeText(m, "You Currently Have No Favorites", Style.ALERT, frame).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }.execute();
    }

    private void parseJson(JSONArray jsonArray) {
        //store the previous product list position and productID into a hashmap
        //because product list postion is important for synchronous updates
        int ID;

        favoritesList.clear();
        m.favoritePositionHMap.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                ID = oneObject.getInt("ProductID");

                m.favoritePositionHMap.put(ID, i);

                favoritesList.add(new TopTensModel(oneObject));
            }
            catch (JSONException e) {}
        }
    }

    public void removeFavoriteFromList(int productId) {
        int favoritePosition = m.favoritePositionHMap.get(productId);
        //set this will prevent results from going into wrong condition
        m.favoritePositionHMap.remove(productId);
        //remove product from favorites list
        m.FLcon.favoritesList.remove(favoritePosition);
        //remove favorite from backend
        m.RAFcon.removeFavorite(productId);

        try {
            m.Nav.favoritesFragment.mAdapter.removeItem(favoritePosition);
        } catch (Exception e) {
            Log.v("CATCH", "There is a catch");
        }
    }

    public void addFavoriteToList(int productId) {
        TopTensModel model = m.PLcon.getProductList().get(m.positionHMap.get(productId));
        int favPosition = favoritesList.size();
        m.favoritePositionHMap.put(productId, favPosition);
        favoritesList.add(model);
    }

    public void updateFavorite(Intent data, int productId) {
        //Favorites model at favoritePosition
        TopTensModel model = m.FLcon.favoritesList.get(m.favoritePositionHMap.get(productId));
        model.userRating = data.getExtras().getDouble("UserRating");
        model.typePic = data.getExtras().getInt("ParentType");
        model.containerType = data.getExtras().getString("ContainerType");
        model.containerQuantity = data.getExtras().getInt("ContainerQty");
        model.volume = data.getExtras().getDouble("Volume");
        model.volumeMeasure = data.getExtras().getString("VolumeMeasure");
        model.abv = data.getExtras().getDouble("ABV");

        model.closestStoreId = data.getExtras().getInt("ClosestStoreId");
        model.cheapestStoreId = data.getExtras().getInt("CheapestStoreId");
        model.closestStoreName = data.getExtras().getString("ClosestStoreName");
        model.cheapestStoreName = data.getExtras().getString("CheapestStoreName");
        model.closestStoreAddress = data.getExtras().getString("ClosestStoreAddress");
        model.cheapestStoreName = data.getExtras().getString("CheapestStoreAddress");
        model.closestStoreDist = data.getExtras().getDouble("ClosestStoreDist");
        model.cheapestStoreDist = data.getExtras().getDouble("CheapestStoreDist");
        model.closestPrice = data.getExtras().getDouble("ClosestPrice");
        model.cheapestPrice = data.getExtras().getDouble("CheapestPrice");
    }

}
