package comjason_lewisg.httpsgithub.boozic.Models;

public class UpdateProductModel {

    public boolean updated = false;
    public int position;
    public int favoritePosition = -1;

    public String label = null;
    public int parentType = -1;
    public int type = -1;
    public double userRating = -1;
    public String upc;
    public int productId;

    public int StoreID = -1;
    public String StoreName = null;
    public double Price = -1;

    public int favorite = -1;

    public String containerType = null;
    public int containerQuantity = -1;
    public double volume = -1;
    public String volumeMeasure = null;

    public double abv = -1;

    public UpdateProductModel(String upc, int productId, int position) {
        this.upc = upc;
        this.productId = productId;
        this.position = position;
    }

    public UpdateProductModel(String upc, int productId, int position, int favoritePosition) {
        this.upc = upc;
        this.productId = productId;
        this.position = position;
        this.favoritePosition = favoritePosition;
    }

    public void updateContainerType(String container) {
        this.containerType = container;
        updated = true;
    }

    public void updateContainerQuant(int quantity, double volume, int oldQuantity) {
        this.containerQuantity = quantity;
        updated = true;

        double singleContainerVol = volume / (double)oldQuantity;

        this.volume = quantity * singleContainerVol;
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

    public void updateVolume(double volume, int currentQty, String oldVolumeMeasure) {
        this.volume = volume * (double)currentQty;
        if (this.volume > 1000) {
            this.volume = this.volume / 1000.0;
            this.volumeMeasure = "L";
        }
        else if (this.volume < 1000 && oldVolumeMeasure.equals("L")) {
            this.volumeMeasure = "ml";
        }
        updated = true;
    }

    public void updateRating(double rating) {
        this.userRating = rating;
        updated = true;
    }

    public void updateFavorite(int favorite) {
        this.favorite = favorite;
        updated = true;
    }

    public void updateParentType(int type) {
        this.parentType = type;
    }
    public void updateType(int type) {
        this.type = type;
        updated = true;
    }

    public void updateLabel(String label) {
        this.label = label;
        updated = true;
    }
}
