package com.ninehackers.speedgear.network;
import com.ninehackers.speedgear.utils.data.ConfigData;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by dabaosod011 on 2015/7/8.
 */
public class SpeedGearHttpClient {
    private String mServerAddress;
    private String mServerPort;

    public SpeedGearHttpClient(String server_address, String server_port){
        this.mServerAddress = server_address;
        this.mServerPort = server_port;
    }

    public boolean isServerAvailable(){
        return false;
    }

}
