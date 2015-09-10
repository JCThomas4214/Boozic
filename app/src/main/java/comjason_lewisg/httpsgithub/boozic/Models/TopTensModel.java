package comjason_lewisg.httpsgithub.boozic.Models;

public class TopTensModel {
    private static int nextId = 0;
    public String label;
    public String pathToImage;
    int id = ++nextId;

    public TopTensModel(String title) {
        label = title;

    }
}
