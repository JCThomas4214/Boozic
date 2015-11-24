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

    public ProductStorageModel(String label, String upc, int productId, String lastUpdate, double userRating, int closestStoreId, int cheapestStoreId, String closestStoreName, String cheapestStoreName,
                               String closestStoreAddress, String cheapestStoreAddress, double closestStoreDist, double cheapestStoreDist, double closestPrice, double cheapestPrice, int type, int favorite, String container,
                               int containerQuantity, double abv, int[] rating, double volume, String volumeMeasure, double pbv, double abp, double pdd, double td, double avgRating) {

        this.label = label;
        if (!lastUpdate.equals("null")) this.lastUpdate = lastUpdate;
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
