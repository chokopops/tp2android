package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
        img = (ImageView) findViewById(R.id.image);
        GoToList = (Button) findViewById(R.id.GoToList);
        Getimage = (Button) findViewById(R.id.Getimage);

        // Using the GetImageOnClickListener which override the usual onClick listener
        Getimage.setOnClickListener(new GetImageOnClickListener() {
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

        LocationManager locationManager = (LocationManager)
                this.getSystemService(this.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        if (locationManager != null) {
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

            // Register the listener with the Location Manager to receive location updates
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        Button b1 = (Button)findViewById(R.id.coordinate);
        b1.setOnClickListener(new LocationOnClickListener(this){
            @Override
            public void onClick(View v) {
                super.onClick(v);
            }
        });
    }

    public static void setRes(Bitmap bm){
        img.setImageBitmap(bm);
    }
}