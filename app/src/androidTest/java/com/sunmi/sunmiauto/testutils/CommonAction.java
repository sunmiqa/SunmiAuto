package com.sunmi.sunmiauto.testutils;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Assert;

import static com.sunmi.sunmiauto.testutils.TestConstants.LOG_V;
import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;

/**
 * Created by fengy on 2017/8/23.
 */

public class CommonAction {
    public static int deviceHeight = device.getDisplayHeight();
    public static int deviceWidth = device.getDisplayWidth();

    public static boolean clickById(String id){
        return clickById(id,20);
    }

    public static boolean clickById(String id,int counts){
        return clickByInfo("res",id,counts);
    }

    public static boolean clickByText(String text){
        return clickByText(text,20);
    }

    public static boolean clickByText(String text,int counts){
        return clickByInfo("text",text,counts);
    }

    public static boolean clickByDesc(String desc){
        return clickByDesc(desc,20);
    }

    public static boolean clickByDesc(String desc,int counts){
        return clickByInfo("desc",desc,counts);
    }

    public static boolean clickByFocused(){
        return clickByFocused(20);
    }

    public static boolean clickByFocused(int counts){
        return clickByInfo("focused","",counts);
    }

    public static boolean clickBySelected(){
        return clickBySelected(20);
    }

    public static boolean clickBySelected(int counts){
        return clickByInfo("selected","",counts);
    }

    public static boolean clickByInfo(String click,String str,int counts){
        BySelector selector = null;
        switch (click){
            case "res": selector = By.res(str); break;
            case "text": selector = By.text(str); break;
            case "desc": selector = By.desc(str); break;
            case "focused": selector = By.focused(true); break;
            case "selected":selector = By.selected(true); break;
            default: return false;
        }
        UiObject2 uiObject = device.findObject(selector);
        int i = 0;
        while(uiObject == null && i < counts){
            //解决一些可能出现的问题
            //等待5次
            sleep(500);
            uiObject = device.findObject(selector);
            if(i == counts-1){
                screenshotCap("wrong UI");
                Assert.fail("未找到包含"+str+"属性的控件");
            }
            i++;
        }
        uiObject.click();
        return true;
    }

    //向左滑动屏幕（从屏幕最右中心位置滑动到屏幕最左中心位置）
    public static void swipeToLeft(){
        device.swipe(deviceWidth-5,deviceHeight/2,5,deviceHeight/2,30);
    }

    //向右滑动屏幕（从屏幕最左中心位置滑动到屏幕最右中心位置）
    public static void swipeToRight(){
        device.swipe(5,deviceHeight/2,deviceWidth-5,deviceHeight/2,30);
    }

    //滚动到text，默认使用resourceId为"android:id/list"的uiscrollable（默认垂直滚动）
    public static void scrollToText(String text){
        scrollToText("android:id/list",text);
    }

    //使用resourceId为res的uiscrollable定位到text属性控件（默认垂直滚动）
    public static void scrollToText(String res,String text){
        scrollToText(res,text,false);
    }

    //使用resourceId为res的uiscrollable定位到text属性控件(当direction为true时候为横向滚动，为false时为垂直滚动)
    public static void scrollToText(String res,String text,boolean direction){
        UiScrollable uiScrollable = new UiScrollable(new UiSelector().resourceId(res));
        int i = 0;
        while(uiScrollable == null && i < 20){
            //解决一些可能出现的问题
            //等待20次
            sleep(500);
            uiScrollable = new UiScrollable(new UiSelector().resourceId(res));
            if(i == 19){
                screenshotCap("wrong UI");
                Assert.fail("未找到包含"+res+"属性的控件");
            }
            i++;
        }
        if (direction){
            uiScrollable.setAsHorizontalList();
        }
        boolean b = false;
        try {
            b = uiScrollable.scrollTextIntoView(text);
            if(!b){
                throw new UiObjectNotFoundException("未找到"+text);
            }
        } catch (UiObjectNotFoundException e) {
            Log.e(LOG_V,"未找到"+text);
            Assert.fail("未找到"+text);
        }
    }


}
