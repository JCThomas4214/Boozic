package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import comjason_lewisg.httpsgithub.boozic.Handlers.DialogHandler;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;

public class ProductTypeListController {
    List<Integer> productIDs = new ArrayList<>();
    List<String> productLabels = new ArrayList<>();
    DialogHandler DHandler;

    public void onCreate() {}

    public ProductTypeListController() {
    }

    public void getList(ProductActivity p, int productParentId, boolean cameFromStartProductInfo) {
        DHandler = new DialogHandler(p);
        getListInBackground(p, productParentId, cameFromStartProductInfo);
    }

    private void getListInBackground(final ProductActivity p, final int productParentId, final boolean cameFromStartProductInfo) {

        final MaterialDialog dialog = DHandler.progressDialog();
        dialog.show();

        productIDs.clear();
        productLabels.clear();

        new AsyncTask<Void, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/Products/getProductTypes?");
                    //append product parent ID
                    urlString.append("ParentId=").append(productParentId);

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
                        return new JSONObject(stringBuilder.toString());
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonData) {
                String productName;
                String productID;
                try {
                    Iterator iter = jsonData.keys();
                    while (iter.hasNext()) {
                        productID = (String)iter.next();
                        productName = jsonData.getString(productID);
                        productLabels.add(productName);
                        productIDs.add(Integer.parseInt(productID));
                    }
                } catch (JSONException e) {}
                dialog.hide();
                DHandler.UpdateProductType(productLabels,productIDs,cameFromStartProductInfo);
            }
        }.execute();
    }
}
