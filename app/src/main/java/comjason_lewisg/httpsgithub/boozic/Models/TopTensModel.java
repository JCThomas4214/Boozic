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

    public double pbv;
    public double abv;
    public int proof;
    public double abp;
    public double pdd;
    public double td;

    public int[] rating = new int[5];
    public double avgRating;

    public TopTensModel(String label, String lastUpdate, double userRating, String closestStoreName, String cheapestStoreName, double closestStoreDist, double cheapestStoreDist,
                        double closestPrice, double cheapestPrice, int type, boolean favorite, String container,
                        double abv, int proof, int[] rating) {

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
        System.arraycopy(rating,0,this.rating,0,rating.length);

        volume = findVol();
        pbv = findPBV();
        abp = findABP();
        pdd = findPDD();
        td = findTD();
        avgRating = findAverage();
    }

    public TopTensModel(String label, String lastUpdate, double userRating, String closestStoreName, double closestStoreDist, double closestPrice, int type, boolean favorite,
                        String container, double abv, int proof, int[] rating) {

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
        System.arraycopy(rating,0,this.rating,0,rating.length);

        volume = findVol();
        pbv = findPBV();
        abp = findABP();
        avgRating = findAverage();
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
        double volumetmp = convertVol();
        float abptmp = (float)cheapestPrice / (((float)abv/100f) * (float)volumetmp);

        return (double)abptmp;
    }
    public double findPDD() {
        float pddtmp = 1f/21f;
        float pddtmp2 = ((float)cheapestStoreDist - (float)closestStoreDist) * (float)2.0 * (float)2.59;
        pddtmp = pddtmp * pddtmp2;

        return (double)pddtmp;
    }
    private double findPBV() {
        float pbvtmp = (float)cheapestPrice / (float)convertVol();
        return (double)pbvtmp;
    }
    private double findTD() {
        float tdtmp = (float)closestPrice - (float)cheapestPrice - (float)pdd;
        return (double)tdtmp;
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

    private double convertVol() {
        double volumetmp = volume;
        String volmeas = findVolMeasure();
        if (volmeas.equals("oz"))
            volumetmp = volume * 29.5735;
        else if (volmeas.equals("L"))
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

        double avg = ((wTotal / total) * 100) / 20;

        return avg;
    }
}
