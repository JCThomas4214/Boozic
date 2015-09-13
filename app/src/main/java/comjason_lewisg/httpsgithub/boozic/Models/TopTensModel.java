package comjason_lewisg.httpsgithub.boozic.Models;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class TopTensModel {
    private static int nextId = 0;
    public String label;
    public String pathToImage;
    public String storeName;
    public BigDecimal price;
    public int typePic;
    public double distance;
    public boolean favorite;
    public String volume;
    public double rating;
    int id = ++nextId;

    public TopTensModel(int type, String title, String store, String vol, double dist, BigDecimal value, boolean fave) {
        label = title;
        typePic = type;
        storeName = store + " (" + dist + "mi)";
        price = value;
        volume = "(" + vol + ")";
        favorite = fave;
    }
}
