package com.wradchuk.task;

import android.content.pm.PackageManager;
import android.os.Build;

import com.wradchuk.Manifest;
import com.wradchuk.utils.PatchedAndroidApplication;

public class PlatformManager {
    private PatchedAndroidApplication context;
    private static final int KEY = 777;
    public String[] permissions = // Разрешения на доступ к геолокации
            {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
    public boolean isCheck = false;
    public boolean complete = false;

    public PlatformManager() {}
    public PlatformManager(PatchedAndroidApplication _context) {
        this.context = _context;
    }

    public void cheack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                context.requestPermissions(permissions, KEY);
            } else  isCheck = true;
        } else isCheck = true;
    }


    public void create() {
       if(!isCheck) cheack();
       else complete = true;
    }
}
