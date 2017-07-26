package sunmi.com.sunmiauto.testutils;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static sunmi.com.sunmiauto.testutils.TestConstants.LOG_V;
import static sunmi.com.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static sunmi.com.sunmiauto.testutils.TestConstants.SHORT_SLEEP;

/**
 * Created by fengy on 2017/7/8.
 */

public class TestUtils {
    public static Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    public static UiDevice device = UiDevice.getInstance(instrumentation);

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

    //传递一个应用名称，找到该名称的应用，找到返回true，未找到返回false
    public static boolean findAppByText(String appName){
        device.pressHome();
        device.pressHome();
        device.pressHome();
        UiObject2 appIcon = device.findObject(By.text(appName));
        if(null != appIcon){
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
            if(null != appObj){
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

    //传递一个应用名称，找到该名称的应用并打开，找到返回true，未找到返回false
    public static boolean findAppAndOpenByText(String appName){
        device.pressHome();
        device.pressHome();
        device.pressHome();
        UiObject2 appIcon = device.findObject(By.text(appName));
        if(null != appIcon){
            appIcon.clickAndWait(Until.newWindow(),LONG_WAIT);
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
            if(null != appObj){
                appObj.clickAndWait(Until.newWindow(),LONG_WAIT);
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
