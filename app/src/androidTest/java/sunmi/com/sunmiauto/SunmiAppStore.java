package sunmi.com.sunmiauto;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
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
import java.util.List;

import javax.mail.MessagingException;

import static sunmi.com.sunmiauto.SunmiUtil.*;


/**
 * Created by Administrator on 2017/6/7.
 */

public class SunmiAppStore {
    static Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    static UiDevice device = UiDevice.getInstance(instrumentation);


    @Before
    //每条测试用例开始前执行操作
    public void setup() throws RemoteException, UiObjectNotFoundException, IOException {
        clearAllRecentApps();
        findAppAndOpenByText("应用市场");
        sleep(2000);
    }

    @After
    //每条测试用例结束后执行操作
    public void teardown() throws RemoteException {
        SunmiUtil.sleep(1000);
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
        SunmiUtil.screenshotCap("afterEnter");
        UiObject2 suggObj = device.findObject(By.res("woyou.market:id/fab_me"));
        Assert.assertNotNull("未找到用户入口，判断打开应用市场失败", suggObj);
    }

    //测试新品应用存在
    @Test
    public void testCheckNewArrive() {
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/tv_newest_all").text("全部")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("afterClickNewestAll");
        UiObject2 newestAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("不存在woyou.market:id/list_view resId", newestAllObj);
    }

    //测试新品应用滑动查看
    @Test
    public void testNewScroll() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("afterEnter");
        UiScrollable newAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
        newAllScroll.setAsHorizontalList();
        newAllScroll.scrollToBeginning(10, 10);
        newAllScroll.scrollToEnd(10, 10);
        newAllScroll.scrollToBeginning(10, 10);
        UiScrollable newAllScroll1 = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
        Assert.assertNotNull("新品上架模块未能找到",newAllScroll1);
    }

    //测试热门中全部应用查看
    @Test
    public void testCheckHotApps() {
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("afterClickHotAll");
        UiObject2 hotAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("找不到res为woyou.market:id/list_view的对象", hotAllObj);
    }

    //测试热门应用滑动查看
    @Test
    public void testHotScroll() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("afterEnter");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/linear_hot_view"));
        hotAllScroll.scrollToEnd(20, 10);
        hotAllScroll.scrollToBeginning(20, 10);
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/item_hot_title"));
        Assert.assertNotNull("热门应用Title为能够找到",hotObj);
    }

    //测试分类信息正确
    @Test
    public void testCheckCategory() throws UiObjectNotFoundException {
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
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/fab_me")).clickAndWait(Until.newWindow(), 10000);
        UiObject2 mineObj = device.findObject(By.res("woyou.market:id/tv_title").text("我的"));
        Assert.assertNotNull("不存在\"我的\"", mineObj);
    }

    //测试从应用商店跳转到登录商米账户界面
    @Test
    public void testEnterLoginPageFromAppStore() {
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/fab_me")).clickAndWait(Until.newWindow(), 10000);
        device.findObject(By.res("woyou.market:id/item_user_info")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("enterUserLoginCenter");
        String actulPkg = device.getCurrentPackageName();
        Assert.assertEquals("本应该是com.sunmi.usercenter，而实际是" + actulPkg, "com.sunmi.usercenter", actulPkg);
    }

    //测试安装热门中一个应用
    @Test
    public void testInstallAppFromHot() throws UiObjectNotFoundException, IOException {
        SunmiUtil.screenshotCap("afterEnter");
        device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部")).clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("afterClickHotAll");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollTextIntoView("安装");
        UiObject2 installObj = device.findObject(By.text("安装"));
        UiObject2 fullInstallObj = installObj.getParent().getParent();
        UiObject2 installNameObj = fullInstallObj.findObject(By.res("woyou.market:id/tv_name"));
        String name = installNameObj.getText();
        UiObject2 installObjnew =  fullInstallObj.getParent().findObject(By.text("安装"));
        installObjnew.click();
        Assert.assertTrue("下载安装了300秒，仍然未安装好",installObjnew.wait(Until.textEquals("打开"),300000));
        SunmiUtil.screenshotCap("install_info");
        device.pressHome();
        findAppByText(name);
        UiObject2 nameObj = device.findObject(By.text(name));
        Assert.assertNotNull("未找到"+name,nameObj);
    }

    //测试搜索“谷歌浏览器Google”，该应用显示在结果列表中第一位
    @Test
    public void testSearchByTitle() throws UiObjectNotFoundException {
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        sleep(2000);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
        searchObj1.click();
        Configurator.getInstance().setKeyInjectionDelay(1000L);
        searchObj1.setText("谷歌浏览器Google");
        UiScrollable appList = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject appInfo = appList.getChildByInstance(new UiSelector().resourceId("woyou.market:id/app_view"),0);
        UiObject appNameObj = appInfo.getChild(new UiSelector().resourceId("woyou.market:id/tv_name"));
        Assert.assertEquals("谷歌浏览器Google Chrome",appNameObj.getText());
    }

    //测试热搜应用存在
    @Test
    public void testHotSearch(){
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        sleep(2000);
        UiObject2 hotSearchObj = device.findObject(By.res("woyou.market:id/search_hot_text"));
        UiObject2 hotWordObj = hotSearchObj.findObject(By.clazz("android.widget.TextView"));
        Assert.assertEquals("热搜应用",hotWordObj.getText());
    }

    //测试热搜应用能够点击并且点击的应用与跳转到的应用详情是同一个应用
    @Test
    public void testClickHotSearchApp(){
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        sleep(2000);
        int hotAppCount = device.findObject(By.res("woyou.market:id/grid_view")).getChildCount();
        if(hotAppCount != 0){
            String appName = device.findObject(By.res("woyou.market:id/grid_view")).findObject(By.res("woyou.market:id/tv_name")).getText();
            device.findObject(By.res("woyou.market:id/grid_view")).findObject(By.res("woyou.market:id/item_app")).click();
            String appDetailName = device.findObject(By.res("woyou.market:id/tv_name")).getText();
            Assert.assertEquals(appName,appDetailName);
        }else{
            Assert.fail("没有任何热搜应用，需要确定是否确实不存在热搜应用");
        }
    }

    //测试清除搜索栏里的内容
    @Test
    public void testClearSearchBarButton(){
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        sleep(2000);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
        searchObj1.click();
        searchObj1.setText("Google");
        UiObject2 clearButton = device.findObject(By.res("woyou.market:id/iv_delete"));
        clearButton.click();
        UiObject2 searchObj2 = device.findObject(By.res("woyou.market:id/et_search"));
        String textContent = searchObj2.getText();
        Assert.assertEquals("搜索",textContent);
    }

    //测试通过右上角返回按钮从搜索界面返回到应用市场Home界面
    @Test
    public void testBacktoHomeFromSearchButton(){
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        sleep(2000);
        UiObject2 backButton = device.findObject(By.res("woyou.market:id/btn_back"));
        backButton.clickAndWait(Until.newWindow(),5000);
        sleep(2000);
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        Assert.assertNotNull(mineEntrence);
    }

    //测试通过应用市场入口登陆用户中心
    @Test
    public void testLogin(){
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        UiObject2 userCenterEntrence = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence.clickAndWait(Until.newWindow(),5000);
        UiObject2 loginText = device.findObject(By.res("com.sunmi.usercenter:id/edit_login_username"));
        loginText.clear();
        loginText.setText("zhangjiyang@sunmi.com");
        UiObject2 pwdText = device.findObject(By.res("com.sunmi.usercenter:id/txt_password"));
        pwdText.clear();
        pwdText.setText("sunmi2016");
        UiObject2 loginButton = device.findObject(By.res("com.sunmi.usercenter:id/btn_login"));
        loginButton.clickAndWait(Until.newWindow(),5000);
        sleep(2000);
        String shopName = device.findObject(By.res("woyou.market:id/tv_shop_name")).getText();
        Assert.assertNotEquals("未登录",shopName);
    }

    //测试能够进入到购买记录界面
    @Test
    public void testBuiedApps(){
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        UiObject2 buyOption = device.findObject(By.res("woyou.market:id/item_buy_app"));
        buyOption.clickAndWait(Until.newWindow(),5000);
        UiObject2 buyHistory = device.findObject(By.text("购买记录"));
        Assert.assertNotNull(buyHistory);
    }

//    @Test
//    public void testUpdatableApps(){
//
//    }
//
//    @Test
//    public void testInstallingApps(){
//
//    }
//
//    @Test
//    public void testOpenAutoUpdateButton(){
//
//    }
//
//    @Test
//    public void testOpenSaveDataButton(){
//
//    }
//    @Test
//    public void testEnterFeedback(){
//
//    }
//    @Test
//    public void testCheckServiceProvider(){
//
//    }
//    @Test
//    public void testCheckRecentVersion(){
//
//    }
//    @Test
//    public void testCheckAppDetail(){
//
//    }
//    @Test
//    public void testCommentBeforeInstall(){
//
//    }
//    @Test
//    public void testCommentAfterInstall(){
//
//    }
//
//    @Test
//    public void testFoldupAppDetail(){
//
//    }
//
//    @Test
//    public void testCheckAppDetailStatusNotInstalled(){
//
//    }
//
//    @Test
//    public void testCheckAppDetailStatusDownloading(){
//
//    }
//
//    @Test
//    public void testCheckAppDetailStatusInstalling(){
//
//    }
//
//    @Test
//    public void testCheckAppDetailStatusInstalled(){
//
//    }
//
//    @Test
//    public void testCheckSearchHistory(){
//
//    }
}
