package com.wradchuk.utils.sys;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.wradchuk.AndroidLauncher;
import org.jetbrains.annotations.NotNull;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogOut {
    public static final String TEG = "KITCHEN";
    public static WifiManager wifi;



    /***
     * Текущие время
     * @return System.currentTimeMillis()
     */
    public static long sysTime() {
        return System.currentTimeMillis();
    }

    /// Три функции для логирования, но это для дебага, потом уберётся
    public static void logMsg(String _msg) {
        Gdx.app.log(TEG,"ServerMSG: " +_msg);
    }
    public static void log(String _msg) {
        Gdx.app.log(TEG,"Log: " +_msg);
    }
    public static void logEx(String _ex) {
        Gdx.app.log(TEG,"Exception: "+_ex);
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public static void  logV2(@NotNull Vector2 _msg) {
        Gdx.app.log(TEG, "{"+_msg.x+"|"+_msg.y+"}");
    }
    public static void  logInts(int[] ints) {
        Gdx.app.log(TEG, "{"+ints[0]+"|"+ints[1]+"}");
    }
    public static void  logInts(int[] ints, String _msg) {
        String res = "[";
        for(int i = 0; i < ints.length; i++) {
            if (i!=ints.length-1) res = res.concat(" "+ints[i]+",");
            else res = res.concat(" "+ints[i]+" ]");
        }

        Gdx.app.log(TEG, _msg+""+res);
    }
    ////////////////////////////////////////////////////////////



    /***
     * Кодирует данные в MD5
     * @param string - данные
     * @return вернёт шех MD5
     */
    public static String md5(String string) {
        String res = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(string.getBytes());
            BigInteger bigInt = new BigInteger(1, digest.digest());
            res = bigInt.toString(16);
        } catch(NoSuchAlgorithmException ex) { logEx(ex.getMessage()); }
        return res;
    }
    /***
     * Вернёт Frame per second в приложении
     * @return
     */
    public static int getFPS () { return Gdx.graphics.getFramesPerSecond();}
    /***
     * Узнаём статус WIFI соединения (включен, запускается, выключен)
     * @return вернёт целочисленный код статуса
     */
    @SuppressLint("WifiManagerLeak")
    public static int getWifiState() {
        wifi =(WifiManager) AndroidLauncher.context.getSystemService(AndroidLauncher.context.WIFI_SERVICE);
        return wifi.getWifiState();
    }
    /***
     * Вернёт правду если включен мобильный интернет или есть WIFI подлключение
     *  иначе интернет службы выключены
     * @return
     */
    public static boolean isOnline() {
        boolean result = false;
        ConnectivityManager mgr = (ConnectivityManager) AndroidLauncher.context.getSystemService(AndroidLauncher.context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();
        if (netInfo != null)
            if (netInfo.isConnected()) result = true;
            else result = false;
        else result = false;

        return result;
    }

}