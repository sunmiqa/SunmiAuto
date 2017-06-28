package sunmi.com.sunmiauto;

import android.app.Instrumentation;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;

/**
 * Created by Administrator on 2017/6/21.
 */

public class UiSanqianke {
    static Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    static UiDevice device = UiDevice.getInstance(instrumentation);

    @BeforeClass
    //该测试类开始前执行操作
    public static void initLiza() throws RemoteException {
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
    public static void clearDown() throws MessagingException, InterruptedException {
        Log.i("ssss","ddddd");
        Thread.sleep(10000);
    }

    @Test
    public void testRecentKey() throws RemoteException, MessagingException {
        device.pressRecentApps();
    }

//    @Test
//    public void testSanqianke() throws UiObjectNotFoundException, IOException {
//        UiScrollable vegTypeScroll = new UiScrollable(new UiSelector().resourceId("com.luedongtech.phonepos:id/lv_category"));
//        List<String> vegList = new ArrayList<>();
//        vegList.add("热门");
//        vegList.add("杭帮菜");
//        vegList.add("新的新店");
//        vegList.add("冷菜");
//        vegList.add("点心");
//        vegList.add("面食");
//        vegList.add("小炒");
//        vegList.add("新品尝鲜");
//        vegList.add("套餐类");
//        vegList.add("特色名吃");
//        vegList.add("零号线外卖-体验店快餐1");
//        vegList.add("鲜笋烧麦");
//        vegList.add("考拉先生-粥类");
//        vegList.add("考拉先生-饮料");
//        vegList.add("超市水吧");
//        vegList.add("甜品");
//        vegList.add("外卖");
//
//        for (int i = 0; i < 20; i++) {
//            Random random = new Random();
//            int m = random.nextInt(16);
//            vegTypeScroll.scrollTextIntoView(vegList.get(m));
//            UiObject2 vegObj = device.findObject(By.text(vegList.get(m)));
//            vegObj.clickAndWait(Until.newWindow(),1000);
//        }
//
//    }
//
//    @Test
//    public void testScrollApps() throws UiObjectNotFoundException {
//        UiScrollable newAPPUiScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
//        newAPPUiScroll.setAsHorizontalList();
//        for (int i = 0; i < 50; i++) {
//            newAPPUiScroll.scrollToBeginning(20,100);
//            newAPPUiScroll.scrollToEnd(20,100);
//        }
//    }

//    @Test
//    public void testClearText() throws InterruptedException, RemoteException {
////        device.clearLastTraversedText();
////        device.click(67,668);
//        UiObject obj1 = device.findObject(new UiSelector().text("QQ浏览器"));
//        UiObject2 obj2 = device.findObject(By.text("QQ浏览器"));
////        List<UiObject2> objs = device.findObjects(By.textContains("应用"));
//        device.openNotification();
//        Thread.sleep(5000);
//        device.openQuickSettings();
//        device.takeScreenshot(new File("sdcard/"));
//        device.sleep();
//        Thread.sleep(5000);
//        device.wakeUp();
//        obj1.waitForExists(5000);
//        obj2.getChildren();
//    }

}
