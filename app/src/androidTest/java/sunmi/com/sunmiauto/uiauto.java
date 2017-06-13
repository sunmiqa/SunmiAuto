package sunmi.com.sunmiauto;

import android.app.Instrumentation;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/7.
 */

public class uiauto {
    static Instrumentation instrumentation =InstrumentationRegistry.getInstrumentation();
    static UiDevice device = UiDevice.getInstance(instrumentation);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    //每条测试用例开始前执行操作
    public void setup() throws RemoteException {
        device.pressHome();
        device.pressRecentApps();
        sleep(2000);
        UiObject2 cleanObj = device.findObject(By.res("com.android.systemui:id/loading"));
        cleanObj.click();
        sleep(1000);
        device.pressBack();
        sleep(2000);
    }

    @After
    //每条测试用例结束后执行操作
    public void teardown() throws RemoteException {
        sleep(3000);
        device.pressHome();
    }

    @BeforeClass
    //该测试类开始前执行操作
    public static void initLiza() throws RemoteException {
        device.pressRecentApps();
        File dir=new File(Environment.getExternalStorageDirectory()+File.separator+"app_spoon-screenshots");
        class Inner {
            //递归删除文件夹
            private void deleteFile(File file) {
                if (file.exists()) {//判断文件是否存在
                    if (file.isFile()) {//判断是否是文件
                        file.delete();//删除文件
                    } else if (file.isDirectory()) {//否则如果它是一个目录
                        File[] files = file.listFiles();//声明目录下所有的文件 files[];
                        for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                            this.deleteFile(files[i]);//把每个文件用这个方法进行迭代
                        }
                        file.delete();//删除文件夹
                    }
                } else {
                    System.out.println("所删除的文件不存在");
                }
            }
        }
        new Inner().deleteFile(dir);
        dir.mkdir();
    }

    @AfterClass
    //该测试类结束后执行操作
    public static void clearDown(){

    }

    @Test
    public void test用户中心() {
        sleep(3000);
        screenshotCap("before_enter");
        UiObject2 settingObj = device.findObject(By.text("用户中心"));
        settingObj.click();
        sleep(2000);
        screenshotCap("after_enter");
        UiObject2 regObj = device.findObject(By.res("com.sunmi.usercenter:id/edit_login_username"));
        regObj.setText("zhangjiyang@sunmi.com");
        sleep(2000);
        screenshotCap("after_click");
        sleep(2000);
    }

    @Test
    public void test打开无线网() {
        screenshotCap("berfore_enter");
        UiObject2 settingObj = device.findObject(By.text("设置"));
        settingObj.clickAndWait(Until.newWindow(), 1000);
        sleep(2000);
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("WLAN"));
        WlanObj.click();
        sleep(2000);
        screenshotCap("after_click");
        sleep(2000);
    }

    @Test
    public void test打开蓝牙() {
        screenshotCap("berfore_enter");
        sleep(2000);
        UiObject2 settingObj = device.findObject(By.text("设置"));
        settingObj.clickAndWait(Until.newWindow(), 1000);
        sleep(2000);
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.click();
        sleep(2000);
        screenshotCap("after_click");
        sleep(2000);
    }

    public void screenshotCap(String tag){
        StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
        String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
        String methodName = testClass.getMethodName();
        File dir=new File(Environment.getExternalStorageDirectory()
                +File.separator+"app_spoon-screenshots"+File.separator
                +className+File.separator+methodName);
        Log.v("TEST", dir.getAbsolutePath());
        if(!dir.exists()){
            dir.mkdirs();
        }
        String screenshotName = System.currentTimeMillis() + "_" + tag + ".png";
        File screenshotFile = new File(dir, screenshotName);
        device.takeScreenshot(screenshotFile);
        sleep(2000);
    }



    StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
        for(int i = trace.length - 1; i >= 0; --i) {
            StackTraceElement element = trace[i];
            if("android.test.InstrumentationTestCase".equals(element.getClassName()) && "runMethod".equals(element.getMethodName())) {
                return trace[i - 2];
            }

            if("org.junit.runners.model.FrameworkMethod$1".equals(element.getClassName()) && "runReflectiveCall".equals(element.getMethodName())) {
                return trace[i - 2];
            }
        }

        throw new IllegalArgumentException("Could not find test class!");
    }

    public static void sleep(int mint) {
        try {
            Thread.sleep(mint);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
