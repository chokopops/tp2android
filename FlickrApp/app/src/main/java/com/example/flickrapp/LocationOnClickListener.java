package com.example.flickrapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

class LocationOnClickListener implements View.OnClickListener {
    private MainActivity mainActivity;


    public LocationOnClickListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= 29 &&
                ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager androidLocationManager = (LocationManager)mainActivity.getSystemService(Context.LOCATION_SERVICE);
            Location loc = androidLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            String latitude = "" + loc.getLatitude();
            String longitude = "" + loc.getLongitude();
            String coordinates = String.format("Latitude : %f - Longitude : %f\n", loc.getLatitude(), loc.getLongitude());
            Log.i("TE", coordinates);
            TextView latitude2 = (TextView)mainActivity.findViewById(R.id.latitude);
            TextView longitude2 = (TextView)mainActivity.findViewById(R.id.longitude);
            latitude2.setText(latitude);
            longitude2.setText(longitude);


        } else {
            Log.i("CIO", "Permissions denied. SDK_INT=" + Build.VERSION.SDK_INT);
            Log.i("CIO", "ACCESS_COARSE_LOCATION: " + ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION));
            Log.i("CIO", "ACCESS_FINE_LOCATION: " + ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION));
            Log.i("CIO", "PackageManager.PERMISSION_GRANTED=" + PackageManager.PERMISSION_GRANTED);
        }
    }
}
