package comjason_lewisg.httpsgithub.boozic.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductStorageModel {
    public String label;
    public String lastUpdate = "N/A";
    public int userRating = -1;
    public int boozicScore = -1;

    public String upc;
    public int productId;

    public int closestStoreId;
    public int cheapestStoreId;
    public String closestStoreName = null;
    public String cheapestStoreName = null;
    public String closestStoreAddress = null;
    public String cheapestStoreAddress = null;
    public double closestStoreDist;
    public double cheapestStoreDist;
    public double closestPrice;
    public double cheapestPrice;

    public int typePic = 4;
    public int favorite = 0;

    public String containerType = "N/A";
    public int containerQuantity = -1;
    public double volume;
    public String volumeMeasure;

    public double pbv = -1;
    public double abv = -1;
    public int proof = -1;
    public double abp = -1;
    public double pdd = -1;
    public double td = -1;

    public int[] rating = new int[5];
    public double avgRating;

    public ProductStorageModel(String label, String upc, int productId, String lastUpdate, int userRating, int closestStoreId, int cheapestStoreId, String closestStoreName, String cheapestStoreName,
                               String closestStoreAddress, String cheapestStoreAddress, double closestStoreDist, double cheapestStoreDist, double closestPrice, double cheapestPrice, int type, int favorite, String container,
                               int containerQuantity, double abv, int[] rating, double volume, String volumeMeasure, double pbv, double abp, double pdd, double td, double avgRating) {

        this.label = label;
        if (lastUpdate != null) this.lastUpdate = lastUpdate;
        this.userRating = userRating;
        typePic = type;

        this.upc = upc;
        this.productId = productId;

        this.closestStoreId = closestStoreId;
        this.cheapestStoreId = cheapestStoreId;
        if (closestStoreName != null) this.closestStoreName = closestStoreName;
        if (cheapestStoreName != null) this.cheapestStoreName = cheapestStoreName;
        this.closestStoreAddress = closestStoreAddress;
        this.cheapestStoreAddress = cheapestStoreAddress;
        this.closestStoreDist = closestStoreDist;
        this.cheapestStoreDist = cheapestStoreDist;
        this.closestPrice = closestPrice;
        this.cheapestPrice = cheapestPrice;
        this.favorite = favorite;
        this.containerType = container;
        this.containerQuantity = containerQuantity;
        this.abv = abv;
        this.proof = 2 * (int)abv;
        System.arraycopy(rating,0,this.rating,0,rating.length);

        this.volume = volume;
        this.volumeMeasure = volumeMeasure;
        this.pbv = pbv;
        this.abp = abp;
        this.pdd = pdd;
        this.td = td;
        this.avgRating = avgRating;
    }

    public ProductStorageModel(String label, String upc, int productId,  double volume, String volumeMeasure) {
        this.label = label;
        this.upc = upc;
        this.productId = productId;
        this.volume = volume;
        this.volumeMeasure = volumeMeasure;

        int[] ratingTmp = new int[] {0,0,0,0,0};
        System.arraycopy(ratingTmp,0,this.rating,0,rating.length);
    }

    public ProductStorageModel(JSONObject object) {

        try {
            JSONObject closestStoreObject = object.getJSONObject("ClosestStore");
            JSONObject cheapestStoreObject = object.getJSONObject("CheapestStore");

            label = object.getString("ProductName");
            productId = object.getInt("ProductID");
            lastUpdate = closestStoreObject.getString("LastUpdated");
            userRating = object.getInt("RatingByCurrentUser");
            upc = object.getString("UPC");

            closestStoreId = closestStoreObject.getInt("StoreID");
            cheapestStoreId = cheapestStoreObject.getInt("StoreID");
            closestStoreName = closestStoreObject.getString("StoreName");
            if (closestStoreName.equals("null")) closestStoreName = null;
            cheapestStoreName = cheapestStoreObject.getString("StoreName");
            if (cheapestStoreName.equals("null")) cheapestStoreName = null;
            closestStoreAddress = closestStoreObject.getString("Address");
            if (closestStoreAddress.equals("null")) closestStoreAddress = null;
            cheapestStoreAddress = cheapestStoreObject.getString("Address");
            if (cheapestStoreAddress.equals("null")) cheapestStoreAddress = null;
            closestStoreDist = closestStoreObject.getDouble("DistanceInMiles");
            cheapestStoreDist = cheapestStoreObject.getDouble("DistanceInMiles");
            closestPrice = closestStoreObject.getDouble("Price");
            cheapestPrice = cheapestStoreObject.getDouble("Price");

            typePic = object.getInt("ProductParentTypeId");
            favorite = object.getInt("IsFavourite");

            containerType = object.getString("ContainerType");
            if (containerType.equals("null")) containerType = null;

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
            avgRating = object.getDouble("CombinedRating");

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
            Log.v("CATCH", "productstoragemodel creation error");
        }
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

    public void setABP() {
        float abptmp = (float)closestPrice / (((float)abv/100f) * (float)convertVol());

        abp = (double)abptmp;
    }

    public void setPBV() {
        float pbvtmp = (float)closestPrice / (float)convertVol();
        pbv = (double)pbvtmp;
    }

    private double convertVol() {
        double volumetmp = volume;

        if (volumeMeasure.equals("oz"))
            volumetmp = volume * 29.5735;
        else if (volumeMeasure.equals("L"))
            volumetmp = volume * 1000;
        else {
            switch (typePic) {
                case 1:
                    volumeMeasure = "ml";
                    break;
                case 2:
                    volumeMeasure = "oz";
                    break;
                case 3:
                    volumeMeasure = "ml";
                    break;
                default:
                    volumeMeasure = "ml";
                    break;
            }
        }

        return volumetmp;
    }
}
