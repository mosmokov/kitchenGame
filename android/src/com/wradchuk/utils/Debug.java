package com.wradchuk.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.wradchuk.main.Launcher;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.commons.io.IOUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Debug {
    private static final String TEG = "KITCHEN ";
    public static final String HOST = "https://pointsales.buisness-app.ru/kitchen/";
    public static WifiManager wifi;


    public static void  debug(String _output) { Gdx.app.log(TEG, _output);}
    public static void  debug(boolean _output) {Gdx.app.log(TEG, ""+_output);}
    public static void  debug(int _output) {Gdx.app.log(TEG, ""+_output);}
    public static void  debug(float _output) {Gdx.app.log(TEG, ""+_output);}
    public static void  debug(@NotNull Vector2 _output) {Gdx.app.log(TEG, "{"+_output.x+"|"+_output.y+"}");}
    public static void  debug(int[] ints) {
        Gdx.app.log(TEG, "{"+ints[0]+"|"+ints[1]+"}");
    }

    public static String  local() {
        return Gdx.files.getLocalStoragePath();
    }
    public static boolean isHost(String host) {
        if(host.equals(HOST)) return true;
        else return false;
    }

    public static int   getFPS () { return Gdx.graphics.getFramesPerSecond();}
    public static float getTime() { return Gdx.graphics.getDeltaTime();      }

    public static BitmapFont createFonts(String _font, int _size) {
        BitmapFont font;
        String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя abcdefghijklmnopqrstuvwxyz АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,;'\"(!?)+-*/=";
        FileHandle fontFile = Gdx.files.internal(_font);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARACTERS;
        parameter.size = _size;

        parameter.color.add(Color.BLACK);

        font = generator.generateFont(parameter);

        generator.dispose();
        return font;
    }

    public static Model loadModelOBJ(String _model_file) {
        ModelLoader loader = new ObjLoader();
        //Debug.debug(Gdx.files.internal(_model_file).path());
        Model model =  loader.loadModel(Gdx.files.internal(_model_file));
        return model;

    }
    public static Model loadModelG3D(String _model_file) {
        //Debug.debug(Gdx.files.internal(_model_file).path());
        AssetManager assets = new AssetManager();
        assets.load(Gdx.files.internal(_model_file).path(), Model.class);
        assets.finishLoading();
        Model model = assets.get(Gdx.files.internal(_model_file).path(), Model.class);
        return model;
    }


    public static void downloadURL(String f_name, String f_url) {
        int count;
        try {
            URL url = new URL(f_url);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(Debug.local().concat(f_name));
            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            Debug.debug(total);
            output.flush();
            output.close();
            input.close();
        }catch(Exception e) {
            Debug.debug("Error: " + e.getMessage());
        }
    }
    public static JSONObject loadJSON(File _file) throws JSONException {
        JSONObject result = null;
        try {
            InputStream is = new FileInputStream(_file);
            String jsonTxt = IOUtils.toString(is, Charset.forName("UTF-8"));
            result = new JSONObject(jsonTxt);
        } catch (FileNotFoundException ex) {  Debug.debug(ex.getMessage());
        } catch (IOException ex) { Debug.debug(ex.getMessage()); }

        return result;
    }
    public static JSONObject loadJSON(String file) {
        JSONObject result = null;
        try {
            result = Debug.loadJSON(new File(file));
        } catch (JSONException e) {
            Debug.debug(e.getMessage());
        }
        return result;
    }



    @SuppressLint("WifiManagerLeak")
    public static int getWifiState() {
        wifi =(WifiManager) Launcher.context.getSystemService(Launcher.context.WIFI_SERVICE);
        return wifi.getWifiState();
    }
    public static boolean isOnline() {
        boolean result = false;
        ConnectivityManager mgr = (ConnectivityManager) Launcher.context.getSystemService(Launcher.context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();
        if (netInfo != null)
            if (netInfo.isConnected()) result = true;
            else result = false;
        else result = false;

        return result;
    }

    public static void dispose(Disposable _disposable) {
        if(_disposable!=null) _disposable.dispose();

    }
    public static void Intent(Object _object, Activity _context, boolean _finish) {
        _context.startActivity(new Intent(_context, _object.getClass()));
        if(_finish) _context.finish();
    }


    public static Texture setBackground(String _img, int _width, int _height) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(_img));
        Pixmap full = new Pixmap(_width, _height, pixmap.getFormat());
        full.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, full.getWidth(), full.getHeight() );
        Texture result = new Texture(full);
        Debug.dispose(pixmap);
        Debug.dispose(full);
        return result;
    }

    public static double KOF() {
        return ((double)Gdx.graphics.getHeight()/(double)1300);
    }


    public static Color getPixelColor() {
        ByteBuffer buffer = ByteBuffer.allocate(1*1*4);
        Gdx.gl.glReadPixels(Gdx.input.getX(), Gdx.input.getY(),
                1, 1, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, buffer);
        buffer = getColor(buffer);
        return new Color(buffer.get(0), buffer.get(1), buffer.get(2), buffer.get(3));
    }
    public static ByteBuffer getColor(ByteBuffer byteBuffer) {
        ByteBuffer buffer = ByteBuffer.allocate(1*1*4);

        buffer.put(0, assigned(byteBuffer.get(0)));
        buffer.put(1, assigned(byteBuffer.get(1)));
        buffer.put(2, assigned(byteBuffer.get(2)));
        buffer.put(3, assigned(byteBuffer.get(3)));
        return buffer;
    }
    public static byte assigned(byte b) {
        byte res = 0;
        if(b < 0) {
            b = (byte) ((int)b *-1);
            res = b;
        }
        return b;
    }


    public static double getMouseX() {
        double a = (double) Gdx.graphics.getWidth()/2.0f;
        double b = (double) Gdx.input.getX()-a;
        return b/KOF();
    }
    public static double getMouseY() {
        double a = (double) Gdx.graphics.getHeight()/2.0f;
        double b = (double)Gdx.input.getY()-a;
        return b/KOF();
    }
}