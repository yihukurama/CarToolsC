package com.yihukurama.cartoolsc.controler.utils.android;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by dengshuai on 16/4/13.
 */
public class GestureUtil {
    private static GestureUtil ourInstance = new GestureUtil();

    public static GestureUtil getInstance() {
        return ourInstance;
    }

    private GestureUtil() {
    }


    //获取手势动作屏幕上两个点之间的距离
    public double spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
