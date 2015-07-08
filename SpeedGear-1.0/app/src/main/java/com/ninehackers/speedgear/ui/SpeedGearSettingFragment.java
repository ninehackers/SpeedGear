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
        String server_address = configData.getSpeedgearServerAddress();
        String server_port = configData.getSpeedgearServerPort();
        String username = configData.getSpeedgearUsername();

        if (!StringUtil.isEmpty(server_address)) {
            serverAddressText.setText(server_address);
        }

        if (!StringUtil.isEmpty(server_port)) {
            serverPortText.setText(server_port);
        }

        if (!StringUtil.isEmpty(username)) {
            usernameText.setText(username);
            usernameText.setEnabled(false);
        }
    }

    private void saveSettings() {
        String server_address = ((EditText) (dialogView.findViewById(R.id.server_address_id))).getText().toString();
        String server_port = ((EditText) (dialogView.findViewById(R.id.server_port_id))).getText().toString();
        String username = ((EditText) (dialogView.findViewById(R.id.username_id))).getText().toString();

        ConfigData configData = new ConfigData(getActivity());
        configData.setSpeedgearServerAddress(server_address);
        configData.setSpeedgearServerPort(server_port);
        configData.setSpeedgearUsername(username);
    }
}
