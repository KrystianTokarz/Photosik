package pl.silnepalce.photosik.settings;

import android.graphics.Point;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kryst on 07.04.2017.
 */

public class PointsSingleton {
    private static final PointsSingleton ourInstance = new PointsSingleton();

    public static PointsSingleton getInstance() {
        return ourInstance;
    }

    private PointsSingleton() {
    }

    private ArrayDeque<Point> lastPoints = new ArrayDeque<>();
    private Map<Integer, List<Point>> pointsWithColour = new HashMap<>();

    public ArrayDeque<Point> getLastPoints() {
        return lastPoints;
    }

    public void setLastPoints(ArrayDeque<Point> lastPoints) {
        this.lastPoints = lastPoints;
    }

    public Map<Integer, List<Point>> getPointsWithColour() {
        return pointsWithColour;
    }

    public void setPointsWithColour(Map<Integer, List<Point>> pointsWithColour) {
        this.pointsWithColour = pointsWithColour;
    }
}
