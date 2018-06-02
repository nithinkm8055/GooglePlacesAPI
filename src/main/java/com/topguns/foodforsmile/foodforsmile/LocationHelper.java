package com.topguns.foodforsmile.foodforsmile;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class LocationHelper {

    final static int REQUEST_LOCATION = 1;
    String latitudeLongitude = null;

    String getLocation(Activity context, LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (null != location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                latitudeLongitude = latitude + ";" + longitude;
            }
        }
        return latitudeLongitude;
    }

}
