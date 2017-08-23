package com.sunmi.sunmiauto.testutils;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;

/**
 * Created by fengy on 2017/8/23.
 */

public class CommonAction {
    public static boolean clickById(String id){
        return clickByInfo("res",id);
    }

    public static boolean clickByText(String text){
        return clickByInfo("text",text);
    }

    public static boolean clickByDesc(String desc){
        return clickByInfo("desc",desc);
    }

    public static boolean clickByFocused(){
        return clickByInfo("focused","");
    }

    public static boolean clickBySelected(){
        return clickByInfo("selected","");
    }

    public static boolean clickByInfo(String click,String str){
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
        while(uiObject == null && i < 20){
            //解决一些可能出现的问题
            //等待5次
            sleep(500);
            uiObject = device.findObject(selector);
            if(i == 19){
                screenshotCap("wrong UI");
                return false;
            }
            i++;
        }
        uiObject.click();
        return true;
    }
}
