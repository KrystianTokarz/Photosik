package pl.silnepalce.photosik.settings;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
//import android.widget.ImageView;
import android.graphics.Point;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MyImageView extends AppCompatImageView {

    private Paint currentPaint;
    private Paint borderPaint;

    private float pointX;
    private float pointY;
    private Point2D p1 = null;
    private Point2D p2 = null;

    private boolean isBorderSelected = false;
    private boolean isBorderCreated = false;

    private ArrayDeque<Point> lastPoints = new ArrayDeque<>();
    private Map<Integer, List<Point>> pointsWithColour = new HashMap<>();

    public ArrayDeque<Point> getLastPoints(){
        return this.lastPoints;
    }
    public void setLastPoints(ArrayDeque<Point> lastPoints){
        this.lastPoints = lastPoints;
    }
    public void setPointsWithColour(Map<Integer, List<Point>> pointsWithColour){
        this.pointsWithColour = pointsWithColour;
    }
    public Map<Integer, List<Point>> getPointsWithColour(){
        return this.pointsWithColour;
    }

    public MyImageView(Context context, AttributeSet attrs){
        super(context,attrs);
        currentPaint = new Paint();

        currentPaint.setDither(true);
        currentPaint.setColor(Color.BLACK);
        currentPaint.setStyle(Paint.Style.FILL);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);

        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(5);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeJoin(Paint.Join.ROUND);
        borderPaint.setStrokeCap(Paint.Cap.ROUND);
        createColourMap();
    }

    public void createColourMap(){
        pointsWithColour.put(Color.RED, new ArrayList<Point>());
        pointsWithColour.put(Color.BLACK, new ArrayList<Point>());
        pointsWithColour.put(Color.BLUE, new ArrayList<Point>());
        pointsWithColour.put(Color.YELLOW, new ArrayList<Point>());
        pointsWithColour.put(Color.MAGENTA, new ArrayList<Point>());
        pointsWithColour.put(Color.GREEN, new ArrayList<Point>());
    }

    public Paint getRightPaint(int value){
        Paint tmpPaint = new Paint();
        tmpPaint.setDither(true);
        tmpPaint.setColor(value);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setStrokeJoin(Paint.Join.ROUND);
        tmpPaint.setStrokeCap(Paint.Cap.ROUND);
        return tmpPaint;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isBorderSelected) {
            isBorderCreated= true;
            pointX = event.getX();
            pointY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    p1 = new Point2D(pointX, pointY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    p2 = new Point2D(pointX, pointY);
                    break;
                case MotionEvent.ACTION_UP:
                    p2 = new Point2D(pointX, pointY);
                    break;
            }
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Point> points = new ArrayList<>();
        points = pointsWithColour.get(Color.RED);
        for (Point point: points) {
            canvas.drawCircle(point.x,point.y,10,getRightPaint(Color.RED));
        }
        points = pointsWithColour.get(Color.BLUE);
        for (Point point: points) {
            canvas.drawCircle(point.x,point.y,10,getRightPaint(Color.BLUE));
        }
        points = pointsWithColour.get(Color.GREEN);
        for (Point point: points) {
            canvas.drawCircle(point.x,point.y,10,getRightPaint(Color.GREEN));
        }
        points = pointsWithColour.get(Color.YELLOW);
        for (Point point: points) {
            canvas.drawCircle(point.x,point.y,10,getRightPaint(Color.YELLOW));
        }
         points = pointsWithColour.get(Color.BLACK);
        for (Point point: points) {
            canvas.drawCircle(point.x,point.y,10,getRightPaint(Color.BLACK));
        }
        points = pointsWithColour.get(Color.MAGENTA);
        for (Point point: points) {
            canvas.drawCircle(point.x,point.y,10,getRightPaint(Color.MAGENTA));
        }

        if(p1!=null && p2!=null) {


            if(p1.getPointX() > p2.getPointX() && p1.getPointY() > p2.getPointY())
                canvas.drawRect(p2.getPointX(), p2.getPointY(), p1.getPointX(), p1.getPointY(), borderPaint);
            else if(p1.getPointX() > p2.getPointX() && p1.getPointY() < p2.getPointY())
                canvas.drawRect(p2.getPointX(), p1.getPointY(), p1.getPointX(), p2.getPointY(), borderPaint);
            else if(p1.getPointX() < p2.getPointX() && p1.getPointY() > p2.getPointY())
                canvas.drawRect(p1.getPointX(), p2.getPointY(), p2.getPointX(), p1.getPointY(), borderPaint);
            else
                canvas.drawRect(p1.getPointX(), p1.getPointY(), p2.getPointX(), p2.getPointY(), borderPaint);
        }
    }

    public Bitmap getBitmap() {
            return this.getDrawingCache();

    }

    public void setColour(int color){
        this.currentPaint.setColor(color);
    }

    public int getActuallyPaintColour(){
        return this.currentPaint.getColor();
    }


    public void cancelLastAction(){
        List<Point> points;
        lastLoop: for(int i=0; i<10; i++) {
            points = pointsWithColour.get(Color.RED);
            for (Point point: points) {
                if(point == lastPoints.getLast()){
                    lastPoints.remove(point);
                    points.remove(point);
                    continue lastLoop;
                }

            }
            points = pointsWithColour.get(Color.BLUE);
            for (Point point: points) {
                if(point == lastPoints.getLast()) {
                    lastPoints.remove(point);
                    points.remove(point);
                    continue lastLoop;
                }
            }
            points = pointsWithColour.get(Color.GREEN);
            for (Point point: points) {
                if(point == lastPoints.getLast()){
                    lastPoints.remove(point);
                    points.remove(point);
                    continue lastLoop;
                }
            }
            points = pointsWithColour.get(Color.YELLOW);
            for (Point point: points) {
                if(point == lastPoints.getLast()){
                    lastPoints.remove(point);
                    points.remove(point);
                    continue lastLoop;
                }
            }
            points = pointsWithColour.get(Color.BLACK);
            for (Point point: points) {
                if(point == lastPoints.getLast()){
                    lastPoints.remove(point);
                    points.remove(point);
                    continue lastLoop;
                }
            }
            points = pointsWithColour.get(Color.MAGENTA);
            for (Point point: points) {
                if(point == lastPoints.getLast()){
                    lastPoints.remove(point);
                    points.remove(point);
                    continue lastLoop;
                }
            }
        }
    }

    public void clearEverything() {
        lastPoints = new ArrayDeque<>();
        pointsWithColour = new HashMap<>();
        createColourMap();
    }

    public void setBorderSelected(boolean value){
        isBorderSelected = value;
    }

    public boolean checkBitmapIsToCut(){
        return isBorderCreated;
    }

    public Point2D getP1(){
        return this.p1;
    }

    public Point2D getP2(){
        return this.p2;
    }

    public void clearBorder(){
        isBorderCreated= false;
        isBorderSelected = false;

        p1 = null;
        p2 = null;
        invalidate();
    }
}
