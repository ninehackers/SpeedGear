package com.ninehackers.speedgear;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ninehackers.speedgear.ui.SpeedGearSettingFragment;
import com.ninehackers.speedgear.utils.StringUtil;
import com.ninehackers.speedgear.utils.data.ConfigData;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static java.lang.Thread.*;


public class SpeedGearLauchActivity extends ActionBarActivity {
    private static String LOG_TAG = "SpeedGearLauchActivity";
    private Button btnPingServer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedgear_lauch);
        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speed_gear_lauch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.menu_about: {
                Toast.makeText(getApplicationContext(), R.string.about_description,
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_settings: {
                try {
                    SpeedGearSettingFragment sgSettingDialog = new SpeedGearSettingFragment();
                    sgSettingDialog.show(getFragmentManager(), "dialog");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        btnPingServer = (Button) this.findViewById(R.id.btn_ping_server);

        btnPingServer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.d("Log", "click on btnPingServer");

                ConfigData configData = new ConfigData(getApplicationContext());
                String server_address = configData.getSpeedgearServerAddress();
                String server_port = configData.getSpeedgearServerPort();
                String username = configData.getSpeedgearUsername();

                if (StringUtil.isEmpty(server_address) ||
                        StringUtil.isEmpty(server_port) ||
                        StringUtil.isEmpty(username)) {
                    try {
                        SpeedGearSettingFragment sgSettingDialog = new SpeedGearSettingFragment();
                        sgSettingDialog.show(getFragmentManager(), "dialog");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    new pingServerTask(server_address, server_port, username).execute();
                }
            }
        });
    }


    private class pingServerTask extends AsyncTask<Void, Void, Boolean> {
        private String server_address = null;
        private String server_port = null;
        private String username = null;
        private String greetingUrl = null;

        pingServerTask(String s_address, String s_port, String uname) {
            server_address = s_address;
            server_port = s_port;
            username = uname;
            greetingUrl = "http://" + server_address + ":" + server_port + "/greeting/" + username;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            OkHttpClient httpClient = new OkHttpClient();
            if (null != greetingUrl) {
                try {
                    Request request = new Request.Builder().url(greetingUrl).build();
                    Response response = httpClient.newCall(request).execute();
                    String responseBody = response.body().string();

                    Log.d(LOG_TAG, "response is: " + response.toString());
                    Log.d(LOG_TAG, "responseBody is: " + responseBody);

                    //  need to check both response code and body,
                    //  because the http request maybe hijacked by search engine or ISP
                    if (response != null
                            && response.code() == 200
                            && responseBody.indexOf(username) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnPingServer.setClickable(false);
            btnPingServer.setEnabled(false);
            //btnPingServer.setOnClickListener(null);
        }

        protected void onPostExecute(Boolean result) {
            Toast.makeText(getApplicationContext(), result ? R.string.ping_server_success : R.string.ping_server_falied,
                    Toast.LENGTH_SHORT).show();
            btnPingServer.setClickable(true);
            btnPingServer.setEnabled(true);
        }
    }
}
