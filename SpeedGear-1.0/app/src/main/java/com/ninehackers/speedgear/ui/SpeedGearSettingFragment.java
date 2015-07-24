package com.ninehackers.speedgear.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

import com.ninehackers.speedgear.R;
import com.ninehackers.speedgear.utils.StringUtil;
import com.ninehackers.speedgear.utils.data.ConfigData;


/**
 * Created by dabaosod011 on 2015/7/7.
 */
public class SpeedGearSettingFragment extends DialogFragment {
    private static String LOG_TAG = "SpeedGearSettingFragment";
    private View dialogView;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        dialogView = inflater.inflate(R.layout.dialog_speedgear_settings, null);

        initSettings();

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.speedgear_setting_title)
                .setView(dialogView)
                .setPositiveButton(R.string.save,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                saveSettings();
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).create();
    }

    private void initSettings() {
        EditText serverAddressText = (EditText) dialogView.findViewById(R.id.server_address_id);
        EditText serverPortText = (EditText) dialogView.findViewById(R.id.server_port_id);
        EditText usernameText = (EditText) dialogView.findViewById(R.id.username_id);

        ConfigData configData = new ConfigData(getActivity());
        String serverAddress = configData.getSpeedgearServerAddress();
        String serverPort = configData.getSpeedgearServerPort();
        String userName = configData.getSpeedgearUsername();

        if (!StringUtil.isEmpty(serverAddress)) {
            serverAddressText.setText(serverAddress);
        }

        if (!StringUtil.isEmpty(serverPort)) {
            serverPortText.setText(serverPort);
        }

        if (!StringUtil.isEmpty(userName)) {
            usernameText.setText(userName);
            usernameText.setEnabled(false);
        }
    }

    private void saveSettings() {
        String serverAddress = ((EditText) (dialogView.findViewById(R.id.server_address_id))).getText().toString();
        String serverPort = ((EditText) (dialogView.findViewById(R.id.server_port_id))).getText().toString();
        String userName = ((EditText) (dialogView.findViewById(R.id.username_id))).getText().toString();

        ConfigData configData = new ConfigData(getActivity());
        configData.setSpeedgearServerAddress(serverAddress);
        configData.setSpeedgearServerPort(serverPort);
        configData.setSpeedgearUsername(userName);
    }
}
