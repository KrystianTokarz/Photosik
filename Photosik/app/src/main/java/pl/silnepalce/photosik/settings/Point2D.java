package pl.silnepalce.photosik.settings;

/**
 * Created by kryst on 30.04.2017.
 */

public class Point2D {


    private float pointX;

    private float pointY;

    public Point2D(float pointX, float pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public float getPointX() {
        return pointX;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }
}
