package com.sshakuf.board.board;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends Fragment {

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private String shouldAnimate;

    public SettingsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Constants.SharedPrefrencesKey, Context.MODE_PRIVATE);
        shouldAnimate = sharedpreferences.getString(Constants.sp_ShouldAnimateFall, "1");

        CheckBox cb = (CheckBox)getActivity().findViewById(R.id.cb_Animate);
        cb.setChecked(false);
        if (shouldAnimate.compareTo("0") !=0) {
            cb.setChecked(true);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // save into sharedPrefrenses
                shouldAnimate = isChecked ? "1" : "0";
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Constants.SharedPrefrencesKey, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Constants.sp_ShouldAnimateFall, shouldAnimate);
                editor.commit();

            }
        });

        Button btnSetting = (Button)getActivity().findViewById(R.id.btnBTSettings);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);

            }
        });


    }
}
