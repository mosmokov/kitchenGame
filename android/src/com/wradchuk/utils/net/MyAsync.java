package com.wradchuk.utils.net;

import android.os.AsyncTask;

import com.wradchuk.utils.sys.LogOut;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyAsync extends AsyncTask<String, Void, String> {
    private JSONObject error;
    public AsyncResultPasser delegate = null;
    Socket socket;
    Scanner scanner;
    PrintWriter writer;

    public MyAsync(AsyncResultPasser resultPasser) {
        delegate = resultPasser;
        try {
            error = new JSONObject().put("com", "server_error");
        } catch(JSONException ex) { LogOut.logEx(ex.getMessage()); }
    }


    @Override protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override protected String doInBackground(String... s) {
        String msg = "";

        try {
            socket = new Socket(s[1], Integer.parseInt(s[2]));
            scanner = new Scanner    (socket.getInputStream());
            writer  = new PrintWriter(socket.getOutputStream());
        } catch(IOException ex) { msg = error.toString(); LogOut.logEx(ex.getMessage()); }

        try {
            if(socket!=null) sendMsg(new JSONObject(s[0]), writer);
        } catch(JSONException ex) { msg = error.toString();  LogOut.logEx(ex.getMessage()); }

        if(socket!=null) msg = inMsg(scanner);
        else msg = error.toString();

        return msg;
    }
    @Override protected void onPostExecute(String res) {
        super.onPostExecute(res);
        delegate.message(res);

    }

    public static String inMsg(Scanner _scanner) {
        if(_scanner.hasNext()) return _scanner.nextLine();
        else return "";
    }
    public void sendMsg(JSONObject _msg, PrintWriter writer) {
        try {
            writer.println(_msg.toString());
            writer.flush();
        } catch (Exception ex) { LogOut.logEx(ex.getMessage()); }
    }
}