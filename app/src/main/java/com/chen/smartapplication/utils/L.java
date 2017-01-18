package com.chen.smartapplication.utils;

import android.util.Log;

/**
 * Created by chen on 2017-01-18.
 */

public class L {
    //开关
    public static final boolean DEBUG=true;
    //TAG
    public static final String TAG="SmartApplication";

    //五个等级DIWEF
    public static void d(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }
    }
    public static void i(String text){
        if (DEBUG){
            Log.i(TAG,text);
        }
    }
    public static void w(String text){
        if (DEBUG){
            Log.w(TAG,text);
        }
    }
    public static void e(String text){
        if (DEBUG){
            Log.e(TAG,text);
        }
    }

}
