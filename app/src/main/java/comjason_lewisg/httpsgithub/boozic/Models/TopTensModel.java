package comjason_lewisg.httpsgithub.boozic.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class TopTensModel {
    public String label;
    public String lastUpdate;
    public double userRating = -1;
    public int boozicScore;

    public String upc;
    public int productID;

    public int closestStoreId;
    public int cheapestStoreId;
    public String closestStoreName;
    public String cheapestStoreName;
    public String closestStoreAddress;
    public String cheapestStoreAddress;
    public double closestStoreDist;
    public double cheapestStoreDist;
    public double closestPrice;
    public double cheapestPrice;

    public int typePic;
    public boolean favorite;

    public String containerType;
    public int containerQuantity;
    public double volume;
    public String volumeMeasure = null;

    public double pbv = -1;
    public double abv = -1;
    public int proof = -1;
    public double abp = -1;
    public double pdd = -1;
    public double td = -1;

    public int[] rating;
    public double avgRating;

    public TopTensModel(JSONObject object) {

        try {
            JSONObject closestStoreObject = object.getJSONObject("ClosestStore");
            JSONObject cheapestStoreObject = object.getJSONObject("CheapestStore");

            label = object.getString("ProductName");
            productID = object.getInt("ProductID");
            lastUpdate = closestStoreObject.getString("LastUpdated");
            userRating = 0; //oneObject.getDouble("PRODUCT_USERRATING");

            upc = object.getString("UPC");

            closestStoreId = closestStoreObject.getInt("StoreID");
            cheapestStoreId = cheapestStoreObject.getInt("StoreID");
            closestStoreName = closestStoreObject.getString("StoreName");
            cheapestStoreName = cheapestStoreObject.getString("StoreName");
            closestStoreAddress = closestStoreObject.getString("Address");
            cheapestStoreAddress = cheapestStoreObject.getString("Address");
            closestStoreDist = closestStoreObject.getDouble("DistanceInMiles");
            cheapestStoreDist = cheapestStoreObject.getDouble("DistanceInMiles");
            closestPrice = closestStoreObject.getDouble("Price");
            cheapestPrice = cheapestStoreObject.getDouble("Price");

            typePic = object.getInt("ProductParentTypeId");
            favorite = false; //favorite

            containerType = object.getString("ContainerType");
            if (containerType.equals("null")) containerType = "N/A";

            containerQuantity = object.getInt("ContainerQty");

            volume = object.getDouble("Volume");
            if (volume == 0) volume = -1;

            //must be changed when backend -1
            abv = object.getDouble("ABV");
            if (abv == 0) abv = -1;

            //must be changed when backend -1
            proof = (int) (abv * 2);
            if (proof == 0) proof = -1;

            rating = new int[]{object.getInt("Rating1"), object.getInt("Rating2"),
                            object.getInt("Rating3"), object.getInt("Rating4"), object.getInt("Rating5")};
            avgRating = object.getDouble("CombinedRating");//findAverage();

            volumeMeasure = object.getString("VolumeUnit");
            getVolMeasure();

            if (cheapestPrice != -1) {

                pdd = findPDD();
                td = findTD();
                if (this.volumeMeasure != null && volume != -1) {
                    pbv = findPBV();
                    if (abv != -1) abp = findABP();
                }
            }

        } catch (JSONException e) {
            Log.e("TT ERROR", e.getMessage(), e);
        }
    }

    public TopTensModel(String label, String lastUpdate, double userRating, String closestStoreName, String cheapestStoreName, double closestStoreDist, double cheapestStoreDist,
                        double closestPrice, double cheapestPrice, int type, boolean favorite, String container,
                        double abv, int proof, int[] rating, double volume) {

        this.label = label;
        this.lastUpdate = lastUpdate;
        this.userRating = userRating;
        typePic = type;

        this.closestStoreName = closestStoreName;
        this.cheapestStoreName = cheapestStoreName;
        this.closestStoreDist = closestStoreDist;
        this.cheapestStoreDist = cheapestStoreDist;
        this.closestPrice = closestPrice;
        this.cheapestPrice = cheapestPrice;
        this.favorite = favorite;
        this.containerType = container;
        getVolMeasure();
        this.abv = abv;
        this.proof = proof;
        //System.arraycopy(rating,0,this.rating,0,rating.length);
        this.rating = rating;

        this.volume = volume;
        pbv = findPBV();
        abp = findABP();
        pdd = findPDD();
        td = findTD();
        avgRating = findAverage();
    }

    public TopTensModel(String label, String lastUpdate, double userRating, String closestStoreName, double closestStoreDist, double closestPrice, int type, boolean favorite,
                        String container, double abv, int proof, int[] rating, double volume) {

        this.label = label;
        this.lastUpdate = lastUpdate;
        this.userRating = userRating;
        typePic = type;

        this.closestStoreName = closestStoreName;
        this.cheapestStoreName = closestStoreName;
        this.closestStoreDist = closestStoreDist;
        this.cheapestStoreDist = closestStoreDist;
        this.closestPrice = closestPrice;
        this.cheapestPrice = closestPrice;
        this.favorite = favorite;
        this.containerType = container;
        getVolMeasure();
        this.abv = abv;
        this.proof = proof;
        //System.arraycopy(rating,0,this.rating,0,rating.length);
        this.rating = rating;

        this.volume = volume;
        pbv = findPBV();
        abp = findABP();
        avgRating = findAverage();
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

    private double findAverage() {
        double wTotal = 0;
        double total = 0;

        for (int i = 0; i < rating.length; i++) {
            wTotal += ((float) 1.0 - 0.2 * (i)) * rating[i];
            total += rating[i];
        }

        return ((wTotal / total) * 100) / 20;
    }
}
