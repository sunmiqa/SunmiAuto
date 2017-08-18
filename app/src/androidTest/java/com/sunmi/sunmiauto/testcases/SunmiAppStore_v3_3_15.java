package com.sunmi.sunmiauto.testcases;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import com.sunmi.sunmiauto.testcategory.CategoryAppStoreTests_v3_3_15;
import com.sunmi.sunmiauto.testutils.TestUtils;

import static com.sunmi.sunmiauto.testutils.TestConstants.SHORT_SLEEP;
import static com.sunmi.sunmiauto.testutils.TestConstants.USERCENTER_ACCOUNT;
import static com.sunmi.sunmiauto.testutils.TestConstants.USERCENTER_PWD;
import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestConstants.DOWNLOAD_WAIT;
import static com.sunmi.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;


/**
 * Created by Administrator on 2017/6/7.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiAppStore_v3_3_15 {

    @Before
    //每条测试用例开始前执行操作
    public void setup() throws RemoteException, UiObjectNotFoundException, IOException {
        TestUtils.clearAllRecentApps();
        TestUtils.findAppAndOpenByText("应用市场");
        TestUtils.sleep(SHORT_SLEEP);
    }

    @After
    //每条测试用例结束后执行操作
    public void teardown() throws RemoteException {
        TestUtils.sleep(SHORT_SLEEP);
    }

    @BeforeClass
    //该测试类开始前执行操作
    public static void initLiza(){
    }

    @AfterClass
    //该测试类结束后执行操作
    public static void clearDown() throws MessagingException, RemoteException {

    }


    //测试能够成功打开应用市场
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test001OpenAppStore() {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 suggObj = device.findObject(By.res("woyou.market:id/fab_me"));
        Assert.assertNotNull("未找到用户入口，判断打开应用市场失败", suggObj);
    }

    //测试新品应用存在
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test002CheckNewArrive() {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 newObj = device.findObject(By.res("woyou.market:id/tv_newset_all").text("全部"));
        newObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("newAppsAll");
        UiObject2 newestAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("不存在woyou.market:id/list_view res Id", newestAllObj);
    }

//    //测试新品应用滑动查看
//    @Test
//    public void testNewScroll() throws UiObjectNotFoundException {
//        TestUtils.screenshotCap("appStoreHome");
//        UiScrollable newAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
//        newAllScroll.setAsHorizontalList();
//        newAllScroll.scrollToBeginning(10, 10);
//        newAllScroll.scrollToEnd(10, 10);
//        newAllScroll.scrollToBeginning(10, 10);
//        TestUtils.screenshotCap("afterNewAppsScroll");
//        UiScrollable newAllScroll1 = new UiScrollable(new UiSelector().resourceId("woyou.market:id/recycler_view_newest"));
//        Assert.assertNotNull("新品上架模块未能找到",newAllScroll1);
//    }

    //测试热门中全部应用查看
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test003CheckHotApps() {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("hotAppsAll");
        UiObject2 hotAllObj = device.findObject(By.res("woyou.market:id/list_view"));
        Assert.assertNotNull("找不到res为woyou.market:id/list_view的对象", hotAllObj);
    }

    //测试热门应用滑动查看
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test004HotScroll() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/linear_hot_view"));
        hotAllScroll.scrollToEnd(20, 10);
        hotAllScroll.scrollToBeginning(20, 10);
        TestUtils.screenshotCap("scollInterface");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/item_hot_title"));
        Assert.assertNotNull("热门应用Title为能够找到",hotObj);
    }

//    //测试分类信息正确
//    @Test
//    public void testCheckCategory() throws UiObjectNotFoundException {
//        TestUtils.screenshotCap("appStoreHome");
//        UiObject2 categoryObj = device.findObject(By.text("分类"));
//        categoryObj.clickAndWait(Until.newWindow(), 10000);
//        TestUtils.screenshotCap("categoryInterface");
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
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test005EnterCategory() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 categoryObj = device.findObject(By.text("分类"));
        categoryObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("categoryInterface");
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
            sCategoryObj.clickAndWait(Until.newWindow(), LONG_WAIT);
            UiObject2 cateNmaeObj = device.findObject(By.res("woyou.market:id/tv_title"));
            String cateName = cateNmaeObj.getText();
            Assert.assertEquals("应该是" + s + ",而实际是" + cateName, s, cateName);
            device.pressBack();
        }
    }

    //测试进入我的
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test006EnterMine() {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntObj = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiObject2 mineObj = device.findObject(By.res("woyou.market:id/tv_title").text("我的"));
        Assert.assertNotNull("不存在\"我的\"", mineObj);
    }

    //测试从应用商店跳转到登录商米账户界面
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test007EnterLoginPageFromAppStore() {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntObj = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiObject2 userObj = device.findObject(By.res("woyou.market:id/item_user_info"));
        userObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("loginInterface");
        String actulPkg = device.getCurrentPackageName();
        Assert.assertEquals("本应该是com.sunmi.usercenter，而实际是" + actulPkg, "com.sunmi.usercenter", actulPkg);
    }

    //测试安装热门中一个应用
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test008InstallAppFromHot() throws UiObjectNotFoundException, IOException {
        device.registerWatcher("downLoadFail", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                UiObject2 confirmObj = device.findObject(By.res("woyou.market:id/tv_confirm"));
                if(null != confirmObj){
                    confirmObj.clickAndWait(Until.newWindow(),LONG_WAIT);
                    return true;
                }
                return false;
            }
        });

        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("allHotAppsInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollTextIntoView("安装");
        TestUtils.screenshotCap("ScrollToInstallableInterface");
        UiObject2 installObj = device.findObject(By.text("安装"));
        UiObject2 fullInstallObj = installObj.getParent().getParent();
        UiObject2 installNameObj = fullInstallObj.findObject(By.res("woyou.market:id/tv_name"));
        String name = installNameObj.getText();
        UiObject2 installObjnew =  fullInstallObj.getParent().findObject(By.text("安装"));
        installObjnew.click();
        TestUtils.screenshotCap("afterClickInstallBtn");
        boolean installSucc = installObjnew.wait(Until.textEquals("打开"),DOWNLOAD_WAIT);
        if(!installSucc){
            installObjnew.click();
            TestUtils.screenshotCap("installFailed");
            Assert.fail("下载安装了300秒，仍然未安装好,暂停了下载");
        }
        TestUtils.screenshotCap("afterInstalled");
        device.pressHome();
        Boolean b = TestUtils.findAppByText(name);
        TestUtils.screenshotCap("findInstalledApp");
        Assert.assertTrue("桌面上未找到\""+name+"\"这个应用",b);
    }

    //测试搜索“DUDU”，该应用显示在结果列表中第一位
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test009SearchByTitle() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotOne = device.findObject(By.res("woyou.market:id/linear_hot_view")).findObject(By.res("woyou.market:id/tv_name"));
        String targetAppName = hotOne.getText();
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        TestUtils.screenshotCap("afterClickSearchBar");
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
        searchObj1.click();
        searchObj1.setText(targetAppName);
        TestUtils.screenshotCap("inputSearchContent");
        UiScrollable appList = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject appInfo = appList.getChildByInstance(new UiSelector().className("android.widget.FrameLayout"),0);
        UiObject appNameObj = appInfo.getChild(new UiSelector().resourceId("woyou.market:id/tv_name"));
        Assert.assertEquals(targetAppName,appNameObj.getText());
    }

    //测试热搜应用存在
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test010HotSearch(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        TestUtils.screenshotCap("afterClickSearchBar");
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 hotSearchObj = device.findObject(By.res("woyou.market:id/search_hot_text"));
        UiObject2 hotWordObj = hotSearchObj.findObject(By.clazz("android.widget.TextView"));
        Assert.assertEquals("热搜应用",hotWordObj.getText());
    }

    //测试热搜应用能够点击并且点击的应用与跳转到的应用详情是同一个应用
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test011ClickHotSearchApp(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        TestUtils.screenshotCap("afterClickSearchBar");
        TestUtils.sleep(SHORT_SLEEP);
        int hotAppCount = device.findObject(By.res("woyou.market:id/grid_view")).getChildCount();
        if(hotAppCount != 0){
            String appName = device.findObject(By.res("woyou.market:id/grid_view")).findObject(By.res("woyou.market:id/tv_name")).getText();
            UiObject2 hotSearchObj = device.findObject(By.res("woyou.market:id/grid_view")).findObject(By.res("woyou.market:id/item_app"));
            hotSearchObj.clickAndWait(Until.newWindow(),LONG_WAIT);
            TestUtils.screenshotCap("clickHotsearchFirstOne");
            UiObject2 appDetailObj = device.findObject(By.res("woyou.market:id/tv_name"));
            String appDetailName = appDetailObj.getText();
            Assert.assertEquals(appName,appDetailName);
        }else{
            Assert.fail("没有任何热搜应用，需要确定是否确实不存在热搜应用");
        }
    }

    //测试清除搜索栏里的内容
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test012ClearSearchBarButton(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        TestUtils.screenshotCap("afterClickSearchBar");
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
//        searchObj1.click();
        searchObj1.setText("DUDU");
        TestUtils.screenshotCap("inputSearchContentInterface");
        UiObject2 clearButton = device.findObject(By.res("woyou.market:id/iv_delete"));
        clearButton.click();
        TestUtils.screenshotCap("afterClickClearBtn");
        UiObject2 searchObj2 = device.findObject(By.res("woyou.market:id/et_search"));
        String textContent = searchObj2.getText();
        Assert.assertEquals("搜索",textContent);
    }

    //测试通过右上角返回按钮从搜索界面返回到应用市场Home界面
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test013BacktoHomeFromSearchButton(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        TestUtils.screenshotCap("afterClickSearchBar");
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 backButton = device.findObject(By.res("woyou.market:id/btn_back"));
        backButton.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("afterClickBackBtn");
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        Assert.assertNotNull(mineEntrence);
    }

    //测试通过应用市场入口登陆用户中心
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test014Login(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiObject2 userCenterEntrence = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("loginInterface");
        UiObject2 loginText = device.findObject(By.res("com.sunmi.usercenter:id/edit_login_username"));
        loginText.clear();
        loginText.setText(USERCENTER_ACCOUNT);
        UiObject2 pwdText = device.findObject(By.res("com.sunmi.usercenter:id/txt_password"));
        pwdText.clear();
        pwdText.setText(USERCENTER_PWD);
        TestUtils.screenshotCap("afterInputInfo");
        UiObject2 loginButton = device.findObject(By.res("com.sunmi.usercenter:id/btn_login"));
        loginButton.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("afterClickLogin");
        TestUtils.sleep(SHORT_SLEEP);
        String shopName = device.findObject(By.res("woyou.market:id/tv_shop_name")).getText();
        Assert.assertNotEquals("未登录",shopName);
        UiObject2 userCenterEntrence1 = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence1.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 currAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/rel_now_account"));
        currAccountObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 outAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/btn_login_out"));
        outAccountObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 pwdTextObj = device.findObject(By.res("com.sunmi.usercenter:id/edit"));
        pwdTextObj.clear();
        pwdTextObj.setText(USERCENTER_PWD);
        UiObject2 loginOutButton = device.findObject(By.res("com.sunmi.usercenter:id/right"));
        loginOutButton.clickAndWait(Until.newWindow(),LONG_WAIT);
    }

    //测试能够进入到购买记录界面
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test015BuiedApps(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 userCenterEntrence = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 loginText = device.findObject(By.res("com.sunmi.usercenter:id/edit_login_username"));
        loginText.clear();
        loginText.setText(USERCENTER_ACCOUNT);
        UiObject2 pwdText = device.findObject(By.res("com.sunmi.usercenter:id/txt_password"));
        pwdText.clear();
        pwdText.setText(USERCENTER_PWD);
        UiObject2 loginButton = device.findObject(By.res("com.sunmi.usercenter:id/btn_login"));
        loginButton.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("afterLoginUsercenterInterface");
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 buyOption = device.findObject(By.res("woyou.market:id/item_buy_app"));
        buyOption.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("buiedHisInterface");
        UiObject2 buyHistory = device.findObject(By.text("购买记录"));
        Assert.assertNotNull(buyHistory);
        device.pressBack();
        UiObject2 userCenterEntrence1 = device.findObject(By.res("woyou.market:id/item_user_info"));
        userCenterEntrence1.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 currAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/rel_now_account"));
        currAccountObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 outAccountObj = device.findObject(By.res("com.sunmi.usercenter:id/btn_login_out"));
        outAccountObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        UiObject2 pwdTextObj = device.findObject(By.res("com.sunmi.usercenter:id/edit"));
        pwdTextObj.clear();
        pwdTextObj.setText(USERCENTER_PWD);
        UiObject2 loginOutButton = device.findObject(By.res("com.sunmi.usercenter:id/right"));
        loginOutButton.clickAndWait(Until.newWindow(),LONG_WAIT);
    }

    //测试检查能够进入到appUpdateInterface
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test016UpdatableApps(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiObject2 updateOption = device.findObject(By.res("woyou.market:id/item_update_app"));
        updateOption.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("appUpdateInterface");
        UiObject2 updateHistory = device.findObject(By.text("应用更新"));
        Assert.assertNotNull(updateHistory);
    }

    //测试检查能够进入到安装应用界面
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test017InstallingApps(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiObject2 installOption = device.findObject(By.res("woyou.market:id/item_install_app"));
        installOption.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("appInstallInterface");
        UiObject2 installHistory = device.findObject(By.text("安装列表"));
        Assert.assertNotNull(installHistory);
    }

    //测试检查应用自动更新开关默认打开
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test018OpenAutoUpdateButton(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiObject2 updateSwitch = device.findObject(By.res("woyou.market:id/switch_auto_update"));
        Assert.assertTrue("应用自动更新开关默认应该打开，当前时关闭",updateSwitch.isChecked());
    }

    //测试检查省流量开关默认打开
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test019OpenSaveDataButton(){
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiObject2 saveSwitch = device.findObject(By.res("woyou.market:id/switch_save_flow"));
        Assert.assertTrue("省流量开关默认应该打开，当前时关闭",saveSwitch.isCheckable());
    }

    //测试从反馈入口进入到反馈中
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test020EnterFeedback() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiScrollable mineScroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        mineScroll.scrollTextIntoView("反馈");
        TestUtils.screenshotCap("ScrollToFeedbackInterface");
        UiObject2 feedbackObj = device.findObject(By.text("反馈"));
        feedbackObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("enterFeedbackInterface");
        String currentPkgName = device.getCurrentPackageName();
//        for (int i = 0; i <10 ; i++) {
//            Log.v("myautotest1",device.getCurrentPackageName());
//            sleep(500);
//        }
        Assert.assertEquals("期望当前包名为com.sunmi.userfeedback，而实际为"+currentPkgName,"com.sunmi.userfeedback",currentPkgName);
    }

    //测试检查当前的服务商
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test021CheckServiceProvider() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiScrollable mineScroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        mineScroll.scrollTextIntoView("服务商");
        TestUtils.screenshotCap("ScrollToServicePro");
        UiObject2 serviceProObj = device.findObject(By.text("服务商"));
        Assert.assertNotNull("未找到服务商信息",serviceProObj);
    }

    //测试检查当前的appstore版本
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test022CheckRecentVersion() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 mineEntrence = device.findObject(By.res("woyou.market:id/fab_me"));
        mineEntrence.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("mineHome");
        UiScrollable mineScroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        mineScroll.scrollTextIntoView("当前版本");
        TestUtils.screenshotCap("scrollToCurVersion");
        UiObject2 serviceProObj = device.findObject(By.text("当前版本"));
        Assert.assertNotNull("未找到版本信息", serviceProObj);
    }

    //测试从热门应用enterAppDetail，应用详情和点击进入的应用信息一致
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test023CheckAppDetail() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject fullAppObj = hotAllScroll.getChild(new UiSelector().className("android.widget.FrameLayout"));
        String appName = fullAppObj.getChild(new UiSelector().resourceId("woyou.market:id/tv_name")).getText();
        fullAppObj.clickAndWaitForNewWindow(LONG_WAIT);
        TestUtils.screenshotCap("enterHotAppsFirstOne");
        UiObject2 nameObj = device.findObject(By.res("woyou.market:id/tv_name"));
        Assert.assertEquals("期望的名字是"+appName+"，而实际是"+nameObj.getText(),appName,nameObj.getText());
    }

    //测试应用未安装应用无法评论
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test024CommentBeforeInstall() throws IOException, UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/id_tv_install_view").text("安装"));
        TestUtils.screenshotCap("scrollInstallBtnInterface");
        UiObject2 installObj = device.findObject(By.res("woyou.market:id/id_tv_install_view").text("安装"));
        UiObject2 fullAppObj = installObj.getParent().getParent();
        fullAppObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("uninstalledAppDetail");
        UiObject2 commentObj = device.findObject(By.res("woyou.market:id/tv_install_comment_app"));
        commentObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("afterClickComment");
        UiObject2 rateObj = device.findObject(By.res("woyou.market:id/rating_bar"));
        Assert.assertNull(rateObj);
    }

    //测试已安装应用可以发表评论
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test025CommentAfterInstall() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/id_tv_install_view").text("打开"));
        UiObject2 installObj = device.findObject(By.res("woyou.market:id/id_tv_install_view").text("打开"));
        UiObject2 fullAppObj = installObj.getParent().getParent();
        fullAppObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("enterAppDetail");
        UiObject2 commentObj = device.findObject(By.res("woyou.market:id/tv_install_comment_app"));
        commentObj.clickAndWait(Until.newWindow(),LONG_WAIT);
        device.wait(Until.hasObject(By.res("woyou.market:id/rating_bar")),LONG_WAIT);
        TestUtils.screenshotCap("afterClickComment");
        UiObject2 rateObj = device.findObject(By.res("woyou.market:id/rating_bar"));
        Assert.assertNotNull(rateObj);
        device.pressBack();
    }

    //测试应用详情中点击顶部收起按钮退出应用详情界面
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test026FoldupAppDetail() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
        hotObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        TestUtils.screenshotCap("hotAllInterface");
        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject fullAppObj = hotAllScroll.getChild(new UiSelector().className("android.widget.FrameLayout"));
        fullAppObj.clickAndWaitForNewWindow(LONG_WAIT);
        TestUtils.screenshotCap("enterAppDetail");
        UiObject2 foldupButton = device.findObject(By.res("woyou.market:id/iv_arrow"));
        foldupButton.clickAndWait(Until.newWindow(),LONG_WAIT);
        TestUtils.screenshotCap("foldUpAppDetail");
        UiScrollable hotAllScroll1 = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        Assert.assertNotNull(hotAllScroll1);
    }

//    //测试应用点击安装时候，安装按钮变为暂停按钮
//    @Test
//    public void testCheckAppDetailStatusDownloading() throws UiObjectNotFoundException, InterruptedException {
//        TestUtils.screenshotCap("appStoreHome");
//        device.wait(Until.hasObject(By.res("woyou.market:id/tv_hot_all").text("全部")),20000);
//        UiObject2 hotObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
//        hotObj.clickAndWait(Until.newWindow(), 10000);
//        TestUtils.screenshotCap("hotAllInterface");
//        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
//        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("安装"));
//        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("安装"));
//        installObj.click();
//        Assert.assertTrue(installObj.wait(Until.textEquals("暂停"),5000));
//        installObj.click();
//    }

//    //测试应用点击暂停下载时候，暂停按钮变成继续按钮
//    @Test
//    public void testCheckAppDetailStatusPause() throws UiObjectNotFoundException {
//        TestUtils.screenshotCap("appStoreHome");
//        UiObject2 allObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
//        allObj.clickAndWait(Until.newWindow(), 10000);
//        TestUtils.screenshotCap("hotAllInterface");
//        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
//        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("安装"));
//        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("安装"));
//        installObj.click();
//        TestUtils.screenshotCap("afterClickInstallBtn");
//        installObj.wait(Until.textEquals("暂停"),5000);
//        installObj.click();
//        Assert.assertTrue(installObj.wait(Until.textEquals("继续"),5000));
//        installObj.click();
//    }

//    //测试应用点击暂停下载时候，暂停按钮变成继续按钮，再点击继续按钮，继续按钮变成暂停按钮状态
//    @Test
//    public void testCheckAppDetailStatusPauseGoon() throws UiObjectNotFoundException {
//        TestUtils.screenshotCap("appStoreHome");
//        UiObject2 allObj = device.findObject(By.res("woyou.market:id/tv_hot_all").text("全部"));
//        allObj.clickAndWait(Until.newWindow(), 10000);
//        TestUtils.screenshotCap("hotAllInterface");
//        UiScrollable hotAllScroll = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
//        hotAllScroll.scrollIntoView(new UiSelector().resourceId("woyou.market:id/tv_install").text("安装"));
//        UiObject2 installObj = device.findObject(By.res("woyou.market:id/tv_install").text("安装"));
//        installObj.click();
//        installObj.wait(Until.textEquals("暂停"),5000);
//        installObj.click();
//        installObj.wait(Until.textEquals("继续"),5000);
//        installObj.click();
//        Assert.assertTrue(installObj.wait(Until.textEquals("暂停"),5000));
//
//    }

    //检查搜索历史记录正常，搜索一个应用，该应用名称和历史记录中第一个相同
    @Category(CategoryAppStoreTests_v3_3_15.class)
    @Test
    public void test027CheckSearchHistory() throws UiObjectNotFoundException {
        TestUtils.screenshotCap("appStoreHome");
        UiObject2 hotOne = device.findObject(By.res("woyou.market:id/linear_hot_view")).findObject(By.res("woyou.market:id/tv_name"));
        String targetAppName = hotOne.getText();
        UiObject2 searchObj = device.findObject(By.res("woyou.market:id/tv_search").text("搜索"));
        searchObj.click();
        TestUtils.screenshotCap("afterClickSearchBtn");
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 searchObj1 = device.findObject(By.res("woyou.market:id/et_search").text("搜索").focused(true));
        searchObj1.click();
        searchObj1.setText(targetAppName);
        TestUtils.screenshotCap("enterSearchContent");
        UiScrollable appList = new UiScrollable(new UiSelector().resourceId("woyou.market:id/list_view"));
        UiObject appInfo = appList.getChildByInstance(new UiSelector().className("android.widget.FrameLayout"),0);
        UiObject appNameObj = appInfo.getChild(new UiSelector().resourceId("woyou.market:id/tv_name"));
        String appName = appNameObj.getText();
        appInfo.click();
        TestUtils.screenshotCap("enterTheFirstSearchResult");
        device.pressBack();
        TestUtils.sleep(SHORT_SLEEP);
        UiObject2 clearButton = device.findObject(By.res("woyou.market:id/iv_delete"));
        clearButton.click();
        TestUtils.screenshotCap("clearSearchBar");
        TestUtils.sleep(SHORT_SLEEP);
        String historyObjName = device.findObject(By.res("woyou.market:id/history_key")).findObject(By.clazz("android.widget.TextView")).getText();
        Assert.assertEquals("期望的名字是"+appName+",而实际是"+historyObjName,appName,historyObjName);
    }

}
