package pl.silnepalce.photosik;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import layout.BorderFragment;
import layout.FilterFragment;
import layout.PaintFragment;
import pl.silnepalce.photosik.settings.Filters;
import pl.silnepalce.photosik.settings.MyImageView;
import pl.silnepalce.photosik.settings.Point2D;
import pl.silnepalce.photosik.settings.PointsSingleton;
import layout.ResizableFragment;
import layout.RgbFragment;
import pl.silnepalce.photosik.settings.RgbSingleton;
import layout.WaitingFragment;

public class Editor extends AppCompatActivity implements FilterFragment.FragmentFilterListener,
        RgbFragment.FragmentRgbListener,
        ResizableFragment.FragmentResizableListener,
        PaintFragment.FragmentPaintListener,
        BorderFragment.FragmentBorderListener {

    private MyImageView imageView;
    private Bitmap originalBitmap;
    private Bitmap actuallyBitmap;
    private Bitmap bitmapForRgb,cachedBitmap;
    private android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private ImageButton resizableButton, filterButton,rbgButton, paintButton;
    private Fragment fragment;
    private RgbSingleton rgbSingleton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        imageView = (MyImageView) findViewById(R.id.imageView);
        imageView.setDrawingCacheEnabled(true);
        rgbSingleton = RgbSingleton.getInstance();
        if (savedInstanceState != null) {
            actuallyBitmap = savedInstanceState.getParcelable("actuallyBitmap");
            bitmapForRgb = savedInstanceState.getParcelable("rgbBitmap");
            originalBitmap = savedInstanceState.getParcelable("originalBitmap");
            imageView.setImageBitmap(actuallyBitmap);
            PointsSingleton pointsSingleton = PointsSingleton.getInstance();
            imageView.setPointsWithColour(pointsSingleton.getPointsWithColour());
            imageView.setLastPoints(pointsSingleton.getLastPoints());
        } else {
            Bundle dataFromStartActivity = getIntent().getExtras();
            int value = (int) dataFromStartActivity.get("value");

            if (value == 0) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(cameraIntent, 0);
            } else if (value == 1) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
            rgbSingleton.resetAllValue();
        }
        resizableButton = (ImageButton) findViewById(R.id.resizableButton);
        filterButton = (ImageButton ) findViewById(R.id.filtrButton);
        rbgButton = (ImageButton ) findViewById(R.id.rgbButton);
        paintButton = (ImageButton ) findViewById(R.id.paintButton);
        setFragment(1);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PointsSingleton pointsSingleton = PointsSingleton.getInstance();
        pointsSingleton.setLastPoints(imageView.getLastPoints());
        pointsSingleton.setPointsWithColour(imageView.getPointsWithColour());
        outState.putParcelable("actuallyBitmap",actuallyBitmap);
        outState.putParcelable("rgbBitmap",bitmapForRgb);
        outState.putParcelable("originalBitmap",originalBitmap);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri dataUri = data.getData();
            try {
                originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), dataUri);
                originalBitmap = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.getWidth()/5, originalBitmap.getHeight()/5, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            actuallyBitmap  = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.getWidth(), originalBitmap.getHeight(), false); ;
            imageView.setImageBitmap(actuallyBitmap);
        }else if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            originalBitmap = (Bitmap) data.getExtras().get("data");
            actuallyBitmap = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.getWidth(), originalBitmap.getHeight(), false); ;
            imageView.setImageBitmap(actuallyBitmap);
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.savePhoto:
                savePhoto();
                return true;
            case R.id.refreshPhoto:
                refreshPhoto();
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void savePhoto(){
        cachedBitmap = imageView.getBitmap();
        MediaStore.Images.Media.insertImage(getContentResolver(),cachedBitmap, "titles", "photo from ImageChanger app");
        Toast.makeText(this, "Photo is saved into your gallery!",
                Toast.LENGTH_LONG).show();
    }

    public void refreshPhoto(){
        RgbSingleton rbgSingleton = RgbSingleton.getInstance();
        rbgSingleton.setB(0);
        rbgSingleton.setR(0);
        rbgSingleton.setG(0);
        imageView.clearEverything();
        imageView.clearBorder();
        imageView.setImageBitmap(originalBitmap);
        actuallyBitmap = Bitmap.createBitmap(originalBitmap);
        rgbSingleton.resetAllValue();
        setFragment(1);
        bitmapForRgb = null;
    }

    public void changeFragment(View view){

        fragmentTransaction = fragmentManager.beginTransaction();

        if(view == findViewById(R.id.filtrButton)){
            fragment = new FilterFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        else if(view == findViewById(R.id.rgbButton)) {
            fragment = new RgbFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            if(bitmapForRgb == null)
                bitmapForRgb = Bitmap.createBitmap(actuallyBitmap);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        else if(view == findViewById(R.id.resizableButton)) {
            fragment = new ResizableFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                   return true;
                }
            });
        }
        else if(view == findViewById(R.id.paintButton)) {
            fragment = new PaintFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    List<Point> points = imageView.getPointsWithColour().get(imageView.getActuallyPaintColour());
                    Point point = new Point((int) event.getX(), (int) event.getY());
                    imageView.getLastPoints().add(point);
                    points.add(point);
                    imageView.invalidate();
                    return true;
                }
            });
        }
        else if(view == findViewById(R.id.borderButton)){
            fragment = new BorderFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            imageView.setBorderSelected(false);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return imageView.onTouchEvent(event);
                }
            });

        }
    }

    public void changeButtonEnabled(boolean value){
        resizableButton.setEnabled(value);
        filterButton.setEnabled(value);
        rbgButton.setEnabled(value);
        paintButton.setEnabled(value);
    }

    public void setFragment(int value){
        fragmentTransaction = fragmentManager.beginTransaction();
        if(value == 0 )
            fragment = new WaitingFragment();
        else if (value == 1 )
            fragment = new FilterFragment();
        else if (value == 2 )
            fragment = new RgbFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }




    @Override
    public void filterSelected(int number) {
        changeButtonEnabled(false);
        setFragment(0);
        bitmapForRgb=null;


        final Point2D p1 = imageView.getP1();
        final Point2D p2 = imageView.getP2();
        final Matrix imageMatrix = imageView.getImageMatrix();
        switch (number){
            case 1:{

                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {

                        return  Filters.applyInvertEffect(originalBitmap,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
            case 2: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applyGreyscaleEffect(originalBitmap,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
            case 3: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applyGammaEffect(originalBitmap,1.8,2.8,1.8,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
            case 4: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applyGammaEffect(originalBitmap,3.8,4.8,3.8,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
            case 5: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applySepiaToningEffect(originalBitmap,10,1.2,2.87,2.1,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
            case 6: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applySepiaToningEffect(originalBitmap,20,1.2,1.87,3.1,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;

            }
            case 7: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applyColorFilterEffect(originalBitmap,2,1,2,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
            case 8: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applyGammaEffect(originalBitmap,9.8,4.1,1.8,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
            case 9: {
                new AsyncTask<Bitmap, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Bitmap... params) {
                        return  Filters.applyGammaEffect(originalBitmap,1.8,1.8,11.8,p1,p2,imageMatrix);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        imageView.setImageBitmap(bitmap);
                        changeButtonEnabled(true);
                        setFragment(1);
                        actuallyBitmap = bitmap;
//                        cachedBitmap = imageView.getBitmap();
                    }
                }.execute(originalBitmap);
                break;
            }
        }
        imageView.invalidate();
    }

    @Override
    public void rgbSelected(final int redValue,final int greenValue,final int blueValue) {
        changeButtonEnabled(false);
        setFragment(0);
        final Point2D p1 = imageView.getP1();
        final Point2D p2 = imageView.getP2();
        final Matrix imageMatrix = imageView.getImageMatrix();
        new AsyncTask<Bitmap, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Bitmap... bitmap) {
                return Filters.changeRGBColour(bitmapForRgb,redValue,greenValue,blueValue,p1,p2,imageMatrix);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
//                cachedBitmap = imageView.getBitmap();
                actuallyBitmap = bitmap;
                changeButtonEnabled(true);
                setFragment(2);
            }
        }.execute();
        imageView.invalidate();
    }

    @Override
    public void resizableSelected(int type, int value) {
        Matrix matrix = new Matrix();
        bitmapForRgb=null;

        if(type == 1){
            matrix.postRotate(value);
            actuallyBitmap = Bitmap.createBitmap(actuallyBitmap , 0, 0, actuallyBitmap .getWidth(), actuallyBitmap .getHeight(), matrix, false);
            cachedBitmap = actuallyBitmap;
            imageView.setImageBitmap(cachedBitmap);
        }else if(type == 2){
            matrix.postRotate(value);
            actuallyBitmap = Bitmap.createBitmap(actuallyBitmap , 0, 0, actuallyBitmap .getWidth(), actuallyBitmap .getHeight(), matrix, false);
            cachedBitmap = actuallyBitmap;
            imageView.setImageBitmap(cachedBitmap);
        }else{
            matrix.postRotate(value);
            actuallyBitmap = Bitmap.createBitmap(actuallyBitmap , 0, 0, actuallyBitmap .getWidth(), actuallyBitmap .getHeight(), matrix, false);
            cachedBitmap = actuallyBitmap;
            imageView.setImageBitmap(cachedBitmap);
        }
        imageView.invalidate();
    }

    @Override
    public void paintAction(int number) {
        imageView.setColour(number);
    }

    @Override
    public void cancelLastActions() {
        imageView.cancelLastAction();
        imageView.invalidate();
//        cachedBitmap = imageView.getBitmap();
    }

    @Override
    public void clearAllActions() {
        imageView.clearEverything();
        imageView.invalidate();
    }

    @Override
    public void drawBorder() {
        imageView.setBorderSelected(true);
    }

    @Override
    public void clearBorder() {
        imageView.clearBorder();
    }
}
