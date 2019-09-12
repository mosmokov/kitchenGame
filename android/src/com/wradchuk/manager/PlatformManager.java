package com.wradchuk.manager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import com.wradchuk.utils.PatchedAndroidApplication;

public class PlatformManager {

  private PatchedAndroidApplication context;
  private static final int KEY = 777;
  public  boolean isCheak = false;
  public String[] permissions = { // Разрешения на доступ...
          Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.ACCESS_COARSE_LOCATION,
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.WRITE_EXTERNAL_STORAGE
  };
  public PlatformManager() {}
  public PlatformManager(PatchedAndroidApplication _context) { this.context = _context; }
  public void check() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
              context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
              context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
              context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        context.requestPermissions(permissions, KEY);
        isCheak = false;
      } else isCheak = true;
    } else isCheak = true;
  }

}