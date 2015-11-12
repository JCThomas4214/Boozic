package comjason_lewisg.httpsgithub.boozic.Models;

public class UpdateProductModel {

    public boolean updated = false;

    public int type = -1;
    public double userRating = -1;
    public String upc;
    public int productId;

    public int StoreID = -1;
    public String StoreName = null;
    public double Price = -1;

    public Boolean favorite = null;

    public String container = null;
    public double volume = -1;
    public String volumeMeasure = null;

    public double abv;

    public UpdateProductModel(String upc, int productId) {
        this.upc = upc;
        this.productId = productId;
    }

    public void updateContainer(String container) {
        this.container = container;
        updated = true;
    }

    public void updateABV(double abv) {
        this.abv = abv;
        updated = true;
    }

    public void updateStore(String StoreName, int StoreID) {
        this.StoreName = StoreName;
        this.StoreID = StoreID;
        updated = true;
    }

    public void updateStorePrice(double Price) {
        this.Price = Price;
        updated = true;
    }

    public void updateVolume(double volume) {
        this.volume = volume;
        updated = true;
    }

    public void updateRating(double rating) {
        this.userRating = rating;
        updated = true;
    }

    public void updateFavorite(boolean favorite) {
        this.favorite = favorite;
        updated = true;
    }

    public void updateType(int type) {
        this.type = type;
        updated = true;
    }
}
