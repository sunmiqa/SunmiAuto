

package com.sunmi.sunmiauto.testcases;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.telephony.CarrierConfigManager;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.internal.runners.statements.Fail;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sunmi.sunmiauto.testcategory.CategorySettingsTests;
import com.sunmi.sunmiauto.testutils.CommonAction;
import com.sunmi.sunmiauto.testutils.TestUtils;
import com.sunmi.sunmiauto.testutils.UiobjectFinder;

import javax.mail.internet.NewsAddress;

import static android.support.test.espresso.action.ViewActions.click;
import static com.sunmi.sunmiauto.testutils.TestConstants.LOG_V;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_BT_NAME;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_SETTINGSLIST;
import static com.sunmi.sunmiauto.testutils.TestConstants.SHORT_SLEEP;
import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.drawPattern;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;
import static com.sunmi.sunmiauto.testutils.TestConstants.V1_BT_NAME;
import static com.sunmi.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static com.sunmi.sunmiauto.testutils.TestConstants.NETWORKNAMETEST;
import static com.sunmi.sunmiauto.testutils.TestConstants.NETWORKPWDTEST;
import static com.sunmi.sunmiauto.testutils.TestConstants.V1_SETTINGSLIST;
import static com.sunmi.sunmiauto.testutils.TestConstants.USER_CENTER_PKG;

/**
 * Created by fengy on 2017/7/8.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiSettings {

    private long timeoutSeconds;

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

    @Test
    public void test001WiFi() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("setting_interface");
            UiObject2 wifiops = device.findObject(By.text("WLAN"));
            wifiops.clickAndWait(Until.newWindow(), LONG_WAIT);
            screenshotCap("wifi_interface");
            UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Assert.assertEquals("Wifi开关默认应该为打开状态", true, wifiButton.isChecked());
        }
    }

    @Test
    public void test002DataUsage() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("setting_interface");
            UiObject2 ethOps = device.findObject(By.text("流量使用情况"));
            ethOps.clickAndWait(Until.newWindow(), LONG_WAIT);
            screenshotCap("dataUsage_interface");
            UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况").clazz("android.widget.TextView"));
            Assert.assertNotNull("未找到流量使用情况标识", dataUsageObj);
        }
    }

    @Test
    public void test003OpenBT() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("after_enter");
            UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
            WlanObj.click();
            sleep(SHORT_SLEEP);
            screenshotCap("after_click");
            UiObject2 BTButtonObj = device.findObject(By.res("com.android.settings:id/switch_widget"));
            sleep(SHORT_SLEEP);
            Assert.assertTrue("蓝牙开关默认不是打开状态", BTButtonObj.isChecked());
        }
    }

    //检查WLAN中添加网络成功
    @Test
    public void test004AddNetwork() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
    }

    //检查设置中所有一级选项可以正常进入
    @Test
    public void test005CheckFirstLevel() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL)) {
            screenshotCap("after_enter");
            UiScrollable settingsScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            for (int i = 0; i < V1_SETTINGSLIST.size(); i++) {
                Boolean found = settingsScroll.scrollTextIntoView(V1_SETTINGSLIST.get(i));
                Assert.assertTrue("未找到" + V1_SETTINGSLIST.get(i), found);
                UiObject2 optObj = device.findObject(By.text(V1_SETTINGSLIST.get(i)));
                optObj.clickAndWait(Until.newWindow(), 5000);
                if (V1_SETTINGSLIST.get(i).equalsIgnoreCase("用户中心")) {
                    Assert.assertEquals("未找到" + V1_SETTINGSLIST.get(i), USER_CENTER_PKG, device.getCurrentPackageName());
                    device.pressBack();
                    continue;
                }
                if (V1_SETTINGSLIST.get(i).equalsIgnoreCase("音量键自定义")) {
                    UiObject2 volumeKeyObj = device.findObject(By.pkg("com.sunmi.sidekey").text("音量键自定义"));
                    Assert.assertNotNull("未找到" + V1_SETTINGSLIST.get(i), volumeKeyObj);
                    device.pressBack();
                    continue;
                }
                UiObject2 optEnterObj = device.findObject(By.clazz("android.widget.TextView").text(V1_SETTINGSLIST.get(i)));
                Assert.assertNotNull("未找到" + V1_SETTINGSLIST.get(i), optEnterObj);
                device.pressBack();
            }
        } else if ("P1".equals(Build.MODEL)) {
            screenshotCap("after_enter");
            UiScrollable settingsScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            for (int i = 0; i < P1_SETTINGSLIST.size(); i++) {
                Boolean found = settingsScroll.scrollTextIntoView(P1_SETTINGSLIST.get(i));
                Assert.assertTrue("未找到" + P1_SETTINGSLIST.get(i), found);
                UiObject2 optObj = device.findObject(By.text(P1_SETTINGSLIST.get(i)));
                optObj.clickAndWait(Until.newWindow(), 5000);
                if (P1_SETTINGSLIST.get(i).equalsIgnoreCase("用户中心")) {
                    Assert.assertEquals("未找到" + P1_SETTINGSLIST.get(i), USER_CENTER_PKG, device.getCurrentPackageName());
                    device.pressBack();
                    device.pressBack();
                    continue;
                }
                if (P1_SETTINGSLIST.get(i).equalsIgnoreCase("音量键自定义")) {
                    UiObject2 volumeKeyObj = device.findObject(By.pkg("com.sunmi.sidekey").text("音量键自定义"));
                    Assert.assertNotNull("未找到" + P1_SETTINGSLIST.get(i), volumeKeyObj);
                    device.pressBack();
                    continue;
                }
                if (P1_SETTINGSLIST.get(i).equalsIgnoreCase("存储设备和 USB")) {
                    UiObject2 storeObj = device.findObject(By.clazz("android.widget.TextView").text("内部存储设备"));
                    Assert.assertNotNull("未找到" + P1_SETTINGSLIST.get(i), storeObj);
                    device.pressBack();
                    continue;
                }

                UiObject2 optEnterObj = device.findObject(By.clazz("android.widget.TextView").text(P1_SETTINGSLIST.get(i)));
                Assert.assertNotNull("未找到" + P1_SETTINGSLIST.get(i), optEnterObj);
                device.pressBack();
            }
        }

    }

    //检查WLAN为打开状态
    @Test
    public void test006CheckWLANStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("setting_interface");
            UiObject2 wifiops = device.findObject(By.text("WLAN"));
            wifiops.clickAndWait(Until.newWindow(), LONG_WAIT);
            screenshotCap("wifi_interface");
            UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Assert.assertEquals("Wifi开关默认应该为打开状态", true, wifiButton.isChecked());
        }
    }

    //能够进入到WLAN高级菜单中
    @Test
    public void test007CheckEnterAdvPass() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
    }

    //检查蓝牙默认设备名称正确
    @Test
    public void test008CheckBTNamePASS() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
            sleep(SHORT_SLEEP);
            UiObject2 nameObj = device.findObject(By.focused(true));
            String BTName = nameObj.getText();
            if ("V1".equals(Build.MODEL)) {
                Assert.assertEquals("蓝牙名称不正确", V1_BT_NAME, BTName);
            } else if ("P1".equals(Build.MODEL)) {
                Assert.assertEquals("蓝牙名称不正确", P1_BT_NAME, BTName);
            }
        }
    }

    //检查重命名蓝牙设备名称成功
    @Test
    public void test009CheckBTRenamePASS() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
            sleep(SHORT_SLEEP);
            UiObject2 nameObj = device.findObject(By.focused(true));
            nameObj.clear();
            nameObj.setText("TESTV1");
            UiObject2 renameBtn = device.findObject(By.text("重命名"));
            renameBtn.clickAndWait(Until.newWindow(), LONG_WAIT);
            sleep(SHORT_SLEEP);
            UiObject2 moreOptBtn1 = device.findObject(By.desc("更多选项"));
            moreOptBtn1.click();
            sleep(SHORT_SLEEP);
            UiObject2 renameObj1 = device.findObject(By.text("重命名此设备"));
            renameObj1.clickAndWait(Until.newWindow(), LONG_WAIT);
            sleep(SHORT_SLEEP);
            UiObject2 nameObj1 = device.findObject(By.focused(true));
            String BTName = nameObj1.getText();
            Assert.assertEquals("蓝牙改名失败", "TESTV1", BTName);
            nameObj1.clear();
            if ("V1".equals(Build.MODEL)) {
                nameObj1.setText(V1_BT_NAME);
            } else if ("P1".equals(Build.MODEL)) {
                nameObj1.setText(P1_BT_NAME);
            }
            UiObject2 renameBtn1 = device.findObject(By.text("重命名"));
            renameBtn1.clickAndWait(Until.newWindow(), LONG_WAIT);
        }
    }

    //检查流量使用情况存在概览和WLAN选项
    @Test
    public void test010CheckDataUsage() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("after_enter");
            UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况"));
            dataUsageObj.clickAndWait(Until.newWindow(), LONG_WAIT);
            UiObject2 gernalObj = device.findObject(By.selected(true).text("概览"));
            Assert.assertNotNull("未找到概览tab", gernalObj);
            UiObject2 wlanObj = device.findObject(By.text("WLAN").res("android:id/title"));
            Assert.assertNotNull("未找到WLANtab", wlanObj);
        }
    }

    //检查更多中飞行模式开关状态正确
    @Test
    public void test011CheckAirPlaneStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("after_enter");
            UiObject2 moreObj = device.findObject(By.text("更多"));
            moreObj.clickAndWait(Until.newWindow(), LONG_WAIT);
            UiObject2 airplaneSwitch = device.findObject(By.text("飞行模式")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
            Boolean airplaneOpen = airplaneSwitch.isChecked();
            Assert.assertFalse("飞行模式不是关闭状态", airplaneOpen);
        }
    }

    //检查设置Ibeacon内容及开关是否打开(T1)
    @Test
    public void test012CheckIbeacon() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
    }

    //检查设置Ibeacon功能正常(T1)
    @Test
    public void test013CheckIbeaconSetting() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
    }

    //检查网络共享与便携热点界面下默认开关状态正确（T1）
    @Test
    public void test014CheckNetShareStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
    }

    //检查网络共享与便携热点可正常配置（T1）
    @Test
    public void test015CheckAP() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
    }

    //检查VPN可添加（T1）
    @Test
    public void test016CheckAddVPN() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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
    @Category(CategorySettingsTests.class)
    public void test022Search() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            Log.v("myautotest1", "233333333");
            UiObject2 searchBtnObj = device.findObject(By.res("com.android.settings:id/search"));
            searchBtnObj.clickAndWait(Until.newWindow(), LONG_WAIT);
            UiObject2 searchTextObj = device.findObject(By.focused(true));
            searchTextObj.setText("W");
            UiScrollable resultScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/list_results"));
            Boolean searched = resultScroll.scrollTextIntoView("WLAN");
            Assert.assertTrue("未搜索到要查找的WLAN", searched);
        }
    }

    //owner:wangshilin
    //【开关】无线网络
    @Test
    @Category(CategorySettingsTests.class)
    public void test023CheckWifiStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.findObject(By.text("WLAN")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("wlan界面");
            UiObject2 wifiStatusObj = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Assert.assertTrue("测试失败，WIFI开关默认不是开启状态", wifiStatusObj.isChecked());
        }

    }

    //owner:liuyang
    //连接指定无线网络
    @Test
    @Category(CategorySettingsTests.class)
    public void test024ConnectSpecifyAP() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("wifi_enter");
            device.findObject(By.text("WLAN")).clickAndWait(Until.newWindow(), 5000);

            screenshotCap("wifi_open");
            //如果WiFi为关闭则打开

            UiObject2 openQuick = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Boolean wifiSwitchStatus = openQuick.isChecked();
            if (!wifiSwitchStatus) {//感叹号表示如果为关闭就点击
                openQuick.click();
                Assert.assertEquals(true, openQuick.wait(Until.checked(true), 10000));
            }
            screenshotCap("wifi_enter");
            UiScrollable SettingScoll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            SettingScoll.scrollTextIntoView("SUNMI");
            device.findObject(By.text("SUNMI")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);

            UiObject2 showPwdObj = device.findObject(By.text("连接"));
            UiObject2 canclSaveObj = device.findObject(By.text("取消保存"));
            if (showPwdObj != null) {//不为空，表示存在
                UiObject2 passwordWIFI1 = device.findObject(By.res("com.android.settings:id/password"));
                passwordWIFI1.setText("sunmi388");
                sleep(2000);
                device.pressBack();
                sleep(1000);
                device.findObject(By.res("android:id/button1")).clickAndWait(Until.newWindow(), 5000);//点击连接

            } else if (canclSaveObj != null) {
                device.pressBack();
            }
            sleep(5000);
            CommonAction.clickByText("已连接");//调用节时点击
            screenshotCap("wifi_enter");
            UiObject2 wlan = device.findObject(By.text("SUNMI"));
            Assert.assertNotNull("测试失败，未连接到指定的无线网络",wlan);

            UiObject2 openQuick1 = device.findObject(By.res("com.android.settings:id/switch_widget"));
            if(openQuick1.isChecked() != wifiSwitchStatus){
                openQuick1.click();
            }

        }
    }

    //owner:zhangruili
    //刷新可添加的无线网络
    @Test
    @Category(CategorySettingsTests.class)
    public void test025RefreshWifi() {
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL))  {
            UiObject2 wifiops = device.findObject(By.text("WLAN"));//找到WLAN
            wifiops.clickAndWait(Until.newWindow(),timeoutSeconds);//点击WLAN
            sleep(2000);

            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(),5000);//点击更多选项
            device.waitForIdle(1000);
            device.findObject(By.text("刷新")).click();//点击刷新按钮
            sleep(2000);
            UiObject2 wifiPage = device.findObject(By.text("WLAN"));//检查WiFi页面
            Assert.assertNotNull("未找到WiFi页面",wifiPage);
        }

    }

    //owner:zhaizhongjie
    //手动添加无线网络
    @Test
    @Category(CategorySettingsTests.class)
    public void test026AddNetwork(){
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
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

    }

    //owner:wangshilin
    //【开关】启用无线通知
    @Test
    @Category(CategorySettingsTests.class)
    public void test027CheckWifiNotificationStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.findObject(By.text("WLAN")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.desc("更多选项")).click();
            sleep(1000);
            device.findObject(By.text("高级")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("高级界面");
            UiObject2 noticeObj = device.findObject(By.res("android:id/switchWidget"));
            Assert.assertTrue("测试失败，网络通知默认为关闭", noticeObj.isChecked());
        }
    }

    //owner:liuyang
    //【开关】随时都可扫描
    @Test
    @Category(CategorySettingsTests.class)
    public void test028CheckScanAlwaysStatus() {
        if ("V1".equals(Build.MODEL)) {
            screenshotCap("wifi_advanced");
            device.findObject(By.text("WLAN")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            device.findObject(By.desc("更多选项")).click();
            sleep(2000);
            device.findObject(By.text("高级")).clickAndWait(Until.newWindow(), 5000);

            sleep(2000);
            screenshotCap("wifi_advanced_1");
            UiObject2 openQuick = device.findObjects(By.res("android:id/switchWidget")).get(1);
            Assert.assertEquals("随时都可扫描开关默认为关闭", false, openQuick.isChecked());

        }

    }

    //owner:zhangruili
    //【选项】休眠状态wlan连接策略
    @Test
    @Category(CategorySettingsTests.class)
    public void test029CheckWlanConnStrategy() {
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {

            UiObject2 wifiops = device.findObject(By.text("WLAN"));//找到WLAN
            device.findObject(By.text("WLAN")).clickAndWait(Until.newWindow(),5000);//点击WLAN
            device.waitForIdle(10000);
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(),5000);//点击更多选项
            device.waitForIdle();
            screenshotCap("after_click");
            device.findObject(By.text("高级")).clickAndWait(Until.newWindow(),5000);//点击高级
            device.waitForIdle();
            UiObject2 sleepObj = device.findObject(By.text("在休眠状态下保持WLAN网络连接"));//检查是否显示“在休眠状态下保持WLAN网络连接”功能
            Assert.assertNotNull("未找到在休眠状态下保持WLAN网络连接标识",sleepObj);
            UiObject2 alwaysObj = device.findObject(By.text("始终").clazz("android.widget.TextView"));//检查默认显示始终
            Assert.assertNotNull("未找到始终标识",alwaysObj);
            device.pressBack();//点击返回
            device.waitForIdle();
        }

    }

    //owner:zhaizhongjie
    //【开关】有线网络
    @Test
    @Category(CategorySettingsTests.class)
    public void test030CheckWiredStatus() {

    }

    //owner:wangshilin
    //有线网络设置
    @Test
    @Category(CategorySettingsTests.class)
    public void test031CheckWiredSetting() {

    }

    //owner:liuyang
    //【开关】蓝牙
    @Test
    @Category(CategorySettingsTests.class)
    public void test032CheckBTStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("BT_status");
            device.findObject(By.text("蓝牙")).clickAndWait(Until.newWindow(), 5000);
            sleep(5000);

            if ("V1".equals(Build.MODEL)){

                UiObject2 BT = device.findObject(By.res("com.android.settings:id/switch_widget"));
                Assert.assertTrue("测试失败，蓝牙开关默认是关闭状态", BT.isChecked());
            }else if("P1".equals(Build.MODEL)) {
                screenshotCap("BT_status_1");
                UiObject2 BT = device.findObject(By.res("com.android.settings:id/switch_widget"));
                Assert.assertFalse("测试失败，蓝牙开关默认是开启状态", BT.isChecked());
            }

        }


    }

    //owner:zhangruili
    //默认蓝牙设备名称
    @Test
    @Category(CategorySettingsTests.class)
    public void test033CheckDefaultBTName() {
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.findObject(By.text("蓝牙")).clickAndWait(Until.newWindow(), 5000);//点击蓝牙
            sleep(2000);
            screenshotCap("after_enter");//进入后截图
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(),5000);
            device.findObject(By.text("重命名此设备")).clickAndWait(Until.newWindow(),5000);
            sleep(2000);
            if("V1".equals(Build.MODEL)) {
                UiObject2 nameObj = device.findObject(By.text("V1").clazz("android.widget.EditText"));
                Assert.assertNotNull("蓝牙设备名称默认不是V1",nameObj);
            }
            else
            {
                UiObject2 nameObj = device.findObject(By.text("Sunmi P1").clazz("android.widget.EditText"));
                Assert.assertNotNull("蓝牙设备名称默认不是Sunmi P1",nameObj);
            }
        }
    }

    //owner:zhaizhongjie
    //已配对的设备列表
    @Test
    @Category(CategorySettingsTests.class)
    public void test034CheckPairedDevice() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiObject2 bluetooth = device.findObject(By.text("蓝牙"));
            //点击蓝牙
            sleep(SHORT_SLEEP);
            bluetooth.click();
            sleep(SHORT_SLEEP);
            UiObject2 dayin = device.findObject(By.text("InnerPrinter"));
            Assert.assertNotNull("未找到打印机", dayin);


        }
    }

    //owner:wangshilin
    //修改已配对设备名称
    @Test
    @Category(CategorySettingsTests.class)
    public void test035ModifyPairedDeviceName() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.findObject(By.text("蓝牙")).clickAndWait(Until.newWindow(), 5000);
            sleep(3000);
            UiObject2 switchObj = device.findObject(By.res("com.android.settings:id/switch_widget"));
            screenshotCap("进入蓝牙界面");
            if (switchObj.isChecked() == false) {//判断蓝牙开关状态
                switchObj.click();
                sleep(3000);
            }
            device.findObject(By.text("InnerPrinter")).getParent().getParent().findObject(By.res("com.android.settings:id/deviceDetails")).click();
            sleep(1000);
            device.findObject(By.res("com.android.settings:id/name")).setText("测试重命名");
            sleep(1000);
            screenshotCap("修改蓝牙名称后");
            device.findObject(By.text("确定")).click();
            sleep(1000);
            screenshotCap("after_enter");
            UiObject2 testObj = device.findObject(By.text("测试重命名"));
            Assert.assertNotNull("测试失败，未找到修改后的蓝牙名", testObj);
            sleep(1000);
            device.findObject(By.text("测试重命名")).getParent().getParent().findObject(By.res("com.android.settings:id/deviceDetails")).click();
            sleep(1000);
            device.findObject(By.res("com.android.settings:id/name")).setText("InnerPrinter");
            sleep(1000);
            screenshotCap("重置蓝牙名称");
            device.findObject(By.text("确定")).click();
        }
    }

    //owner:liuyang
    //取消已配对设备
    @Test
    @Category(CategorySettingsTests.class)
    public void test036CancelPairedDevice() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("BT_Cancel match");
            device.findObject(By.text("蓝牙")).clickAndWait(Until.newWindow(), 5000);
            sleep(5000);

            UiObject2 openQuick = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Boolean btSwitchStatus = openQuick.isChecked();
            screenshotCap("BT_Cancel match_1");
            if (!btSwitchStatus) {//感叹号表示如果为关闭就点击
                openQuick.click();
                Assert.assertEquals(true, openQuick.wait(Until.checked(true), 10000));
            }
            sleep(5000);
            device.findObject(By.res("com.android.settings:id/deviceDetails")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);

            if ("P1".equals(Build.MODEL)) {device.pressBack();}//p1点击已配对的蓝牙时自动弹出输入框

            sleep(5000);
            UiObject2 cancel = device.findObject(By.text("取消保存"));
            cancel.clickAndWait(Until.newWindow(), timeoutSeconds);

            sleep(4000);
            UiScrollable Sliding = new UiScrollable(new UiSelector().resourceId("android:id/list"));//取主屏幕
            UiSelector selector = new UiSelector().text("InnerPrinter");//查找APP名称
            Sliding.scrollIntoView(selector);
            device.findObject(By.text("InnerPrinter")).clickAndWait(Until.newWindow(), 5000);

            UiScrollable Sliding1 = new UiScrollable(new UiSelector().resourceId("android:id/list"));//取主屏幕
            UiSelector selector1 = new UiSelector().text("InnerPrinter");//查找APP名称
            Sliding1.scrollIntoView(selector1);
            Sliding1.flingToBeginning(20);

            screenshotCap("BT_Cancel match_2");
            sleep(40000);
            UiObject2 pairedSetBtn = device.findObject(By.text("InnerPrinter")).getParent().getParent().findObject(By.res("com.android.settings:id/deviceDetails"));
            Assert.assertNotNull(pairedSetBtn);//
            sleep(2000);
            device.findObject(By.res("com.android.settings:id/switch_widget")).clickAndWait(Until.newWindow(), 5000);

            UiObject2 openQuick1 = device.findObject(By.res("com.android.settings:id/switch_widget"));
            if(openQuick1.isChecked() != btSwitchStatus){
                openQuick1.click();
            }
        }
    }

    //owner:zhangruili
    //重命名本设备蓝牙名称
    @Test
    @Category(CategorySettingsTests.class)
    public void test037RenameBTName() {
        if("V1".equals(Build.MODEL)|| "P1".equals(Build.MODEL)){
            UiObject2 BTObj = device.findObject(By.text("蓝牙"));//查找蓝牙
            BTObj.click();//点击蓝牙
            sleep(2000);
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(),5000);//点击更多选项
            sleep(2000);
            device.findObject(By.text("重命名此设备")).clickAndWait(Until.newWindow(),5000);//点击重命名此设备
            sleep(2000);
            UiObject2 nameObj= device.findObject(By.res("com.android.settings:id/edittext").clazz("android.widget.EditText"));//找到输入的内容
            nameObj.setText("hehe");//替换内容为hehe
            sleep(2000);
            device.findObject(By.text("重命名")).click();//点击重命名
            sleep(2000);
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(),5000);//点击更多选项
            sleep(2000);
            device.findObject(By.text("重命名此设备")).clickAndWait(Until.newWindow(),5000);//点击重命名此设备
            sleep(2000);
            UiObject2 name1Obj = device.findObject(By.text("hehe").clazz("android.widget.EditText"));//检查输入内容是否为hehe
            Assert.assertNotNull("未找到hehe标识",name1Obj);//找不到hehe标识则报错
            if("V1".equals(Build.MODEL)) {
                UiObject2 name1 = device.findObject(By.res("com.android.settings:id/edittext").clazz("android.widget.EditText"));
                name1Obj.setText("V1");
            }
            else {
                UiObject2 name1 = device.findObject(By.res("com.android.settings:id/edittext").clazz("android.widget.EditText"));
                name1Obj.setText("Sunmi P1");
            }
        }

    }

    //owner:zhaizhongjie
    //刷新可用设备列表
    @Test
    @Category(CategorySettingsTests.class)
    public void test038RefreshBTDeviceList() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiObject2 lanya = device.findObject(By.text("蓝牙"));
            //进入蓝牙页面
            sleep(SHORT_SLEEP);
            lanya.click();
            sleep(SHORT_SLEEP);
            UiObject2 gengduo = device.findObject(By.desc("更多选项"));
            //点击蓝牙上方的更多选项
            sleep(SHORT_SLEEP);
            gengduo.click();
            sleep(120000);
            UiObject2 shuaxin = device.findObject(By.text("刷新"));
            //点击更多里面的刷新
            sleep(SHORT_SLEEP);
            shuaxin.click();


        }
    }

    //owner:wangshilin
    //【开关】NFC
    @Test
    @Category(CategorySettingsTests.class)
    public void test039CheckNFCStatus() {

    }

    //owner:liuyang
    //Android Beam
    @Test
    @Category(CategorySettingsTests.class)
    public void test040CheckAndroidBeamStatus() {
        //P1、V1均无此功能
    }

    //owner:zhangruili
    //【开关】USB网络共享
    @Test
    @Category(CategorySettingsTests.class)
    public void test041CheckUSBNetworkSharedStatus() {
        if("V1".equals(Build.MODEL)|| "P1".equals(Build.MODEL)){
            device.findObject(By.text("更多")).clickAndWait(Until.newWindow(),5000);//点击更多
            sleep(2000);
            if("V1".equals(Build.MODEL)) {
                UiObject2 shareObj = device.findObject(By.text("网络共享与便携式热点"));
                Assert.assertNull("测试失败，存在网络共享与便携式热点",shareObj);
            }
            else {
                device.findObject(By.text("网络共享与便携式热点")).clickAndWait(Until.newWindow(),5000);//点击网络共享与便携式热点
                sleep(2000);
                UiObject2 shareButton = device.findObject(By.res("android:id/switchWidget"));//检查USB网络共享的开关
                Assert.assertEquals("网络共享与便携式热点开关默认为打开", false, shareButton.isChecked());
            }
        }

    }

    //owner:zhaizhongjie
    //【开关】便携式WLAN热点
    @Test
    @Category(CategorySettingsTests.class)
    public void test042CheckAPStatus(){
        if("P1".equals(Build.MODEL)) {
        UiObject2 options = device.findObject(By.text("更多"));
        //点击设置中的更多
        sleep(SHORT_SLEEP);
        options.click();
        sleep(SHORT_SLEEP);
        UiObject2 Portable = device.findObject(By.text("网络共享与便携式热点"));
        //点击更多页面中的网络共享与便携式热点
        sleep(SHORT_SLEEP);
        Portable.click();
        sleep(SHORT_SLEEP);
        UiObject2 WLANhot = device.findObject(By.text("WLAN 热点"));
        //点击WLAN热点
        sleep(SHORT_SLEEP);
        WLANhot.click();
        sleep(SHORT_SLEEP);
        UiObject2 WLANguanbi = device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertFalse("显示为开启", WLANguanbi.isChecked());

        }
        else if ("V1".equals(Build.MODEL)){

        }

    }

    //owner:wangshilin
    //默认Wifi热点名称
    @Test
    @Category(CategorySettingsTests.class)
    public void test043CheckAPDefaultName() {
        if ("P1".equals(Build.MODEL)) {
            device.findObject(By.text("更多")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("更多界面显示");
            device.findObject(By.text("网络共享与便携式热点")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.text("WLAN 热点")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.text("设置WLAN热点")).clickAndWait(Until.newWindow(), 5000);
            sleep(1000);
            UiObject2 wlanNameObj = device.findObject(By.text("SunmiP1"));
            screenshotCap("设置WLAN界面");
            Assert.assertNotNull("测试失败,默认名称不是SunmiP1", wlanNameObj);
        } else if ("V1".equals(Build.MODEL)) {

        }
    }

    //owner:liuyang
    //【开关】蓝牙网络共享
    @Test
    @Category(CategorySettingsTests.class)
    public void test044CheckBTNetworkSharedStatus() {
        if ("P1".equals(Build.MODEL)) {
            device.findObject(By.text("更多")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("More_interfacedisplay");
            device.findObject(By.text("网络共享与便携式热点")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("More_Bluetooth");
            UiObject2 openQuick = device.findObjects(By.res("android:id/switchWidget")).get(1);
            Assert.assertFalse("测试失败，蓝牙网络共享默认为开启", openQuick.isChecked());

        }
    }

    //owner:zhangruili
    //【选项】网络共享与便携式热点设置
    @Test
    @Category(CategorySettingsTests.class)
    public void test045CheckAPSettings() {
        if("V1".equals(Build.MODEL)|| "P1".equals(Build.MODEL)){
            device.findObject(By.text("更多")).clickAndWait(Until.newWindow(),5000);//点击更多
            if("V1".equals(Build.MODEL)) {
                UiObject2 setObj = device.findObject(By.text("网络共享与便携式热点设置"));
                Assert.assertNull("测试失败，显示了网络共享与便携式热点设置",setObj);
            }
            else {
                device.findObject(By.text("网络共享与便携式热点")).clickAndWait(Until.newWindow(),5000);//点击网络共享与便携式热点
                UiObject2 setObj = device.findObject(By.text("网络共享与便携式热点设置"));//查找网络共享与便携式热点设置
                Assert.assertNotNull("未找到网络共享与便携式热点设置标识", setObj);
                UiObject2 ipObj = device.findObject(By.text("IPv4").clazz("android.widget.TextView"));//检查默认值是否为IPv4
                Assert.assertNotNull("未找到IPv4标识", ipObj);
            }
        }


    }

    //owner:zhaizhongjie
    //流量使用概览
    @Test
    @Category(CategorySettingsTests.class)
    public void test046CheckNetUsedStatus(){
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiObject2 Anoverviewof = device.findObject(By.text("流量使用情况"));
            //点击设置中流量使用情况功能
            sleep(SHORT_SLEEP);
            Anoverviewof.click();
            sleep(SHORT_SLEEP);
            UiObject2 verviewof = device.findObject(By.text("概览"));
            Assert.assertNotNull("无概览功能", verviewof);
        }


    }

    //owner:wangshilin
    //【开关】飞行模式
    @Test
    @Category(CategorySettingsTests.class)
    public void test047CheckAirplaneStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.findObject(By.text("更多")).clickAndWait(Until.newWindow(), 5000);
            UiObject2 switchObj = device.findObject(By.res("android:id/switchWidget"));
            screenshotCap("更多界面显示");
            Assert.assertFalse("测试失败，飞行模式开关默认不是关闭", switchObj.isChecked());
        }

    }

    //owner:liuyang
    //增删改VPN配置文件
    @Test
    @Category(CategorySettingsTests.class)
    public void test048AddVPN() throws UiObjectNotFoundException {
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.findObject(By.text("更多")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            device.findObject(By.text("VPN")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            screenshotCap("More_VPN");
            if ("P1".equals(Build.MODEL)) {
                device.findObject(By.res("com.android.settings:id/vpn_create")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);
                UiObject2 Condensation = device.findObject(By.res("android:id/message"));
                sleep(2000);
            }
            device.findObject(By.text("确定")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            device.findObject(By.text("密码")).clickAndWait(Until.newWindow(), 5000);

            UiObject enterPassword = new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));
            //输入密码
            enterPassword.click();
            enterPassword.setText("L111111");
            sleep(2000);
            device.findObject(By.text("继续")).clickAndWait(Until.newWindow(), 5000);

            sleep(2000);
            UiObject enterPassword2 = new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));
            //输入密码
            enterPassword2.click();
            enterPassword2.setText("L111111");
            sleep(2000);
            device.findObject(By.text("确定")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            device.findObject(By.text("完成")).clickAndWait(Until.newWindow(), 5000);

            if ("V1".equals(Build.MODEL)) {
                device.findObject(By.res("com.android.settings:id/vpn_create")).clickAndWait(Until.newWindow(), 5000);
            }
            sleep(2000);
            UiObject name = new UiObject(new UiSelector().resourceId("com.android.settings:id/name"));
            //输入vpn名称
            name.click();
            name.setText("SUNMI");

            UiObject serverAddress = new UiObject(new UiSelector().resourceId("com.android.settings:id/server"));
            //输入服务器地址
            serverAddress.click();
            serverAddress.setText("p1.hk2.heyjump.com");

            device.findObject(By.text("保存")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);

            device.findObject(By.text("SUNMI")).clickAndWait(Until.newWindow(), 5000);

            UiObject username = new UiObject(new UiSelector().resourceId("com.android.settings:id/username"));
            //输入用户名
            username.click();
            username.setText("Jzorrof");

            UiObject password = new UiObject(new UiSelector().resourceId("com.android.settings:id/password"));
            //输入用户名
            password.click();
            password.setText("qscvhi$$!_");

            device.findObject(By.res("com.android.settings:id/save_login")).clickAndWait(Until.newWindow(), 5000);

            device.findObject(By.text("连接")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);

            screenshotCap("More_VPN_1");
            UiObject2 configurationFile = device.findObject(By.text("SUNMI"));
            Assert.assertNotNull("测试失败,未找到配置文件", configurationFile);

            if ("P1".equals(Build.MODEL)) {
                device.findObject( By.desc("设置")).clickAndWait( Until.newWindow(), 5000);

                sleep(2000);
                device.findObject( By.text("取消保存")).clickAndWait( Until.newWindow(), 5000);
                device.pressBack ();
                sleep(2000);
                device.pressBack ();
                sleep(2000);
            }
            else if ("V1".equals(Build.MODEL)){
                UiObject2 setting = device.findObject(By.text("SUNMI"));
                device.swipe(setting.getVisibleBounds().centerX(),setting.getVisibleBounds().centerY(),setting.getVisibleBounds().centerX(),setting.getVisibleBounds().centerY(),300);
                sleep(2000);
                device.findObject( By.text("删除配置文件")).clickAndWait( Until.newWindow(), 5000);
                sleep(2000);
//                CommonAction.clickByText("删除配置文件");//调用节时点击
                device.pressBack ();
                sleep(3000);
                device.pressBack ();
                sleep(3000);

            }

            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("安全");
            device.findObject(By.text("安全")).clickAndWait(Until.newWindow(), 5000);
            device.findObject( By.text("屏幕锁定方式")).clickAndWait( Until.newWindow(), 5000);
            UiObject confirmPassword = new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry"));
            //输入用户名
            confirmPassword.click();
            confirmPassword.setText("L111111");
            device.pressEnter();
            sleep(2000);
            device.findObject( By.text("滑动")).clickAndWait( Until.newWindow(), 5000);
            sleep(2000);

            if ("V1".equals(Build.MODEL)){
                device.findObject( By.text("确定")).clickAndWait( Until.newWindow(), 5000);
                sleep(2000);
            }else if("P1".equals(Build.MODEL)) {

                device.findObject( By.text("是，移除")).clickAndWait( Until.newWindow(), 5000);
                sleep(2000);
            }
            screenshotCap("More_VPN_1");
            UiObject2 lockScreenMode = device.findObject(By.text("滑动"));
            Assert.assertNotNull("测试失败,未找到滑动", lockScreenMode);
        }
    }

    //owner:zhangruili
    //【开关】移动网络
    @Test
    @Category(CategorySettingsTests.class)
    public void test049CheckMobileNetStatus() {
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)){
            device.findObject(By.text("更多")).clickAndWait(Until.newWindow(),5000);//找到更多
            UiObject2 netObj = device.findObject(By.text("移动网络"));//找到移动网络
            boolean a = netObj.isEnabled();
            Assert.assertFalse("移动网络没有被禁用",a);
        }

    }

    //owner:zhaizhongjie
    //默认亮度
    @Test
    @Category(CategorySettingsTests.class)
    public void test050CheckDefaultBrightness() {
        if ("P1".equals(Build.MODEL)) {
            UiObject2 According = device.findObject(By.text("显示"));
            //点击显示
            sleep(SHORT_SLEEP);
            According.click();
            sleep(SHORT_SLEEP);
            UiObject2 brightness = device.findObject(By.text("亮度"));
            //点击显示页面的亮度
            sleep(SHORT_SLEEP);
            brightness.click();
            sleep(SHORT_SLEEP);
            UiObject2 WlanNameObj = device.findObject(By.text("屏幕亮度"));
            screenshotCap("设置显示页面");
            Assert.assertNotNull("测试失败未弹出亮度显示", WlanNameObj);

        }
        else if ("V1".equals(Build.MODEL)){
            UiObject2 According = device.findObject(By.text("显示"));
            //点击显示
            sleep(SHORT_SLEEP);
            According.click();
            sleep(SHORT_SLEEP);
            UiObject2 brightness = device.findObject(By.text("亮度"));
            //点击显示页面的亮度
            sleep(SHORT_SLEEP);
            brightness.click();
            sleep(SHORT_SLEEP);
            UiObject2 WlanNameObj = device.findObject(By.desc("屏幕亮度"));
            screenshotCap("设置显示页面");
            Assert.assertNotNull("测试失败未弹出亮度显示", WlanNameObj);

        }
    }


    //owner:wangshilin
    //【开关】自动调节屏幕亮度
    @Test
    @Category(CategorySettingsTests.class)
    public void test051CheckAutoBrightnessStatus() {

    }

    //owner:liuyang
    //【选项】静置休眠时间
    @Test
    @Category(CategorySettingsTests.class)
    public void test052SleepTimeoutValue() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.findObject(By.text("显示")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            device.findObject(By.text("休眠")).clickAndWait(Until.newWindow(), 5000);

            if ("V1".equals(Build.MODEL)) {
                screenshotCap("Sleep_V1");
                UiObject2 Sleep = device.findObject(By.text("1分钟"));
                Assert.assertTrue("测试失败，默认显示不为1分钟", Sleep.isChecked());
                sleep(2000);
            } else if ("P1".equals(Build.MODEL)) {
                sleep(2000);
                screenshotCap("Sleep_P1");
                UiObject2 Sleep = device.findObject(By.text("永不"));
                Assert.assertTrue("测试失败，默认显示不为永不", Sleep.isChecked());

            }

        }
    }

    //owner:zhangruili
    //【开关】主动显示
    @Test
    @Category(CategorySettingsTests.class)
    public void test053CheckActiveshowStatus() {
        //无此功能

    }

    //owner:zhaizhongjie
    //【选项】字体大小
    @Test
    @Category(CategorySettingsTests.class)
    public void test054CheckFontSizeValue(){
        if ("P1".equals(Build.MODEL)) {
            UiObject2 display1 = device.findObject(By.text("显示"));
            //点击设置中的显示
            sleep(SHORT_SLEEP);
            display1.click();
            sleep(SHORT_SLEEP);
            UiObject2 Thefontsize = device.findObject(By.text("字体大小"));
            screenshotCap("设置显示页面");
            Assert.assertNotNull("无字体大小功能", Thefontsize);
        }



    }

    //owner:wangshilin
    //【开关】隐藏底部导航栏
    @Test
    @Category(CategorySettingsTests.class)
    public void test055CheckHideNavigateBarStatus() {

    }

    //owner:liuyang
    //【开关】位置信息开关
    @Test
    @Category(CategorySettingsTests.class)
    public void test056CheckLocationStatus() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            screenshotCap("location_information");
            UiScrollable SettingScoll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScoll.scrollTextIntoView("位置信息");
            device.findObject(By.text("位置信息")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("location_information_1");
            sleep(2000);
            UiObject2 information = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Assert.assertTrue("测试失败，位置信息默认为开启状态", information.isChecked());

        }
    }

    //owner:zhangruili
    //【选项】位置信息模式
    @Test
    @Category(CategorySettingsTests.class)
    public void test057CheckLocationMode() throws UiObjectNotFoundException {
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)){
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            //选择高级选项，往上滚动（在viewer上Scrollable必须为true才能有效)
            sleep(2000);
            if("V1".equals(Build.MODEL)) {
                //V1没有位置信息功能

            }
            else {
                UiSelector message1 = new UiSelector().text("位置信息");//查找音量键自定义
                scroll1.scrollIntoView(message1);
                device.findObject(By.text("位置信息")).clickAndWait(Until.newWindow(),5000);
                sleep(2000);

                UiObject2 moObj = device.findObject(By.text("模式"));
                Assert.assertNotNull("未找到模式标识", moObj);
                UiObject2 hiObj = device.findObject(By.text("高精确度").clazz("android.widget.TextView"));
                Assert.assertNotNull("未找到高精确度标识", hiObj);
            }
        }

    }

    //owner:zhaizhongjie
    //【选项】屏幕锁定方式
    @Test
    @Category(CategorySettingsTests.class)
    public void test058CheckLockStylePass() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            SettingScroll.scrollTextIntoView("安全");
            //找到安全
            UiObject2 security = device.findObject(By.text("安全"));
            sleep(SHORT_SLEEP);
            security.click();
            //点击安全
            sleep(SHORT_SLEEP);
            UiObject2 Lockscreenmode = device.findObject(By.text("滑动"));
            screenshotCap("设置安全页面");
            Assert.assertNotNull("默认方式不是滑动", Lockscreenmode);
        }




    }

    //owner:wangshilin
    //锁定屏幕消息
    @Test
    @Category(CategorySettingsTests.class)
    public void test059CheckLockScreenMessage() {

    }

    //owner:liuyang
    //【开关】显示图案
    @Test
    @Category(CategorySettingsTests.class)
    public void test060CheckShowPattenStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable SettingScoll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScoll.scrollTextIntoView("安全");
            device.findObject(By.text("安全")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("Safety");
            UiObject2 pattern = device.findObject(By.text("显示图案"));
            UiObject2 CancelSave = device.findObject(By.text("滑动"));
            if (pattern != null) {//不为空，表示存在
                screenshotCap("Safety_1");
                UiObject2 information = device.findObject(By.res("android:id/switchWidget"));
                Assert.assertTrue("测试失败，显示图案默认未开启", information.isChecked());

            } else if (CancelSave != null) {
                screenshotCap("Safety_2");
                device.findObject(By.text("屏幕锁定方式")).clickAndWait(Until.newWindow(), 5000);
                device.findObject(By.text("图案")).clickAndWait(Until.newWindow(), 5000);

                UiObject2 uu = device.findObject(By.res("com.android.settings:id/lockPattern"));
                int[] array = {1, 4, 7, 8, 9};
                drawPattern(uu, array);
                sleep(5000);
                device.findObject(By.res("com.android.settings:id/footerRightButton")).clickAndWait(Until.newWindow(), 5000);
                UiObject2 ii = device.findObject(By.res("com.android.settings:id/lockPattern"));
                int[] array2 = {1, 4, 7, 8, 9};
                drawPattern(uu, array);
                sleep(5000);
                device.findObject(By.res("com.android.settings:id/footerRightButton")).clickAndWait(Until.newWindow(), 5000);
                device.findObject(By.res("com.android.settings:id/next_button")).clickAndWait(Until.newWindow(), 5000);

                screenshotCap("Safety_3");
                UiObject2 information = device.findObject(By.res("android:id/switchWidget"));
                Assert.assertTrue("测试失败，显示图案默认未开启", information.isChecked());

                device.findObject(By.text("屏幕锁定方式")).clickAndWait(Until.newWindow(), 5000);
                UiObject2 vv = device.findObject(By.res("com.android.settings:id/lockPattern"));
                int[] array3 = {1, 4, 7, 8, 9};
                drawPattern(vv, array3);
                sleep(5000);
                device.findObject(By.text("滑动")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);

                if ("V1".equals(Build.MODEL)){

                    device.findObject(By.text("确定")).clickAndWait(Until.newWindow(), 5000);

                }else if("P1".equals(Build.MODEL)) {

                    device.findObject(By.text("是，移除")).clickAndWait(Until.newWindow(), 5000);
                    screenshotCap("Safety_4");

                }
            }
        }
    }

    //owner:zhangruili
    //【选项】自动锁定
    @Test
    @Category(CategorySettingsTests.class)
    public void test061CheckLockAutoStatus() throws UiObjectNotFoundException{
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable SettingScoll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScoll.scrollTextIntoView("安全");//滚动查找到安全
            device.findObject(By.text("安全")).clickAndWait(Until.newWindow(), 5000);//点击安全
            screenshotCap("安全");//截图
            UiObject2 pattern = device.findObject(By.text("显示图案"));//查找显示图案
            UiObject2 CancelSave = device.findObject(By.text("滑动"));//查找滑动
            if (pattern != null) {//不为空，表示存在
                UiObject2 information = device.findObject(By.res("android:id/switchWidget"));
                Assert.assertTrue("测试失败，显示图案默认未开启", information.isChecked());//显示图案不是未开启则测试失败
            } else if (CancelSave != null) {          //显示图案未开启则：
                device.findObject(By.text("屏幕锁定方式")).clickAndWait(Until.newWindow(), 5000);//找到屏幕锁定方式并点击直到新的窗口打开
                device.findObject(By.text("图案")).clickAndWait(Until.newWindow(), 5000);//找到图案并点击直到新的窗口打开

                UiObject2 uu = device.findObject(By.res("com.android.settings:id/lockPattern"));//找到绘制图案
                int[] array = {1, 4, 7, 8, 9};
                drawPattern(uu, array);//绘制图案
                sleep(5000);
                device.findObject(By.res("com.android.settings:id/footerRightButton")).clickAndWait(Until.newWindow(), 5000);//点击继续
                sleep(2000);
                UiObject2 ii = device.findObject(By.res("com.android.settings:id/lockPattern"));//找到绘制图案
                int[] array2 = {1, 4, 7, 8, 9};
                drawPattern(ii, array);
                sleep(5000);
                device.findObject(By.res("com.android.settings:id/footerRightButton")).clickAndWait(Until.newWindow(), 5000);//点击
                device.findObject(By.res("com.android.settings:id/next_button")).clickAndWait(Until.newWindow(), 5000);//点击确认
                UiObject2 information = device.findObject(By.res("android:id/switchWidget"));
                Assert.assertTrue("测试失败，显示图案默认未开启", information.isChecked());//如果显示图案未开启则测试失败

                UiObject2 autolockObj = device.findObject(By.text("自动锁定"));//找到自动锁定
                Assert.assertNotNull("未找到自动锁定标识", autolockObj);//未找到自动锁定标识则报错
                UiObject2 sleepObj = device.findObject(By.text("休眠5秒后").clazz("android.widget.TextView"));
                Assert.assertNotNull("未找到休眠5秒后标识", sleepObj);//未找到休眠5秒后标识则报错
            }
        }
    }

    //owner:zhaizhongjie
    //【开关】电源按钮即时锁定
    @Test
    @Category(CategorySettingsTests.class)
    public void test062CheckPowerKeyLockImmediateStatus() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("安全");
            //找到安全
            UiObject2 security1 = device.findObject(By.text("安全"));
            //点击安全
            sleep(SHORT_SLEEP);
            security1.click();
            sleep(SHORT_SLEEP);
            UiObject2 Screenlockmode = device.findObject(By.text("屏幕锁定方式"));
            sleep(SHORT_SLEEP);
            Screenlockmode.click();
            sleep(SHORT_SLEEP);
            UiObject2 pattern = device.findObject(By.text("图案"));
            //点击图案
            sleep(SHORT_SLEEP);
            pattern.click();
            sleep(SHORT_SLEEP);
            UiObject2 patternObj = device.findObject(By.res("com.android.settings:id/lockPattern"));
            //定位图案框
            int[] array = {1, 4, 7, 8, 9};
            TestUtils.drawPattern(patternObj, array);
            //输入图案
            sleep(SHORT_SLEEP);
            UiObject2 continu = device.findObject(By.text("继续"));
            //点击图案下方的继续
            sleep(SHORT_SLEEP);
            continu.click();
            sleep(SHORT_SLEEP);
            UiObject2 patternObj1 = device.findObject(By.res("com.android.settings:id/lockPattern"));
            //定位图案
            int[] user1 = {1, 4, 7, 8, 9};
            TestUtils.drawPattern(patternObj, array);
            //输入图案
            sleep(SHORT_SLEEP);
            UiObject2 confirm = device.findObject(By.text("确认"));
            //点击确认
            sleep(SHORT_SLEEP);
            confirm.click();
            sleep(SHORT_SLEEP);
            UiObject2 Tocomplete = device.findObject(By.text("完成"));
            //点击完成
            sleep(SHORT_SLEEP);
            Tocomplete.click();
            sleep(SHORT_SLEEP);
            UiObject2 Thepowerbutton = device.findObject(By.text("电源按钮即时锁定"));
            //选择电源按钮即时锁定
            Assert.assertFalse("测试失败，显示图案默认未开启", Thepowerbutton.isChecked());
        }

    }

    //owner:wangshilin
    //【开关】显示密码
    @Test
    @Category(CategorySettingsTests.class)
    public void test063CheckShowPasswordStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable settingScoll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScoll.scrollTextIntoView("安全");
            device.findObject(By.text("安全")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("安全界面");
            UiScrollable switchScoll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            switchScoll.scrollTextIntoView("显示密码");
            UiObject2 switchObj = device.findObjects(By.res("android:id/switchWidget")).get(0);
            Assert.assertTrue("测试失败，显示密码默认不是打开", switchObj.isChecked());

        }
    }

    //owner:liuyang
    //【开关】允许安装未知来源应用
    @Test
    @Category(CategorySettingsTests.class)
    public void test064CheckAllowInstallUnknownStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("Safety_UnknownSource");
            UiScrollable SettingScoll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScoll.scrollTextIntoView("安全");
            device.findObject(By.text("安全")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("安全");
            sleep(2000);

            screenshotCap("Safety_UnknownSource_1");
            UiScrollable Sliding1 = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            UiSelector Unknown = new UiSelector().text("未知来源");
            Sliding1.scrollIntoView(Unknown);

            sleep(2000);
            screenshotCap("Safety_UnknownSource_2");
            UiObject2 openQuick = device.findObject(By.text("未知来源")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
            Assert.assertTrue("测试失败，未知来源默认为关闭状态", openQuick.isChecked());


        }
    }

    //owner:zhangruili
    //【开关】应用权限
    @Test
    @Category(CategorySettingsTests.class)
    public void test065CheckAppPermissionStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)){
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            //选择高级选项,往上滚动（在viewer上Scrollable必须为true才能有效）
            UiSelector message1 = new UiSelector().text("安全");
            scroll1.scrollIntoView(message1);//向上滚动查找到安全
            device.findObject(By.text("安全")).clickAndWait(Until.newWindow(),5000);//点击安全
            sleep(2000);
            if("V1".equals(Build.MODEL)) {
                //V1没有应用权限功能
            }
            else {
                UiObject2 authorityButton = device.findObject(By.res("android:id/switchWidget"));
                Assert.assertEquals("应用权限默认是关闭状态", true, authorityButton.isChecked());//应用权限开关默认是关闭状态
            }
        }
    }

    //owner:zhaizhongjie
    //【选项】自动确定日期和时间
    @Test
    @Category(CategorySettingsTests.class)
    public void test066CheckAutoGetTimeValue() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable Datetime = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            Datetime.scrollTextIntoView("日期和时间");
            sleep(SHORT_SLEEP);
            UiObject2 time = device.findObject(By.text("日期和时间"));
            //点击日期和时间
            sleep(SHORT_SLEEP);
            time.click();
            sleep(SHORT_SLEEP);
            UiObject2 zidong = device.findObject(By.text("使用网络提供时间"));
            //找到使用网络提供时间
            Assert.assertNotNull("默认不是使用网络提供时间", zidong);
        }


    }

    //owner:wangshilin
    //【开关】自动确定时区
    @Test
    @Category(CategorySettingsTests.class)
    public void test067CheckAutoGetTimeZoneStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("日期和时间");
            device.findObject(By.text("日期和时间")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("日期和时间界面");
            UiScrollable dateScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            dateScroll.scrollTextIntoView("自动确定日期和时间");
            UiObject2 dataObj = device.findObjects(By.res("android:id/switchWidget")).get(0);
            Assert.assertTrue("测试失败，自动确定时区开关默认不是打开", dataObj.isChecked());
        }

    }

    //owner:liuyang
    //手动选择日期
    @Test
    @Category(CategorySettingsTests.class)
    public void test068ModifyDate() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("DateAndTime");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("日期和时间");
            device.findObject(By.text("日期和时间")).clickAndWait(Until.newWindow(), 5000);

            if ("V1".equals(Build.MODEL)) {
                screenshotCap("DateAndTime_1");
                UiObject2 date = device.findObject(By.text("设置日期"));
                Assert.assertFalse("测试失败，默认可以设置日期时间", date.isEnabled());

                device.findObject(By.text("自动确定日期和时间")).clickAndWait(Until.newWindow(), 5000);
                device.findObject(By.text("关闭")).clickAndWait(Until.newWindow(), 5000);
                sleep(SHORT_SLEEP);

                screenshotCap("DateAndTime_2");
                UiObject2 date2 = device.findObject(By.text("设置日期"));
                Assert.assertTrue("测试失败，不可以设置日期时间", date2.isEnabled());

                device.findObject(By.text("自动确定日期和时间")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);
                device.findObject(By.text("使用网络提供时间")).clickAndWait(Until.newWindow(), 5000);


            } else if ("P1".equals(Build.MODEL)) {

                UiObject2 date = device.findObject(By.text("设置日期"));
                screenshotCap("Set the date");
                Assert.assertFalse("测试失败，默认可以设置日期时间", date.isEnabled());
                sleep(SHORT_SLEEP);
                device.findObject(By.text("自动确定日期和时间")).clickAndWait(Until.newWindow(), 5000);
                sleep(SHORT_SLEEP);
                UiObject2 date2 = device.findObject(By.text("关闭"));
                Assert.assertNull("测试失败，可以关闭自动确认日期和时间", date2);
            }
        }
    }

    //owner:zhangruili
    //手动选择时间
    @Test
    @Category(CategorySettingsTests.class)
    public void test069ModifyTime() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            //选择高级选项,往上滚动（在viewer上Scrollable必须为true才能有效）
            UiSelector message1 = new UiSelector().text("日期和时间");//查找日期和时间
            scroll1.scrollIntoView(message1);
            device.findObject(By.text("日期和时间")).clickAndWait(Until.newWindow(), 5000);//点击日期和时间
            UiObject2 timeObj = device.findObject(By.text("设置时间"));
            boolean a = timeObj.isEnabled();
            Assert.assertFalse("设置时间没有被禁用", a);//检查设置时间没有被禁用则报错

        }
    }

    //owner:zhaizhongjie
    //手动选择时区
    @Test
    @Category(CategorySettingsTests.class)
    public void test070ModifyTimeZone() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("日期和时间");
            //找到日期和时间
            UiObject2 dates = device.findObject(By.text("日期和时间"));
            //点击日期和时间
            sleep(SHORT_SLEEP);
            dates.click();
            sleep(SHORT_SLEEP);
            UiObject2 guanbishqu1 = device.findObject(By.text("自动确定时区"));
            //关闭自动确定时区
            sleep(SHORT_SLEEP);
            guanbishqu1.click();
            sleep(SHORT_SLEEP);
            UiObject2 xiuanzeshqu = device.findObject(By.text("选择时区"));
            //点击修改时区
            sleep(SHORT_SLEEP);
            xiuanzeshqu.click();
            sleep(SHORT_SLEEP);
            UiObject2 xianggang = device.findObject(By.text("香港"));
            //点击香港
            sleep(SHORT_SLEEP);
            xianggang.click();
            sleep(SHORT_SLEEP);
            UiObject2 xianggangshjian = device.findObject(By.text("GMT+08:00 香港标准时间"));
            //找到使用网络提供时间
            Assert.assertNotNull("无法手动选择时区", xianggangshjian);
        }
    }

    //owner:wangshilin
    //【开关】使用24小时格式
    @Test
    @Category(CategorySettingsTests.class)
    public void test071Check24HFormatStatus() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("日期和时间");
            device.findObject(By.text("日期和时间")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("日期和时间界面截图");
            UiScrollable dataScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            dataScroll.scrollTextIntoView("使用24小时制");
            UiObject2 formatObj = device.findObjects(By.res("android:id/switchWidget")).get(1);
            Assert.assertTrue("测试失败，使用24小时制开关默认不是打开状态", formatObj.isChecked());
        }
        else if ("V1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("日期和时间");
            device.findObject(By.text("日期和时间")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("日期和时间界面截图");
            UiScrollable dataScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            dataScroll.scrollTextIntoView("使用24小时制");
            UiObject2 formatObj = device.findObjects(By.res("android:id/switchWidget")).get(1);
            Assert.assertFalse("测试失败，使用24小时制开关默认不是关闭状态", formatObj.isChecked());
        }

    }
    //owner:liuyang
    //选择日期格式
    @Test
    @Category(CategorySettingsTests.class)
    public void test072CheckDateFormatValue() throws UiObjectNotFoundException {
        if ( "P1".equals(Build.MODEL)) {

            screenshotCap("DateFormat");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("日期和时间");
            device.findObject(By.text("日期和时间")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.text("选择日期格式")).clickAndWait(Until.newWindow(), 5000);

            screenshotCap("DateFormat");
            UiObject2 FormatObj = device.findObjects(By.res("android:id/text1")).get(3);
            Assert.assertTrue("测试失败，默认日期格式不是年月日", FormatObj.isChecked());


        }
    }

    //owner:zhangruili
    //【选项】语言
    @Test
    @Category(CategorySettingsTests.class)
    public void test073CheckDefaultLanguage() throws UiObjectNotFoundException{
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)){
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            //选择高级选项，往上滚动（在viewer上Scrollable必须为true才能有效)
            sleep(2000);
            UiSelector message1 = new UiSelector().text("语言和输入法");//查找语言和输入法
            scroll1.scrollIntoView(message1);
            device.findObject(By.text("语言和输入法")).clickAndWait(Until.newWindow(),5000);//点击语言和输入法
            UiObject2 language = device.findObject(By.text("语言"));
            Assert.assertNull("测试失败，显示了语言选项",language);//显示语言选项则用例失败

        }
    }

    //owner:zhaizhongjie
    //【选项】当前输入法
    @Test
    @Category(CategorySettingsTests.class)
    public void test074CheckDefaultInputMethod() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("语言和输入法");
            //找到语言和输入法
            UiObject2 yuyan = device.findObject(By.text("语言和输入法"));
            //点击语言和输入法
            sleep(SHORT_SLEEP);
            yuyan.click();
            sleep(SHORT_SLEEP);
            UiObject2 baiduxiaomi = device.findObject(By.text("百度输入法小米版"));
            //找到默认输入法
            Assert.assertNotNull("默认不是百度输入法", baiduxiaomi);
        }
    }

    //owner:wangshilin
    //默认情景模式
    @Test
    @Category(CategorySettingsTests.class)
    public void test075CheckDefaultSituation() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("提示音和通知");
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("提示音和通知界面");
            UiObject2 modeObj = device.findObjects(By.res("com.android.settings:id/radiobutton")).get(0);
            Assert.assertTrue("测试失败，默认不是标准模式", modeObj.isChecked());

        }
    }
    //owner:liuyang
    //情景模式选择
    @Test
    @Category(CategorySettingsTests.class)
    public void test076CheckChooseOtherSituation() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("SceneMode");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("提示音和通知");
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(), 5000);

            device.findObject(By.text("静音")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("SceneMode_1");
            UiObject2 FormatObj = device.findObjects(By.res("com.android.settings:id/radiobutton")).get(1);
            Assert.assertTrue("测试失败，无法点击静音", FormatObj.isChecked());
            device.findObject(By.text("标准")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("SceneMode_2");

        }
    }

    //owner:zhangruili
    //增加情景模式
    @Test
    @Category(CategorySettingsTests.class)
    public void test077AddSituation() throws UiObjectNotFoundException{
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            //选择高级选项，往上滚动（在viewer上Scrollable必须为true才能有效)
            UiSelector message1 = new UiSelector().text("提示音和通知");//查找提示音和通知
            scroll1.scrollIntoView(message1);
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(),5000);//点击提示音和通知
            sleep(2000);
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(),5000);//点击更多选项
            sleep(2000);
            device.findObject(By.text("添加情景模式")).clickAndWait(Until.newWindow(),50000);//点击添加情景设置
            sleep(2000);
            UiObject2 sceneObj = device.findObject(By.res("com.android.settings:id/edittext"));//查找输入栏
            sceneObj.setText("工作模式");//输入工作模式
            sleep(2000);
            device.findObject(By.text("确定")).click();//点击确定
            UiObject2 workObj = device.findObject(By.text("工作模式"));
            Assert.assertNotNull("未找到工作模式标识",workObj);//未找到工作模式则报错
        }

    }

    //owner:zhaizhongjie
    //重置情景模式
    @Test
    @Category(CategorySettingsTests.class)
    public void test078ResetSituation() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("提示音和通知");
            //找到提示音和通知
            UiObject2 Prompt = device.findObject(By.text("提示音和通知"));
            //点击提示音和通知
            sleep(SHORT_SLEEP);
            Prompt.click();
            sleep(SHORT_SLEEP);
            UiObject2 inform = device.findObject(By.desc("更多选项"));
            //点击提示音和通知上方的更多
            sleep(SHORT_SLEEP);
            inform.click();
            sleep(SHORT_SLEEP);
            UiObject2 mode = device.findObject(By.text("模式重设"));
            //点击重设模式
            sleep(SHORT_SLEEP);
            mode.click();
            sleep(SHORT_SLEEP);
            UiObject2 determine = device.findObject(By.text("确定"));
            //点击确定重设模式
            sleep(SHORT_SLEEP);
            determine.click();
        }

    }

    //owner:wangshilin
    //-【选项】媒体音量-【选项】闹钟音量-【选项】通知音量

    @Test
    @Category(CategorySettingsTests.class)
    public void test079CheckMediaClockNotifiStatus() throws UiObjectNotFoundException {
//        if ("P1".equals(Build.MODEL)) {
//            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
//            SettingScroll.scrollTextIntoView("提示音和通知");
//            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(), 5000);
//            device.findObjects(By.res("android:id/list")).get(0).findObject(By.clazz("android.widget.LinearLayout")).findObject(By.res("com.android.settings:id/deviceDetails")).clickAndWait(Until.newWindow(),5000);
//
//
//        }
//        else if ("V1".equals(Build.MODEL)){
//
//        }
    }

    //owner:liuyang
    //-【选项】设备铃声
    @Test
    @Category(CategorySettingsTests.class)
    public void test080CheckDeviceRingtoneValue() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) ) {
            screenshotCap("SceneMode_ring");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("提示音和通知");
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.res("com.android.settings:id/deviceDetails")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.text("设备铃声")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("SceneMode_ring_1");
            UiObject2 equipmentRingtones = device.findObject(By.text("Flutey Phone"));
            Assert.assertTrue("测试失败，默认铃声不是Flutey Phone", equipmentRingtones.isChecked());
        }
    }

    //owner:zhangruili
    //-【选项】通知提示音铃声
    @Test
    @Category(CategorySettingsTests.class)
    public void test081CheckDeviceNotificationSoundValue() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            UiSelector message1 = new UiSelector().text("提示音和通知");//滚动查找提示音和通知
            scroll1.scrollIntoView(message1);
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(), 5000);//点击提示音和通知
            device.findObject(By.res("com.android.settings:id/deviceDetails")).clickAndWait(Until.newWindow(), 5000);//点击设置
            device.findObject(By.text("默认通知提示音")).clickAndWait(Until.newWindow(), 5000);//点击默认通知提示音
            UiObject2 cuetoneObj = device.findObject(By.checked(true).text("Pixie Dust"));
            Assert.assertEquals("默认铃声是Pixie Dust", true, cuetoneObj.isChecked());//检查默认铃声是Pixie Dust则通过
        }
    }

    //owner:zhaizhongjie
    //-【开关】震动
    @Test
    @Category(CategorySettingsTests.class)
    public void test082CheckVibrateStatus(){

    }

    //owner:wangshilin
    //-【开关】拨号键盘触摸音效
    @Test
    @Category(CategorySettingsTests.class)
    public void test083CheckDialpadTouchSoundStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("提示音和通知");
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap(",提示音通知界面");
            device.findObject(By.res("com.android.settings:id/deviceDetails")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("模式设置界面");
            UiObject2 soundObj = device.findObject(By.text("拨号键盘触摸音效")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
            Assert.assertTrue("测试失败，默认不是打开状态",soundObj.isChecked());

            }
            else if ("P1".equals(Build.MODEL)){

            }
    }



    //owner:liuyang
    //-【开关】触摸提示音
    @Test
    @Category(CategorySettingsTests.class)
    public void test084CheckTouchSoundStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("SceneMode_TouchTheTone");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("提示音和通知");
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.res("com.android.settings:id/deviceDetails")).clickAndWait(Until.newWindow(), 5000);
            if ("V1".equals(Build.MODEL)){
                screenshotCap("SceneMode_TouchTheTone_1");
                UiObject2 touchTone = device.findObjects(By.res("android:id/switchWidget")).get(1);
                Assert.assertFalse("测试失败，触摸提示音默认为开启",touchTone.isChecked());
            }else if("P1".equals(Build.MODEL)) {
                screenshotCap("SceneMode_TouchTheTone_2");
                UiObject2 touchTone = device.findObjects(By.res("android:id/switchWidget")).get(0);
                Assert.assertFalse("测试失败，触摸提示音默认为开启",touchTone.isChecked());
            }
        }
    }

    //owner:zhangruili
    //-【开关】锁屏提示音
    @Test
    @Category(CategorySettingsTests.class)
    public void test085CheckScreenLockSoundStatus() throws UiObjectNotFoundException{
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            //选择高级选项，往上滚动（在viewer上Scrollable必须为true才能有效)
            sleep(2000);
            UiSelector message1 = new UiSelector().text("提示音和通知");//查找提示音和通知
            scroll1.scrollIntoView(message1);
            device.findObject(By.text("提示音和通知")).clickAndWait(Until.newWindow(),5000);//点击提示音和通知

            device.findObject(By.res("com.android.settings:id/deviceDetails")).clickAndWait(Until.newWindow(),5000);
            if ("V1".equals(Build.MODEL)) {
                UiScrollable scroll2 = new UiScrollable(new UiSelector().className("android.widget.ListView"));
                UiSelector message2 = new UiSelector().text("锁屏提示音");
                scroll2.scrollIntoView(message2);
                UiObject2 lockObj = device.findObject(By.text("锁屏提示音"));
                UiObject2 lockButton = lockObj.getParent().getParent().findObject(By.res("android:id/switchWidget"));
                Assert.assertEquals("锁屏提示音默认为打开状态",true,lockButton.isChecked());
            }
            else {
                UiObject2 lockObj = device.findObject(By.text("锁屏提示音"));
                UiObject2 lockButton = lockObj.getParent().getParent().findObject(By.res("android:id/switchWidget"));
                Assert.assertEquals("锁屏提示音默认为打开状态", true, lockButton.isChecked());
            }
        }

    }

    //owner:zhaizhongjie
    //-【选项】紧急提示音
    @Test
    @Category(CategorySettingsTests.class)
    public void test086CheckEmergencypromptValue(){

    }

    //owner:wangshilin
    //【选项】打印浓度
    @Test
    @Category(CategorySettingsTests.class)
    public void test087CheckPrintLevelsValue() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("打印");
            device.findObject(By.text("打印")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("打印界面截图");
            device.findObject(By.text("内置打印管理")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("内置打印管理界面");
            UiObject2 valueObj = device.findObject(By.text("1.10"));
            Assert.assertNotNull("测试失败，打印浓度默认不是“1.10”",valueObj);
        }
        else if ("V1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("打印");
            device.findObject(By.text("打印")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("打印界面截图");
            device.findObject(By.text("内置打印管理")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("内置打印管理界面");
            UiObject2 valueObj = device.findObject(By.text("110%"));
            Assert.assertNotNull("测试失败，打印浓度默认不是“110%”",valueObj);

        }

    }

    //owner:liuyang
    //原生打印功能
    @Test
    @Category(CategorySettingsTests.class)
    public void test088CheckOriginPrintPass() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("SceneMode_Native print");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("打印");
            device.findObject(By.text("打印")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.text("外置打印管理")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("SceneMode_Native print_1");
            UiObject2 print = device.findObject(By.res("com.android.settings:id/icon"));
            Assert.assertNotNull("测试失败，不显示打印", print);
        }
    }
    //owner:zhangruili
    //已下载应用列表视图
    @Test
    @Category(CategorySettingsTests.class)
    public void test089CheckAlreadyDownloadedList() throws UiObjectNotFoundException{
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if ("V1".equals(Build.MODEL)) {
                UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                UiSelector message1=new UiSelector().text("应用程序管理");//查找应用程序管理
                scroll1.scrollIntoView(message1);
                device.findObject(By.text("应用程序管理")).clickAndWait(Until.newWindow(),5000);//点击应用程序管理
                UiObject2 downloadObj = device.findObject(By.text("已下载"));//检查是否存在已下载页面
                Assert.assertNotNull("未找到已下载页面",downloadObj);
            }
            else {
                //P1无此功能
            }
        }

    }

    //owner:zhaizhongjie
    //正在运行应用列表视图
    @Test
    @Category(CategorySettingsTests.class)
    public void test090CheckRunningAppsList() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) ) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("应用程序管理");
            //找到应用程序管理
            UiObject2 management = device.findObject(By.text("应用程序管理"));
            //点击应用程序管理
            sleep(SHORT_SLEEP);
            management.click();
            sleep(SHORT_SLEEP);
            UiObject2 run = device.findObject(By.text("正在运行"));
            //点击正在运行
            sleep(SHORT_SLEEP);
            run.click();
            sleep(6000);
            UiObject2 shyong = device.findObject(By.text("应用内存使用情况"));
            //sleep(7000);
            Assert.assertNotNull("正在运行页面无信息", shyong);
        }

    }

    //owner:wangshilin
    //非系统应用列表视图
    @Test
    @Category(CategorySettingsTests.class)
    public void test091CheckNonSystemAppsList() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) ) {

        }
        else if ("P1".equals(Build.MODEL)){
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("应用");
            device.findObject(By.text("应用")).clickAndWait(Until.newWindow(),5000);
            sleep(2000);
            screenshotCap("应用界面显示");
            int size = device.findObject(By.res("android:id/list")).findObjects(By.clazz("android.widget.LinearLayout")).size(); //获取非系统应用数量
            Assert.assertNotEquals(0,size);
        }
    }

    //owner:liuyang
    //全部应用列表视图
    @Test
    @Category(CategorySettingsTests.class)
    public void test092CheckAllAppsList() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL)) {
            screenshotCap("ApplicationList_All");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView ("应用程序管理");
            device.findObject(By.text("应用程序管理")).clickAndWait(Until.newWindow(), 5000);

            UiScrollable application = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/pager"));
            UiSelector selector = new UiSelector().text("全部");
            application.setAsHorizontalList();//设置为水平列表
            application.scrollTextIntoView("全部");
            application.scrollForward();//向←滑动

            screenshotCap("ApplicationList_All_1");
            UiObject2 print = device.findObject(By.text("全部"));
            Assert.assertNotNull("测试失败，不显示打印", print);

        } else if ("P1".equals(Build.MODEL)) {
            screenshotCap("ApplicationList_All_2");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView ("应用");
            device.findObject(By.text("应用")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            screenshotCap("ApplicationList_All_3");
            UiObject2 Displays = device.findObject(By.text("显示系统进程"));//默认显示系统进程
            Assert.assertNotNull("测试失败，找不到显示系统进程", Displays);
            device.pressBack ();

            sleep(2000);
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            device.findObject(By.text("显示系统进程")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            device.findObject(By.desc("更多选项")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            screenshotCap("ApplicationList_All_4");
            UiObject2 Displays2 = device.findObject(By.text("隐藏系统进程"));//默认显示系统进程
            Assert.assertNotNull("测试失败，找不到隐藏系统进程", Displays2);

        }
    }
    //owner:zhangruili
    //已停用应用列表视图
    @Test
    @Category(CategorySettingsTests.class)
    public void test093CheckDisabledAppsList() throws UiObjectNotFoundException{
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if ("V1".equals(Build.MODEL)) {
                UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                UiSelector message1 = new UiSelector().text("应用程序管理");//查找音量键自定义
                scroll1.scrollIntoView(message1);
                device.findObject(By.text("应用程序管理")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);
                UiScrollable application = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/pager"));
                UiSelector selector = new UiSelector().text("全部");
                application.setAsHorizontalList();//设置为水平列表
                application.scrollTextIntoView("全部");
                application.scrollForward();//向←滑动
                application.scrollForward();//向←滑动
                sleep(2000);
                UiObject2 stopObj = device.findObject(By.text("已停用"));
                Assert.assertNotNull("未找到已停用标识",stopObj);
                sleep(2000);
                device.findObject(By.clazz("android.widget.GridLayout")).clickAndWait(Until.newWindow(),5000);
                UiObject2 stop1 = device.findObject(By.text("启用"));
                Assert.assertNotNull("未找到启用标识",stop1);
            }
            else {
                UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                UiSelector message1 = new UiSelector().text("应用");//查找音量键自定义
                scroll1.scrollIntoView(message1);
                device.findObject(By.text("应用")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);
                device.findObject(By.text("计算器")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);
                device.findObject(By.text("停用")).clickAndWait(Until.newWindow(), 5000);
                device.findObject(By.text("停用应用")).click();
                device.pressBack();
                device.waitForIdle(1000);
                device.findObject(By.res("com.android.settings:id/filter_spinner")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);
                device.findObject(By.text("已停用")).click();
                sleep(2000);
                UiObject2 stopObj = device.findObject(By.text("计算器"));
                Assert.assertNotNull("未找到计算器标识", stopObj);
                device.findObject(By.text("计算器")).clickAndWait(Until.newWindow(), 5000);
                device.findObject(By.text("启用")).click();
            }
        }

    }

    //owner:zhaizhongjie
    //应用权限（权限控制列表）
    @Test
    @Category(CategorySettingsTests.class)
    public void test094CheckAppsPermmsionsList(){

    }

    //owner:wangshilin
    //存储使用概览
    @Test
    @Category(CategorySettingsTests.class)
    public void test095CheckStorageInfo() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) ) {

        }
        else if ("P1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("存储设备和 USB");
            device.findObject(By.text("存储设备和 USB")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("内部存储设备界面");
            UiScrollable storageScorll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            ArrayList<String> Stroagelist = new ArrayList();
            Stroagelist.add("应用");
            Stroagelist.add("图片");
            Stroagelist.add("视频");
            Stroagelist.add("音频");
            Stroagelist.add("其他");
            Stroagelist.add("缓存数据");
            Stroagelist.add("浏览");
            for (int i = 0; i < Stroagelist.size(); i++) {
                storageScorll.scrollTextIntoView(Stroagelist.get(i));
                UiObject2 menuobj = device.findObject(By.text(Stroagelist.get(i)));
                Assert.assertNotNull("未找到"+Stroagelist.get(i)+"测试失败",menuobj);
            }


        }


    }

    //owner:liuyang
    //内存使用概览
    @Test
    @Category(CategorySettingsTests.class)
    public void test096CheckRAMUsedInfo() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            screenshotCap("RAM_Overview");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("内存");
            device.findObject(By.text("内存")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("内存");
            screenshotCap("RAM_Overview_1");
            UiObject2 format = device.findObject(By.text("平均内存使用量"));
            Assert.assertNotNull("测试失败，找不到平均内存使用量", format);
        }
    }
    //owner:zhangruili
    //应用使用内存列表
    @Test
    @Category(CategorySettingsTests.class)
    public void test097CheckRAMUsedByAppsList()  throws UiObjectNotFoundException{
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            screenshotCap("setting_interface");
            if ("V1".equals(Build.MODEL)) {
                //V1无存储设备和USB功能
            }
            else{
                UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                sleep(2000);
                UiSelector message1 = new UiSelector().text("存储设备和 USB");//查找存储设备和USB
                scroll1.scrollIntoView(message1);

                device.findObject(By.text("存储设备和 USB")).clickAndWait(Until.newWindow(), 10000);//点击存储设备和USB
                device.findObject(By.text("应用")).clickAndWait(Until.newWindow(), 5000);//点击应用
                UiObject2 storagePage = device.findObject(By.text("应用存储空间"));
                Assert.assertNotNull("未找到应用存储空间页面", storagePage);
            }
        }

    }

    //owner:zhaizhongjie
    //【开关】待机智能省电
    @Test
    @Category(CategorySettingsTests.class)
    public void test098CheckStandbySmartSavePowerStatus(){

    }

    //owner:wangshilin
    //【开关】电量百分比
    @Test
    @Category(CategorySettingsTests.class)
    public void test099CheckPowerPercentageStatus() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("电池");
            device.findObject(By.text("电池")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("电池界面显示");
            UiObject2 switchObj = device.findObject(By.res("android:id/checkbox"));
            Assert.assertTrue("测试失败，默认未打开电量百分比",switchObj.isChecked());
        }
        else if ("V1".equals(Build.MODEL)){

        }

    }

    //owner:liuyang
    //电池使用情况
    @Test
    @Category(CategorySettingsTests.class)
    public void test100CheckPowerUsedInfo() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            screenshotCap("battery_UseSituation");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("电池");
            device.findObject(By.text("电池")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("battery");
            screenshotCap("battery_UseSituation_1");
            UiObject2 situation = device.findObject(By.res("com.android.settings:id/battery_history_chart"));
            Assert.assertNotNull("测试失败，找不到电量使用图",situation);
        }
        else if ("V1".equals(Build.MODEL)) {
            screenshotCap("battery_UseSituation_2");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("关于设备");
            device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(), 5000);

            UiScrollable SettingScrol2 = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            SettingScrol2.scrollTextIntoView("电池");
            device.findObject(By.text("电池")).clickAndWait(Until.newWindow(), 5000);

            screenshotCap("battery_UseSituation_3");
            UiObject2 situation = device.findObject(By.res("com.android.settings:id/battery_history_chart"));
            Assert.assertNotNull("测试失败，找不到电量使用图",situation);
        }
    }

    //owner:zhangruili
    //各应用使用电量视图
    @Test
    @Category(CategorySettingsTests.class)
    public void test101CheckPowerUsedByAppsList()  throws UiObjectNotFoundException{
        if("V1".equals(Build.MODEL)|| "P1".equals(Build.MODEL)) {
            if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
                screenshotCap("setting_interface");
                if ("V1".equals(Build.MODEL)) {
                    //V1默认没有电池功能
                } else {
                    UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                    UiSelector message1 = new UiSelector().text("电池");//查找电池
                    scroll1.scrollIntoView(message1);
                    device.findObject(By.text("电池")).clickAndWait(Until.newWindow(), 5000);//点击电池
                    UiObject2 electricityObj = device.findObject(By.text("上次充满后的电量使用情况"));
                    Assert.assertNotNull("未找到上次充满后的电量使用情况标识", electricityObj);
                }
            }
        }

    }

    //owner:zhaizhongjie
    //【开关】节电助手
    @Test
    @Category(CategorySettingsTests.class)
    public void test102CheckSaveAssistantStatus(){

    }

    //owner:wangshilin
    //【选项】节电助手启动时机
    @Test
    @Category(CategorySettingsTests.class)
    public void test103CheckSvaeAssistantStartValue() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("电池");
            device.findObject(By.text("电池")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("进入电池界面");
            device.findObject(By.desc("更多选项")).click();
            sleep(1000);
            device.findObject(By.text("节电助手")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("节电助手界面");
            UiObject2 autoObj = device.findObject(By.text("一律不"));
            Assert.assertNotNull("测试失败，默认选项不是一律不",autoObj);
        }
        else if ("V1".equals(Build.MODEL)){

        }

    }

    //owner:liuyang
    //可优化电池使用的应用列表
    @Test
    @Category(CategorySettingsTests.class)
    public void test104CheckOptimizableAppsList() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            screenshotCap("battery_ApplicationList");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("电池");
            device.findObject(By.text("电池")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("电池");
            screenshotCap("battery_ApplicationList_1");
            UiObject2 situation = device.findObject(By.text("上次充满后的电量使用情况"));
            Assert.assertNotNull("测试失败，找不到电量使用列表",situation);
        }
    }
    //owner:zhangruili
    //【开关】定时开机【开关】定时关机
    @Test
    @Category(CategorySettingsTests.class)
    public void test105CheckAutoOnAndOffVaule()  throws UiObjectNotFoundException{
        if("V1".equals(Build.MODEL)|| "P1".equals(Build.MODEL))  {
            if("V1".equals(Build.MODEL)) {
                //V1无此功能
            }
            else {
                UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                UiSelector message1 = new UiSelector().text("定时开关机");//
                scroll1.scrollIntoView(message1);
                device.findObject(By.text("定时开关机")).clickAndWait(Until.newWindow(), 5000);//点击定时开关机
                sleep(2000);

                UiObject2 openObj = device.findObject(By.text("07:00"));
                UiObject2 openButton = openObj.getParent().getParent().getParent().findObject(By.res("com.mediatek.schpwronoff:id/alarmButton"));
                Assert.assertEquals("定时开机默认为关闭状态", false, openButton.isChecked());//定时开机默认为关闭状态
                sleep(2000);

                UiObject2 closeObj = device.findObject(By.text("08:30"));
                UiObject2 closeButton = closeObj.getParent().getParent().getParent().findObject(By.res("com.mediatek.schpwronoff:id/alarmButton"));
                Assert.assertEquals("定时关机默认为关闭状态", false, closeButton.isChecked());//定时关机默认为关闭状态
            }
        }

    }

    //owner:zhaizhongjie
    //【开关】开发者选项
    @Test
    @Category(CategorySettingsTests.class)
    public void test106CheckDeveloperOptionStatus() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("关于设备");
          //找到关于设备
            sleep(SHORT_SLEEP);
            UiObject2 guanyu = device.findObject(By.text("关于设备"));
            //点击关于设备
            guanyu.click();
            sleep(SHORT_SLEEP);
            UiScrollable anquan1 = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            anquan1.scrollTextIntoView("版本号");//找到版本号
            UiScrollable AboutScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            AboutScroll.scrollTextIntoView("版本号");
            for (int i = 0; i < 8; i++) {
                sleep(200);
                device.findObject(By.text("版本号")).click();
            }
            device.pressBack();
            sleep(SHORT_SLEEP);
            UiObject2 developers = device.findObject(By.text("开发者选项"));
            //点击开发者选项
            developers.click();
            sleep(SHORT_SLEEP);
            UiObject2 kaiguan = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Assert.assertTrue("默认不是为开", kaiguan.isChecked());
        }
        else if ("V1".equals(Build.MODEL)){
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("关于设备");
            //找到关于设备
            sleep(SHORT_SLEEP);
            UiObject2 guanyushbei = device.findObject(By.text("关于设备"));
            //点击关于设备
            guanyushbei.click();
            sleep(SHORT_SLEEP);
            UiScrollable anquan1 = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            anquan1.scrollTextIntoView("版本号");//找到版本号
            sleep(SHORT_SLEEP);
            UiScrollable AboutScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            AboutScroll.scrollTextIntoView("版本号");
            for (int i = 0; i < 8; i++) {
                sleep(200);
                device.findObject(By.text("版本号")).click();
            }
            device.pressBack();
            sleep(SHORT_SLEEP);
            UiObject2 developers = device.findObject(By.text("开发者选项"));
            //点击开发者选项
            developers.click();
            sleep(SHORT_SLEEP);
            UiObject2 kaiguan = device.findObject(By.res("com.android.settings:id/switch_widget"));
            Assert.assertTrue("默认不是为开", kaiguan.isChecked());


        }


    }

    //owner:wangshilin
    //【开关】不锁定屏幕（充电亮屏）
    @Test
    @Category(CategorySettingsTests.class)
    public void test107CheckStayAwakeStatus() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)){
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("关于设备");
            device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("关于设备界面截图");
            UiScrollable aboutScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            aboutScroll.scrollTextIntoView("版本号");
            for (int i = 0; i < 8; i++) {
                sleep(200);
                device.findObject(By.text("版本号")).click();
            }
            device.pressBack();
            sleep(1000);
            screenshotCap("打开开发者选项后截图");
            device.findObject(By.text("开发者选项")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("开发者选项界面截图");
            UiObject2 developerObj = device.findObjects(By.res("android:id/switchWidget")).get(0);
            Assert.assertFalse("测试失败，默认不是关闭状态",developerObj.isChecked());

        }

    }

    //owner:liuyang
    //【开关】OEM解锁
    @Test
    @Category(CategorySettingsTests.class)
    public void test108CheckOEMStatus()throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)){
            screenshotCap("OEM");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
        SettingScroll.scrollTextIntoView("关于设备");
        device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(),5000);
        UiScrollable AboutScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        AboutScroll.scrollTextIntoView("版本号");
        for (int i = 0; i < 8; i++) {
            sleep(200);

            device.findObject(By.text("版本号")).click();
        }
        device.pressBack();
            sleep(2000);
            device.findObject(By.text("开发者选项")).clickAndWait(Until.newWindow(),5000);
            screenshotCap("OEM_1");
        UiObject2 OEM = device.findObjects(By.res("android:id/switchWidget")).get(2);
        Assert.assertFalse("测试失败，默认不是关闭状态",OEM.isChecked());

        }
    }

    //owner:zhangruili
    //【开关】USB调试
    @Test
    @Category(CategorySettingsTests.class)
    public void test109CheckUSBStatus()  throws UiObjectNotFoundException{
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL))  {
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            UiSelector message1=new UiSelector().text("关于设备");//查找关于设备
            scroll1.scrollIntoView(message1);
            device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(),5000);//点击关于设备

            UiScrollable scroll2 = new UiScrollable(new UiSelector().className("android.widget.ListView"));
            UiSelector message2=new UiSelector().text("版本号");//查找版本号
            scroll2.scrollIntoView(message2);
            device.findObject(By.text("版本号")).click();
            for (int i = 0; i < 8; i++) {
                sleep(200);
                device.findObject(By.text("版本号")).click();
            }//点击8次版本号
            sleep(3000);
            device.pressBack();//返回
            sleep(2000);
            device.findObject(By.text("开发者选项")).clickAndWait(Until.newWindow(),5000);//点击开发者选项
            sleep(2000);

            UiScrollable scroll3 = new UiScrollable(new UiSelector().className("android.widget.ListView"));
            UiSelector message3=new UiSelector().text("USB调试");//查找USB调试
            scroll3.scrollIntoView(message3);
            sleep(2000);

            UiObject2 usbObj = device.findObject(By.text("USB调试"));
            UiObject2 usbButton = usbObj.getParent().getParent().findObject(By.res("android:id/switchWidget"));
            Assert.assertEquals("USB调试开关默认为打开状态",true,usbButton.isChecked());//P1调试模式默认开启

        }

    }

    //owner:zhaizhongjie
    //用户中心
    @Test
    @Category(CategorySettingsTests.class)
    public void test110CheckEnterUsercenter() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("用户中心");
            //找到用户中心
            sleep(SHORT_SLEEP);
            UiObject2 user = device.findObject(By.text("用户中心"));
            //点击用户中心
            user.click();
            sleep(SHORT_SLEEP);
            UiObject2 username = device.findObject(By.text("登录商米账号"));
            //找到商米账号
            Assert.assertNotNull("无法进入用户中心", username);
        }

    }

    //owner:wangshilin
    //自定义音量键
    @Test
    @Category(CategorySettingsTests.class)
    public void test111CheckDefineVolumeKey() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("音量键自定义");
            device.findObject(By.text("音量键自定义")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("音量键自定义界面");
            UiObject2 switchObj = device.findObject(By.res("com.sunmi.sidekey:id/set_switch"));
            Assert.assertTrue("测试失败，默认不是打开状态", switchObj.isChecked());
        }
    }

    //owner:liuyang
    //iBeacon
    @Test
    @Category(CategorySettingsTests.class)
    public void test112CheckIBeaconInfo() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) ) {
            screenshotCap("iBeacon");
            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            SettingScroll.scrollTextIntoView("其他设置");
            device.findObject(By.text("其他设置")).clickAndWait(Until.newWindow(), 5000);
            device.findObject(By.text("iBeacon")).clickAndWait(Until.newWindow(), 5000);
            screenshotCap("iBeacon_1");
            UiObject2 iBeacon = device.findObject(By.res("com.sunmi.ibeacon:id/swi"));
            Assert.assertTrue("测试失败，iBeacon默认不是打开状态", iBeacon.isChecked());
            UiObject2 iBeaconSetting = device.findObject(By.res("com.sunmi.ibeacon:id/rl_setting"));
            if (iBeaconSetting.isClickable()) {
                device.findObject(By.text("设置iBeacon")).clickAndWait(Until.newWindow(), 5000);
                device.findObject(By.text("微信")).clickAndWait(Until.newWindow(), 5000);

                UiObject major = new UiObject(new UiSelector().resourceId("com.sunmi.ibeacon:id/et_major"));
                //输入密码
                major.click();
                major.setText("10010");
                sleep(2000);
                device.pressBack();
                UiObject minor = new UiObject(new UiSelector().resourceId("com.sunmi.ibeacon:id/et_minor"));
                //输入密码
                minor.click();
                minor.setText("10086");
                device.pressBack();
                sleep(2000);
                device.findObject(By.text("确认")).clickAndWait(Until.newWindow(), 5000);
                sleep(2000);

                screenshotCap("iBeacon_2");
                UiObject2 situation = device.findObject(By.text("10010"));
                Assert.assertNotNull("测试失败，未能正确保存参数",situation);
            } else {
                device.pressBack();
            }
            device.findObject(By.text("设置iBeacon")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);
            UiObject major = new UiObject(new UiSelector().resourceId("com.sunmi.ibeacon:id/et_major"));
            //输入密码
            major.click();
            major.setText("10073");
            sleep(2000);
            device.pressBack();
            UiObject minor = new UiObject(new UiSelector().resourceId("com.sunmi.ibeacon:id/et_minor"));
            //输入密码
            minor.click();
            minor.setText("61418");
            device.pressBack();
            sleep(2000);
            device.findObject(By.text("确认")).clickAndWait(Until.newWindow(), 5000);
            sleep(2000);

            screenshotCap("iBeacon_3");
            UiObject2 situation = device.findObject(By.text("10073"));
            Assert.assertNotNull("测试失败，未能恢复默认值",situation);
        }
    }

    //owner:zhangruili
    //默认蓝牙打印支持APP
    @Test
    @Category(CategorySettingsTests.class)
    public void test113CheckBTSupportApp() throws UiObjectNotFoundException{
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL))  {
            if("V1".equals(Build.MODEL))  {
                UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
                UiSelector message1=new UiSelector().text("其他设置");//查找其他设置
                scroll1.scrollIntoView(message1);
                device.findObject(By.text("其他设置")).clickAndWait(Until.newWindow(),5000);
                device.findObject(By.text("默认蓝牙打印支持APP")).clickAndWait(Until.newWindow(),5000);//点击默认蓝牙打印支持APP
                UiObject2 printerObj = device.findObject(By.text("商米V1蓝牙打印"));//V1显示为：商米V1蓝牙打印
                Assert.assertNotNull("未找到商米V1蓝牙打印标识",printerObj);
            }
            else{
                device.findObject(By.text("更多")).clickAndWait(Until.newWindow(),5000);//点击更多
                device.findObject(By.text("默认蓝牙打印支持APP")).clickAndWait(Until.newWindow(),5000);//点击默认蓝牙打印支持APP
                UiObject2 printerObj = device.findObject(By.text("商米P1蓝牙打印"));//P1显示为：商米P1蓝牙打印
                Assert.assertNotNull("未找到商米P1蓝牙打印");
            }
        }

    }

    //owner:zhaizhongjie
    //系统更新
    @Test
    @Category(CategorySettingsTests.class)
    public void test114CheckSystemUpdate() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("关于设备");
            sleep(SHORT_SLEEP);
            UiObject2 shbei = device.findObject(By.text("关于设备"));
            //点击关于设备
            shbei.click();
            sleep(SHORT_SLEEP);
            UiObject2 system = device.findObject(By.text("系统更新"));
            //点击系统更新
            system.click();
            sleep(SHORT_SLEEP);
            UiObject2 dangqian = device.findObject(By.res("com.sunmi.ota:id/version_info_rl"));
            //判断系统更新是否可以进入
            Assert.assertNotNull("无法进入系统更新页面", dangqian);
        }


    }

    //owner:wangshilin
    //开发者指南
    @Test
    @Category(CategorySettingsTests.class)
    public void test115CheckDevGuide() throws UiObjectNotFoundException {
//        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
//            UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
//            SettingScroll.scrollTextIntoView("开发者选项");
//            device.findObject(By.text("开发者指南")).clickAndWait(Until.newWindow(), 5000);
//            UiScrollable DeveloperScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
//            DeveloperScroll.scrollTextIntoView("开发者指南");
//            device.findObject(By.text("开发者指南")).clickAndWait(Until.newWindow(),5000);
//        }
    }


    //owner:liuyang
    //硬件信息
    @Test
    @Category(CategorySettingsTests.class)
    public void test116CheckHardwareInfo() throws UiObjectNotFoundException {
        //P1/V1均无此显示
    }

    //owner:zhangruili
    //状态信息
    @Test
    @Category(CategorySettingsTests.class)
    public void test117CheckStatusInfo()  throws UiObjectNotFoundException{
        if("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL))  {
            UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            UiSelector message1=new UiSelector().text("关于设备");
            scroll1.scrollIntoView(message1);
            device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(),5000);//点击关于设备

            device.findObject(By.text("状态信息")).clickAndWait(Until.newWindow(),5000);//点击状态信息
            UiObject2 stateObj = device.findObject(By.text("状态信息"));
            Assert.assertNotNull("未找到状态信息页面",stateObj);//找不到状态信息页面，测试失败
        }

    }

    //owner:zhaizhongjie
    //法律信息
    @Test
    @Category(CategorySettingsTests.class)
    public void test118ChekcLawInfo() throws UiObjectNotFoundException {

        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiScrollable anquan = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
            anquan.scrollTextIntoView("关于设备");
            sleep(SHORT_SLEEP);
            UiObject2 shbei = device.findObject(By.text("关于设备"));
            //点击关于设备
            shbei.click();
            sleep(SHORT_SLEEP);
            UiObject2 falvxinxi = device.findObject(By.text("法律信息"));
            //点击法律信息
            falvxinxi.click();
            sleep(SHORT_SLEEP);
            UiObject2 Licensecode = device.findObject(By.text("开放源代码许可"));
            //判断法律信息是否可用
            Assert.assertNotNull("无法进入法律信息", Licensecode);
        }


    }

    //owner:wangshilin
    //型号
    @Test
    @Category(CategorySettingsTests.class)
    public void test119CheckModel() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("关于设备");
            device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(), 5000);
            UiScrollable aboutScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            aboutScroll.scrollTextIntoView("型号");
            screenshotCap("关于设备界面");
            UiObject2 modelObj = device.findObject(By.text("P1"));
            Assert.assertNotNull("测试失败，型号不是P1",modelObj);
        }
        else if ("V1".equals(Build.MODEL)) {
            UiScrollable settingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
            settingScroll.scrollTextIntoView("关于设备");
            device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(), 5000);
            UiScrollable aboutScroll = new UiScrollable(new UiSelector().resourceId("android:id/list"));
            aboutScroll.scrollTextIntoView("型号");
            screenshotCap("关于设备界面");
            UiObject2 modelObj = device.findObject(By.text("V1"));
            Assert.assertNotNull("测试失败，型号不是V1",modelObj);

        }

    }

    //owner:liuyang
    //Android版本
    @Test
    @Category(CategorySettingsTests.class)
    public void test120CheckAndroidVersion() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) ;
        screenshotCap("Android_version");
        UiScrollable SettingScroll = new UiScrollable(new UiSelector().resourceId("android:id/content"));
        SettingScroll.scrollTextIntoView("关于设备");
        device.findObject(By.text("关于设备")).clickAndWait(Until.newWindow(), 5000);

        if ("V1".equals(Build.MODEL)) {
            screenshotCap("Android_version_1");
            UiObject2 version = device.findObject(By.text("5.1"));
            Assert.assertNotNull("测试失败，Android版本不是5.1", version);
            sleep(2000);
        } else if ("P1".equals(Build.MODEL)) {
            sleep(2000);
            screenshotCap("Android_version_2");
            UiObject2 version = device.findObject(By.text("6.0"));
            Assert.assertNotNull("测试失败，Android版本不是6.0", version);

        }

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
