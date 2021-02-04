package com.example.vehicle_try;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class MapCall extends AppCompatActivity {
//    String Place=getIntent().getStringExtra("place");
    double latitude,longitude;
    public int locationStatus;
    public int backpress=0;
    private static final int ACCESS_CORE_LOCATION_PERMISSION_CODE = 100;
    private static final int ACCESS_FINE_LOCATION_PERMISSION_CODE = 101;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        if (ContextCompat.checkSelfPermission(MapCall.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
//
//            // Requesting the permission
//            ActivityCompat.requestPermissions(MapCall.this,
//                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION },
//                    ACCESS_FINE_LOCATION_PERMISSION_CODE);
//        }
//        else {
//            SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
//            value = sharedPreferences.getString("value","");
//            mapcall();
//        }
        if(off==0){
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(onGPS);
        }
        System.out.println(value);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode,
//                        permissions,
//                        grantResults);
//
//        if (requestCode == ACCESS_FINE_LOCATION_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MapCall.this,
//                        "Camera Permission Granted",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                Toast.makeText(MapCall.this,
//                        "Camera Permission Denied",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }
        LocationManager locationManager;
//    public void checkPermission(String permission, int requestCode)
//    {
//
//    }
    public void mapcall(){

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.i("LOCATION", "Latitude:" + latitude + ", Longitude:" + longitude);
                    Toast.makeText(getApplicationContext(), "Latitude:" + latitude + ", Longitude:" + longitude, Toast.LENGTH_SHORT).show();
                    //getWeaterData(longitude, latitude);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.i("LOCATION", "disable");
                    Toast.makeText(getApplicationContext(), "Please enable your Location Service.", Toast.LENGTH_LONG).show();
                    ;
                    locationStatus = -1;
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.i("LOCATION", "enable");
                    locationStatus = 1;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.i("LOCATION", "status" + status);
                }

            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
                System.out.println(latitude+" ,"+longitude);
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude +"?q="+value);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}