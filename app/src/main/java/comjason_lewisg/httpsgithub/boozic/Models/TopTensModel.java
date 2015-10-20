package comjason_lewisg.httpsgithub.boozic.Models;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class TopTensModel {
    public String label;
    public String lastUpdate;
    public double userRating;
    public int boozicScore;

    public String closestStoreName;
    public String cheapestStoreName;
    public double closestStoreDist;
    public double cheapestStoreDist;
    public double closestPrice;
    public double cheapestPrice;

    public int typePic;
    public boolean favorite;

    public String container;
    public double volume;
    public String volumeMeasure;

    public double abv;
    public int proof;
    public double abp;
    public double pdd;

    public int rating5;
    public int rating4;
    public int rating3;
    public int rating2;
    public int rating1;

    public TopTensModel(String label, String lastUpdate, double userRating, String closestStoreName, String cheapestStoreName, double closestStoreDist, double cheapestStoreDist,
                        double closestPrice, double cheapestPrice, int type, boolean favorite, String container,
                        double abv, int proof, int rating5, int rating4,int rating3, int rating2, int rating1) {

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
        this.container = container;
        this.abv = abv;
        this.proof = proof;
        this.rating5 = rating5;
        this.rating4 = rating4;
        this.rating3 = rating3;
        this.rating2 = rating2;
        this.rating1 = rating1;

        volume = findVol();
        abp = findABP();
        pdd = findPDD();
    }

    public TopTensModel(String label, String lastUpdate, double userRating, String closestStoreName, double closestStoreDist, double closestPrice, int type, boolean favorite,
                        String container, double abv, int proof, int rating5, int rating4,int rating3, int rating2, int rating1) {

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
        this.container = container;
        this.abv = abv;
        this.proof = proof;
        this.rating5 = rating5;
        this.rating4 = rating4;
        this.rating3 = rating3;
        this.rating2 = rating2;
        this.rating1 = rating1;

        volume = findVol();
        abp = findABP();
        pdd = findPDD();
    }

    private double findVol() {
        double volumetmp = 0;

        switch (container) {
            case "handle":
                volumetmp = 1.75;
                volumeMeasure = "L";
                break;
            case "fifth":
                volumetmp = 750;
                volumeMeasure = "ml";
                break;
            case "(6) bottle":
                volumetmp = 72;
                volumeMeasure = "oz";
                break;
            case "(12) bottle":
                volumetmp = 144;
                volumeMeasure = "oz";
                break;
            case "(12) can":
                volumetmp = 144;
                volumeMeasure = "oz";
                break;
            case "(24) can":
                volumetmp = 288;
                volumeMeasure = "oz";
                break;
        }

        return volumetmp;
    }

    public double findABP() {
        double volumetmp = volume;
        String volmeas = findVolMeasure();

        if (volmeas == "oz")
            volumetmp = volume * 29.5735;
        else if (volmeas == "L")
            volumetmp = volume * 1000;

        float abptmp = (float)cheapestPrice / (((float)abv/100f) * (float)volumetmp);

        return (double)abptmp;
    }
    public double findPDD() {
        float pddtmp = 1f/21f;
        float pddtmp2 = ((float)cheapestStoreDist - (float)closestStoreDist) * (float)2.0 * (float)2.59;
        pddtmp = pddtmp * pddtmp2;

        return (double)pddtmp;
    }
    private String findVolMeasure() {
        String volMeasure = "";

        switch (container) {
            case "handle":
                volMeasure = "L";
                break;
            case "fifth":
                volMeasure = "ml";
                break;
            case "(6) bottle":
                volMeasure = "oz";
                break;
            case "(12) bottle":
                volMeasure = "oz";
                break;
            case "(12) can":
                volMeasure = "oz";
                break;
            case "(24) can":
                volMeasure = "oz";
                break;
        }

        return volMeasure;
    }
}
