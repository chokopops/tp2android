package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button Getimage;
    private Button GoToList;
    private static ImageView img;
    public static Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting elements of the layout
        img = (ImageView)findViewById(R.id.image);
        GoToList = (Button)findViewById(R.id.GoToList);
        Getimage = (Button)findViewById(R.id.Getimage);

        // Using the GetImageOnClickListener which override the usual onClick listener
        Getimage.setOnClickListener(new GetImageOnClickListener(){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                setRes(bm); //la fonction setRes s'execute trop rapidement et charge donc la dernière
            }
        });

        // Ceating an Intent to redirect to another activity while clicking
        GoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listPage = new Intent(MainActivity.this, ListActivity.class);
                startActivity(listPage);
            }
        });
    }

    public static void setRes(Bitmap bm){
        img.setImageBitmap(bm);
    }
}