package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.provider.Settings;
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

    private List<TopTensModel> productList = new ArrayList<>();
    MainActivity m;

    public void onCreate() {}

    public ProductListController() {}

    public void callList(MainActivity m, FilterMenuHandler fm, AdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout, double latitude, double longitude) {
        this.m = m;

        if (m.checkPlayServices()) {
            getListInBackground(m, fm, mAdapter, swipeRefreshLayout, latitude, longitude);
        }
    }

    private void getListInBackground(final MainActivity m, final FilterMenuHandler fm, final AdapterHandler mAdapter, final SwipeRefreshLayout swipeRefreshLayout, final double latitude, final double longitude) {

        if (!swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(true);

        new AsyncTask<Void, Void, JSONArray>() {

            @Override
            protected JSONArray doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    String android_id = Settings.Secure.getString(m.applicationContext.getContentResolver(),Settings.Secure.ANDROID_ID);
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/getProducts?");
                    //append location
                    urlString.append("latitude=").append(latitude).append("&longitude=").append(longitude);
                    //append Device id
                    urlString.append("&DeviceId=").append( android_id );
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
                mAdapter.clearData();
                parseJsonObject(jsonData, mAdapter, swipeRefreshLayout);
            }
        }.execute();
    }

    public void parseJsonObject(JSONArray jArr, AdapterHandler mAdapter, SwipeRefreshLayout swipeRefreshLayout) {

        productList.clear();
        m.FLcon.favoritesList.clear();
        int size = jArr.length();
        boolean firstSet = true;
        int mod;

        for (int i = 1; i <= size; i++) {
            mod = i % 25;
            try {
                JSONObject oneObject = jArr.getJSONObject(i-1);
                int favorite = oneObject.getInt("IsFavourite");
                //continue to add models to list
                productList.add(new TopTensModel(oneObject,i-1));
                //start the list so the user will not have to wait for 500 toptensproducts to be created for the entire list
                if (mod == 0 && firstSet) {
                    mAdapter.startList(productList, swipeRefreshLayout);
                    firstSet = false;
                }
                else if (mod == 0) {
                    mAdapter.addList(productList.subList(i-25, i));
                }
                //above only adds to list every 25 items, use this to get the last items
                else if (i == size && mod != 0) {
                    if (i < 25) mAdapter.startList(productList, swipeRefreshLayout);
                    else mAdapter.addList(productList.subList((size/25)*25, size));
                }

                if (favorite == 1) m.FLcon.favoritesList.add(new TopTensModel(oneObject,i-1));
            } catch (JSONException e) {}
        }
    }

    public List<TopTensModel> getProductList() {
        return productList;
    }
}
