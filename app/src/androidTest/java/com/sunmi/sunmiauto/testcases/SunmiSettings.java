

package com.sunmi.sunmiauto.testcases;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.system.Os;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.List;

import com.sunmi.sunmiauto.testcategory.CategorySettingsTests;
import com.sunmi.sunmiauto.testutils.TestUtils;

import static com.sunmi.sunmiauto.testutils.TestConstants.LOG_V;
import static com.sunmi.sunmiauto.testutils.TestConstants.SHORT_SLEEP;
import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;
import static com.sunmi.sunmiauto.testutils.TestConstants.BT_NAME;
import static com.sunmi.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static com.sunmi.sunmiauto.testutils.TestConstants.NETWORKNAMETEST;
import static com.sunmi.sunmiauto.testutils.TestConstants.NETWORKPWDTEST;
import static com.sunmi.sunmiauto.testutils.TestConstants.SETTINGSLIST;
import static com.sunmi.sunmiauto.testutils.TestConstants.USER_CENTER_PKG;

/**
 * Created by fengy on 2017/7/8.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiSettings {

    @BeforeClass
    public static void beforeTestClass() {

    }

    @Before
    public void setup() throws IOException, RemoteException {
        TestUtils.clearAllRecentApps();
        TestUtils.findAppByText("设置");
        long begin = System.currentTimeMillis();
        UiObject2 setObj = device.findObject(By.text("设置"));
        long end = System.currentTimeMillis();
        Log.v(LOG_V, String.valueOf(end - begin));
        setObj.clickAndWait(Until.newWindow(), LONG_WAIT);
    }

    @Category(CategorySettingsTests.class)
    @Test
    public void test001WiFi() {
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(), LONG_WAIT);
        screenshotCap("wifi_interface");
        UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertEquals("Wifi开关默认应该为打开状态", true, wifiButton.isChecked());
    }

    @Category(CategorySettingsTests.class)
    @Test
    public void test002DataUsage() {
        screenshotCap("setting_interface");
        UiObject2 ethOps = device.findObject(By.text("流量使用情况"));
        ethOps.clickAndWait(Until.newWindow(), LONG_WAIT);
        screenshotCap("dataUsage_interface");
        UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况").clazz("android.widget.TextView"));
        Assert.assertNotNull("未找到流量使用情况标识", dataUsageObj);
    }

    @Category(CategorySettingsTests.class)
    @Test
    public void test003OpenBT() {
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.click();
        sleep(SHORT_SLEEP);
        screenshotCap("after_click");
        UiObject2 BTButtonObj = device.findObject(By.res("com.android.settings:id/switch_widget"));
        sleep(SHORT_SLEEP);
        Assert.assertTrue("蓝牙开关默认不是打开状态", BTButtonObj.isChecked());

    }

    //检查WLAN中添加网络成功
    @Category(CategorySettingsTests.class)
    @Test
    public void test004AddNetwork() {
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("WLAN"));
        WlanObj.click();
        sleep(SHORT_SLEEP);
        UiObject2 moreBtn = device.findObject(By.clazz("android.widget.ImageButton").desc("更多选项"));
        moreBtn.click();
        sleep(SHORT_SLEEP);
        UiObject2 addNetworkObj = device.findObject(By.text("添加网络"));
        addNetworkObj.click();
        sleep(SHORT_SLEEP);
        UiObject2 networkName = device.findObject(By.focused(true));
        networkName.setText(NETWORKNAMETEST);
        sleep(SHORT_SLEEP);
        device.pressBack();
        UiObject2 securityOpt = device.findObject(By.res("com.android.settings:id/security"));
        securityOpt.click();
        sleep(SHORT_SLEEP);
        UiObject2 WPAWPA2PSK = device.findObject(By.text("WPA/WPA2 PSK"));
        WPAWPA2PSK.click();
        sleep(SHORT_SLEEP);
        UiObject2 pwdObj = device.findObject(By.res("com.android.settings:id/password"));
        pwdObj.setText(NETWORKPWDTEST);
        sleep(SHORT_SLEEP);
        UiObject2 saveObj = device.findObject(By.res("android:id/button1"));
        saveObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        UiObject2 moreBtn1 = device.findObject(By.clazz("android.widget.ImageButton").desc("更多选项"));
        moreBtn1.click();
        sleep(SHORT_SLEEP);
        UiObject2 savedNetworkObj = device.findObject(By.text("已保存的网络"));
        savedNetworkObj.click();
        sleep(SHORT_SLEEP);
        UiObject2 addedNetwork = device.findObject(By.text(NETWORKNAMETEST));
        Assert.assertNotNull("未找到添加的" + NETWORKNAMETEST, addedNetwork);
        addedNetwork.click();
        sleep(SHORT_SLEEP);
        UiObject2 canclObj = device.findObject(By.text("取消保存"));
        canclObj.click();

    }

    //检查设置中所有一级选项可以正常进入
    @Category(CategorySettingsTests.class)
    @Test
    public void test005CheckFirstLevel() throws UiObjectNotFoundException {
        screenshotCap("after_enter");
        UiScrollable settingsScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        for (int i = 0; i < SETTINGSLIST.size(); i++) {
            settingsScroll.scrollTextIntoView(SETTINGSLIST.get(i));
            UiObject2 optObj = device.findObject(By.text(SETTINGSLIST.get(i)));
            optObj.clickAndWait(Until.newWindow(), 5000);
            if (SETTINGSLIST.get(i).equalsIgnoreCase("用户中心")) {
                Assert.assertEquals("未找到" + SETTINGSLIST.get(i), USER_CENTER_PKG, device.getCurrentPackageName());
                device.pressBack();
                continue;
            }
            if (SETTINGSLIST.get(i).equalsIgnoreCase("音量键自定义")) {
                UiObject2 volumeKeyObj = device.findObject(By.pkg("com.sunmi.sidekey").text("音量键自定义"));
                Assert.assertNotNull("未找到" + SETTINGSLIST.get(i), volumeKeyObj);
                device.pressBack();
                continue;
            }
            UiObject2 optEnterObj = device.findObject(By.clazz("android.widget.TextView").text(SETTINGSLIST.get(i)));
            Assert.assertNotNull("未找到" + SETTINGSLIST.get(i), optEnterObj);
            device.pressBack();
        }
    }

    //检查WLAN为打开状态
    @Category(CategorySettingsTests.class)
    @Test
    public void test006CheckWLANStatus() {
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(), LONG_WAIT);
        screenshotCap("wifi_interface");
        UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertEquals("Wifi开关默认应该为打开状态", true, wifiButton.isChecked());
    }

    //能够进入到WLAN高级菜单中
    @Category(CategorySettingsTests.class)
    @Test
    public void test007CheckEnterAdvPass() {
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(), LONG_WAIT);
        screenshotCap("wifi_interface");
        sleep(SHORT_SLEEP);
        UiObject2 moreBtn = device.findObject(By.clazz("android.widget.ImageButton").desc("更多选项"));
        moreBtn.click();
        sleep(SHORT_SLEEP);
        UiObject2 addNetworkObj = device.findObject(By.text("高级"));
        addNetworkObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        UiObject2 advWLANObj = device.findObject(By.text("高级WLAN"));
        Assert.assertNotNull("没有能够进入到高级WLAN页面", advWLANObj);
    }

    //检查蓝牙默认设备名称正确
    @Category(CategorySettingsTests.class)
    @Test
    public void test008CheckBTNamePASS() {
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        screenshotCap("after_click");
        UiObject2 moreOptBtn = device.findObject(By.desc("更多选项"));
        moreOptBtn.click();
        sleep(SHORT_SLEEP);
        UiObject2 renameObj = device.findObject(By.text("重命名此设备"));
        renameObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 nameObj = device.findObject(By.focused(true));
        String BTName = nameObj.getText();
        Assert.assertEquals("蓝牙名称不正确", BT_NAME, BTName);

    }

    //检查重命名蓝牙设备名称成功
    @Category(CategorySettingsTests.class)
    @Test
    public void test009CheckBTRenamePASS() {
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        screenshotCap("after_click");
        UiObject2 moreOptBtn = device.findObject(By.desc("更多选项"));
        moreOptBtn.click();
        sleep(SHORT_SLEEP);
        UiObject2 renameObj = device.findObject(By.text("重命名此设备"));
        renameObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 nameObj = device.findObject(By.focused(true));
        nameObj.clear();
        nameObj.setText("TESTV1");
        UiObject2 renameBtn = device.findObject(By.text("重命名"));
        renameBtn.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 moreOptBtn1 = device.findObject(By.desc("更多选项"));
        moreOptBtn1.click();
        sleep(SHORT_SLEEP);
        UiObject2 renameObj1 = device.findObject(By.text("重命名此设备"));
        renameObj1.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 nameObj1 = device.findObject(By.focused(true));
        String BTName = nameObj1.getText();
        Assert.assertEquals("蓝牙改名失败", "TESTV1", BTName);
        nameObj1.clear();
        nameObj1.setText(BT_NAME);
        UiObject2 renameBtn1 = device.findObject(By.text("重命名"));
        renameBtn1.clickAndWait(Until.newWindow(), LONG_WAIT);
    }

    //检查流量使用情况存在概览和WLAN选项
    @Category(CategorySettingsTests.class)
    @Test
    public void test010CheckDataUsage() {
        screenshotCap("after_enter");
        UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况"));
        dataUsageObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 gernalObj = device.findObject(By.selected(true).text("概览"));
        Assert.assertNotNull("未找到概览tab", gernalObj);
        UiObject2 wlanObj = device.findObject(By.text("WLAN").res("android:id/title"));
        Assert.assertNotNull("未找到WLANtab", wlanObj);
    }

    //检查更多中飞行模式开关状态正确
    @Category(CategorySettingsTests.class)
    @Test
    public void test011CheckAirPlaneStatus() {
        screenshotCap("after_enter");
        UiObject2 moreObj = device.findObject(By.text("更多"));
        moreObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 airplaneSwitch = device.findObject(By.text("飞行模式")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
        Boolean airplaneOpen = airplaneSwitch.isChecked();
        Assert.assertFalse("飞行模式不是关闭状态", airplaneOpen);
    }

    //检查设置Ibeacon内容及开关是否打开(T1)
    @Test
    public void test012CheckIbeacon() {
        screenshotCap("after_enter");
        UiObject2 moreObj = device.findObject(By.text("更多"));
        moreObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 ibeaconObj = device.findObject(By.text("iBeacon"));
        ibeaconObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 ibeaconTitle = device.findObject(By.res("com.sunmi.ibeacon:id/text").text("IBEACON"));
        Assert.assertNotNull("未找到IBEANCON标题", ibeaconTitle);
        UiObject2 ibeaconSwitch = device.findObject(By.res("com.sunmi.ibeacon:id/swi"));
        Assert.assertTrue("iBeacon开关没有打开", ibeaconSwitch.isChecked());
        //检查默认的ibeacon参数
        UiObject2 defaultIbeaconUUIDObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_uuid").text("FDA50693-A4E2-4FB1-AFCF-C6EB07647825"));
        UiObject2 defaultIbeaconMAJORObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_major").text("10073"));
        UiObject2 defaultIbeaconMINORObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_minor").text("61418"));
        Assert.assertNotNull("默认UUID不正确", defaultIbeaconUUIDObj);
        Assert.assertNotNull("默认MAJRO不正确", defaultIbeaconMAJORObj);
        Assert.assertNotNull("默认MINOR不正确", defaultIbeaconMINORObj);
    }

    //检查设置Ibeacon功能正常(T1)
    @Test
    public void test013CheckIbeaconSetting() {
        screenshotCap("after_enter");
        UiObject2 moreObj = device.findObject(By.text("更多"));
        moreObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 ibeaconObj = device.findObject(By.text("iBeacon"));
        ibeaconObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 ibeaconSetObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_setting").text("设置iBeacon"));
        ibeaconSetObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        device.pressBack();
        UiObject2 othersUUIDObj = device.findObject(By.text("其他"));
        othersUUIDObj.click();
        UiObject2 setUUIDObj = device.findObject(By.focused(true));
        setUUIDObj.setText("11111111-2222-3333-4444-012345678910");
        UiObject2 setMAJORObj = device.findObject(By.res("com.sunmi.ibeacon:id/et_major"));
        setMAJORObj.setText("12345");
        UiObject2 setMINORObj = device.findObject(By.res("com.sunmi.ibeacon:id/et_minor"));
        setMINORObj.setText("54321");
        UiObject2 confirmObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_confirm"));
        confirmObj.click();
        UiObject2 UUIDObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_uuid").text("11111111-2222-3333-4444-012345678910"));
        Assert.assertNotNull("UUID未设置成功", UUIDObj);
        UiObject2 MAJORObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_major").text("12345"));
        Assert.assertNotNull("MAJOR未设置成功", MAJORObj);
        UiObject2 MINORObj = device.findObject(By.res("com.sunmi.ibeacon:id/tv_minor").text("54321"));
        Assert.assertNotNull("MINOR未设置成功", MINORObj);
        ibeaconSetObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        device.pressBack();
        UiObject2 wechatUUIDObj = device.findObject(By.text("微信"));
        wechatUUIDObj.click();
        UiObject2 confirmObj1 = device.findObject(By.res("com.sunmi.ibeacon:id/tv_confirm"));
        confirmObj1.clickAndWait(Until.newWindow(), LONG_WAIT);
    }

    //检查网络共享与便携热点界面下默认开关状态正确（T1）
    @Test
    public void test014CheckNetShareStatus() {
        screenshotCap("after_enter");
        UiObject2 moreObj = device.findObject(By.text("更多"));
        moreObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 APObj = device.findObject(By.text("网络共享与便携式热点"));
        APObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 USBObj = device.findObject(By.text("USB网络共享")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
        UiObject2 WLANObj = device.findObject(By.text("便携式WLAN热点")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
        UiObject2 BTObj = device.findObject(By.text("蓝牙网络共享")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
        Assert.assertFalse("USB网络共享默认不是关闭", USBObj.isChecked());
        Assert.assertFalse("便携式WLAN热点默认不是关闭", WLANObj.isChecked());
        Assert.assertFalse("蓝牙网络共享默认不是关闭", BTObj.isChecked());
    }

    //检查网络共享与便携热点可正常配置（T1）
    @Test
    public void test015CheckAP() {
        screenshotCap("after_enter");
        UiObject2 moreObj = device.findObject(By.text("更多"));
        moreObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 APObj = device.findObject(By.text("网络共享与便携式热点"));
        APObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 WLANObj = device.findObject(By.text("设置WLAN热点"));
        WLANObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 ssidObj = device.findObject(By.res("com.android.settings:id/ssid"));
        ssidObj.clear();
        ssidObj.setText("SUNMITEST");
        UiObject2 pwdObj = device.findObject(By.res("com.android.settings:id/password"));
        pwdObj.clear();
        pwdObj.setText("12345678");
        UiObject2 saveObj = device.findObject(By.res("android:id/button1"));
        saveObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 APInfoObj = device.findObject(By.text("SUNMITEST WPA2 PSK便携式WLAN热点"));
        Assert.assertNotNull("未找到设置的SUNMITEST便携式热点", APInfoObj);
    }

    //检查VPN可添加（T1）
    @Test
    public void test016CheckAddVPN() throws UiObjectNotFoundException {
        screenshotCap("after_enter");
        //进入到更多中找到VPN选项进入
        UiObject2 moreObj = device.findObject(By.text("更多"));
        moreObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 VPNObj = device.findObject(By.text("VPN"));
        VPNObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        //判断VPN下为空
        UiObject2 VPNListObj = device.findObject(By.res("android:id/list"));
        Assert.assertEquals("VPN列表下不为空", 0, VPNListObj.getChildCount());
        //点击新增VPN按钮
        UiObject2 addObj = device.findObject(By.res("com.android.settings:id/vpn_create"));
        addObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        UiObject2 confirmObj = device.findObject(By.res("android:id/button1"));
        confirmObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        //选择图案加密项
        UiObject2 picObj = device.findObject(By.text("图案"));
        picObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        //确定所要绘制的图案为倒L形
        TestUtils.drawLPattern();
        sleep(SHORT_SLEEP);
        UiObject2 goonObj = device.findObject(By.res("com.android.settings:id/footerRightButton"));
        goonObj.click();
        sleep(SHORT_SLEEP);
        TestUtils.drawLPattern();
        sleep(SHORT_SLEEP);
        UiObject2 confirmObj1 = device.findObject(By.res("com.android.settings:id/footerRightButton"));
        confirmObj1.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 finishObj = device.findObject(By.res("com.android.settings:id/next_button"));
        finishObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        //输入VPN名称和服务器地址
        UiObject2 VPNNameObj = device.findObject(By.res("com.android.settings:id/name"));
        UiObject2 serverObj = device.findObject(By.res("com.android.settings:id/server"));
        VPNNameObj.clear();
        VPNNameObj.setText("testVPN");
        serverObj.clear();
        serverObj.setText("p1.hk2.heyjump.com");
        UiObject2 saveVPNObj = device.findObject(By.res("android:id/button1"));
        saveVPNObj.click();
        sleep(SHORT_SLEEP);
        //检查添加的VPN存在
        UiObject2 testVPNObj = device.findObject(By.text("testVPN"));
        Assert.assertNotNull("添加的testVPN不存在", testVPNObj);
        //添加账户密码
        testVPNObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 userName = device.findObject(By.focused(true));
        userName.setText("Jzorrof");
        UiObject2 pwdObj = device.findObjects(By.clazz("android.widget.EditText")).get(1);
        pwdObj.setText("qscvhi$$!_");
        sleep(SHORT_SLEEP);
        UiObject2 linkObj = device.findObject(By.res("android:id/button1"));
        linkObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        device.findObject(By.res("android:id/list")).wait(Until.findObject(By.text("已连接")), LONG_WAIT);
        UiObject2 linkedObj = device.findObject(By.res("android:id/summary").text("已连接"));
        Assert.assertNotNull("没有找到已连接信息，判断连接失败", linkedObj);
//        device.executeShellCommand("ping www.google.com");
        device.pressBack();
        device.pressBack();
        UiScrollable settingsScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        settingsScroll.scrollTextIntoView("安全");
        UiObject2 securityObj = device.findObject(By.text("安全"));
        securityObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 lockStyleObj = device.findObject(By.text("屏幕锁定方式"));
        lockStyleObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        TestUtils.drawLPattern();
        sleep(SHORT_SLEEP);
        UiObject2 swipeObj = device.findObject(By.text("滑动"));
        swipeObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 moveObj = device.findObject(By.res("android:id/button1"));
        moveObj.clickAndWait(Until.newWindow(), LONG_WAIT);
    }

    @Test
    //检查显示下亮度可用(T1)
    public void test017CheckClickLightLevel() throws UiObjectNotFoundException {
        screenshotCap("after_enter");
        TestUtils.enterSettingsFirstLevelByName("显示");
        UiObject2 lightObj = device.findObject(By.text("亮度"));
        lightObj.click();
        sleep(SHORT_SLEEP);
        UiObject2 lightSeekBar = device.findObject(By.res("com.android.systemui:id/slider"));
        Assert.assertNotNull("未找到亮度的seekBar", lightSeekBar);
    }

    @Test
    //检查显示下休眠时间正确(T1)
    public void test018CheckSleepTimeOut() throws UiObjectNotFoundException {
        screenshotCap("after_enter");
        TestUtils.enterSettingsFirstLevelByName("显示");
        UiObject2 sleepTimeObj = device.findObject(By.text("休眠")).getParent().findObject(By.res("android:id/summary"));
        String sleepTime = sleepTimeObj.getText();
        Assert.assertEquals("期望是无操作1周后，而实际是" + sleepTime, "无操作1周后", sleepTime);
        sleep(SHORT_SLEEP);
        sleepTimeObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 sleepOpt = device.findObject(By.checked(true));
        String sleepTime1 = sleepOpt.getText();
        Assert.assertEquals("期望是1周，而实际是" + sleepTime1, "1周", sleepTime1);
        sleep(SHORT_SLEEP);
        UiObject2 fiveMinObj = device.findObject(By.text("5分钟"));
        fiveMinObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 sleepTimeObj1 = device.findObject(By.text("休眠")).getParent().findObject(By.res("android:id/summary"));
        String sleepTime2 = sleepTimeObj1.getText();
        Assert.assertEquals("期望是无操作5分钟后，而实际是" + sleepTime, "无操作5分钟后", sleepTime2);
        sleep(SHORT_SLEEP);
        sleepTimeObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 oneWeekObj = device.findObject(By.text("1周"));
        oneWeekObj.click();
    }

    @Test
    //检查显示下字体大小正确(T1)
    public void test019CheckFont() throws UiObjectNotFoundException {
        screenshotCap("after_enter");
        TestUtils.enterSettingsFirstLevelByName("显示");
        UiObject2 fontObj = device.findObject(By.text("字体大小")).getParent().findObject(By.res("android:id/summary"));
        String fontSize = fontObj.getText();
        Assert.assertEquals("期望是正常，而实际是" + fontSize, "正常", fontSize);
        sleep(SHORT_SLEEP);
        fontObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 fontOpt = device.findObject(By.checked(true));
        String fontSize1 = fontOpt.getText();
        Assert.assertEquals("期望是正常，而实际是" + fontSize1, "正常", fontSize1);
        sleep(SHORT_SLEEP);
        UiObject2 hugeObj = device.findObject(By.text("超大"));
        hugeObj.clickAndWait(Until.newWindow(), LONG_WAIT);
        sleep(SHORT_SLEEP);
        UiObject2 fontObj1 = device.findObject(By.text("字体大小")).getParent().findObject(By.res("android:id/summary"));
        String fontSize2 = fontObj1.getText();
        Assert.assertEquals("期望是超大，而实际是" + fontSize2, "超大", fontSize2);
        sleep(SHORT_SLEEP);
        fontObj1.clickAndWait(Until.newWindow(), LONG_WAIT);
        UiObject2 normalObj = device.findObject(By.text("正常"));
        normalObj.click();
    }

    @Test
    //检查隐藏底部导航条开关状态(T1)
    public void test020CheckNavigateBarSwitch() throws UiObjectNotFoundException {
        screenshotCap("after_enter");
        TestUtils.enterSettingsFirstLevelByName("显示");
        UiObject2 hideBottomNavigateBarObj = device.findObject(By.text("隐藏底部导航条")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
        Boolean switchStatus = hideBottomNavigateBarObj.isChecked();
        Assert.assertFalse("隐藏底部导航条开关默认为开", switchStatus);
    }

    @Test
    //检查提示音和通知(T1)
    public void test021CheckTipsAndNotifFirstLevel() throws UiObjectNotFoundException {
        screenshotCap("after_enter");
        TestUtils.enterSettingsFirstLevelByName("提示音和通知");
        sleep(SHORT_SLEEP);
//        Pattern p = Pattern.compile("\\s");
        String[] tipAndNotfi = {"提示音和通知", "声音", "媒体音量", "通知音量", "免打扰", "默认通知铃声", "Pixie Dust", "其他提示音", "通知", "设备锁定时", "显示所有通知内容", "应用通知"};
        List<UiObject2> tipAndNotifList = device.findObjects(By.clazz("android.widget.TextView"));
        sleep(SHORT_SLEEP);
        for (int i = 0; i < tipAndNotifList.size(); i++) {
            Assert.assertEquals("期望是" + tipAndNotfi[i] + ",而实际是" + tipAndNotifList.get(i), tipAndNotfi[i], tipAndNotifList.get(i).getText());
            Log.v("myautotest", tipAndNotifList.get(i).getText());
        }
//        Iterator it = tipAndNotifList.iterator();
//        while(it.hasNext()){
//            Log.v("myautotest",it.next());
//        }
    }

    //搜索
    @Test
    public void test022Search() throws UiObjectNotFoundException {
        if("V1-B18".equals(Build.MODEL) || "P1".equals(Build.MODEL)){
            Log.v("myautotest1","233333333");
            UiObject2 searchBtnObj = device.findObject(By.res("com.android.settings:id/search"));
            searchBtnObj.clickAndWait(Until.newWindow(),LONG_WAIT);
            UiObject2 searchTextObj = device.findObject(By.focused(true));
            searchTextObj.setText("W");
            UiScrollable resultScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/list_results"));
            Boolean searched = resultScroll.scrollTextIntoView("WLAN");
            Assert.assertTrue("未搜索到要查找的WLAN",searched);
        }
    }

    //owner:wangshilin
    //【开关】无线网络
    @Test
    public void test023CheckWifiStatus(){

    }

    //owner:liuyang
    //连接指定无线网络
    @Test
    public void test024ConnectSpecifyAP(){

    }

    //owner:zhangruili
    //刷新可添加的无线网络
    @Test
    public void test025RefreshWifi(){

    }

    //owner:zhaizhongjie
    //手动添加无线网络
    @Test
    public void test026AddNetwork(){

    }

    //owner:wangshilin
    //【开关】启用无线通知
    @Test
    public void test027CheckWifiNotificationStatus(){

    }

    //owner:liuyang
    //【开关】随时都可扫描
    @Test
    public void test028CheckScanAlwaysStatus(){

    }

    //owner:zhangruili
    //【选项】休眠状态wlan连接策略
    @Test
    public void test029CheckWlanConnStrategy(){

    }

    //owner:zhaizhongjie
    //【开关】有线网络
    @Test
    public void test030CheckWiredStatus(){

    }

    //owner:wangshilin
    //有线网络设置
    @Test
    public void test031CheckWiredSetting(){

    }

    //owner:liuyang
    //【开关】蓝牙
    @Test
    public void test032CheckBTStatus(){

    }

    //owner:zhangruili
    //默认蓝牙设备名称
    @Test
    public void test033CheckDefaultBTName(){

    }

    //owner:zhaizhongjie
    //已配对的设备列表
    @Test
    public void test034CheckPairedDevice(){

    }

    //owner:wangshilin
    //修改已配对设备名称
    @Test
    public void test035ModifyPairedDeviceName(){

    }

    //owner:liuyang
    //取消已配对设备
    @Test
    public void test036CancelPairedDevice(){

    }

    //owner:zhangruili
    //重命名本设备蓝牙名称
    @Test
    public void test037RenameBTName(){

    }

    //owner:zhaizhongjie
    //刷新可用设备列表
    @Test
    public void test038RefreshBTDeviceList(){

    }

    //owner:wangshilin
    //【开关】NFC
    @Test
    public void test039CheckNFCStatus(){

    }

    //owner:liuyang
    //Android Beam
    @Test
    public void test040CheckAndroidBeamStatus(){

    }

    //owner:zhangruili
    //【开关】USB网络共享
    @Test
    public void test041CheckUSBNetworkSharedStatus(){

    }

    //owner:zhaizhongjie
    //【开关】便携式WLAN热点
    @Test
    public void test042CheckAPStatus(){

    }

    //owner:wangshilin
    //默认Wifi热点名称
    @Test
    public void test043CheckAPDefaultName(){

    }

    //owner:liuyang
    //【开关】蓝牙网络共享
    @Test
    public void test044CheckBTNetworkSharedStatus(){

    }

    //owner:zhangruili
    //【选项】网络共享与便携式热点设置
    @Test
    public void test045CheckAPSettings(){

    }

    //owner:zhaizhongjie
    //流量使用概览
    @Test
    public void test046CheckNetUsedStatus(){

    }

    //owner:wangshilin
    //【开关】飞行模式
    @Test
    public void test047CheckAirplaneStatus(){

    }

    //owner:liuyang
    //增删改VPN配置文件
    @Test
    public void test048AddVPN(){

    }

    //owner:zhangruili
    //【开关】移动网络
    @Test
    public void test049CheckMobileNetStatus(){

    }

    //owner:zhaizhongjie
    //默认亮度
    @Test
    public void test050CheckDefaultBrightness(){

    }

    //owner:wangshilin
    //【开关】自动调节屏幕亮度
    @Test
    public void test051CheckAutoBrightnessStatus(){

    }

    //owner:liuyang
    //【选项】静置休眠时间
    @Test
    public void test052SleepTimeoutValue(){

    }

    //owner:zhangruili
    //【开关】主动显示
    @Test
    public void test053CheckActiveshowStatus(){

    }

    //owner:zhaizhongjie
    //【选项】字体大小
    @Test
    public void test054CheckFontSizeValue(){

    }

    //owner:wangshilin
    //【开关】隐藏底部导航栏
    @Test
    public void test055CheckHideNavigateBarStatus(){

    }

    //owner:liuyang
    //【开关】位置信息开关
    @Test
    public void test056CheckLocationStatus(){

    }

    //owner:zhangruili
    //【选项】位置信息模式
    @Test
    public void test057CheckLocationMode(){

    }

    //owner:zhaizhongjie
    //【选项】屏幕锁定方式
    @Test
    public void test058CheckLockStylePass(){

    }

    //owner:wangshilin
    //锁定屏幕消息
    @Test
    public void test059CheckLockScreenMessage(){

    }

    //owner:liuyang
    //【开关】显示图案
    @Test
    public void test060CheckShowPattenStatus(){

    }

    //owner:zhangruili
    //【选项】自动锁定
    @Test
    public void test061CheckLockAutoStatus(){

    }

    //owner:zhaizhongjie
    //【开关】电源按钮即时锁定
    @Test
    public void test062CheckPowerKeyLockImmediateStatus(){

    }

    //owner:wangshilin
    //【开关】显示密码
    @Test
    public void test063CheckShowPasswordStatus(){

    }

    //owner:liuyang
    //【开关】允许安装未知来源应用
    @Test
    public void test064CheckAllowInstallUnknownStatus(){

    }

    //owner:zhangruili
    //【开关】应用权限
    @Test
    public void test065CheckAppPermissionStatus(){

    }

    //owner:zhaizhongjie
    //【选项】自动确定日期和时间
    @Test
    public void test066CheckAutoGetTimeValue(){

    }

    //owner:wangshilin
    //【开关】自动确定时区
    @Test
    public void test067CheckAutoGetTimeZoneStatus(){

    }

    //owner:liuyang
    //手动选择日期
    @Test
    public void test068ModifyDate(){

    }

    //owner:zhangruili
    //手动选择时间
    @Test
    public void test069ModifyTime(){

    }

    //owner:zhaizhongjie
    //手动选择时区
    @Test
    public void test070ModifyTimeZone(){

    }

    //owner:wangshilin
    //【开关】使用24小时格式
    @Test
    public void test071Check24HFormatStatus(){

    }

    //owner:liuyang
    //选择日期格式
    @Test
    public void test072CheckDateFormatValue(){

    }

    //owner:zhangruili
    //【选项】语言
    @Test
    public void test073CheckDefaultLanguage(){

    }

    //owner:zhaizhongjie
    //【选项】当前输入法
    @Test
    public void test074CheckDefaultInputMethod(){

    }

    //owner:wangshilin
    //默认情景模式
    @Test
    public void test075CheckDefaultSituation(){

    }

    //owner:liuyang
    //情景模式选择
    @Test
    public void test076CheckChooseOtherSituation(){

    }

    //owner:zhangruili
    //增加情景模式
    @Test
    public void test077AddSituation(){

    }

    //owner:zhaizhongjie
    //重置情景模式
    @Test
    public void test078ResetSituation(){

    }

    //owner:wangshilin
    //-【选项】媒体音量-【选项】闹钟音量-【选项】通知音量
    @Test
    public void test079CheckMediaClockNotifiStatus(){

    }

    //owner:liuyang
    //-【选项】设备铃声
    @Test
    public void test080CheckDeviceRingtoneValue(){

    }

    //owner:zhangruili
    //-【选项】通知提示音铃声
    @Test
    public void test081CheckDeviceNotificationSoundValue(){

    }

    //owner:zhaizhongjie
    //-【开关】震动
    @Test
    public void test082CheckVibrateStatus(){

    }

    //owner:wangshilin
    //-【开关】拨号键盘触摸音效
    @Test
    public void test083CheckDialpadTouchSoundStatus(){

    }

    //owner:liuyang
    //-【开关】触摸提示音
    @Test
    public void test084CheckTouchSoundStatus(){

    }

    //owner:zhangruili
    //-【开关】锁屏提示音
    @Test
    public void test085CheckScreenLockSoundStatus(){

    }

    //owner:zhaizhongjie
    //-【选项】紧急提示音
    @Test
    public void test086CheckEmergencypromptValue(){

    }

    //owner:wangshilin
    //【选项】打印浓度
    @Test
    public void test087CheckPrintLevelsValue(){

    }

    //owner:liuyang
    //原生打印功能
    @Test
    public void test088CheckOriginPrintPass(){

    }

    //owner:zhangruili
    //已下载应用列表视图
    @Test
    public void test089CheckAlreadyDownloadedList(){

    }

    //owner:zhaizhongjie
    //正在运行应用列表视图
    @Test
    public void test090CheckRunningAppsList(){

    }

    //owner:wangshilin
    //非系统应用列表视图
    @Test
    public void test091CheckNonSystemAppsList(){

    }

    //owner:liuyang
    //全部应用列表视图
    @Test
    public void test092CheckAllAppsList(){

    }

    //owner:zhangruili
    //已停用应用列表视图
    @Test
    public void test093CheckDisabledAppsList(){

    }

    //owner:zhaizhongjie
    //应用权限（权限控制列表）
    @Test
    public void test094CheckAppsPermmsionsList(){

    }

    //owner:wangshilin
    //存储使用概览
    @Test
    public void test095CheckStorageInfo(){

    }

    //owner:liuyang
    //内存使用概览
    @Test
    public void test096CheckRAMUsedInfo(){

    }

    //owner:zhangruili
    //应用使用内容列表
    @Test
    public void test097CheckRAMUsedByAppsList(){

    }

    //owner:zhaizhongjie
    //【开关】待机智能省电
    @Test
    public void test098CheckStandbySmartSavePowerStatus(){

    }

    //owner:wangshilin
    //【开关】电量百分比
    @Test
    public void test099CheckPowerPercentageStatus(){

    }

    //owner:liuyang
    //电池使用情况
    @Test
    public void test100CheckPowerUsedInfo(){

    }

    //owner:zhangruili
    //各应用使用电量视图
    @Test
    public void test101CheckPowerUsedByAppsList(){

    }

    //owner:zhaizhongjie
    //【开关】节电助手
    @Test
    public void test102CheckSaveAssistantStatus(){

    }

    //owner:wangshilin
    //【选项】节电助手启动时机
    @Test
    public void test103CheckSvaeAssistantStartValue(){

    }

    //owner:liuyang
    //可优化电池使用的应用列表
    @Test
    public void test104CheckOptimizableAppsList(){

    }

    //owner:zhangruili
    //【开关】定时开机【开关】定时关机
    @Test
    public void test105CheckAutoOnAndOffVaule(){

    }

    //owner:zhaizhongjie
    //【开关】开发者选项
    @Test
    public void test106CheckDeveloperOptionStatus(){

    }

    //owner:wangshilin
    //【开关】不锁定屏幕（充电亮屏）
    @Test
    public void test107CheckStayAwakeStatus(){

    }

    //owner:liuyang
    //【开关】OEM解锁
    @Test
    public void test108CheckOEMStatus(){

    }

    //owner:zhangruili
    //【开关】USB调试
    @Test
    public void test109CheckUSBStatus(){

    }

    //owner:zhaizhongjie
    //用户中心
    @Test
    public void test110CheckEnterUsercenter(){

    }

    //owner:wangshilin
    //自定义音量键
    @Test
    public void test111CheckDefineVolumeKey(){

    }

    //owner:liuyang
    //iBeacon
    @Test
    public void test112CheckIBeaconInfo(){

    }

    //owner:zhangruili
    //默认蓝牙打印支持APP
    @Test
    public void test113CheckBTSupportApp(){

    }

    //owner:zhaizhongjie
    //系统更新
    @Test
    public void test114CheckSystemUpdate(){

    }

    //owner:wangshilin
    //开发者指南
    @Test
    public void test115CheckDevGuide(){

    }

    //owner:liuyang
    //硬件信息
    @Test
    public void test116CheckHardwareInfo(){

    }

    //owner:zhangruili
    //状态信息
    @Test
    public void test117CheckStatusInfo(){

    }

    //owner:zhaizhongjie
    //法律信息
    @Test
    public void test118ChekcLawInfo(){

    }

    //owner:wangshilin
    //型号
    @Test
    public void test119CheckModel(){

    }

    //owner:liuyang
    //Android版本
    @Test
    public void test120CheckAndroidVersion(){

    }



    //检查音量自定义开启状态正确
    //检查音量自定义上音量键自定义可用
    //检查音量自定义下音量键自定义可用
    //提示音和通知默认情景模式正确
    //默认情景模式属性正确
    //检查情景模式可以更换
    //检查应用选项中存在系统中某一些应用
    //检查内部存储设备中各个选项可以对应进入
    //检查电池中电量百分比
    //检查节点助手开关默认状态正确
    //检查电池优化能够成功进入
    //检查能够进入到各个应用使用的内存
    //检查位置信息开关状态正确
    //检查位置信息模式正确
    //检查屏幕锁定方式各个方式可用
    //检查安全下各个开关的打开状态正确
    //检查用户中心可以登陆
    //检查语言和输入法下内容正确
    //检查备份和重置选项
    //检查日期和时间开关默认状态
    //检查定时开关机开关默认状态
    //检查无障碍开关默认状态
    //检查打印浓度
    //检查关于设备中信息
}
