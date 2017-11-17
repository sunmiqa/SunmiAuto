package com.sunmi.sunmiauto.testutils;

import android.graphics.Point;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import org.junit.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.sunmi.sunmiauto.testutils.TestConstants.LOG_V;
import static com.sunmi.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;

/**
 * Created by fengy on 2017/8/23.
 */

public class CommonAction {
    public static int deviceHeight = device.getDisplayHeight();
    public static int deviceWidth = device.getDisplayWidth();
    public static UiObject2 uiObject2;
    public static BySelector by;
    private static int DEFAULT_SEARCH_TIME = 20;
    private static int ONE_TIME_USED = 500;
    private static int DEFAULT_CHOOSE_NUMBER = 0;

    public static boolean clickById(String id){
        return clickById(id,DEFAULT_CHOOSE_NUMBER);
    }

    public static boolean clickById(String id,int number){
        return clickById(id,number,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickById(String id ,int number,int counts){
        by = By.res(id);
        return clickBySelector(by,number,counts);
    }

    public static boolean clickByText(String text){
        return clickByText(text,DEFAULT_CHOOSE_NUMBER);
    }

    public static boolean clickByText(String text,int number){
        return clickByText(text,number,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickByText(String text,int number,int counts){
        by = By.text(text);
        return clickBySelector(by,number,counts);
    }

    public static boolean clickByPartText(String text){
        return clickByPartText(text,DEFAULT_CHOOSE_NUMBER);
    }

    public static boolean clickByPartText(String text,int number){
        return clickByPartText(text,number,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickByPartText(String text,int number,int counts){
        by = By.textContains(text);
        return clickBySelector(by,number,counts);
    }

    public static boolean clickByDesc(String desc){
        return clickByDesc(desc,DEFAULT_CHOOSE_NUMBER);
    }

    public static boolean clickByDesc(String desc,int number){
        return clickByDesc(desc,number,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickByDesc(String desc,int number,int counts){
        by = By.desc(desc);
        return clickBySelector(by,number,counts);
    }

    public static boolean clickByFocused(){
        return clickByFocused(DEFAULT_SEARCH_TIME);
    }

    public static boolean clickByFocused(int counts){
        by = By.focused(true);
        return clickBySelector(by,DEFAULT_CHOOSE_NUMBER,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickBySelected(){
        return clickBySelected(DEFAULT_SEARCH_TIME);
    }

    public static boolean clickBySelected(int counts){
        by = By.selected(true);
        return clickBySelector(by,DEFAULT_CHOOSE_NUMBER,counts);
    }

    public static boolean clickByClass(String clazz){
        return clickByClass(clazz,DEFAULT_CHOOSE_NUMBER,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickByClass(String clazz,int number){
        return clickByClass(clazz,number,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickByClass(String clazz,int number,int counts){
        by = By.clazz(clazz);
        return clickBySelector(by,number,counts);
    }

    public static boolean clickBySelector(BySelector by){
        return clickBySelector(by,DEFAULT_CHOOSE_NUMBER,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickBySelector(BySelector by,int number){
        return clickBySelector(by,number,DEFAULT_SEARCH_TIME);
    }

    public static boolean clickBySelector(BySelector by,int number,int counts){
        device.waitForIdle(LONG_WAIT);
        UiObject2 uiObject2 = null;
        int i = 0;
        do {
            i++;
            sleep(ONE_TIME_USED);
            if (number == 0) {
                uiObject2 = device.findObject(by);
            } else {
                List<UiObject2> list = device.findObjects(by);
                if (list.size() < number - 1) {
                    uiObject2 = null;
                } else {
                    uiObject2 = list.get(number - 1);
                }
            }
            if (i == counts && uiObject2 == null) {
                screenshotCap("wrong UI");
                Assert.fail("未找到想要找的属性为" + by.toString() + "控件");
            }
        } while (uiObject2 == null && i < counts);
        uiObject2.click();
        sleep(ONE_TIME_USED);
        return true;
    }


    public static boolean longClick(UiObject2 uiObject2){
        boolean b = longClick(uiObject2,3000);
        return b;
    }

    public static boolean longClick(UiObject2 uiObject2,int time){
        int startX = uiObject2.getVisibleBounds().centerX();
        int startY = uiObject2.getVisibleBounds().centerY();
        boolean b = device.swipe(startX,startY,startX,startY,time/20);
        return b;
    }

    public static void setText(String text){
        sleep(ONE_TIME_USED);
        BySelector selector = null;
        selector = By.focused(true);
        UiObject2 uiObject = device.findObject(selector);
        int i = 0;
        while(uiObject == null && i < DEFAULT_SEARCH_TIME){
            //解决一些可能出现的问题
            //等待5次
            i++;
            sleep(ONE_TIME_USED);
            uiObject = device.findObject(selector);
            if(i == DEFAULT_SEARCH_TIME && uiObject == null){
                screenshotCap("wrong UI");
                Assert.fail("未获取到当前页面焦点控件");
            }
        }
        uiObject.setText(text);
        sleep(ONE_TIME_USED);
    }


    //向左滑动屏幕（从屏幕最右中心位置滑动到屏幕最左中心位置）
    public static void swipeToLeft(){
        device.swipe(deviceWidth-5,deviceHeight/2,5,deviceHeight/2,30);
    }

    //向右滑动屏幕（从屏幕最左中心位置滑动到屏幕最右中心位置）
    public static void swipeToRight(){
        device.swipe(5,deviceHeight/2,deviceWidth-5,deviceHeight/2,30);
    }

    //向下滑动屏幕（从屏幕最顶部中心位置滑动到屏幕最底部中心位置）
    public static void swipeToBottom(){
        device.swipe(deviceWidth/2,5,deviceWidth/2,deviceHeight-5,30);
    }

    //向上滑动屏幕（从屏幕最底部中心位置滑动到屏幕最顶部中心位置）
    public static void swipeToTop(){
        device.swipe(deviceWidth/2,deviceHeight-5,deviceWidth/2,5,30);
    }

    //点击屏幕中央位置
    public static void clickCenter(){
        device.click(deviceWidth/2,deviceHeight/2);
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
        sleep(ONE_TIME_USED);
        UiScrollable uiScrollable = new UiScrollable(new UiSelector().resourceId(res));
        int i = 0;
        while(uiScrollable == null && i < DEFAULT_SEARCH_TIME){
            //解决一些可能出现的问题
            //等待20次
            i++;
            sleep(ONE_TIME_USED);
            uiScrollable = new UiScrollable(new UiSelector().resourceId(res));
            if(i == DEFAULT_SEARCH_TIME && uiScrollable == null){
                screenshotCap("wrong UI");
                Assert.fail("未找到包含"+res+"属性的控件");
            }
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
        sleep(ONE_TIME_USED);
    }

//    public static void swipeMultiPoint(Point[] points){
////        for (int i = 0; i < points.length; i++) {
//            MultiThread multiThread = new MultiThread(points[points.length-1]);
//            MultiThread multiThread1 = new MultiThread(points[points.length-2]);
//            new Thread(multiThread,"pro1").start();
//        new Thread(multiThread1,"pro2").start();
//        sleep(6000);
////        }
//    }

    public static void longPressTwoKeyCode(int[][] ss){
        MultiThread multiThread1 = new MultiThread(ss[0][0],ss[0][1]);
        MultiThread multiThread2 = new MultiThread(ss[1][0],ss[1][0]);
        new Thread(multiThread1,"pro1").start();
        new Thread(multiThread2,"pro2").start();
    }

    public static void dragToCenter(UiObject2 uiObject2){
        uiObject2.drag(new Point(deviceWidth/2,deviceHeight/2),200);
    }

    // 长按物理键
    public  static boolean longPressKeyCode(int keyCode,int PressTime) throws InvocationTargetException {
        try {
            Field mUiAutomationBridge = Class.forName("android.support.test.uiautomator.UiDevice").getDeclaredField("mUiAutomationBridge");
            mUiAutomationBridge.setAccessible(true);
            Object bridgeObj = mUiAutomationBridge.get(device);
            Method injectInputEvent = Class.forName("android.support.test.uiautomator.UiAutomatorBridge")
                    .getDeclaredMethod("injectInputEvent",new Class[]{android.view.InputEvent.class,boolean.class});
            final long eventTime = SystemClock.uptimeMillis();
            KeyEvent downEvent = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN,
                    keyCode, 0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                    InputDevice.SOURCE_KEYBOARD);
            if ((Boolean) injectInputEvent.invoke(bridgeObj, new Object[]{downEvent, true})) {
                SystemClock.sleep(PressTime);
                KeyEvent upEvent = new KeyEvent(eventTime, eventTime,
                        KeyEvent.ACTION_UP, keyCode, 0, 0,
                        KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                        InputDevice.SOURCE_KEYBOARD);
                if ((Boolean) injectInputEvent.invoke(bridgeObj, new Object[]{upEvent, true})) {
                    return true;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    //拖动一个对象到指定坐标
    public static void dragObjectToXY(UiObject2 uiObject2,int x,int y){
        device.drag(uiObject2.getVisibleBounds().centerX(),uiObject2.getVisibleBounds().centerY(),x,y,20);
    }

    //拖动一个对象到另一个对象上
    public static void dragObjectToObject(UiObject2 uiObject,UiObject2 uiObject2){
        device.drag(uiObject.getVisibleBounds().centerX(),uiObject.getVisibleBounds().centerY(),
                uiObject2.getVisibleBounds().centerX(),uiObject2.getVisibleBounds().centerY(),20);
    }

    //从一个对象位置滑动到指定坐标（如果对象距离目标坐标点非常近，则滑动可能产生长按的效果，取决于传递参数的步数，此处默认20步，一步需要5ms时间）
    public static void swipObjectToXY(UiObject2 uiObject,int x,int y){
        device.swipe(uiObject.getVisibleBounds().centerX(),uiObject.getVisibleBounds().centerY(),x,y,20);
    }

    //从一个对象位置滑动到另一个对象位置
    public static void swipObjectToObject(UiObject2 uiObject,UiObject2 uiObject2){
        device.swipe(uiObject.getVisibleBounds().centerX(),uiObject.getVisibleBounds().centerY(),
                uiObject2.getVisibleBounds().centerX(),uiObject2.getVisibleBounds().centerY(),20);
    }

    //从一个位置滑动到另一个位置
    public static void swipeXYToXY(int startX,int startY,int endX,int endY){
        device.swipe(startX,startY,endX,endY,20);
    }
}
