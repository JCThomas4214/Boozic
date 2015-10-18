package comjason_lewisg.httpsgithub.boozic.Models;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class TopTensModel {
    private static int nextId = 0;
    public String label;
    public String closestStoreName;
    public String cheapestStoreName;
    public BigDecimal closestPrice;
    public BigDecimal cheapestPrice;
    public int typePic;
    public double distance;
    public boolean favorite;
    public String volume;
    public double rating;
    public int alcoholId;
    public long barcode;
    int id = ++nextId;

    public TopTensModel(int type, String title, String store, String vol, double dist, BigDecimal value, boolean fave) {
        label = title;
        typePic = type;
        closestStoreName = store + " (" + dist + "mi)";
        closestPrice = value;
        volume = "(" + vol + ")";
        favorite = fave;
    }
}
