package com.ninehackers.speedgear.utils.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dabaosod011 on 2015/7/7.
 */
public class ConfigData {
    private Context mContext;
    private SharedPreferences sharedPreferences;

    private static final String SPEEDGEAR_SETTINGS = "SPEEDGEAR_SETTINGS";
    private static final String SPEEDGEAR_SERVER_ADDRESS = "speedgear_server_address";
    private static final String SPEEDGEAR_SERVER_PORT = "speedgear_server_port";
    private static final String SPEEDGEAR_USERNAME = "speedgera_username";

    public ConfigData(Context context) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(SPEEDGEAR_SETTINGS, Context.MODE_PRIVATE);
    }

    public String getSpeedgearServerAddress() {
        return sharedPreferences.getString(SPEEDGEAR_SERVER_ADDRESS, "");
    }

    public String getSpeedgearServerPort() {
        return sharedPreferences.getString(SPEEDGEAR_SERVER_PORT, "");
    }

    public String getSpeedgearUsername() {
        return sharedPreferences.getString(SPEEDGEAR_USERNAME, "");
    }

    public void setSpeedgearServerAddress(String server_address) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (server_address != null) {
            editor.putString(SPEEDGEAR_SERVER_ADDRESS, server_address);
        }
        editor.commit();
    }

    public void setSpeedgearServerPort(String server_port) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (server_port != null) {
            editor.putString(SPEEDGEAR_SERVER_PORT, server_port);
        }
        editor.commit();
    }

    public void setSpeedgearUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (username != null) {
            editor.putString(SPEEDGEAR_USERNAME, username);
        }
        editor.commit();
    }
}
