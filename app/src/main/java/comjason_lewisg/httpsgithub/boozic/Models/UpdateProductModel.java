package comjason_lewisg.httpsgithub.boozic.Models;

public class UpdateProductModel {

    public String label;
    public double userRating;
    public int deviceID;

    public String StoreName;
    public double StoreDist;
    public double Price;

    public boolean favorite;

    public String container;
    public double volume;
    public String volumeMeasure;

    public double abv;
    public int proof;

    public UpdateProductModel(String label, double volume, String volumeMeasure, double abv) {
        this.label = label;
        this.volume = volume;
        this.volumeMeasure = volumeMeasure;
        updateABV(abv);

        userRating = -1;
        deviceID = -1;
        StoreName = null;
        StoreDist = -1;
        Price = -1;
        favorite = false;
        container = null;
    }

    public UpdateProductModel(String label, double volume, String volumeMeasure) {
        this.label = label;
        this.volume = volume;
        this.volumeMeasure = volumeMeasure;

        userRating = -1;
        deviceID = -1;
        StoreName = null;
        StoreDist = -1;
        Price = -1;
        favorite = false;
        container = null;
        abv = -1;
        proof = -1;
    }

    public void updateContainer(String container) {
        this.container = container;
    }

    public void updateABV(double abv) {
        this.abv = abv;
        proof = 2 * (int)abv;

        if ((this.Price != -1) && (volume != -1)) {
            findABP();
            findPBV();
        }
    }

    public void updateStore(String StoreName, double StoreDist) {
        this.StoreName = StoreName;
        this.StoreDist = StoreDist;
    }

    public void updateStorePrice(double Price) {
        this.Price = Price;

        if ((this.Price != -1) && (volume != -1)) {
            findABP();
            findPBV();
        }
    }

    public double findABP() {
        double volumetmp = convertVol();
        float abptmp = (float)Price / (((float)abv/100f) * (float)volumetmp);

        return (double)abptmp;
    }
    private double findPBV() {
        float pbvtmp = (float)Price / (float)convertVol();
        return (double)pbvtmp;
    }

    private double convertVol() {
        double volumetmp = volume;

        if (volumeMeasure.equals("oz"))
            volumetmp = volume * 29.5735;
        else if (volumeMeasure.equals("L"))
            volumetmp = volume * 1000;

        return volumetmp;
    }

}
