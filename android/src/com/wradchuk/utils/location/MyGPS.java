package com.wradchuk.utils.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.wradchuk.utils.sys.LogOut;
import com.wradchuk.utils.sys.PatchedAndroidApplication;

public class MyGPS {

    //--------------------------------ДЛЯ РАБОТЫ С GPS----------------------------------------------
    public PatchedAndroidApplication context; // Контекст в котором выполнять
    public final int LPRC = 777; // Location Permission Request Code
    public String[] permissions = // Разрешения на доступ к геолокации
            {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
    //----------------------------------------------------------------------------------------------
    public int time_update      = 1;		// Интервал запроса координат
    public double lat           = 0.0;      // Где сейчас наш пользователь
    public double lng           = 0.0;      // находится
    public LocationManager locationManager; // Локационный менеджер
    public boolean EnabledGPS   = false;    // Датчик GPS работает
    public boolean EnabledNet   = false;    // Интернет активен
    public boolean GarantLoc    = false;    // Разрешения выданы
    public int open = 0;                    // Нужно выпелить (костыль)

    public MyGPS(PatchedAndroidApplication _context) {
        context = _context;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        startGPS(); // Проверка статуса GPS и получение разрешений на работу с ним
    }
    public LocationListener locationListener = new LocationListener() {
        @Override public void onLocationChanged(Location location){showLocation(location);}
        @Override public void onProviderDisabled(String provider) {checkEnabled();}
        @Override public void onProviderEnabled(String provider) {
            checkEnabled();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED ||
                        context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED)
                    GarantLoc = false;
                else GarantLoc = true;
            if(GarantLoc) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000*time_update, 10,  this);
                showLocation(locationManager.getLastKnownLocation(provider));
            }
        }
        @Override public void onStatusChanged(String provider, int status, Bundle extras) {
            checkEnabled();
        }
    };
    public void showLocation(Location location) {
        if (location == null) return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            getCord(location);
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            getCord(location);
        }
    }
    public void getCord(Location location) {
        if (location == null);
        else {
            lat = location.getLatitude();
            lng = location.getLongitude();
        }
    }
    public boolean checkEnabled() {

        EnabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        EnabledNet = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(EnabledGPS || EnabledNet) return true;
        else return false;
    }
    public void startGPS() {
        checkEnabled();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED
            )
            {
                GarantLoc = false;
                context.requestPermissions(permissions, LPRC);
            }
            else
            {
                GarantLoc = true;
            }
            if(GarantLoc && EnabledGPS)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000 * time_update, 10, locationListener);
            if(GarantLoc && EnabledNet)
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 1000 * time_update, 10,
                        locationListener);
        }
    }
    public void render() {

        try {
            context.runOnUiThread(new Runnable()
            {

                public void run() {

                    if(!checkEnabled())
                    {
                        open++;
                        if(open<=1) context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        else {
                            if (!checkEnabled()) {
                                open=0;
                            }
                        }
                    }else
                    {
                        open=0;
                        startGPS();
                    }

                }
            });
        } catch (Exception e) {
            LogOut.logEx(e.getMessage());
        }
    }
    public void pause() {
        locationManager.removeUpdates(locationListener);
    }
    public boolean log() {
        if(EnabledGPS || EnabledNet) return true;
        else return false;
    }
}