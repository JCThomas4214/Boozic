package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Handlers.AdapterHandler;
import comjason_lewisg.httpsgithub.boozic.Handlers.FilterMenuHandler;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;

public class ProductListController {
    public List<TopTensModel> productList = new ArrayList<>();

    public void onCreate() {}

    public ProductListController() {}

    public void callList(MainActivity m, FilterMenuHandler fm, AdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout, double latitude, double longitude) {
        if (m.checkPlayServices()) {
            getListInBackground(m, fm, mAdapter, swipeRefreshLayout, latitude, longitude);
        }
    }

    private void getListInBackground(final MainActivity m, final FilterMenuHandler fm, final AdapterHandler mAdapter, final SwipeRefreshLayout swipeRefreshLayout, final double latitude, final double longitude) {

        if (!swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(true);
        productList.clear();

        new AsyncTask<Void, Void, JSONArray>() {

            @Override
            protected JSONArray doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/getProducts?");
                    //append location
                    urlString.append("latitude=").append(latitude).append("&longitude=").append(longitude);
                    //append types selected in filter menu
                    if (fm.typesButtonPressed != 0) urlString.append("&ProductParentTypeId=").append(fm.typesButtonPressed);
                    //append mile radius selected in filter menu
                    if (fm.distancesButtonPressed != 0) {
                        urlString.append("&Radius=");
                        if (fm.distancesButtonPressed / 4 == 1 ) urlString.append(2);
                        else if (fm.distancesButtonPressed / 2 % 2 == 1) urlString.append(5);
                        else if (fm.distancesButtonPressed % 2 == 1) urlString.append(fm.custommi_miles);
                    }
                    //append price range if selected in filter menu
                    if (fm.priceContRateButtonPressed / 4 == 1)
                        urlString.append("&LowestPrice=").append(fm.pricerange_low).append("&HighestPrice=").append(fm.pricerange_high);
                    //append ABV range if selected in filter menu
                    if (fm.priceContRateButtonPressed / 2 % 2 == 1)
                        urlString.append("&LowestABV=").append(fm.contentrange_low).append("&HighestABV=").append(fm.contentrange_high);
                    //append rating range if selected in filter menu
                    if (fm.priceContRateButtonPressed % 2 == 1)
                        urlString.append("&LowestRating=").append(fm.ratingrange_low).append("&HighestRating=").append(fm.ratingrange_high);

                    if (fm.orderButtonPressed != 0) urlString.append("&SortOption=").append(fm.orderButtonPressed);

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
                mAdapter.clearData();
                parseJsonObject(jsonData, mAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }.execute();
    }

    public void parseJsonObject(JSONArray jArr, AdapterHandler mAdapter) {

        TopTensModel product;

        for (int i = 0; i < jArr.length(); i++) {
            try {
                JSONObject oneObject = jArr.getJSONObject(i);
                JSONObject closestStoreObject = oneObject.getJSONObject("ClosestStore");
                JSONObject cheapestStoreObject = oneObject.getJSONObject("CheapestStore");

                product = new TopTensModel(oneObject.getString("ProductName"),
                        oneObject.getString("LastUpdated"),
                        0, //oneObject.getDouble("PRODUCT_USERRATING"),
                        closestStoreObject.getString("StoreName"),
                        null, //oneObject.getString("PRODUCT_CHEAPEST_STORE"),
                        oneObject.getDouble("DistanceCalculatedInMiles"),
                        -1, //oneObject.getDouble("PRODUCT_CHEAPEST_DIST"),
                        oneObject.getDouble("Price"),
                        -1, //oneObject.getDouble("PRIDUCT_CHEAPEST_PRICE"),
                        oneObject.getInt("ProductParentTypeId"),
                        false, //favorite
                        oneObject.getString("ContainerType"),
                        oneObject.getDouble("ABV"),
                        (int) (oneObject.getDouble("ABV") * 2),
                        new int[]{oneObject.getInt("Rating1"), oneObject.getInt("Rating2"),
                                oneObject.getInt("Rating3"), oneObject.getInt("Rating4"), oneObject.getInt("Rating5")});

                mAdapter.addItem(product);
                productList.add(product);

            } catch (JSONException e) {}
        }
    }
}
