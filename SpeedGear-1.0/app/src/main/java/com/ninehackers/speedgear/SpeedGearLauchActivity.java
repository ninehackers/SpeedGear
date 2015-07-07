package com.ninehackers.speedgear;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.ninehackers.speedgear.ui.SpeedGearSettingFragment;


public class SpeedGearLauchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedgear_lauch);
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
}
