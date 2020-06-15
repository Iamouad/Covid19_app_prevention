package com.project.gps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.models.DeviceAppearance;
import com.project.repositories.UserRepository;

public class MyActualLocation {
    final String TAG = "MYACTUALLOCATION";
    private Context activity;
    private FusedLocationProviderClient mFusedLocationClient;
    private DeviceAppearance device;
    private UserRepository userRepository;

    public MyActualLocation(Context activity, DeviceAppearance device){
        this.activity = activity;
        this.device = device;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        userRepository = new UserRepository();
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation(){
        System.out.println("getting the Last location.......");
        if (GpsPermissionChecker.checkPermissions(activity)) {
            if (GpsPermissionChecker.isLocationEnabled(activity)) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    System.out.println("location was null. restarting");
                                    requestNewLocationData();
                                } else {
                                    System.out.println("Lat: "+location.getLatitude()+"");
                                    System.out.println("Long: "+location.getLongitude()+"");
                                    device.setLatitude(location.getLatitude());
                                    device.setLongitude(location.getLongitude());
                                    System.out.println("adding the contacted device");
                                    userRepository.addContactedDevice(device);

                                }
                            }
                        }
                );
            } else {
                Log.d(TAG, "getLastLocation: ");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

       /* mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);*/

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity.getApplicationContext());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            device.setLatitude(mLastLocation.getLatitude());
            device.setLongitude(mLastLocation.getLongitude());
            System.out.println("Lat: "+mLastLocation.getLatitude()+"");
            System.out.println("Long: "+mLastLocation.getLongitude()+"");
        }
    };

}
