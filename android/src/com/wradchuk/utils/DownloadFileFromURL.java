package com.wradchuk.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.wradchuk.main.Launcher;

import org.json.JSONObject;
import java.util.Vector;

public class DownloadFileFromURL extends AsyncTask<String, Integer, String> {

    private boolean isHeader = false;
    public boolean isLoaded = false;
    private JSONObject header;
    private Vector<String> file_name = new Vector();
    private Vector<String> file_url  = new Vector();
    public int FILE_COUNT = 0;
    public int progress = 0;
    private int step = 0;
    private String ver = "";
    private FileHandle verf = null;

    private Preload preload;




    public DownloadFileFromURL() {}
    public void preloaded() {
        preload = new Preload();
        preload.execute("https://pointsales.buisness-app.ru/kitchen/file/header.json");
    }
    public void addFileToLoader(JSONObject jsonObject) {

        if(Debug.isHost(JSONUtil.getValue(header, "host"))!=true) {
            Debug.debug("Проблема хоста");
            System.exit(1);
        }
        FILE_COUNT = JSONUtil.arraySize(jsonObject, "name");
        step = Launcher.WIDTH/FILE_COUNT;

            for(int i = 0; i < JSONUtil.arraySize(jsonObject, "name"); i++) {
                file_name.add(JSONUtil.arrayValue(jsonObject, "name", i));
                file_url.add(JSONUtil.arrayValue(jsonObject, "url", i));
            }
    }

    @Override protected void onPreExecute() {
        super.onPreExecute();

        verf = Gdx.files.local("ver.txt");

        while (isHeader!=true)
        {
            if(Gdx.files.local("header.json").exists()==true && Gdx.files.local("ver.txt").exists()==true)
            {
                ver = verf.readString();
                isHeader = true;
                break;
            }
            else verf.writeString("", false);
        }

        if(isHeader) {
            header = Debug.loadJSON(Debug.local()+"header.json");

            addFileToLoader(header);
            }
    }
    @Override protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
    @Override protected String doInBackground(String... f_url) {


            for(int i = 0; i < file_name.size(); i++) {
                if (!Gdx.files.local(file_name.elementAt(i)).exists() || !ver.equals(JSONUtil.getValue(header, "version"))) {
                    Debug.downloadURL(file_name.elementAt(i), Debug.HOST.concat(file_url.elementAt(i)));
                    this.progress = this.progress + step;
                    publishProgress(this.progress);
                }
            }
        this.progress = Launcher.WIDTH;
        publishProgress(this.progress);
        return null;
    }
    @Override protected void onPostExecute(String file_url) {
        verf.writeString(JSONUtil.getValue(header, "version"), false);
        isLoaded = true;
    }
    
    @SuppressLint("StaticFieldLeak")
    class Preload extends AsyncTask<String, Integer, String> {

        @Override protected void onPreExecute() {
            super.onPreExecute();
            while (!Debug.isOnline()) Debug.debug("Нет сети!!!");
        }
        @Override protected String doInBackground(String... strings) {
            Debug.downloadURL("header.json", strings[0]);
            return null;
        }
        @Override protected void onPostExecute(String file_url) {
            Debug.debug( "Загрузили заголовок");
        }
    }
}