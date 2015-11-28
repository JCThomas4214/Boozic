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
import java.net.URLEncoder;

import comjason_lewisg.httpsgithub.boozic.MainActivity;
import comjason_lewisg.httpsgithub.boozic.ProductActivity;
import comjason_lewisg.httpsgithub.boozic.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class NewProductController {

    static final int nullInt = 0;

    public double closestStoreDist;
    public double cheapestStoreDist;
    public double closestPrice;
    public double cheapestPrice;

    public double volumeTmp;
    public String volumeMeasureTmp = null;
    public String container;
    public int containerQtyTmp;

    public double pbv = -1;
    public double abvTmp = -1;
    public int proof = -1;
    public double abp = -1;
    public double pdd = -1;
    public double td = -1;

    int found;
    FrameLayout frame;

    public void onCreate() {}

    public NewProductController() {}

    public void newProduct(final MainActivity m, final String label, final String UPC, final int type, final String containerType,
                           final int containerQty, final double volume, final String volumeMeasure, final double abv) {
        frame = (FrameLayout) m.findViewById(R.id.frame3);
        newProductInBackground(m, label, UPC, type, containerType, containerQty, volume, volumeMeasure, abv);
    }

    private void newProductInBackground(final MainActivity m, final String label, final String UPC, final int type, final String containerType,
                                        final int containerQty, final double volume, final String volumeMeasure, final double abv) {

        new AsyncTask<Void, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/insertProduct?");
                    //append UPC
                    urlString.append("&UPC=").append(UPC);
                    //append label
                    String value = URLEncoder.encode(label, "UTF-8");
                    urlString.append("&ProductName=").append(value);
                    //append parentType
                    urlString.append("&ProductTypeId=").append(type);
                    //append containerType
                    urlString.append("&ContainerType=").append(containerType);
                    //append  containerQty
                    urlString.append("&ContainerQty=").append(containerQty);
                    //append volume
                    urlString.append("&Volume=").append(volume);
                    //append volumeMeasure
                    urlString.append("&VolumeUnit=").append(volumeMeasure);
                    //append abv
                    urlString.append("&ABV=").append(abv);

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
                    Log.e("ERROR", e.getMessage());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                Intent i = new Intent(m, ProductActivity.class);

                if (object != null) {
                    try {
                        i.putExtra("Found", 0);

                        //inject model variables
                        i.putExtra("Label", object.getString("ProductName"));
                        i.putExtra("ProductID", object.getInt("ProductID"));
                        i.putExtra("UPC", object.getString("UPC"));
                        i.putExtra("LastUpdate", (String)null);
                        i.putExtra("UserRating", 0);
                        i.putExtra("ClosestStoreId", 0);
                        i.putExtra("CheapestStoreId", 0);
                        i.putExtra("ClosestStore", (String)null);
                        i.putExtra("CheapestStore", (String)null);
                        i.putExtra("ClosestStoreAddress", (String)null);
                        i.putExtra("CheapestStoreAddress", (String)null);
                        closestStoreDist = 0;
                        i.putExtra("ClosestStoreDist", closestStoreDist);
                        cheapestStoreDist = 0;
                        i.putExtra("CheapestStoreDist", cheapestStoreDist);
                        closestPrice = 0;
                        i.putExtra("ClosestPrice", closestPrice);
                        cheapestPrice = 0;
                        i.putExtra("CheapestPrice", cheapestPrice);
                        i.putExtra("Type", object.getInt("ProductParentTypeId"));
                        i.putExtra("Favorites", 0);

                        container = object.getString("ContainerType");
                        if (container.equals("null")) container = "N/A";
                        i.putExtra("Container", container);

                        containerQtyTmp = object.getInt("ContainerQty");
                        i.putExtra("ContainerQty", containerQty);

                        volumeTmp = object.getDouble("Volume");
                        if (volume == nullInt) volumeTmp = -1;
                        i.putExtra("Volume", volume);

                        abvTmp = object.getDouble("ABV");
                        if (abv > nullInt) {
                            proof = (int) (abv * 2);
                        }
                        i.putExtra("ABV", abv);
                        i.putExtra("Proof", proof);

                        volumeMeasureTmp = object.getString("VolumeUnit");
                        getVolMeasure();
                        i.putExtra("VolumeMeasure", volumeMeasure);

                        i.putExtra("ABP", 0.0);
                        i.putExtra("PDD", 0.0);
                        i.putExtra("PBV", 0.0);
                        i.putExtra("TD", 0.0);

                        i.putExtra("Rating", new int[]{0, 0, 0, 0, 0});
                        i.putExtra("AvgRating", 0.0);

                        i.putExtra("LAT", m.latitude);
                        i.putExtra("LONG", m.longitude);

                        i.putExtra("COLOR_PRIMARY_ID", m.getColorPrimaryId());
                        i.putExtra("COLOR_ACCENT_ID", m.getColorAccentId());
                        i.putExtra("COLOR_PRIMARY", m.getColorPrimary());
                        i.putExtra("COLOR_PRIMARY_DARK", m.getColorPrimaryDark());
                        i.putExtra("COLOR_ACCENT", m.getColorAccent());
                        i.putExtra("COLOR_ACCENT_DARK", m.getColorAccentDark());

                        m.startActivityForResult(i, m.PRODUCT_INFO_REQUEST);

                    } catch (JSONException e) {
                        Log.e("ERROR", e.getMessage());
                    }
                }
                else {
                    Crouton.makeText(m, "An Error has Occurred", Style.ALERT, frame).show();
                }
            }
        }.execute();
    }

    public double findABP() {
        double volumetmp = convertVol();
        float abptmp = (float)closestPrice / (((float)abvTmp/100f) * (float)volumetmp);

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
        double volumetmp = volumeTmp;

        if (volumeMeasureTmp.equals("oz"))
            volumetmp = volumeTmp * 29.5735;
        else if (volumeMeasureTmp.equals("L"))
            volumetmp = volumeTmp * 1000;

        return volumetmp;
    }
    private void getVolMeasure() {

        switch (volumeMeasureTmp) {
            case "ML":
                if (volumeTmp > 1000) {
                    volumeTmp = volumeTmp / 1000;
                    volumeMeasureTmp = "L";
                } else {
                    volumeMeasureTmp = "ml";
                }
                break;
            case "L":
                break;
            case "oz":
                break;
            default:
                if (volumeTmp < 50) {
                    volumeMeasureTmp = "oz";
                }
                break;
        }
    }
}
