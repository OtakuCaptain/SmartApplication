package com.chen.smartapplication.ui;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.chen.smartapplication.R;

/**
 * Created by chen on 2017-01-17.
 */

public class SettingActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_setting);
    }
}
