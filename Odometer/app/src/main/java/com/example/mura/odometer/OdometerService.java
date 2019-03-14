package com.example.mura.odometer;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import java.util.Random;

public class OdometerService extends Service {

    private static double distanceInMeters;
    private static Location lastLocation = null;
    private final IBinder binder = new OdometerBinder();
    private LocationManager locManager;
    private LocationListener listener;
    public static final String PERMISSION_STRING = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    public void onCreate(){
        super.onCreate();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if(lastLocation == null){
                    lastLocation = location;
                }
                //расстояние между двямя точками
                distanceInMeters += location.distanceTo(lastLocation);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        //получаем обьект locmager и выбираем самого точного провайдера
        locManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this,PERMISSION_STRING)== PackageManager.PERMISSION_GRANTED){
            String provider = locManager.getBestProvider(new Criteria(),true);
            if (provider != null){
                locManager.requestLocationUpdates(provider,1000,1,listener);
            }
        }
    }

    public class OdometerBinder extends Binder{
        OdometerService getOdometer(){
            return OdometerService.this;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //не получать обновлений
        if(locManager != null && listener != null){
            if (ContextCompat.checkSelfPermission(this,PERMISSION_STRING)
            ==PackageManager.PERMISSION_GRANTED){
                locManager.removeUpdates(listener);
            }
            locManager = null;
            listener = null;
        }
    }

    public OdometerService() {
    }

    public double getDistance(){
        return this.distanceInMeters*100;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }
}
