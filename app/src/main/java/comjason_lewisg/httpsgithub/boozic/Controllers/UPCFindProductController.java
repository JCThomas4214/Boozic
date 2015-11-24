package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class UPCFindProductController {

    static final int nullInt = 0;

    public double closestStoreDist;
    public double cheapestStoreDist;
    public double closestPrice;
    public double cheapestPrice;

    public double volume;
    public String volumeMeasure = null;
    public String container;
    public int containerQty;

    public double pbv = -1;
    public double abv = -1;
    public int proof = -1;
    public double abp = -1;
    public double pdd = -1;
    public double td = -1;

    int found;
    FrameLayout frame;

    public void onCreate() {}

    public UPCFindProductController() {}

    public void callProduct(MainActivity m, String UPC, double latitude, double longitude) {
        frame = (FrameLayout) m.findViewById(R.id.frame3);
        getProductInBackground(m, UPC, latitude, longitude);
    }

    private void getProductInBackground(final MainActivity m, final String UPC, final double latitude, final double longitude) {

        new AsyncTask<Void, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/getProductInfo?");
                    //append UPC
                    urlString.append("UPC=").append(UPC);
                    //append location
                    urlString.append("&latitude=").append(latitude).append("&longitude=").append(longitude);

                    Log.v("getProductURL", urlString.toString());
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
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                Intent i = new Intent(m, ProductActivity.class);

                if (object != null) {
                    try {
                        //if the product is in the list, it is not a new product
                        found = object.getInt("IsFoundInDatabase");
                        i.putExtra("Found", found);

                        switch (found) {
                            case 0: //Found on our DB
                                JSONObject closestStoreObject = object.getJSONObject("ClosestStore");
                                JSONObject cheapestStoreObject = object.getJSONObject("CheapestStore");

                                i.putExtra("Position", -1);
                                i.putExtra("FavoritePosition", -1);
                                //inject model variables
                                i.putExtra("Label", object.getString("ProductName"));
                                i.putExtra("ProductID", object.getInt("ProductID"));
                                i.putExtra("UPC", object.getString("UPC"));
                                i.putExtra("LastUpdate", closestStoreObject.getString("LastUpdated"));
                                i.putExtra("UserRating", 0.0);
                                i.putExtra("ClosestStoreId", closestStoreObject.getInt("StoreID"));
                                i.putExtra("CheapestStoreId", cheapestStoreObject.getInt("StoreID"));
                                i.putExtra("ClosestStore", closestStoreObject.getString("StoreName"));
                                i.putExtra("CheapestStore", cheapestStoreObject.getString("StoreName"));
                                i.putExtra("ClosestStoreAddress", closestStoreObject.getString("Address"));
                                i.putExtra("CheapestStoreAddress", cheapestStoreObject.getString("Address"));
                                closestStoreDist = closestStoreObject.getDouble("DistanceInMiles");
                                i.putExtra("ClosestStoreDist", closestStoreDist);
                                cheapestStoreDist = cheapestStoreObject.getDouble("DistanceInMiles");
                                i.putExtra("CheapestStoreDist", cheapestStoreDist);
                                closestPrice = closestStoreObject.getDouble("Price");
                                i.putExtra("ClosestPrice", closestPrice);
                                cheapestPrice = cheapestStoreObject.getDouble("Price");
                                i.putExtra("CheapestPrice", cheapestPrice);
                                i.putExtra("Type", object.getInt("ProductParentTypeId"));
                                i.putExtra("Favorites", object.getInt("IsFavourite"));

                                container = object.getString("ContainerType");
                                if (container.equals("null")) container = "N/A";
                                i.putExtra("Container", container);

                                containerQty = object.getInt("ContainerQty");
                                i.putExtra("ContainerQty", containerQty);

                                volume = object.getDouble("Volume");
                                if (volume == nullInt) volume = -1;
                                i.putExtra("Volume", volume);

                                abv = object.getDouble("ABV");
                                if (abv > nullInt) {
                                    proof = (int) (abv * 2);
                                }
                                i.putExtra("ABV", abv);
                                i.putExtra("Proof", proof);

                                volumeMeasure = object.getString("VolumeUnit");
                                getVolMeasure();
                                i.putExtra("VolumeMeasure", volumeMeasure);

                                if (cheapestStoreObject.getDouble("Price") > 0) {

                                    pdd = findPDD();
                                    td = findTD();
                                    if (volumeMeasure != null && volume <= 0) {
                                        pbv = findPBV();
                                        if (abv <= 0) abp = findABP();
                                    }
                                }

                                i.putExtra("ABP", abp);
                                i.putExtra("PDD", pdd);
                                i.putExtra("PBV", pbv);
                                i.putExtra("TD", td);

                                i.putExtra("Rating", new int[]{object.getInt("Rating1"), object.getInt("Rating2"),
                                        object.getInt("Rating3"), object.getInt("Rating4"), object.getInt("Rating5")});
                                i.putExtra("AvgRating", object.getDouble("CombinedRating"));

                                i.putExtra("LAT", m.latitude);
                                i.putExtra("LONG", m.longitude);

                                i.putExtra("COLOR_PRIMARY_ID", m.getColorPrimaryId());
                                i.putExtra("COLOR_ACCENT_ID", m.getColorAccentId());
                                i.putExtra("COLOR_PRIMARY", m.getColorPrimary());
                                i.putExtra("COLOR_PRIMARY_DARK", m.getColorPrimaryDark());
                                i.putExtra("COLOR_ACCENT", m.getColorAccent());
                                i.putExtra("COLOR_ACCENT_DARK", m.getColorAccentDark());

                                m.startActivity(i);
                                break;
                            case 1: //Found on UPC DB
                                //inject new product variables
                                i.putExtra("Label", object.getString("ProductName"));
                                i.putExtra("ProductId", object.getInt("ProductID"));
                                i.putExtra("UPC", object.getString("UPC"));

                                volume = object.getDouble("Volume");
                                if (volume == 0) volume = -1;
                                i.putExtra("Volume", volume);

                                volumeMeasure = object.getString("VolumeUnit");
                                getVolMeasure();
                                i.putExtra("VolumeMeasure", volumeMeasure);

                                i.putExtra("LAT", m.getLastLocation().getLatitude());
                                i.putExtra("LONG", m.getLastLocation().getLongitude());

                                i.putExtra("COLOR_PRIMARY_ID", m.getColorPrimaryId());
                                i.putExtra("COLOR_ACCENT_ID", m.getColorAccentId());
                                i.putExtra("COLOR_PRIMARY", m.getColorPrimary());
                                i.putExtra("COLOR_PRIMARY_DARK", m.getColorPrimaryDark());
                                i.putExtra("COLOR_ACCENT", m.getColorAccent());
                                i.putExtra("COLOR_ACCENT_DARK", m.getColorAccentDark());

                                m.startActivity(i);
                                break;
                            case 2: //Found found on our server and UPC DB
                                //toast no store information available
                                m.DHandle.varifyUPC(UPC);
                                break;
                        }
                    } catch (JSONException e) {}
                }
                else {
                    Crouton.makeText(m, "An Error has Occured", Style.ALERT, frame).show();
                }
            }
        }.execute();
    }

    public double findABP() {
        double volumetmp = convertVol();
        float abptmp = (float)closestPrice / (((float)abv/100f) * (float)volumetmp);

        return (double)abptmp;
    }
    public double findPDD() {
        float pddtmp = 1f/21f;
        float pddtmp2 = ((float)cheapestStoreDist - (float)closestStoreDist) * (float)2.0 * (float)2.59;
        pddtmp = pddtmp * pddtmp2;

        return (double)pddtmp;
    }
    private double findPBV() {
        float pbvtmp = (float)closestPrice / (float)convertVol();
        return (double)pbvtmp;
    }
    private double findTD() {
        float tdtmp = (float)closestPrice - (float)cheapestPrice - (float)pdd;
        return (double)tdtmp;
    }
    private double convertVol() {
        double volumetmp = volume;

        if (volumeMeasure.equals("oz"))
            volumetmp = volume * 29.5735;
        else if (volumeMeasure.equals("L"))
            volumetmp = volume * 1000;

        return volumetmp;
    }
    private void getVolMeasure() {

        switch (volumeMeasure) {
            case "ML":
                if (volume > 1000) {
                    volume = volume / 1000;
                    volumeMeasure = "L";
                } else {
                    volumeMeasure = "ml";
                }
                break;
            case "L":
                break;
            case "oz":
                break;
            default:
                if (volume < 50) {
                    volumeMeasure = "oz";
                }
                break;
        }
    }
}
