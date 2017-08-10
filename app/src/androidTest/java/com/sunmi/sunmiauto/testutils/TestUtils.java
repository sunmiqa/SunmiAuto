package com.sunmi.sunmiauto.testutils;

import android.app.Instrumentation;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.sunmi.sunmiauto.testutils.TestConstants.LOG_V;
import static com.sunmi.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static com.sunmi.sunmiauto.testutils.TestConstants.SHORT_SLEEP;

/**
 * Created by fengy on 2017/7/8.
 */

public class TestUtils {
    public static Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    public static UiDevice device = UiDevice.getInstance(instrumentation);

    //注册监听器
    public static void uiwatchSuite(){
        device.registerWatcher("crash", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 crashObj = device.findObject(By.textEndsWith("已停止运行。"));
                Log.v("myautotest","enterWatch");
                if(crashObj != null){
                    sleep(SHORT_SLEEP);
                    device.findObject(By.text("确定")).clickAndWait(Until.newWindow(),LONG_WAIT);
                    return true;
                }
                return false;
            }
        });

    }

    //初始化存储截图的文件夹
    public static void initLiza() throws RemoteException, IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "app_spoon-screenshots");
        Log.v(LOG_V, Environment.getExternalStorageDirectory() + File.separator + "app_spoon-screenshots");
        class Inner {
            //递归删除文件夹
            private void deleteAll(File file) {
                if(!file.exists()){
                    return;
                }
                if (file.isFile() || file.list().length == 0) {
                    file.delete();
                } else {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        deleteAll(f);//递归删除每一个文件
                        f.delete();//删除该文件夹
                    }
                }
            }
        }
        new Inner().deleteAll(dir);
        dir.mkdir();
    }

    //绘制图案锁屏（倒L形）
    public static void drawLPattern(){
        UiObject2 lockPatternObj = device.findObject(By.res("com.android.settings:id/lockPattern"));
        Rect lockRect = lockPatternObj.getVisibleBounds();
        int x1 = lockRect.left;
        int y1 = lockRect.top;
        int x2 = lockRect.right;
        int y2 = lockRect.bottom;
        Point pointOne = new Point((x1+(x2-x1)/6),(y1+(y2-y1)/6));
        Point pointTwo = new Point((x1+(x2-x1)/6*5),(y1+(y2-y1)/6));
        Point pointThree = new Point((x1+(x2-x1)/6*5),(y1+(y2-y1)/6*5));
        Point[] points = new Point[]{pointOne,pointTwo,pointThree};
        device.swipe(points,10);
    }

    //绘制图案锁屏(需要传入图案解锁最底层UiObject2对象，int一维数组，数组中的值为所要划过的点
    // 划过的点从左到右，从上到下分别为1，2，3，4，5，6，7，8，9)
    /***************************
     *    1      2      3      *
     *                         *
     *                         *
     *    4      5      6      *
     *                         *
     *                         *
     *   7       8      9      *
     * *************************/
    public static void drawPattern(UiObject2 patternObj,int[] array){
        Rect lockRect = patternObj.getVisibleBounds();
        int x1 = lockRect.left;
        int y1 = lockRect.top;
        int x2 = lockRect.right;
        int y2 = lockRect.bottom;
        Point p1 = new Point((x1+(x2-x1)/6),(y1+(y2-y1)/6));
        Point p2 = new Point((x1+(x2-x1)/6*3),(y1+(y2-y1)/6));
        Point p3 = new Point((x1+(x2-x1)/6*5),(y1+(y2-y1)/6));
        Point p4 = new Point((x1+(x2-x1)/6),(y1+(y2-y1)/6*3));
        Point p5 = new Point((x1+(x2-x1)/6*3),(y1+(y2-y1)/6*3));
        Point p6 = new Point((x1+(x2-x1)/6*5),(y1+(y2-y1)/6*3));
        Point p7 = new Point((x1+(x2-x1)/6),(y1+(y2-y1)/6*5));
        Point p8 = new Point((x1+(x2-x1)/6*3),(y1+(y2-y1)/6*5));
        Point p9 = new Point((x1+(x2-x1)/6*5),(y1+(y2-y1)/6*5));
        int length = array.length;
        Point[] points = new Point[length];
        for (int i = 0; i < length; i++) {
            switch (array[i]){
                case 1:
                    points[i] = p1;
                    break;
                case 2:
                    points[i] = p2;
                    break;
                case 3:
                    points[i] = p3;
                    break;
                case 4:
                    points[i] = p4;
                    break;
                case 5:
                    points[i] = p5;
                    break;
                case 6:
                    points[i] = p6;
                    break;
                case 7:
                    points[i] = p7;
                    break;
                case 8:
                    points[i] = p8;
                    break;
                case 9:
                    points[i] = p9;
                    break;
                default:
                    Log.v("myautotest","不要输入1-9以外的值");
            }
        }
        device.swipe(points,20);
    }

    //进入到设置中某一级菜单下
    public static void enterSettingsFirstLevelByName(String name) throws UiObjectNotFoundException {
        UiScrollable settingsScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        settingsScroll.scrollTextIntoView(name);
        UiObject2 securityObj = device.findObject(By.text(name));
        securityObj.clickAndWait(Until.newWindow(),LONG_WAIT);
    }

    //清除最近使用程序
    public static void clearAllRecentApps() throws RemoteException {
        try{
            device.pressHome();
            device.pressHome();
            device.pressRecentApps();
            device.wait(Until.hasObject(By.res("com.android.systemui:id/loading")),LONG_WAIT);
            sleep(SHORT_SLEEP);
            UiObject2 clearObj = device.findObject(By.res("com.android.systemui:id/loading"));
            clearObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        }catch (NullPointerException e){
            device.pressHome();
            device.pressHome();
            device.pressRecentApps();
            device.wait(Until.hasObject(By.res("com.android.systemui:id/loading")),LONG_WAIT);
            sleep(SHORT_SLEEP);
            UiObject2 clearObj = device.findObject(By.res("com.android.systemui:id/loading"));
            clearObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        }

    }

    //传递一个应用名称，找到该名称的应用，找到返回true，未找到返回false（为了适配不同版本桌面，增加了应用未打开前名称带●的判断）
    public static boolean findAppByText(String appName){
        device.pressHome();
        device.pressHome();
        device.pressHome();
        UiObject2 appIcon = device.findObject(By.text(appName));
        UiObject2 appIcon1 = device.findObject(By.text("● "+appName+" "));
        if(null != appIcon || null != appIcon1){
            return true;
        }
        device.wait(Until.hasObject(By.res("com.woyou.launcher:id/page_indicator")),LONG_WAIT);
        UiObject2 indicatorObj = device.findObject(By.res("com.woyou.launcher:id/page_indicator"));
        int pages = indicatorObj.getChildCount();
        Log.v(LOG_V,Integer.toString(pages));
        device.swipe(5,device.getDisplayHeight()/2,device.getDisplayWidth()-5,device.getDisplayHeight()/2,20);
        sleep(SHORT_SLEEP);
        for(int i = 0;i < pages;i++){
            Log.v(LOG_V,Integer.toString(i));
            UiObject2 appObj = device.findObject(By.text(appName));
            UiObject2 appObj1 = device.findObject(By.text("● "+appName+" "));
            if(null != appObj || null != appObj1){
                return true;
            }
            else {
                sleep(SHORT_SLEEP);
                device.swipe(device.getDisplayWidth()-5,device.getDisplayHeight()/2,5,device.getDisplayHeight()/2,10);
                sleep(SHORT_SLEEP);
            }
        }
        return false;
    }

    //传递一个应用名称，找到该名称的应用并打开，找到返回true，未找到返回false（为了适配不同版本桌面，增加了应用未打开前名称带●的判断）
    public static boolean findAppAndOpenByText(String appName){
        device.pressHome();
        device.pressHome();
        device.pressHome();
        UiObject2 appIcon = device.findObject(By.text(appName));
        UiObject2 appIcon1 = device.findObject(By.text("● "+appName+" "));
        if(null != appIcon){
            appIcon.clickAndWait(Until.newWindow(),LONG_WAIT);
            return true;
        }else if (null != appIcon1){
            appIcon1.clickAndWait(Until.newWindow(),LONG_WAIT);
            return true;
        }
        device.wait(Until.hasObject(By.res("com.woyou.launcher:id/page_indicator")),LONG_WAIT);
        int pages = device.findObject(By.res("com.woyou.launcher:id/page_indicator")).getChildCount();
        Log.v(LOG_V,Integer.toString(pages));
        device.swipe(5,device.getDisplayHeight()/2,device.getDisplayWidth()-5,device.getDisplayHeight()/2,20);
        sleep(SHORT_SLEEP);
        int i = 0;
        while(i < pages){
            UiObject2 appObj = device.findObject(By.text(appName));
            UiObject2 appObj1 = device.findObject(By.text("● "+appName+" "));
            if(null != appObj){
                appObj.clickAndWait(Until.newWindow(),LONG_WAIT);
                device.waitForIdle();
                return true;
            }else if(null != appObj1){
                appObj1.clickAndWait(Until.newWindow(),LONG_WAIT);
                device.waitForIdle();
                return true;
            }
            else if(i < pages -1){
                sleep(SHORT_SLEEP);
                device.swipe(device.getDisplayWidth()-5,device.getDisplayHeight()/2,5,device.getDisplayHeight()/2,10);
                sleep(SHORT_SLEEP);
            }
            i++;
        }
        return false;
    }


    //截图并保存图片到相关的测试类,测试case目录下
    public static void screenshotCap(String tag) {
        StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
        String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
        String methodName = testClass.getMethodName();
        Log.v(LOG_V, Environment.getExternalStorageState());
        File dir = new File(Environment.getExternalStorageDirectory()
                + File.separator + "app_spoon-screenshots" + File.separator
                + className + File.separator + methodName);
        Log.v(LOG_V, dir.getAbsolutePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String screenshotName = System.currentTimeMillis() + "_" + tag + ".png";
        File screenshotFile = new File(dir, screenshotName);
        sleep(SHORT_SLEEP);
        device.takeScreenshot(screenshotFile, 0.2F, 50);
        sleep(SHORT_SLEEP);
    }

    //获取栈信息，该方法用于获取当前正在进行的测试case类信息，方法信息
    public static StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
        for (StackTraceElement e : trace) {
            Log.v(LOG_V, e.getClassName() + ":" + e.getMethodName());
        }
        for (int i = trace.length - 1; i >= 0; --i) {
            StackTraceElement element = trace[i];
            if ("android.test.InstrumentationTestCase".equals(element.getClassName()) && "runMethod".equals(element.getMethodName())) {
                if (Build.VERSION.SDK_INT >= 23) {
                    return trace[i - 2];
                } else {
                    return trace[i - 3];
                }
            }

            if ("org.junit.runners.model.FrameworkMethod$1".equals(element.getClassName()) && "runReflectiveCall".equals(element.getMethodName())) {
                if (Build.VERSION.SDK_INT >= 23) {
                    return trace[i - 2];
                } else {
                    return trace[i - 3];
                }
            }
        }

        throw new IllegalArgumentException("Could not find test class!");
    }

    //sleep方法
    public static void sleep(int sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
