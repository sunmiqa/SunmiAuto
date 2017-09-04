package com.sunmi.sunmiauto.testutils;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;

import org.junit.Assert;

import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;

/**
 * Created by fengy on 2017/8/23.
 */

public class UiobjectFinder {

    public static BySelector selector = null;

    public static UiObject2 findById(String id){
        selector = By.res(id);
        return findByInfo(selector);
    }

    public static UiObject2 findByText(String text){
        selector = By.text(text);
        return findByInfo(selector);
    }

    public static UiObject2 findByDesc(String desc){
        selector = By.desc(desc);
        return findByInfo(selector);
    }

    public static UiObject2 findByFocused(boolean focused){
        selector = By.focused(focused);
        return findByInfo(selector);
    }

    public static UiObject2 findBySelected(boolean selected){
        selector = By.selected(selected);
        return findByInfo(selector);
    }

    public static UiObject2 findByInfo(BySelector selector){
        UiObject2 uiObject2 = device.findObject(selector);
        int i = 0;
        while(uiObject2 == null && i < 20){
            //解决一些可能出现的问题
            //等待500ms，最多等待20次
            sleep(500);
            uiObject2 = device.findObject(selector);
            if(i == 19){
                screenshotCap("wrong UI");
                Assert.fail("未找到想要找的控件");
            }
            i++;
        }
        return uiObject2;
    }
}
