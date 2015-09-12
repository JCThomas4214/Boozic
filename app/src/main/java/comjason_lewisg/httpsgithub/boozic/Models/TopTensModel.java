package comjason_lewisg.httpsgithub.boozic.Models;

import java.util.Date;

public class TopTensModel {
    private static int nextId = 0;
    public String label;
    public String description;
    public String pathToImage;
    int id = ++nextId;

    public TopTensModel(String title, String explain) {
        label = title;
        description = explain;
    }
}
