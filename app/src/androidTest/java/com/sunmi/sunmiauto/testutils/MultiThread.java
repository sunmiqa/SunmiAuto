package com.sunmi.sunmiauto.testutils;

import android.graphics.Point;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;

/**
 * Created by fengy on 2017/9/20.
 */

public class MultiThread implements Runnable{
    int keyCode;
    int pressTime;

    public MultiThread(int keyCode, int pressTime) {
        this.keyCode = keyCode;
        this.pressTime = pressTime;
    }

    @Override
    public void run() {
//        device.drag(point.x,point.y,point.x,point.y,400);
        try {
            CommonAction.longPressKeyCode(keyCode,pressTime);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
