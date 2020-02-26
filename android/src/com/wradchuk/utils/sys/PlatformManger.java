package com.wradchuk.utils.sys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.json.JSONObject;

public class PlatformManger {
    private PatchedAndroidApplication context;
    private static int   WIDTH  = -1;					// Ширина дисплея
    private static int   HEIGHT = -1;					// Высота дисплея
    private static final int KEY = 777;
    public  boolean isCheak = false;
    public String[] permissions = { // Разрешения на доступ...
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public Texture sPerm;                              // Изображение "Нет разрешений"
    public Texture sInet;                              // Изображение "Нет интернета"


    public PlatformManger(PatchedAndroidApplication _context) {
        this.context = _context;
    }
    public void check() {
        boolean res = false;
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
    public void create(int w, int h) {
        WIDTH = w;
        HEIGHT=h;
        sPerm = new Texture("img/no-perm.jpg");
        sInet = new Texture("img/no-inet.jpg");

    }
    public void permAndInet(SpriteBatch _batch) {
        check();

        if(!isCheak) {
            _batch.begin();
            _batch.draw(sPerm,0,0, WIDTH, HEIGHT);
            _batch.end();
        } else {
            if(LogOut.getWifiState()==3 || LogOut.isOnline()) {
                reg();
            } else {
                _batch.begin();
                _batch.draw(sInet,0,0, WIDTH, HEIGHT);
                _batch.end();
            }
        }
    }
    public void reg() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                JSONObject user_data = Utils.read_json("user_data.json", Utils.DISCR.LOCAL);

            }});
    }
    public    void  dispose() {
        Utils.dispose(sPerm);
        Utils.dispose(sInet);
    }
}