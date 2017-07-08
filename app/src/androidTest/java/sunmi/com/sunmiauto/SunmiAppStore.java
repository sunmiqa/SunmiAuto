package sunmi.com.sunmiauto;

import android.app.Instrumentation;
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
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;


/**
 * Created by Administrator on 2017/6/7.
 */

public class SunmiAppStore {
    static Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    static UiDevice device = UiDevice.getInstance(instrumentation);


    @Before
    //每条测试用例开始前执行操作
    public void setup() throws RemoteException, UiObjectNotFoundException, IOException {
        device.executeShellCommand("am start -n woyou.market/store.ui.activity.HomeActivity");
        SunmiUtil.sleep(2000);
        device.findObject(By.text("推荐")).click();
        device.pressRecentApps();
    }

    @After
    //每条测试用例结束后执行操作
    public void teardown() throws RemoteException {
        SunmiUtil.sleep(1000);
        device.pressHome();
    }

    @BeforeClass
    //该测试类开始前执行操作
    public static void initLiza() throws RemoteException, IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "app_spoon-screenshots");
        Log.v("testDir", Environment.getExternalStorageDirectory() + File.separator + "app_spoon-screenshots");
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

    @AfterClass
    //该测试类结束后执行操作
    public static void clearDown() throws MessagingException, RemoteException {

    }


    //测试能够成功打开应用市场
    @Test
    public void testOpenAppStore() {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),5000);
//        device.waitForIdle(10000);
        SunmiUtil.screenshotCap("afterEnter");
        UiObject2 suggObj = device.findObject(By.res("woyou.market:id/fab_me"));
        Assert.assertNotNull("未找到用户入口，判断打开应用市场失败", suggObj);
    }

    //测试新品应用存在
    @Test
    public void testCheckNewArrive() {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/tv_newest_all").text("全部")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("afterClickNewestAll");
        UiObject2 newestAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("不存在", newestAllObj);
    }

    //测试新品应用滑动查看
    @Test
    public void testNewScroll() throws UiObjectNotFoundException {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        UiScrollable newAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
        newAllScroll.setAsHorizontalList();
        newAllScroll.scrollToEnd(20, 10);
        newAllScroll.scrollToBeginning(20, 10);
    }

    //测试热门中全部应用查看
    @Test
    public void testCheckHotApps() {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("afterClickHotAll");
        UiObject2 hotAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("找不到res为woyou.market:id/list_view的对象", hotAllObj);
    }

    //测试热门应用滑动查看
    @Test
    public void testHotScroll() throws UiObjectNotFoundException {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/linear_hot_view"));
        hotAllScroll.scrollToEnd(20, 10);
        hotAllScroll.scrollToBeginning(20, 10);
    }

    //测试分类信息正确
    @Test
    public void testCheckCategory() throws UiObjectNotFoundException {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.text("分类")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("enterCategory");
        UiScrollable cateScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view"));
        ArrayList<String> appCategory = new ArrayList<>();
        switch (Build.DEVICE) {
            case "V1-B18":
                Log.v("enterV1-B18", "V1-B18");
                appCategory.add("外卖");
                appCategory.add("支付");
                appCategory.add("餐饮");
                appCategory.add("电商");
                appCategory.add("酒店");
                appCategory.add("零售");
                appCategory.add("工具");
                appCategory.add("配送");
                appCategory.add("旅游");
                appCategory.add("理财");
                appCategory.add("EET");
                break;
            case "V1s-G":
                Log.v("enterV1s-G", "V1s-G");
                appCategory.add("外卖");
                appCategory.add("支付");
                appCategory.add("团购");
                appCategory.add("餐饮");
                appCategory.add("电商");
                appCategory.add("酒店");
                appCategory.add("零售");
                appCategory.add("工具");
                appCategory.add("配送");
                appCategory.add("旅游");
                appCategory.add("理财");
                appCategory.add("EET");
                break;
            default:
                Log.v("enterDefault", "default");
                appCategory.add("外卖");
                appCategory.add("支付");
                appCategory.add("团购");
                appCategory.add("餐饮");
                appCategory.add("电商");
                appCategory.add("酒店");
                appCategory.add("零售");
                appCategory.add("工具");
                appCategory.add("配送");
                appCategory.add("旅游");
                appCategory.add("理财");
                appCategory.add("EET");
        }
        for (String s : appCategory
                ) {
            boolean b = cateScroll.scrollTextIntoView(s);
            Assert.assertTrue(s + "--分类不存在", b);
        }
    }

    //测试进入分类正确
    @Test
    public void testEnterCategory() throws UiObjectNotFoundException {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.text("分类")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("enterCategory");
        UiScrollable cateScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view"));
        ArrayList<String> appCategory = new ArrayList<>();
        switch (Build.DEVICE) {
            case "V1-B18":
                Log.v("enterV1-B18", "V1-B18");
                appCategory.add("外卖");
                appCategory.add("支付");
                appCategory.add("餐饮");
                appCategory.add("电商");
                appCategory.add("酒店");
                appCategory.add("零售");
                appCategory.add("工具");
                appCategory.add("配送");
                appCategory.add("旅游");
                appCategory.add("理财");
                appCategory.add("EET");
                break;
            case "V1s-G":
                Log.v("enterV1s-G", "V1s-G");
                appCategory.add("外卖");
                appCategory.add("支付");
                appCategory.add("团购");
                appCategory.add("餐饮");
                appCategory.add("电商");
                appCategory.add("酒店");
                appCategory.add("零售");
                appCategory.add("工具");
                appCategory.add("配送");
                appCategory.add("旅游");
                appCategory.add("理财");
                appCategory.add("EET");
                break;
            default:
                Log.v("enterDefault", "default");
                appCategory.add("外卖");
                appCategory.add("支付");
                appCategory.add("团购");
                appCategory.add("餐饮");
                appCategory.add("电商");
                appCategory.add("酒店");
                appCategory.add("零售");
                appCategory.add("工具");
                appCategory.add("配送");
                appCategory.add("旅游");
                appCategory.add("理财");
                appCategory.add("EET");
        }
        for (String s : appCategory
                ) {
            boolean b = cateScroll.scrollTextIntoView(s);
            Assert.assertTrue(s + "--分类不存在", b);
            UiObject2 sCategoryObj = device.findObject(By.text(s));
            sCategoryObj.clickAndWait(Until.newWindow(), 10000);
            UiObject2 cateNmaeObj = device.findObject(By.res("woyou.market:id/tv_title"));
            String cateName = cateNmaeObj.getText();
            Assert.assertEquals("应该是" + s + ",而实际是" + cateName, s, cateName);
            device.pressBack();
        }
    }

    //测试进入我的
    @Test
    public void testEnterMine() {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/fab_me")).clickAndWait(Until.newWindow(), 10000);
        UiObject2 mineObj = device.findObject(By.res("woyou.market:id/tv_title").text("我的"));
        Assert.assertNotNull("不存在\"我的\"", mineObj);
    }

    //测试从应用商店跳转到登录商米账户界面
    @Test
    public void testEnterLoginPageFromAppStore() {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/fab_me")).clickAndWait(Until.newWindow(), 10000);
        device.findObject(By.res("woyou.market:id/item_user_info")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("enterUserLoginCenter");
        String actulPkg = device.getCurrentPackageName();
        Assert.assertEquals("本应该是com.sunmi.usercenter，而实际是" + actulPkg, "com.sunmi.usercenter", actulPkg);
    }

    //测试安装热门中一个应用
    @Test
    public void testInstallAppFromHot() throws UiObjectNotFoundException {
//        SunmiUtil.screenshotCap();("beforeEnter");
//        device.findObject(By.text("应用市场")).clickAndWait(Until.newWindow(),10000);
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("afterClickHotAll");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollTextIntoView("安装");
        device.findObject(By.text("安装")).click();
    }
}
