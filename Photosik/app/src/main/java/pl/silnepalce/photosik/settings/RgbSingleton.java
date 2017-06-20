package pl.silnepalce.photosik.settings;

/**
 * Created by kryst on 02.04.2017.
 */

public class RgbSingleton {

    private static final RgbSingleton ourInstance = new RgbSingleton();

    public static RgbSingleton getInstance() {
        return ourInstance;
    }

    private int r;
    private int g;
    private int b;

    private RgbSingleton() {
    }

    public int getR() {
        return r;
    }

    public void resetAllValue(){
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }
    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
