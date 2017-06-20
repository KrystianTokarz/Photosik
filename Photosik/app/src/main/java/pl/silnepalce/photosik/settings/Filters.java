package pl.silnepalce.photosik.settings;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;


public class Filters {


    public static Bitmap changeRGBColour(Bitmap bitmap, int redValue, int greenValue, int blueValue, Point2D p1, Point2D p2,Matrix matrix)  {
        Bitmap tmpBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int A, R, G, B;
        int pixelColor;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();


        float[] pts1 = new float[2];
        float[] pts2 = new float[2];
        if(p1!=null && p2!=null){
            Matrix inverse = new Matrix();
            pts1[0] =  p1.getPointX();
            pts1[1] = p1.getPointY();

            pts2[0] =  p2.getPointX();
            pts2[1] = p2.getPointY();

            matrix.invert(inverse);

            inverse.mapPoints(pts1);
            inverse.mapPoints(pts2);


            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if((pts1[1]>y && pts2[1]<y && pts1[0]>x && pts2[0]<x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]>y && pts2[1]<y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]>x && pts2[0]<x)){

                        pixelColor = bitmap.getPixel(x, y);
                        A = Color.alpha(pixelColor);

                        R = (Color.red(pixelColor) + redValue) % 255;
                        if (R < 0) {
                            R = 255 + R;
                        }
                        G = (Color.green(pixelColor) + greenValue) % 255;
                        if (G < 0) {
                            G = 255 + G;
                        }
                        B = (Color.blue(pixelColor) + blueValue) % 255;
                        if (B < 0) {
                            B = 255 + B;
                        }
                        tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
                    }else{
                        pixelColor = bitmap.getPixel(x, y);
                        A = Color.alpha(pixelColor);
                        R = Color.red(pixelColor);
                        G = Color.green(pixelColor);
                        B = Color.blue(pixelColor);
                        tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
            }
        }else {

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixelColor = bitmap.getPixel(x, y);
                    A = Color.alpha(pixelColor);

                    R = (Color.red(pixelColor) + redValue) % 255;
                    if (R < 0) {
                        R = 255 + R;
                    }
                    G = (Color.green(pixelColor) + greenValue) % 255;
                    if (G < 0) {
                        G = 255 + G;
                    }
                    B = (Color.blue(pixelColor) + blueValue) % 255;
                    if (B < 0) {
                        B = 255 + B;
                    }
                    tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }
        }
        return tmpBitmap;
    }


    public static Bitmap applyInvertEffect(Bitmap bitmap, Point2D p1, Point2D p2,Matrix matrix) {
        Bitmap tmpBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        int A, R, G, B;
        int pixelColor;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        float[] pts1 = new float[2];
        float[] pts2 = new float[2];
        if(p1!=null && p2!=null){
            Matrix inverse = new Matrix();
            pts1[0] =  p1.getPointX();
            pts1[1] = p1.getPointY();
            pts2[0] =  p2.getPointX();
            pts2[1] = p2.getPointY();
            matrix.invert(inverse);
            inverse.mapPoints(pts1);
            inverse.mapPoints(pts2);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if((pts1[1]>y && pts2[1]<y && pts1[0]>x && pts2[0]<x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]>y && pts2[1]<y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]>x && pts2[0]<x)){

                        pixelColor = bitmap.getPixel(x, y);
                        A = Color.alpha(pixelColor);
                        R = 255 - Color.red(pixelColor);
                        G = 255 - Color.green(pixelColor);
                        B = 255 - Color.blue(pixelColor);
                        tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
                    }else{
                        pixelColor = bitmap.getPixel(x, y);
                        A = Color.alpha(pixelColor);
                        R = Color.red(pixelColor);
                        G = Color.green(pixelColor);
                        B = Color.blue(pixelColor);
                        tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
            }
        }else{
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixelColor = bitmap.getPixel(x, y);
                    A = Color.alpha(pixelColor);
                    R = 255 - Color.red(pixelColor);
                    G = 255 - Color.green(pixelColor);
                    B = 255 - Color.blue(pixelColor);
                    tmpBitmap.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }
        }

        return tmpBitmap;
    }

    public static Bitmap applyGreyscaleEffect(Bitmap src, Point2D p1, Point2D p2,Matrix matrix) {

        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int A, R, G, B;
        int pixel;
        int width = src.getWidth();
        int height = src.getHeight();


        float[] pts1 = new float[2];
        float[] pts2 = new float[2];
        if(p1!=null && p2!=null){
            Matrix inverse = new Matrix();
            pts1[0] =  p1.getPointX();
            pts1[1] = p1.getPointY();

            pts2[0] =  p2.getPointX();
            pts2[1] = p2.getPointY();

            matrix.invert(inverse);

            inverse.mapPoints(pts1);
            inverse.mapPoints(pts2);


            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if((pts1[1]>y && pts2[1]<y && pts1[0]>x && pts2[0]<x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]>y && pts2[1]<y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]>x && pts2[0]<x)){

                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }else{
                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
            }
        }else {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    pixel = src.getPixel(x, y);
                    A = Color.alpha(pixel);
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }
        }
        return bmOut;
    }

    public static Bitmap applyGammaEffect(Bitmap src, double red, double green, double blue, Point2D p1, Point2D p2,Matrix matrix) {

        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int width = src.getWidth();
        int height = src.getHeight();
        int A, R, G, B;
        int pixel;
        final int MAX_SIZE = 256;
        final double MAX_VALUE_DBL = 255.0;
        final int MAX_VALUE_INT = 255;
        final double REVERSE = 1.0;

        int[] gammaR = new int[MAX_SIZE];
        int[] gammaG = new int[MAX_SIZE];
        int[] gammaB = new int[MAX_SIZE];

        for (int i = 0; i < MAX_SIZE; ++i) {
            gammaR[i] = (int) Math.min(MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));
            gammaG[i] = (int) Math.min(MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));
            gammaB[i] = (int) Math.min(MAX_VALUE_INT,
                    (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));
        }

        float[] pts1 = new float[2];
        float[] pts2 = new float[2];
        if(p1!=null && p2!=null){
            Matrix inverse = new Matrix();
            pts1[0] =  p1.getPointX();
            pts1[1] = p1.getPointY();

            pts2[0] =  p2.getPointX();
            pts2[1] = p2.getPointY();

            matrix.invert(inverse);

            inverse.mapPoints(pts1);
            inverse.mapPoints(pts2);


            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if((pts1[1]>y && pts2[1]<y && pts1[0]>x && pts2[0]<x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]>y && pts2[1]<y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]>x && pts2[0]<x)){

                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = gammaR[Color.red(pixel)];
                        G = gammaG[Color.green(pixel)];
                        B = gammaB[Color.blue(pixel)];
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }else{
                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
            }
        }else {

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    pixel = src.getPixel(x, y);
                    A = Color.alpha(pixel);
                    R = gammaR[Color.red(pixel)];
                    G = gammaG[Color.green(pixel)];
                    B = gammaB[Color.blue(pixel)];
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }
        }
        return bmOut;
    }

    public static Bitmap applyColorFilterEffect(Bitmap src, double red, double green, double blue, Point2D p1, Point2D p2,Matrix matrix) {

        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        int A, R, G, B;
        int pixel;

        float[] pts1 = new float[2];
        float[] pts2 = new float[2];
        if(p1!=null && p2!=null){
            Matrix inverse = new Matrix();
            pts1[0] =  p1.getPointX();
            pts1[1] = p1.getPointY();

            pts2[0] =  p2.getPointX();
            pts2[1] = p2.getPointY();

            matrix.invert(inverse);

            inverse.mapPoints(pts1);
            inverse.mapPoints(pts2);


            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if((pts1[1]>y && pts2[1]<y && pts1[0]>x && pts2[0]<x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]>y && pts2[1]<y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]>x && pts2[0]<x)){

                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = (int) (Color.red(pixel) * red);
                        G = (int) (Color.green(pixel) * green);
                        B = (int) (Color.blue(pixel) * blue);
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }else{
                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
            }
        }else {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    pixel = src.getPixel(x, y);
                    A = Color.alpha(pixel);
                    R = (int) (Color.red(pixel) * red);
                    G = (int) (Color.green(pixel) * green);
                    B = (int) (Color.blue(pixel) * blue);
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }
        }
        return bmOut;

    }


    public static Bitmap applySepiaToningEffect(Bitmap src, int depth, double red, double green, double blue, Point2D p1, Point2D p2,Matrix matrix) {

        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        int A, R, G, B;
        int pixel;
        float[] pts1 = new float[2];
        float[] pts2 = new float[2];
        if(p1!=null && p2!=null){
            Matrix inverse = new Matrix();
            pts1[0] =  p1.getPointX();
            pts1[1] = p1.getPointY();

            pts2[0] =  p2.getPointX();
            pts2[1] = p2.getPointY();

            matrix.invert(inverse);

            inverse.mapPoints(pts1);
            inverse.mapPoints(pts2);


            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if((pts1[1]>y && pts2[1]<y && pts1[0]>x && pts2[0]<x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]>y && pts2[1]<y && pts1[0]<x && pts2[0]>x) ||
                            (pts1[1]<y && pts2[1]>y && pts1[0]>x && pts2[0]<x)){

                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                        R += (depth * red);
                        if (R > 255) {
                            R = 255;
                        }

                        G += (depth * green);
                        if (G > 255) {
                            G = 255;
                        }

                        B += (depth * blue);
                        if (B > 255) {
                            B = 255;
                        }

                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }else{
                        pixel = src.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
            }
        }else {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    pixel = src.getPixel(x, y);
                    A = Color.alpha(pixel);
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                    R += (depth * red);
                    if (R > 255) {
                        R = 255;
                    }

                    G += (depth * green);
                    if (G > 255) {
                        G = 255;
                    }

                    B += (depth * blue);
                    if (B > 255) {
                        B = 255;
                    }

                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }
        }
        return bmOut;
    }

}
