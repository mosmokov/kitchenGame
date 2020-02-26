package com.wradchuk.utils.net;

import com.wradchuk.utils.sys.LogOut;

import org.json.JSONException;
import org.json.JSONObject;

public class Command {
    private static final String host = "31.131.66.214";
    private static final String port = "3445";


    public static void sendMsg(JSONObject _msg, AsyncResultPasser _passer) {
        new MyAsync(_passer).execute(_msg.toString(), host, port);
    }


    public static void reg (String _login, String _pass, AsyncResultPasser _passer) {
        JSONObject msg = null;
        try {
            msg = new JSONObject();
            msg.put("com", "reg");
            msg.put("login", _login);
            msg.put("pass", LogOut.md5(_pass));
            msg.put("time", System.currentTimeMillis());
            Command.sendMsg(msg, _passer);
        } catch (JSONException e) { LogOut.logEx(e.getMessage()); }
    }
    public static void auth(String _login, String _pass, AsyncResultPasser _passer) {
        JSONObject msg = null;

        try {
            msg = new JSONObject();
            msg.put("com", "auth");
            msg.put("login", _login);
            msg.put("pass", LogOut.md5(_pass));
            msg.put("time", System.currentTimeMillis());
            Command.sendMsg(msg, _passer);
        } catch (JSONException e) { LogOut.logEx(e.getMessage()); }
    }

}