package pl.silnepalce.photosik;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import layout.FilterFragment;

public class MainActivity extends AppCompatActivity {

    private int returnedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void takePhoto(View view){
        returnedValue=0;
        loadEditor();

    }

    public void loadImage(View view){
        returnedValue=1;
        loadEditor();
    }

  protected void loadEditor(){
        Intent intent = new Intent(this, Editor.class);
        intent.putExtra("value", returnedValue);
        startActivity(intent);
    }

}
