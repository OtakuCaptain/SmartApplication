package com.chen.smartapplication.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by chen_ on 2017-01-16.
 */
public class UtilTools {
    public static void setFont(Context context, TextView textView){
        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
