package comjason_lewisg.httpsgithub.boozic.Models;

public class ProductStorageModel {
    public String label;
    public String lastUpdate = "N/A";
    public double userRating = -1;
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
    public boolean favorite = false;

    public String container = "N/A";
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

    public ProductStorageModel(String label, String upc, int productId, String lastUpdate, double userRating, int closestStoreId, int cheapestStoreId, String closestStoreName, String cheapestStoreName,
                               String closestStoreAddress, String cheapestStoreAddress, double closestStoreDist, double cheapestStoreDist, double closestPrice, double cheapestPrice, int type, boolean favorite, String container,
                               double abv, int proof, int[] rating, double volume, String volumeMeasure, double pbv, double abp, double pdd, double td, double avgRating) {

        this.label = label;
        if (!lastUpdate.equals("null")) this.lastUpdate = lastUpdate;
        this.userRating = userRating;
        typePic = type;

        this.upc = upc;
        this.productId = productId;

        this.closestStoreId = closestStoreId;
        this.cheapestStoreId = cheapestStoreId;
        if (!closestStoreName.equals("null")) this.closestStoreName = closestStoreName;
        if (!cheapestStoreName.equals("null")) this.cheapestStoreName = cheapestStoreName;
        this.closestStoreAddress = closestStoreAddress;
        this.cheapestStoreAddress = cheapestStoreAddress;
        this.closestStoreDist = closestStoreDist;
        this.cheapestStoreDist = cheapestStoreDist;
        this.closestPrice = closestPrice;
        this.cheapestPrice = cheapestPrice;
        this.favorite = favorite;
        this.container = container;
        this.abv = abv;
        this.proof = proof;
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
}
