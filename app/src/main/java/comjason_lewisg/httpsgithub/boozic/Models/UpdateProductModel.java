package comjason_lewisg.httpsgithub.boozic.Models;

public class UpdateProductModel {

    public double userRating = -1;
    public String upc;
    public int productId;

    public String StoreName = null;
    public double StoreDist = -1;
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
    }

    public void updateABV(double abv) {
        this.abv = abv;
    }

    public void updateStore(String StoreName, double StoreDist) {
        this.StoreName = StoreName;
        this.StoreDist = StoreDist;
    }

    public void updateStorePrice(double Price) {
        this.Price = Price;
    }

    public void updateVolume(int volume) {
        this.volume = volume;
    }

    public void updateRating(double rating) {
        this.userRating = rating;
    }

    public void updateFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
