package com.chen.smartapplication.application;

import android.app.Application;

import com.chen.smartapplication.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
    }
}
