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
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 suggObj = device.findObject(By.res("woyou.market:id/fab_me"));
        Assert.assertNotNull("未找到用户入口，判断打开应用市场失败", suggObj);
    }

    //测试新品应用存在
    @Test
    public void testCheckNewArrive() {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 newObj = device.findObject(By.res("woyou.market:id/tv_newest_all").text("全部"));
        newObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("newAppsAll");
        UiObject2 newestAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("不存在woyou.market:id/list_view resId", newestAllObj);
    }

//    //测试新品应用滑动查看
//    @Test
//    public void testNewScroll() throws UiObjectNotFoundException {
//        SunmiUtil.screenshotCap("appStoreHome");
//        UiScrollable newAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
//        newAllScroll.setAsHorizontalList();
//        newAllScroll.scrollToBeginning(10, 10);
//        newAllScroll.scrollToEnd(10, 10);
//        newAllScroll.scrollToBeginning(10, 10);
//        SunmiUtil.screenshotCap("afterNewAppsScroll");
//        UiScrollable newAllScroll1 = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
//        Assert.assertNotNull("新品上架模块未能找到",newAllScroll1);
//    }

    //测试热门中全部应用查看
    @Test
    public void testCheckHotApps() {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAppsAll");
        UiObject2 hotAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("找不到res为woyou.market:id/list_view的对象", hotAllObj);
    }

    //测试热门应用滑动查看
    @Test
    public void testHotScroll() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/linear_hot_view"));
        hotAllScroll.scrollToEnd(20, 10);
        hotAllScroll.scrollToBeginning(20, 10);
        SunmiUtil.screenshotCap("scollInterface");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/item_hot_title"));
        Assert.assertNotNull("热门应用Title为能够找到",hotObj);
    }

//    //测试分类信息正确
//    @Test
//    public void testCheckCategory() throws UiObjectNotFoundException {
//        SunmiUtil.screenshotCap("appStoreHome");
//        UiObject2 categoryObj = device.findObject(By.text("分类"));
//        categoryObj.clickAndWait(Until.newWindow(), 10000);
//        SunmiUtil.screenshotCap("categoryInterface");
//        UiScrollable cateScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view"));
//        ArrayList<String> appCategory = new ArrayList<>();
//        switch (Build.DEVICE) {
//            case "V1-B18":
//                Log.v("enterV1-B18", "V1-B18");
//                appCategory.add("外卖");
//                appCategory.add("支付");
//                appCategory.add("餐饮");
//                appCategory.add("电商");
//                appCategory.add("酒店");
//                appCategory.add("零售");
//                appCategory.add("工具");
//                appCategory.add("配送");
//                appCategory.add("旅游");
//                appCategory.add("理财");
//                appCategory.add("EET");
//                break;
//            case "V1s-G":
//                Log.v("enterV1s-G", "V1s-G");
//                appCategory.add("外卖");
//                appCategory.add("支付");
//                appCategory.add("团购");
//                appCategory.add("餐饮");
//                appCategory.add("电商");
//                appCategory.add("酒店");
//                appCategory.add("零售");
//                appCategory.add("工具");
//                appCategory.add("配送");
//                appCategory.add("旅游");
//                appCategory.add("理财");
//                appCategory.add("EET");
//                break;
//            default:
//                Log.v("enterDefault", "default");
//                appCategory.add("外卖");
//                appCategory.add("支付");
//                appCategory.add("团购");
//                appCategory.add("餐饮");
//                appCategory.add("电商");
//                appCategory.add("酒店");
//                appCategory.add("零售");
//                appCategory.add("工具");
//                appCategory.add("配送");
//                appCategory.add("旅游");
//                appCategory.add("理财");
//                appCategory.add("EET");
//        }
//        for (String s : appCategory
//                ) {
//            boolean b = cateScroll.scrollTextIntoView(s);
//            Assert.assertTrue(s + "--分类不存在", b);
//        }
//    }

    //测试进入分类正确
    @Test
    public void testEnterCategory() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 categoryObj = device.findObject(By.text("分类"));
        categoryObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("categoryInterface");
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
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntObj = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("mineHome");
        UiObject2 mineObj = device.findObject(By.res("woyou.market:id/tv_title").text("我的"));
        Assert.assertNotNull("不存在\"我的\"", mineObj);
    }

    //测试从应用商店跳转到登录商米账户界面
    @Test
    public void testEnterLoginPageFromAppStore() {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntObj = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("mineHome");
        UiObject2 userObj = device.findObject(By.res("woyou.market:id/item_user_info"));
        userObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("loginInterface");
        String actulPkg = device.getCurrentPackageName();
        Assert.assertEquals("本应该是com.sunmi.usercenter，而实际是" + actulPkg, "com.sunmi.usercenter", actulPkg);
    }

    //测试安装热门中一个应用
    @Test
    public void testInstallAppFromHot() throws UiObjectNotFoundException, IOException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("allHotAppsInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollTextIntoView("安装");
        SunmiUtil.screenshotCap("ScrollToInstallableInterface");
        UiObject2 installObj = device.findObject(By.text("安装"));
        UiObject2 fullInstallObj = installObj.getParent().getParent();
        UiObject2 installNameObj = fullInstallObj.findObject(By.res("woyou.market:id/tv_name"));
        String name = installNameObj.getText();
        UiObject2 installObjnew =  fullInstallObj.getParent().findObject(By.text("安装"));
        installObjnew.click();
        SunmiUtil.screenshotCap("afterClickInstallBtn");
        boolean installSucc = installObjnew.wait(Until.textEquals("打开"),300000);
        if(!installSucc){
            installObjnew.click();
            SunmiUtil.screenshotCap("installFailed");
            Assert.fail("下载安装了300秒，仍然未安装好,暂停了下载");
        }
        SunmiUtil.screenshotCap("afterInstalled");
        device.pressHome();
        findAppByText(name);
        SunmiUtil.screenshotCap("findInstalledApp");
        UiObject2 nameObj = device.findObject(By.text(name));
        Assert.assertNotNull("未找到"+name,nameObj);
    }

    //测试搜索“DUDU”，该应用显示在结果列表中第一位
    @Test
    public void testSearchByTitle() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        SunmiUtil.screenshotCap("afterClickSearchBar");
        sleep(2000);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
        searchObj1.click();
        searchObj1.setText("DUDU");
        SunmiUtil.screenshotCap("inputSearchContent");
        UiScrollable appList = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject appInfo = appList.getChildByInstance(new UiSelector().resourceId("woyou.market:id/app_view"),0);
        UiObject appNameObj = appInfo.getChild(new UiSelector().resourceId("woyou.market:id/tv_name"));
        Assert.assertEquals("DUDU",appNameObj.getText());
    }

    //测试热搜应用存在
    @Test
    public void testHotSearch(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        SunmiUtil.screenshotCap("afterClickSearchBar");
        sleep(2000);
        UiObject2 hotSearchObj = device.findObject(By.res("woyou.market:id/search_hot_text"));
        UiObject2 hotWordObj = hotSearchObj.findObject(By.clazz("android.widget.TextView"));
        Assert.assertEquals("热搜应用",hotWordObj.getText());
    }

    //测试热搜应用能够点击并且点击的应用与跳转到的应用详情是同一个应用
    @Test
    public void testClickHotSearchApp(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        SunmiUtil.screenshotCap("afterClickSearchBar");
        sleep(2000);
        int hotAppCount = device.findObject(By.res("woyou.market:id/grid_view")).getChildCount();
        if(hotAppCount != 0){
            String appName = device.findObject(By.res("woyou.market:id/grid_view")).findObject(By.res("woyou.market:id/tv_name")).getText();
            UiObject2 hotSearchObj = device.findObject(By.res("woyou.market:id/grid_view")).findObject(By.res("woyou.market:id/item_app"));
            hotSearchObj.clickAndWait(Until.newWindow(),5000);
            SunmiUtil.screenshotCap("clickHotsearchFirstOne");
            UiObject2 appDetailObj = device.findObject(By.res("woyou.market:id/tv_name"));
            String appDetailName = appDetailObj.getText();
            Assert.assertEquals(appName,appDetailName);
        }else{
            Assert.fail("没有任何热搜应用，需要确定是否确实不存在热搜应用");
        }
    }

    //测试清除搜索栏里的内容
    @Test
    public void testClearSearchBarButton(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        SunmiUtil.screenshotCap("afterClickSearchBar");
        sleep(2000);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
        searchObj1.click();
        searchObj1.setText("DUDU");
        SunmiUtil.screenshotCap("inputSearchContentInterface");
        UiObject2 clearButton = device.findObject(By.res("woyou.market:id/iv_delete"));
        clearButton.click();
        SunmiUtil.screenshotCap("afterClickClearBtn");
        UiObject2 searchObj2 = device.findObject(By.res("woyou.market:id/et_search"));
        String textContent = searchObj2.getText();
        Assert.assertEquals("搜索",textContent);
    }

    //测试通过右上角返回按钮从搜索界面返回到应用市场Home界面
    @Test
    public void testBacktoHomeFromSearchButton(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        SunmiUtil.screenshotCap("afterClickSearchBar");
        sleep(2000);
        UiObject2 backButton = device.findObject(By.res("woyou.market:id/btn_back"));
        backButton.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("afterClickBackBtn");
        sleep(2000);
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        Assert.assertNotNull(mineEntrence);
    }

    //测试通过应用市场入口登陆用户中心
    @Test
    public void testLogin(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("mineHome");
        UiObject2 userCenterEntrence = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("loginInterface");
        UiObject2 loginText = device.findObject(By.res("com.sunmi.usercenter:id/edit_login_username"));
        loginText.clear();
        loginText.setText("zhangjiyang@sunmi.com");
        UiObject2 pwdText = device.findObject(By.res("com.sunmi.usercenter:id/txt_password"));
        pwdText.clear();
        pwdText.setText("sunmi2016");
        SunmiUtil.screenshotCap("afterInputInfo");
        UiObject2 loginButton = device.findObject(By.res("com.sunmi.usercenter:id/btn_login"));
        loginButton.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("afterClickLogin");
        sleep(2000);
        String shopName = device.findObject(By.res("woyou.market:id/tv_shop_name")).getText();
        Assert.assertNotEquals("未登录",shopName);
        UiObject2 userCenterEntrence1 = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence1.clickAndWait(Until.newWindow(),5000);
        UiObject2 currAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/rel_now_account"));
        currAccountObj.clickAndWait(Until.newWindow(),5000);
        UiObject2 outAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/btn_login_out"));
        outAccountObj.clickAndWait(Until.newWindow(),5000);
        UiObject2 pwdTextObj = device.findObject(By.res("com.sunmi.usercenter:id/edit"));
        pwdTextObj.clear();
        pwdTextObj.setText("sunmi2016");
        UiObject2 loginOutButton = device.findObject(By.res("com.sunmi.usercenter:id/right"));
        loginOutButton.clickAndWait(Until.newWindow(),5000);
    }

    //测试能够进入到购买记录界面
    @Test
    public void testBuiedApps(){
        SunmiUtil.screenshotCap("appStoreHome");
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
        SunmiUtil.screenshotCap("afterLoginUsercenterInterface");
        sleep(2000);
        UiObject2 buyOption = device.findObject(By.res("woyou.market:id/item_buy_app"));
        buyOption.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("buiedHisInterface");
        UiObject2 buyHistory = device.findObject(By.text("购买记录"));
        Assert.assertNotNull(buyHistory);
        device.pressBack();
        UiObject2 userCenterEntrence1 = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence1.clickAndWait(Until.newWindow(),5000);
        UiObject2 currAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/rel_now_account"));
        currAccountObj.clickAndWait(Until.newWindow(),5000);
        UiObject2 outAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/btn_login_out"));
        outAccountObj.clickAndWait(Until.newWindow(),5000);
        UiObject2 pwdTextObj = device.findObject(By.res("com.sunmi.usercenter:id/edit"));
        pwdTextObj.clear();
        pwdTextObj.setText("sunmi2016");
        UiObject2 loginOutButton = device.findObject(By.res("com.sunmi.usercenter:id/right"));
        loginOutButton.clickAndWait(Until.newWindow(),5000);
    }

    //测试检查能够进入到appUpdateInterface
    @Test
    public void testUpdatableApps(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("mineHome");
        UiObject2 updateOption = device.findObject(By.res("woyou.market:id/item_update_app"));
        updateOption.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("appUpdateInterface");
        UiObject2 updateHistory = device.findObject(By.text("应用更新"));
        Assert.assertNotNull(updateHistory);
    }

    //测试检查能够进入到安装应用界面
    @Test
    public void testInstallingApps(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("mineHome");
        UiObject2 installOption = device.findObject(By.res("woyou.market:id/item_install_app"));
        installOption.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("appInstallInterface");
        UiObject2 installHistory = device.findObject(By.text("安装列表"));
        Assert.assertNotNull(installHistory);
    }

    //测试检查应用自动更新开关默认打开
    @Test
    public void testOpenAutoUpdateButton(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("mineHome");
        UiObject2 updateSwitch = device.findObject(By.res("woyou.market:id/switch_auto_update"));
        Assert.assertTrue("应用自动更新开关默认应该打开，当前时关闭",updateSwitch.isChecked());
    }

    //测试检查省流量开关默认打开
    @Test
    public void testOpenSaveDataButton(){
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("mineHome");
        UiObject2 saveSwitch = device.findObject(By.res("woyou.market:id/switch_save_flow"));
        Assert.assertTrue("省流量开关默认应该打开，当前时关闭",saveSwitch.isCheckable());
    }

    //测试从反馈入口进入到反馈中
    @Test
    public void testEnterFeedback() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("mineHome");
        UiScrollable mineScroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        mineScroll.scrollTextIntoView("反馈");
        SunmiUtil.screenshotCap("ScrollToFeedbackInterface");
        UiObject2 feedbackObj = device.findObject(By.text("反馈"));
        feedbackObj.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("enterFeedbackInterface");
        String currentPkgName = device.getCurrentPackageName();
        Assert.assertEquals("期望当前包名为com.sunmi.userfeedback，而实际为"+currentPkgName,"com.sunmi.userfeedback",currentPkgName);
    }

    //测试检查当前的服务商
    @Test
    public void testCheckServiceProvider() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("mineHome");
        UiScrollable mineScroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        mineScroll.scrollTextIntoView("服务商");
        SunmiUtil.screenshotCap("ScrollToServicePro");
        UiObject2 serviceProObj = device.findObject(By.text("服务商"));
        Assert.assertNotNull("未找到服务商信息",serviceProObj);
    }

    //测试检查当前的appstore版本
    @Test
    public void testCheckRecentVersion() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(), 5000);
        SunmiUtil.screenshotCap("mineHome");
        UiScrollable mineScroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        mineScroll.scrollTextIntoView("当前版本");
        SunmiUtil.screenshotCap("scrollToCurVersion");
        UiObject2 serviceProObj = device.findObject(By.text("当前版本"));
        Assert.assertNotNull("未找到版本信息", serviceProObj);
    }

    //测试从热门应用enterAppDetail，应用详情和点击进入的应用信息一致
    @Test
    public void testCheckAppDetail() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject fullAppObj = hotAllScroll.getChild(new UiSelector().resourceId("woyou.market:id/app_view"));
        String appName = fullAppObj.getChild(new UiSelector().resourceId("woyou.market:id/tv_name")).getText();
        fullAppObj.clickAndWaitForNewWindow(5000);
        SunmiUtil.screenshotCap("enterHotAppsFirstOne");
        UiObject2 nameObj = device.findObject(By.res("woyou.market:id/tv_name"));
        Assert.assertEquals("期望的名字是"+appName+"，而实际是"+nameObj.getText(),appName,nameObj.getText());
    }

    //测试应用未安装应用无法评论
    @Test
    public void testCommentBeforeInstall() throws IOException, UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("安装"));
        SunmiUtil.screenshotCap("scrollInstallBtnInterface");
        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("安装"));
        UiObject2 fullAppObj = installObj.getParent().getParent();
        fullAppObj.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("uninstalledAppDetail");
        UiObject2 commentObj = device.findObject(By.res("woyou.market:id/tv_install_comment_app"));
        commentObj.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("afterClickComment");
        UiObject2 rateObj = device.findObject(By.res("woyou.market:id/rating_bar"));
        Assert.assertNull(rateObj);
    }

    //测试已安装应用可以发表评论
    @Test
    public void testCommentAfterInstall() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("打开"));
        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("打开"));
        UiObject2 fullAppObj = installObj.getParent().getParent();
        fullAppObj.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("enterAppDetail");
        UiObject2 commentObj = device.findObject(By.res("woyou.market:id/tv_install_comment_app"));
        commentObj.clickAndWait(Until.newWindow(),5000);
        device.wait(Until.hasObject(By.res("woyou.market:id/rating_bar")),5000);
        SunmiUtil.screenshotCap("afterClickComment");
        UiObject2 rateObj = device.findObject(By.res("woyou.market:id/rating_bar"));
        Assert.assertNotNull(rateObj);
    }

    //测试应用详情中点击顶部收起按钮退出应用详情界面
    @Test
    public void testFoldupAppDetail() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject fullAppObj = hotAllScroll.getChild(new UiSelector().resourceId("woyou.market:id/app_view"));
        fullAppObj.clickAndWaitForNewWindow(5000);
        SunmiUtil.screenshotCap("enterAppDetail");
        UiObject2 foldupButton = device.findObject(By.res("woyou.market:id/iv_arrow"));
        foldupButton.clickAndWait(Until.newWindow(),5000);
        SunmiUtil.screenshotCap("foldUpAppDetail");
        UiScrollable hotAllScroll1 = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        Assert.assertNotNull(hotAllScroll1);
    }

    //测试应用点击安装时候，安装按钮变为暂停按钮
    @Test
    public void testCheckAppDetailStatusDownloading() throws UiObjectNotFoundException, InterruptedException {
        SunmiUtil.screenshotCap("appStoreHome");
        device.wait(Until.hasObject(By.res("woyou.market:id/tv_hot_all").text("全部")),20000);
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("安装"));
        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("安装"));
        installObj.click();
        Assert.assertTrue(installObj.wait(Until.textEquals("暂停"),5000));
        SunmiUtil.screenshotCap("afterClickInstallBtn");
    }

    //测试应用点击暂停下载时候，暂停按钮变成继续按钮
    @Test
    public void testCheckAppDetailStatusPause() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 allObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        allObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("安装"));
        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("安装"));
        installObj.click();
        SunmiUtil.screenshotCap("afterClickInstallBtn");
        installObj.wait(Until.textEquals("暂停"),5000);
        installObj.click();
        Assert.assertTrue(installObj.wait(Until.textEquals("继续"),5000));
        installObj.click();
    }

    //测试应用点击暂停下载时候，暂停按钮变成继续按钮，再点击继续按钮，继续按钮变成暂停按钮状态
    @Test
    public void testCheckAppDetailStatusPauseGoon() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 allObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        allObj.clickAndWait(Until.newWindow(), 10000);
        SunmiUtil.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("安装"));
        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("安装"));
        installObj.click();
        installObj.wait(Until.textEquals("暂停"),5000);
        installObj.click();
        installObj.wait(Until.textEquals("继续"),5000);
        installObj.click();
        Assert.assertTrue(installObj.wait(Until.textEquals("暂停"),5000));

    }

    //检查搜索历史记录正常，搜索一个应用，该应用名称和历史记录中第一个相同
    @Test
    public void testCheckSearchHistory() throws UiObjectNotFoundException {
        SunmiUtil.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        SunmiUtil.screenshotCap("afterClickSearchBtn");
        sleep(2000);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
        searchObj1.click();
        searchObj1.setText("cam");
        SunmiUtil.screenshotCap("enterSearchContent");
        UiScrollable appList = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject appInfo = appList.getChildByInstance(new UiSelector().resourceId("woyou.market:id/app_view"),0);
        UiObject appNameObj = appInfo.getChild(new UiSelector().resourceId("woyou.market:id/tv_name"));
        String appName = appNameObj.getText();
        appInfo.click();
        SunmiUtil.screenshotCap("enterTheFirstSearchResult");
        device.pressBack();
        sleep(1000);
        UiObject2 clearButton = device.findObject(By.res("woyou.market:id/iv_delete"));
        clearButton.click();
        SunmiUtil.screenshotCap("clearSearchBar");
        sleep(2000);
        String historyObjName = device.findObject(By.res("woyou.market:id/history_key")).findObject(By.clazz("android.widget.TextView")).getText();
        Assert.assertEquals("期望的名字是"+appName+",而实际是"+historyObjName,appName,historyObjName);
    }

}
