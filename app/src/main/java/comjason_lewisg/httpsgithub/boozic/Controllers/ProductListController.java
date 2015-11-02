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

import comjason_lewisg.httpsgithub.boozic.Handlers.FilterMenuHandler;
import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;

public class ProductListController {
    List<TopTensModel> productList;
    JSONObject jObject = null;

    public void onCreate() {}

    public ProductListController() {}

    public List<TopTensModel> callList(MainActivity m, FilterMenuHandler fm, int latitude, int longitude) {
        if (m.checkPlayServices()) return getListInBackground(m, fm, latitude, longitude);
        else return null;
    }

    private List<TopTensModel> getListInBackground(final MainActivity m, final FilterMenuHandler fm, final int latitude, final int longitude) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/getProducts?");
                    //append location
                    urlString.append("latitude=").append(latitude).append("?").append("longitude=").append(longitude).append("?");
                    //append types selected in filter menu
                    if (fm.typesButtonPressed != 0) urlString.append("ProductParentTypeId=").append(fm.typesButtonPressed).append("?");
                    //append mile radius selected in filter menu
                    if (fm.distancesButtonPressed != 0) {
                        urlString.append("Radius=");
                        if (fm.distancesButtonPressed / 4 == 1 ) urlString.append(2+"?");
                        else if (fm.distancesButtonPressed / 2 % 2 == 1) urlString.append(5+"?");
                        else if (fm.distancesButtonPressed % 2 == 1) urlString.append(fm.custommi_miles).append("?");
                    }
                    //append price range if selected in filter menu
                    if (fm.priceContRateButtonPressed / 4 == 1)
                        urlString.append("LowestPrice=").append(fm.pricerange_low).append("?").append("HighestPrice=").append(fm.pricerange_high).append("?");
                    //append ABV range if selected in filter menu
                    if (fm.priceContRateButtonPressed / 2 % 2 == 1)
                        urlString.append("LowestABV=").append(fm.contentrange_low).append("?").append("HighestABV=").append(fm.contentrange_high).append("?");
                    //append rating range if selected in filter menu
                    if (fm.priceContRateButtonPressed % 2 == 1)
                        urlString.append("LowestRating=").append(fm.ratingrange_low).append("?").append("HighestRating=").append(fm.ratingrange_high).append("?");

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
                    } catch (JSONException e) {}
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
