package com.sunmi.sunmiauto.testcases;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.sunmi.sunmiauto.testcategory.CategoryForTest;
import com.sunmi.sunmiauto.testcategory.CategorySettings_Part2_P1;
import com.sunmi.sunmiauto.testcategory.CategorySettings_Part2_V1;
import com.sunmi.sunmiauto.testutils.CommandUtils;
import com.sunmi.sunmiauto.testutils.CommonAction;
import com.sunmi.sunmiauto.testutils.TestUtils;
import com.sunmi.sunmiauto.testutils.UiobjectFinder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.util.List;

import static com.sunmi.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_BASE_CODE;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_FW;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_KERNEL_CODE;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_PATCH_LEVEL;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_SECURITY_CODE;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_VERSION_CODE;
import static com.sunmi.sunmiauto.testutils.TestConstants.P1_VERSION_NAME;
import static com.sunmi.sunmiauto.testutils.TestConstants.SHORT_SLEEP;
import static com.sunmi.sunmiauto.testutils.TestConstants.V1_KERNEL_CODE;
import static com.sunmi.sunmiauto.testutils.TestConstants.V1_PATCH_LEVEL;
import static com.sunmi.sunmiauto.testutils.TestConstants.V1_VERSION_CODE;
import static com.sunmi.sunmiauto.testutils.TestConstants.V1_VERSION_NAME;
import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.drawLPattern;
import static com.sunmi.sunmiauto.testutils.TestUtils.hasSIMCard;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;

/**
 * Created by fengy on 2017/9/13.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiSettings_Part2 {

    @Before
    public void setUp() throws RemoteException {
        TestUtils.clearAllRecentApps();
        TestUtils.findAppAndOpenByText("设置");
    }

    /*
    默认设置应用
    1.下拉通知栏，点击设置快捷键，进入到商米设置APP
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test136CheckDefaultSettingsAPP() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            device.pressHome();
            device.openQuickSettings();
            screenshotCap("quickSettingsPage");
            device.wait(Until.hasObject(By.res("com.android.systemui:id/settings_button")), LONG_WAIT);
            CommonAction.clickById("com.android.systemui:id/settings_button");
            screenshotCap("settingsHomePage");
            Assert.assertEquals("com.android.settings", device.getCurrentPackageName());
        }
    }

    /*
    查找可添加的无线网络
    1.进入到wifi界面
    2.查看可用wifi列表
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test137SearchAvailableWifiList() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            //进入到WLAN
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.scrollToText("SUNMI");
            UiObject2 knownWifiObj = UiobjectFinder.findByText("SUNMI");
            Assert.assertNotNull("未找到SUNMI名称的wifi", knownWifiObj);
        }
    }

    /*
    【选项】WLAN频带
    1.进入到wifi界面
    2.查看WLAN可用频带
     */
    @Test
    public void test138CheckWLANBand() {

    }

    /*
    安装证书
    1.进入到wifi界面
    2.点击安装证书，进入到打开文件选择界面
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test139InstallCertificate() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("高级");
            screenshotCap("advancedPage");
            CommonAction.clickByText("安装证书");
            screenshotCap("installCerPage");
            device.wait(Until.hasObject(By.text("打开文件").pkg("com.android.documentsui")), LONG_WAIT);
            UiObject2 openFileObj = device.findObject(By.text("打开文件").pkg("com.android.documentsui"));
            Assert.assertNotNull("点击安装证书，未能够进入到打开文件界面", openFileObj);
        }
    }

    /*
    WPS按钮
    1.进入到wifi界面
    2.查看WPS按钮状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test140CheckWPSButtonAvailable() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            UiObject2 WPSInstructionObj = null;
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("高级");
            screenshotCap("advancedPage");
            CommonAction.clickByText("WPS按钮");
            screenshotCap("WPSDialog");
            if ("P1".equals(Build.MODEL)) {
                WPSInstructionObj = UiobjectFinder.findByText("请按下您路由器上的 WLAN 保护设置按钮，它可能会标为“WPS”或标有此符号：");
            } else if ("V1".equals(Build.MODEL)) {
                WPSInstructionObj = UiobjectFinder.findByText("请按下您路由器上的WLAN保护设置按钮，它可能会标为“WPS”或包含此符号：");
            }
            Assert.assertNotNull("没有弹出WPS对话框", WPSInstructionObj);
            CommonAction.clickById("com.android.settings:id/wps_dialog_btn");
        }
    }

    /*
    WPS PIN码
    1.进入到wifi界面
    2.查看WPS PIN码按钮状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test141CheckWPSPINButtonAvailable() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("高级");
            screenshotCap("advancedPage");
            CommonAction.clickByText("WPS PIN码输入");
            screenshotCap("WPSPINDialog1");
            UiObject2 PINInfoObj1 = UiobjectFinder.findById("com.android.settings:id/wps_dialog_txt");
            String pinInfo1 = CommandUtils.match(PINInfoObj1.getText(), "PIN码", "。").get(0);
            device.pressBack();
            CommonAction.clickByText("WPS PIN码输入");
            screenshotCap("WPSPINDialog2");
            UiObject2 PINInfoObj2 = UiobjectFinder.findById("com.android.settings:id/wps_dialog_txt");
            String wpsDialogString = PINInfoObj2.getText();
            if(wpsDialogString.startsWith("已连接到WLAN网络")){
                Assert.assertTrue(true);
            }else{
                String pinInfo2 = CommandUtils.match(PINInfoObj2.getText(), "PIN码", "。").get(0);
                device.pressBack();
                Assert.assertNotEquals("两次产生的PIN一样，可能存在问题", pinInfo1, pinInfo2);
            }
        }
    }

    /*
    WLAN直连
    1.进入到wifi界面
    2.点击WLAN直连，进入到WLAN直连界面
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test142WLANDirectConnect() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("高级");
            screenshotCap("advancedPage");
            CommonAction.clickByText("WLAN直连");
            screenshotCap("dirWlanPage");
            UiObject2 dircetionNameObj = UiobjectFinder.findByText("SunmiP1");
            UiObject2 renameDeviceObj = UiobjectFinder.findByText("重命名设备");
            UiObject2 peerDeviceObj = UiobjectFinder.findByText("对等设备");
            UiObject2 savedGroupObj = UiobjectFinder.findByText("已保存的群组");
            Assert.assertNotNull("未找到SunmiP1", dircetionNameObj);
            Assert.assertNotNull("未找到重命名设备", renameDeviceObj);
            Assert.assertNotNull("未找到对等设备", peerDeviceObj);
            Assert.assertNotNull("未找到已保存的群组", savedGroupObj);
        }
    }

    /*
    显示MAC地址
    1.进入到wifi界面
    2.查看MAC地址显示
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test143ShowingMACAddress() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("高级");
            screenshotCap("advancedPage");
            CommonAction.scrollToText("MAC地址");
            screenshotCap("macPage");
            UiObject2 macObj = UiobjectFinder.findByText("MAC地址").getParent().getChildren().get(1);
            String macString = macObj.getText();
            String orginazationCode = macString.substring(0, 8);
            Log.v("myautotest", orginazationCode);
            Assert.assertEquals("mac地址厂家代码不正确", "0c:25:76", orginazationCode);
        }
    }

    /*
    显示IPv4地址
    1.进入到wifi界面
    2.查看IPV4显示
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test144ShowingIPV4Address() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("高级");
            screenshotCap("advancedPage");
            CommonAction.scrollToText("IPv4 地址");
            screenshotCap("IPv4Page");
            UiObject2 IPv4Obj = UiobjectFinder.findByText("IPv4 地址").getParent().getChildren().get(1);
            String IPv4String = IPv4Obj.getText();
            Assert.assertNotNull("IPv4地址为空", IPv4String);
        }
    }

    /*
    显示IPv6地址
    1.进入到Wifi界面
    2.查看IPV6显示
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test145ShowingIPV6Address() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("WLAN");
            screenshotCap("wlanPage");
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("高级");
            screenshotCap("advancedPage");
            CommonAction.scrollToText("IPv6 地址");
            screenshotCap("IPv6Page");
            UiObject2 IPv6Obj = UiobjectFinder.findByText("IPv6 地址").getParent().getChildren().get(1);
            String IPv6String = IPv6Obj.getText();
            Assert.assertNotNull("IPv6地址为空", IPv6String);
        }
    }

    /*
    可用设备列表
    1.进入到蓝牙界面
    2.查看可用设备列表
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test146SearchBTAvailableList() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("蓝牙");
            screenshotCap("BTPage");
            if ("P1".equals(Build.MODEL)) {
                CommonAction.clickById("com.android.settings:id/switch_widget");
            }
            device.wait(Until.hasObject(By.res("com.android.settings:id/scanning_progress")), LONG_WAIT);
            device.wait(Until.gone(By.res("com.android.settings:id/scanning_progress")), LONG_WAIT * 2);
            UiObject2 avaDevicesObj = UiobjectFinder.findByText("可用设备").getParent();
            List<UiObject2> avaDevices = UiobjectFinder.findById("android:id/list").getChildren();
            int counts = avaDevices.indexOf(avaDevicesObj);
            int deviceCounts = avaDevices.size();
            Log.e("myautotest", String.valueOf(counts));
            Assert.assertTrue("蓝牙列表为空", deviceCounts > counts);
            if ("P1".equals(Build.MODEL)) {
                CommonAction.clickById("com.android.settings:id/switch_widget");
            }
        }
    }

    /*
    显示收到的文件
    1.进入到蓝牙界面
    2.点击显示收到的文件，进入到通过蓝牙接收的文件界面
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test147ShowingBTReceivedFile() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByText("蓝牙");
            screenshotCap("BTPage");
            if ("P1".equals(Build.MODEL)) {
                CommonAction.clickById("com.android.settings:id/switch_widget");
            }
            CommonAction.clickByDesc("更多选项");
            screenshotCap("moreOptionsPage");
            CommonAction.clickByText("显示收到的文件");
            screenshotCap("receviedFilesPage");
            UiObject2 receivedInterfaceObj = device.findObject(By.text("通过蓝牙接收的文件").pkg("com.android.bluetooth"));
            Assert.assertNotNull("点击显示收到的文件，未能够跳转到蓝牙传输历史界面", receivedInterfaceObj);
            if ("P1".equals(Build.MODEL)) {
                device.pressBack();
                CommonAction.clickById("com.android.settings:id/switch_widget");
            }
        }
    }

    /*
    支持BLE功能
     */
    @Test
    public void test148SupportBLEFunction() {

    }

    /*
    设置WLAN热点
    1.进入到网络共享与便携式热点
    2.进入到WLAN热点
    3.进入到设置WLAN热点，检查各项参数正确
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test149SettingWLANHotspot() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            CommonAction.clickByText("网络共享与便携式热点");
            CommonAction.clickByText("WLAN 热点");
            screenshotCap("wlanHotspotPage");
            CommonAction.clickByText("设置WLAN热点");
            screenshotCap("wlanHotspotSettingsPage");
            CommonAction.clickById("com.android.settings:id/show_password");
            UiObject2 nameObj = UiobjectFinder.findById("com.android.settings:id/ssid");
            String name = nameObj.getText();
            UiObject2 securityObj = UiobjectFinder.findById("com.android.settings:id/security").getChildren().get(0);
            String security = securityObj.getText();
            UiObject2 passwordObj = UiobjectFinder.findById("com.android.settings:id/password");
            String password = passwordObj.getText();
            int passwordLength = password.length();
            UiObject2 channelObj = UiobjectFinder.findById("com.android.settings:id/choose_channel").getChildren().get(0);
            String channel = channelObj.getText();
            UiScrollable uiScrollable = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
            uiScrollable.scrollTextIntoView("最大连接数");
            UiObject2 connectionNumObj = UiobjectFinder.findById("com.android.settings:id/max_connection_num").getChildren().get(0);
            String connectionNum = connectionNumObj.getText();
            CommonAction.clickById("com.android.settings:id/reset_oob");
            UiObject2 newPasswordObj = UiobjectFinder.findById("com.android.settings:id/password");
            String newPassword = newPasswordObj.getText();
            Assert.assertEquals("默认热点名称不正确", "SunmiP1", name);
            Assert.assertEquals("默认安全方式不正确", "WPA2 PSK", security);
            Assert.assertEquals("默认密码长度不是12位", 12, passwordLength);
            Assert.assertEquals("默认热点频段不正确", "2.4 GHz 频段", channel);
            Assert.assertEquals("默认最大连接数不正确", "6 位用户", connectionNum);
            Assert.assertNotEquals("重置OOB按钮无效", password, newPassword);
            CommonAction.clickById("com.android.settings:id/show_password");
        }
    }

    /*
    WLAN流量使用视图
    1.进入到流量使用情况
    2.检查WLAN流量使用视图
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test150WLANDataUsedView() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("流量使用情况");
            screenshotCap("dataUsedPage");
            if (hasSIMCard()) {
                CommonAction.clickByDesc("更多选项");
                CommonAction.clickByText("显示WLAN流量");
                UiObject2 WLANObj = UiobjectFinder.findById("android:id/tabs").getChildren().get(2);
                WLANObj.click();
            } else {
                UiObject2 WLANObj = UiobjectFinder.findById("android:id/tabs").getChildren().get(1);
                WLANObj.click();
            }
            screenshotCap("wlanUsedPage");
            UiObject2 dateSwitchObj = UiobjectFinder.findById("com.android.settings:id/network_switches_container");
            UiObject2 dataViewObj = UiobjectFinder.findById("com.android.settings:id/chart");
            UiObject2 appUsedHistoryObj = UiobjectFinder.findByText("应用使用情况");
            if (hasSIMCard()) {
                CommonAction.clickByDesc("更多选项");
                CommonAction.clickByText("隐藏WLAN流量");
            }
            Assert.assertNotNull("未找到日期调整switch", dateSwitchObj);
            Assert.assertNotNull("未找到流量使用视图", dataViewObj);
            Assert.assertNotNull("未找到应用使用情况列表", appUsedHistoryObj);
        }
    }

    /*
    移动网络流量使用视图
    1.进入到流量使用情况
    2.检查移动流量使用视图
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test151MobileDataUsedView() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("流量使用情况");
            screenshotCap("dataUsedPage");
            if (hasSIMCard()) {
                UiObject2 dataObj = UiobjectFinder.findById("android:id/tabs").getChildren().get(1);
                dataObj.click();
            } else {
                Assert.fail("没有插入SIM卡，请检查，该项测试需要插入SIM卡");
            }
            screenshotCap("dataTrafficPage");
            UiObject2 dataSwitchObj = UiobjectFinder.findByClazz("android.widget.Switch");
            UiObject2 dataLimitSwitchObj = UiobjectFinder.findByText("设置移动数据流量上限").getParent().getParent().findObject(By.clazz("android.widget.Switch"));
            UiObject2 dateViewObj = UiobjectFinder.findById("com.android.settings:id/cycles_spinner");
            UiObject2 dataChartObj = UiobjectFinder.findById("com.android.settings:id/chart");
            UiObject2 usedDataObj = UiobjectFinder.findById("com.android.settings:id/cycle_summary");
            String fore3Data = usedDataObj.getText().substring(0, 4);
            Log.e("myautotest", fore3Data);
            UiObject2 appUsedHistoryObj;
            if (!"0.00".equals(fore3Data)) {
                appUsedHistoryObj = UiobjectFinder.findByText("应用使用情况");
            } else {
                appUsedHistoryObj = UiobjectFinder.findByText("没有任何应用在此期间产生过数据流量。");
            }
            Assert.assertTrue("移动数据网路开关默认为开", dataSwitchObj.isChecked());
            Assert.assertFalse("设置移动数据流量上限开关默认为关", dataLimitSwitchObj.isChecked());
            Assert.assertNotNull("未找到应用使用情况列表", dataChartObj);
            Assert.assertNotNull("未找到应用使用情况列表", appUsedHistoryObj);
        }
    }

    /*
    【开关】网络限制
    1.进入到流量使用情况
    2.点击右上角更多按钮，进入到网络限制界面
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test152NetworkRestrictionStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("流量使用情况");
            screenshotCap("dataUsedPage");
            CommonAction.clickByDesc("更多选项");
            CommonAction.clickByText("网络限制");
            screenshotCap("netLimitedPage");
            UiObject2 chargingObj = UiobjectFinder.findByText("按流量计费的WLAN网络");
            Assert.assertNotNull("未进入到网络限制页面", chargingObj);
        }
    }

    /*
    有线网络流量使用视图
    1.进入到流量使用情况
    2.检查有线网流量使用视图
     */
    @Test
    public void test153WiredDataUsedView() {

    }

    /*
    【选项】始终开启VPN
    1.进入到VPN界面
    2.点击右上角的更多按钮，弹出始终开启的VPN弹框，检查弹框
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test154VPNAlwaysON() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            CommonAction.clickByText("VPN");
            screenshotCap("VPNPage");
            if ("V1".equals(Build.MODEL)) {
                CommonAction.clickByText("确定");
                CommonAction.clickByText("图案");
                UiObject2 uiObject = UiobjectFinder.findById("com.android.settings:id/lockPattern");
                TestUtils.drawLPattern(uiObject);
                CommonAction.clickByText("继续");
                TestUtils.drawLPattern(uiObject);
                CommonAction.clickByText("确认");
                CommonAction.clickByText("完成");
            }
            CommonAction.clickByDesc("更多选项");
            CommonAction.clickByText("始终开启的VPN");
            screenshotCap("VPNAlwaysOpenDialog");
            UiObject2 defaultVPN = device.findObject(By.text("无").checked(true));
            Assert.assertNotNull("始终开启的VPN默认不是“无”", defaultVPN);
            if ("V1".equals(Build.MODEL)) {
                device.pressBack();
                device.pressBack();
                device.pressBack();
                CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
                CommonAction.clickByText("安全");
                CommonAction.clickByText("屏幕锁定方式");
                UiObject2 uiObject = UiobjectFinder.findById("com.android.settings:id/lockPattern");
                TestUtils.drawLPattern(uiObject);
                CommonAction.clickByText("滑动");
                CommonAction.clickByText("确定");
            }
        }
    }

    /*
    连接登陆VPN
    已经包含在之前的增删改VPN配置文件中，提取出来
     */
//    @Ignore
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test155LoginVPN() throws UiObjectNotFoundException {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            CommonAction.clickByText("VPN");
            screenshotCap("VPNPage");
            if ("P1".equals(Build.MODEL)) {
                CommonAction.clickById("com.android.settings:id/vpn_create");
            }
            CommonAction.clickByText("确定");
            CommonAction.clickByText("图案");
            drawLPattern(device.findObject(By.res("com.android.settings:id/lockPattern")));
            CommonAction.clickByText("继续");
            drawLPattern(device.findObject(By.res("com.android.settings:id/lockPattern")));
            CommonAction.clickByText("确认");
            CommonAction.clickByText("完成");
            if ("V1".equals(Build.MODEL)) {
                CommonAction.clickById("com.android.settings:id/vpn_create");
            }
            //输入vpn名称
            CommonAction.setText("SUNMI");
            //输入服务器地址
            CommonAction.clickById("com.android.settings:id/server");
            CommonAction.setText("p1.hk2.heyjump.com");
            device.pressBack();
            screenshotCap("VPNSettingsPage");
            CommonAction.clickByText("保存");
            screenshotCap("VPNSavedPage");
            CommonAction.clickByText("SUNMI");
            //输入用户名
            CommonAction.setText("Jzorrof");
            device.pressBack();
            //输入用户名
            CommonAction.clickById("com.android.settings:id/password");
            CommonAction.setText("qscvhi$$!_");
            device.pressBack();
            screenshotCap("VPNInfoSetPage");
            CommonAction.clickById("com.android.settings:id/save_login");
            CommonAction.clickByText("连接");
            device.wait(Until.hasObject(By.text("已连接")), LONG_WAIT);
            screenshotCap("connectedPage");
            UiObject2 connectedObj = UiobjectFinder.findByText("已连接");
            if (connectedObj == null) {
                CommonAction.clickByText("SUNMI");
                CommonAction.clickByText("连接");
                device.wait(Until.hasObject(By.text("已连接")), LONG_WAIT);
                screenshotCap("connectedPage_1");
            }
            UiObject2 configurationFile = UiobjectFinder.findByText("SUNMI");
            Assert.assertNotNull("测试失败,未找到配置文件", configurationFile);
            boolean vpnOK = TestUtils.pingYoutube();
            if ("P1".equals(Build.MODEL)) {
                CommonAction.clickByDesc("设置");
                CommonAction.clickByText("取消保存");
                device.pressBack();
                device.pressBack();
            } else if ("V1".equals(Build.MODEL)) {
                UiObject2 setting = device.findObject(By.text("SUNMI"));
                CommonAction.longClick(setting);
                CommonAction.clickByText("删除配置文件");
                device.pressBack();
                device.pressBack();
            }
            sleep(SHORT_SLEEP);
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            CommonAction.clickByText("屏幕锁定方式");
            drawLPattern(device.findObject(By.res("com.android.settings:id/lockPattern")));
            CommonAction.clickByText("滑动");
            if ("V1".equals(Build.MODEL)) {
                CommonAction.clickByText("确定");
            } else if ("P1".equals(Build.MODEL)) {
                CommonAction.clickByText("是，移除");
            }
            UiObject2 lockScreenMode = UiobjectFinder.findByText("滑动");
            Assert.assertTrue("连接上VPN，但是无法访问YOUTUBE", vpnOK);
            Assert.assertNotNull("测试失败,未找到滑动", lockScreenMode);
        }
    }

    /*
    断开VPN连接
    已经包含在之前的增删改VPN配置文件中，提取出来
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class, CategoryForTest.class})
    @Test
    public void test156DisconnectVPN() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            CommonAction.clickByText("VPN");
            screenshotCap("VPNPage");
            if ("P1".equals(Build.MODEL)) {
                CommonAction.clickById("com.android.settings:id/vpn_create");
            }
            CommonAction.clickByText("确定");
            CommonAction.clickByText("图案");
            drawLPattern(device.findObject(By.res("com.android.settings:id/lockPattern")));
            CommonAction.clickByText("继续");
            drawLPattern(device.findObject(By.res("com.android.settings:id/lockPattern")));
            CommonAction.clickByText("确认");
            CommonAction.clickByText("完成");
            if ("V1".equals(Build.MODEL)) {
                CommonAction.clickById("com.android.settings:id/vpn_create");
            }
            //输入vpn名称
            CommonAction.setText("SUNMI");
            //输入服务器地址
            CommonAction.clickById("com.android.settings:id/server");
            CommonAction.setText("p1.hk2.heyjump.com");
            device.pressBack();
            screenshotCap("VPNSettingsPage");
            CommonAction.clickByText("保存");
            screenshotCap("VPNSavedPage");
            CommonAction.clickByText("SUNMI");
            //输入用户名
            CommonAction.setText("Jzorrof");
            device.pressBack();
            //输入用户名
            CommonAction.clickById("com.android.settings:id/password");
            CommonAction.setText("qscvhi$$!_");
            device.pressBack();
            screenshotCap("VPNInfoSetPage");
            CommonAction.clickById("com.android.settings:id/save_login");
            CommonAction.clickByText("连接");
            device.wait(Until.hasObject(By.text("已连接")), LONG_WAIT);
            screenshotCap("connectedPage");
            UiObject2 connectedObj = UiobjectFinder.findByText("已连接");
            UiObject2 configurationFile = UiobjectFinder.findByText("SUNMI");
            Assert.assertNotNull("测试失败,未找到配置文件", configurationFile);
            boolean vpnOK = TestUtils.pingYoutube();
            if(connectedObj != null){
                CommonAction.clickByText("SUNMI");
                CommonAction.clickByText("断开连接");
            }
            screenshotCap("disconnectedPage");
            boolean vpnFail = TestUtils.pingYoutube();
            Assert.assertFalse("断开VPN，仍然可以访问YOUTUBE", vpnFail);
            if ("P1".equals(Build.MODEL)) {
                CommonAction.clickByDesc("设置");
                CommonAction.clickByText("取消保存");
                device.pressBack();
                device.pressBack();
            } else if ("V1".equals(Build.MODEL)) {
                UiObject2 setting = device.findObject(By.text("SUNMI"));
                CommonAction.longClick(setting);
                CommonAction.clickByText("删除配置文件");
                device.pressBack();
                device.pressBack();
            }
            sleep(SHORT_SLEEP);
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            CommonAction.clickByText("屏幕锁定方式");
            drawLPattern(device.findObject(By.res("com.android.settings:id/lockPattern")));
            CommonAction.clickByText("滑动");
            if ("V1".equals(Build.MODEL)) {
                CommonAction.clickByText("确定");
            } else if ("P1".equals(Build.MODEL)) {
                CommonAction.clickByText("是，移除");
            }
            UiObject2 lockScreenMode = UiobjectFinder.findByText("滑动");
            Assert.assertTrue("连接上VPN，但是无法访问YOUTUBE", vpnOK);
            Assert.assertNotNull("测试失败,未找到滑动", lockScreenMode);
        }
    }

    /*
    【开关】移动数据网络漫游
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class, CategoryForTest.class})
    @Test
    public void test157CheckMobileDataRoamStatus() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            UiObject2 roamSwitch = UiobjectFinder.findById("android:id/switchWidget");
            Assert.assertFalse("移动数据网络漫游开关默认为关闭", roamSwitch.isChecked());
        }
    }

    /*
    偏好设置
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test158EnterPreferenceSettings() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            CommonAction.clickByText("偏好设置");
            screenshotCap("prefrenceSetPage");
            UiObject2 addPLMNObj = UiobjectFinder.findByText("新增 PLMN");
            Assert.assertNotNull("没有能够进入到偏好设置", addPLMNObj);
        }
    }

    /*
    【选项】首选网络类型
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test159CheckDefaultSelectedNetworkValue() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            CommonAction.clickByText("首选网络类型");
            screenshotCap("defaultNetTypePage");
            UiObject2 networkTypeObj = UiobjectFinder.findByText("3G");
            Assert.assertTrue("默认网络类型不是3G", networkTypeObj.isChecked());
        }
    }

    /*
    增删改APN（接入点）
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test160CheckDefaultAPNValue() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            CommonAction.clickByText("接入点名称 (APN)");
            screenshotCap("accessPointPage");
            UiObject2 radioButtonObj = UiobjectFinder.findByText("中国联通 3g 网络 (China Unicom)").getParent().getParent().findObject(By.res("com.android.settings:id/apn_radiobutton"));
            Assert.assertTrue("默认的APN不是“中国联通 3g 网络 (China Unicom)”", radioButtonObj.isChecked());
        }
    }

    /*
    重置APN
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test161ResetAPN() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            CommonAction.clickByText("接入点名称 (APN)");
            screenshotCap("accessPointPage");
            CommonAction.clickByDesc("新建 APN");
            screenshotCap("newAPNDialog");
            CommonAction.clickByText("名称");
            CommonAction.setText("TestResetAPN");
            device.pressBack();
            CommonAction.clickByText("确定");
            CommonAction.clickByText("APN");
            CommonAction.setText("4gnet");
            device.pressBack();
            CommonAction.clickByText("确定");
            CommonAction.clickByDesc("更多选项");
            CommonAction.clickByText("保存");
            screenshotCap("addedAPNPage");
            CommonAction.clickByDesc("更多选项");
            CommonAction.clickByText("重置为默认设置");
            device.wait(Until.hasObject(By.text("APN")), LONG_WAIT);
            screenshotCap("resetAPNPage");
            UiObject2 addedAPNObj = device.findObject(By.text("TestResetAPN"));
            Assert.assertNull("重置为默认设置失败", addedAPNObj);
        }
    }

    /*
    搜索网络运营商
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test162SearchNetworkOperator() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            CommonAction.clickByText("网络运营商");
            screenshotCap("netSearchPage");
            UiObject2 searchingObj = UiobjectFinder.findByText("正在搜索...");
            Assert.assertNotNull("没有搜索框弹出", searchingObj);
            device.wait(Until.hasObject(By.text("可用网络")), LONG_WAIT * 2);
            UiObject2 availableNetObj = UiobjectFinder.findByText("可用网络");
            Assert.assertNotNull("未能够搜索出可用网络", availableNetObj);
        }
    }

    /*
    自动选择网络运营商
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test163AutoSelectingNetworkOperator() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            CommonAction.clickByText("网络运营商");
            screenshotCap("netOperatorPage");
            device.wait(Until.hasObject(By.text("可用网络")), LONG_WAIT * 2);
            CommonAction.clickByText("自动选择");
            device.wait(Until.hasObject(By.text("移动网络设置")), LONG_WAIT);
            screenshotCap("networkPage");
            UiObject2 netSettingInterfaceObj = UiobjectFinder.findByText("移动网络设置");
            Assert.assertNotNull("自动选择网络后没有返回到移动网络设置界面", netSettingInterfaceObj);
        }
    }

    /*
    手动选择网络运营商
    1.进入到移动网络界面
    2.检查默认状态
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test164ManualSelectingNetworkOperator() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.clickByText("更多");
            screenshotCap("morePage");
            UiObject2 mobileNetworkObj = UiobjectFinder.findByText("移动网络");
            Assert.assertTrue("移动网络不可用，请检查是否插入有效SIM卡", mobileNetworkObj.isEnabled());
            CommonAction.clickByText("移动网络");
            screenshotCap("mobileNetPage");
            CommonAction.clickByText("网络运营商");
            device.wait(Until.hasObject(By.text("可用网络")), LONG_WAIT * 2);
            screenshotCap("netOperatorPage");
            CommonAction.clickByText("搜索网络");
            UiObject2 searchingObj = UiobjectFinder.findByText("正在搜索...");
            Assert.assertNotNull("没有搜索框弹出", searchingObj);
            device.wait(Until.hasObject(By.text("可用网络")), LONG_WAIT);
            screenshotCap("availableNetPage");
            UiObject2 availableNetObj = UiobjectFinder.findByText("可用网络");
            Assert.assertNotNull("手动点击搜索网络后出错", availableNetObj);
        }
    }

    /*
    MiraVison
    1.进入到显示
    2.点击MiraVision进入到MiraVision界面
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test165EnterMiraVision() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            CommonAction.clickByText("MiraVision™");
            screenshotCap("MiraVisionPage");
            Assert.assertEquals("未能够进入到MiraVision页面", "com.mediatek.miravision.ui", device.getCurrentPackageName());
        }
    }

    /*
    查看开机动画功能
    1.进入显示界面
    2.点击开机动画，进入到开机动画界面
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test166CheckPowerOnAnimation() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            CommonAction.clickByText("开机动画");
            screenshotCap("powerOnAnimationPage");
            UiObject2 powerOnAnimationObj = UiobjectFinder.findBySelector(By.text("开机画面").pkg("com.woyou.channelservice"));
            Assert.assertNotNull("没有进入到开机画面界面", powerOnAnimationObj);
        }
    }

    /*
    选择默认启动应用
    1.进入到显示界面
    2.查看默认启动应用
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test167CheckDefaultStartAPP() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            String defaultStartString = UiobjectFinder.findByText("默认启动应用").getParent().getChildren().get(1).getText();
            Assert.assertEquals("默认启动应用不为空", "没有默认启动应用", defaultStartString);
        }
    }

    /*
    选择自定义桌面
    1.进入到显示界面
    2.查看默自定义桌面
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test168CheckCustomedLauncher() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            String defaultLauncherString = UiobjectFinder.findByText("自定义桌面").getParent().getChildren().get(1).getText();
            Assert.assertEquals("自定义桌面不为空", "没有自定义桌面", defaultLauncherString);
        }
    }

    /*
    选择动态壁纸
    1.进入到显示界面
    2.进入到壁纸，查看动态壁纸
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test169ChooseWallpaperFromCustomedLiveWallpaper() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            String defaultWallpaper = UiobjectFinder.findByText("壁纸").getParent().getChildren().get(1).getText();
            Assert.assertEquals("壁纸不为默认", "默认", defaultWallpaper);
            CommonAction.clickByText("壁纸");
            screenshotCap("wallpaperPage");
            CommonAction.clickByText("动态壁纸");
            screenshotCap("dynamicWallpaperPage");
            UiObject2 liveWallpaperObj = UiobjectFinder.findBySelector(By.text("光环螺旋").pkg("com.android.wallpaper.livepicker"));
            Assert.assertNotNull("没有能够进入到动态壁纸界面", liveWallpaperObj);
        }
    }

    /*
    选择图库
    1.进入到显示界面
    2.进入到壁纸，点击图库，进入到图库"
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test170ChooseWallpaperFromGallery() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            String defaultWallpaper = UiobjectFinder.findByText("壁纸").getParent().getChildren().get(1).getText();
            Assert.assertEquals("壁纸不为默认", "默认", defaultWallpaper);
            CommonAction.clickByText("壁纸");
            screenshotCap("wallpaperPage");
            CommonAction.clickByText("图库");
            screenshotCap("galleryPage");
            UiObject2 galleryWallpaperObj = UiobjectFinder.findBySelector(By.text("选择照片").pkg("com.android.gallery3d"));
            Assert.assertNotNull("没有能够进入到图库界面", galleryWallpaperObj);
        }
    }

    /*
    选择壁纸
    1.进入到显示界面
    2.进入到壁纸，点击壁纸，进入到设置壁纸界面"
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test171ChoosseWallpaperFromWallpaper() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            String defaultWallpaper = UiobjectFinder.findByText("壁纸").getParent().getChildren().get(1).getText();
            Assert.assertEquals("壁纸不为默认", "默认", defaultWallpaper);
            CommonAction.clickByText("壁纸");
            screenshotCap("wallpaperPage");
            CommonAction.clickByText("壁纸");
            screenshotCap("defaultWallpaperPage");
            UiObject2 defaultWallpaperObj = UiobjectFinder.findBySelector(By.text("设置壁纸").pkg("com.woyou.launcher"));
            Assert.assertNotNull("没有能够进入到设置壁纸界面", defaultWallpaperObj);
        }
    }

    /*
    调节屏幕亮度（亮度）
    1.进入到显示界面
    2.点击亮度，弹出屏幕亮度调节seekbar"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test172CheckAdjustBrightnessSeekbar() {
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            CommonAction.clickByText("亮度");
            screenshotCap("brightnessSeekbar");
            UiObject2 brightnessSeekbarObj = UiobjectFinder.findByDesc("屏幕亮度");
            Assert.assertNotNull("未弹出调节亮度seekbar", brightnessSeekbarObj);
        }
    }

    /*
    投射
    1.进入到显示界面
    2.点击投射进入到投射界面"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test173EnterCastInterface() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByText("显示");
            screenshotCap("displayPage");
            CommonAction.clickByText("投射");
            screenshotCap("castPage");
            UiObject2 noDevicesObj = UiobjectFinder.findByText("未在附近找到设备。");
            Assert.assertNotNull("点击投射未进入到正确页面", noDevicesObj);
        }
    }

    /*
    最近的位置信息请求列表
    1.进入到位置信息界面
    2.查看最近的位置信息请求列表"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test174ShowingRecentRequestLocationAppsList() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "位置信息");
            CommonAction.clickByText("位置信息");
            screenshotCap("locationPage");
            UiObject2 recentRequestObj = UiobjectFinder.findByText("最近的位置信息请求");
            Assert.assertNotNull("未能够找到最近的位置信息请求", recentRequestObj);
        }
    }

    /*
    加密设备
    1.进入到安全界面
    2.点击加密设备进入到加密设备界面"
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test175EncryptionDevice() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.clickByText("加密设备");
            screenshotCap("encryptPage");
            UiObject2 encryptButtonObj = device.findObject(By.clazz("android.widget.Button").text("加密设备"));
            Assert.assertNotNull("没有能够找到加密设备按钮", encryptButtonObj);
        }
    }

    /*
    锁定SIM卡
    1.进入到安全界面
    2.点击设置SIM卡锁定方式
    3.检查锁定SIM卡状态"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test176LockSIMCard() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.clickByText("设置SIM卡锁定方式");
            screenshotCap("setSIMLockStylePage");
            UiObject2 lockObj = UiobjectFinder.findById("android:id/switchWidget");
            Assert.assertFalse("锁定SIM卡开关默认应该为关", lockObj.isChecked());
            lockObj.click();
            CommonAction.setText("1234");
            device.pressBack();
            CommonAction.clickByText("确定");
            screenshotCap("afterSetSIMLockStylePage");
            UiObject2 changeSIMPINObj = UiobjectFinder.findByText("更改SIM卡PIN码");
            Assert.assertTrue("没有能够锁定SIM卡", changeSIMPINObj.isEnabled());
            CommonAction.clickById("android:id/switchWidget");
            CommonAction.setText("1234");
            device.pressBack();
            CommonAction.clickByText("确定");
        }
    }

    /*
    更改SIM卡的PIN码
    1.进入到安全界面
    2.点击设置SIM卡锁定方式
    3.检查更改SIM卡PIN码"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test177ChangeSIMCardPIN() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            if (!hasSIMCard()) {
                Assert.fail("没有插入SIM卡，该测试用例需要插入SIM卡，请插入SIM卡进行测试");
            }
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.clickByText("设置SIM卡锁定方式");
            screenshotCap("setSIMLockStylePage");
            CommonAction.clickById("android:id/switchWidget");
            CommonAction.setText("1234");
            device.pressBack();
            CommonAction.clickByText("确定");
            CommonAction.clickByText("更改SIM卡PIN码");
            screenshotCap("changeSIMPINPage");
            CommonAction.setText("1234");
            device.pressBack();
            CommonAction.clickByText("确定");
            CommonAction.setText("1234");
            device.pressBack();
            CommonAction.clickByText("确定");
            CommonAction.setText("1234");
            device.pressBack();
            CommonAction.clickByText("确定");
            UiObject2 changeSIMPINObj = UiobjectFinder.findByText("更改SIM卡PIN码");
            Assert.assertTrue("没有能够锁定SIM卡", changeSIMPINObj.isEnabled());
            CommonAction.clickById("android:id/switchWidget");
            CommonAction.setText("1234");
            CommonAction.clickByText("确定");
        }
    }

    /*
    设备管理器
    1.进入到安全界面
    2.点击设备管理器，进入到设备管理器界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test178EnterDevicesManager() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.scrollToText("设备管理器");
            CommonAction.clickByText("设备管理器");
            screenshotCap("deviceManagerPage");
            UiObject2 deviceListObj = UiobjectFinder.findByText("没有可供显示的设备管理器");
            Assert.assertNotNull("没有能够进入到设备管理器界面", deviceListObj);
        }
    }

    /*
    存储类型
    1.进入到安全界面
    2.查看存储类型"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test179CheckStoreStyle() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.scrollToText("存储类型");
            screenshotCap("storageTypePage");
            UiObject2 supportType = UiobjectFinder.findByText("存储类型").getParent().getChildren().get(1);
            String type = supportType.getText();
            if ("P1".equals(Build.MODEL)) {
                Assert.assertEquals("存储类型不正确", "硬件支持", type);
            } else if ("V1".equals(Build.MODEL)) {
                Assert.assertEquals("存储类型不正确", "仅限软件", type);
            }
        }
    }

    /*
    信任的凭据
    1.进入到安全界面
    2.点击信任的凭据，进入到信任的凭据界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test180EnterTrustedCredential() throws UiObjectNotFoundException {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.scrollToText("信任的凭据");
            CommonAction.clickByText("信任的凭据");
            screenshotCap("trustedCredPage");
            CommonAction.scrollToText("com.android.settings:id/system_list", "ACCV");
            screenshotCap("ACCVPage");
            UiObject2 accvSubjectSecObj = UiobjectFinder.findByText("");
            Assert.assertNotNull("在系统信任的凭据中没有找到ACCVRAIZ1", accvSubjectSecObj);
        }
    }

    /*
    从手机存储（SD）安装
    1.进入到安全界面
    2.点击从SD卡安装，进入到选择证书界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test181InstallCertificateFromSDCard() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            if ("P1".equals(Build.MODEL)) {
                CommonAction.scrollToText("从SD卡安装");
                CommonAction.clickByText("从SD卡安装");
            } else if ("V1".equals(Build.MODEL)) {
                CommonAction.scrollToText("从手机存储安装");
                CommonAction.clickByText("从手机存储安装");
            }
            screenshotCap("installCerPage");
            UiObject2 openDocInterfaceObj = UiobjectFinder.findBySelector(By.text("打开文件").pkg("com.android.documentsui"));
            Assert.assertNotNull("未能进入到打开文件界面", openDocInterfaceObj);
        }
    }

    /*
    清除凭证
    1.进入到安全界面
    2.查看清除凭据"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test182ClearCredential() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.scrollToText("清除凭据");
            screenshotCap("clearCrePage");
            UiObject2 clearCAObj = UiobjectFinder.findByText("清除凭据");
            Assert.assertFalse("清除凭据可以使用", clearCAObj.isEnabled());
        }
    }

    /*
    信任的代理
    1.进入到安全界面
    2.查看信任的代理"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test183EnterTrustedAgentInterface() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.scrollToText("信任的代理");
            CommonAction.clickByText("信任的代理");
            screenshotCap("trustedAgentPage");
            UiObject2 trustedTitleObj = UiobjectFinder.findByDesc("向上导航").getParent().getChildren().get(1);
            Assert.assertEquals("信任的代理应该不可用", "安全", trustedTitleObj.getText());
        }
    }

    /*
    【开关】屏幕固定
    1.进入到安全界面
    2.查看屏幕固定状态
    3.点击进入到屏幕固定界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class, CategoryForTest.class})
    @Test
    public void test184CheckScreenPinned() throws RemoteException {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.scrollToText("屏幕固定");
            CommonAction.clickByText("屏幕固定");
            screenshotCap("screenPinnedPage");
            UiObject2 switchObj = UiobjectFinder.findById("com.android.settings:id/switch_widget");
            Assert.assertFalse("屏幕固定开关默认应该是关闭的", switchObj.isChecked());
            switchObj.click();
            screenshotCap("screenPinnedOpenedPage");
            device.pressHome();
            device.pressRecentApps();
            sleep(SHORT_SLEEP);
            device.pressHome();
            device.pressRecentApps();
            UiObject2 pinObj = UiobjectFinder.findById("com.android.systemui:id/lock_to_app_fab");
            screenshotCap("screenPinnedButtonPage");
            Assert.assertNotNull("没有出现固定按钮", pinObj);
            CommonAction.clickByText("设置");
            UiObject2 switchObj1 = UiobjectFinder.findById("com.android.settings:id/switch_widget");
            switchObj1.click();
        }
    }

    /*
    有权查看使用情况的应用
    1.进入到安全界面
    2.点击有权查看使用情况的应用，进入到该界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test185ShowingAccessUsingAPPList() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "安全");
            CommonAction.clickByText("安全");
            screenshotCap("securityPage");
            CommonAction.scrollToText("有权查看使用情况的应用");
            CommonAction.clickByText("有权查看使用情况的应用");
            screenshotCap("accessUsingAppPage");
            UiObject2 appObj = UiobjectFinder.findById("android:id/action_bar").getChildren().get(1);
            String appString = appObj.getText();
            Assert.assertEquals("点击有权查看使用情况的应用未能够进入到对应页面", "有权查看使用情况的应用", appString);
        }
    }

    /*
    【开关】拼写检查工具
    1.进入到语言和输入法
    2.查看拼写检查工具状态"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test186CheckSpellToolsStatus() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "语言和输入法");
            CommonAction.clickByText("语言和输入法");
            screenshotCap("languageInputMethodPage");
            UiObject2 spellCheckObj = UiobjectFinder.findByText("拼写检查工具");
            if ("P1".equals(Build.MODEL)) {
                Assert.assertFalse("拼写检查工具应该是不可用状态", spellCheckObj.isEnabled());
            } else if ("V1".equals(Build.MODEL)) {
                String checkToolString = spellCheckObj.getParent().getChildren().get(1).getText();
                Assert.assertEquals("拼写检查工具不是Android 拼写检查工具 (AOSP)", "Android 拼写检查工具 (AOSP)", checkToolString);
            }
        }
    }

    /*
    个人字典
    1.进入到语言和输入法
    2.点击个人字典进入到个人字典界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test187CheckPersonalDictionary() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "语言和输入法");
            CommonAction.clickByText("语言和输入法");
            screenshotCap("languageInputMethodPage");
            CommonAction.clickByText("个人字典");
            screenshotCap("personalDicPage");
            UiObject2 personalDirObj = UiobjectFinder.findById("android:id/action_bar").getChildren().get(1);
            String personalDirString = personalDirObj.getText();
            Assert.assertEquals("未能够进入到个人字典中", "个人字典", personalDirString);
            if ("V1".equals(Build.MODEL)) {
                CommonAction.clickByText("所有语言");
            }
            CommonAction.clickByDesc("添加");
            CommonAction.setText("personalDictionary");
            CommonAction.clickById("com.android.settings:id/user_dictionary_add_shortcut");
            CommonAction.setText("pd");
            device.pressBack();
            device.pressBack();
            screenshotCap("addedDicPage");
            UiObject2 addedDic = UiobjectFinder.findByText("personalDictionary");
            Assert.assertNotNull("添加个人字典不成功", addedDic);
            addedDic.click();
            CommonAction.clickByDesc("删除");
            UiObject2 addedDic1 = device.findObject(By.text("personalDictionary"));
            Assert.assertNull("删除个人字典不成功", addedDic1);
        }
    }

    /*
    文字转语音TTS输出
    1.进入到语言和输入法
    2.点击文字转语音(TTS)输出，进入到该界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test188CheckWordsToSpeech() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "语言和输入法");
            CommonAction.clickByText("语言和输入法");
            screenshotCap("languageInputMethodPage");
            CommonAction.clickByText("文字转语音 (TTS) 输出");
            screenshotCap("wordsToVoicePage");
            UiObject2 picoTTSObj = UiobjectFinder.findByText("Pico TTS");
            Assert.assertNotNull("没有找到首选引擎Pico TTS", picoTTSObj);
            CommonAction.clickById("com.android.settings:id/tts_engine_settings");
            CommonAction.clickByText("语言");
            CommonAction.clickByText("英文 (美国)");
            screenshotCap("selectLanPage");
            device.pressBack();
            screenshotCap("availableFunPage");
            UiObject2 voiceSpeedObj = UiobjectFinder.findByText("语速");
            UiObject2 listenSampleObj = UiobjectFinder.findByText("收听示例");
            UiObject2 supLanObj = UiobjectFinder.findByText("完全支持英文 (美国)");
            Assert.assertTrue("语速不可点击", voiceSpeedObj.isEnabled());
            Assert.assertTrue("收听示例不可点击", listenSampleObj.isEnabled());
            Assert.assertNotNull("默认语言状态支持存在问题", supLanObj);
            CommonAction.clickById("com.android.settings:id/tts_engine_settings");
            CommonAction.clickByText("语言");
            CommonAction.clickByText("使用系统语言");
        }
    }

    /*
    语音控制
    1.进入到语言和输入法
    2.点击语音控制，进入到语音控制界面"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test189CheckVoiceControlDetails() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "语言和输入法");
            CommonAction.clickByText("语言和输入法");
            screenshotCap("languageInputMethodPage");
            CommonAction.clickByText("语音控制");
            screenshotCap("voiceControlPage");
            UiObject2 switchObj = UiobjectFinder.findById("android:id/switchWidget");
            Assert.assertFalse("默认语音控制开关不是关闭", switchObj.isChecked());
            switchObj.click();
            Assert.assertTrue("默认语音控制开关不是关闭", switchObj.isChecked());
            UiObject2 switchObj1 = UiobjectFinder.findById("android:id/switchWidget");
            switchObj1.click();
            CommonAction.clickByText("照相机");
            screenshotCap("cameraPage");
            UiObject2 descObj = UiobjectFinder.findById("com.mediatek.voicecommand:id/command_preference_title");
            String descString = descObj.getText();
            Assert.assertEquals("语音控制描述不正确", "你可以说 \"拍照\" 或者 \"茄子\" 拍照。", descString);
        }
    }

    /*
    指针速度
    1.进入到语言和输入法
    2.点击指针速度弹出对话框"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test190CheckPointerSpeed() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "语言和输入法");
            CommonAction.clickByText("语言和输入法");
            screenshotCap("languageInputMethodPage");
            CommonAction.scrollToText("指针速度");
            CommonAction.clickByText("指针速度");
            screenshotCap("pointerSpeedPage");
            UiObject2 PointerObj = UiobjectFinder.findById("android:id/alertTitle");
            String alertTitleString = PointerObj.getText();
            Assert.assertEquals("弹出的对话框存在问题", "指针速度", alertTitleString);
        }
    }

    /*
    添加账户
     */
    @Test
    public void test191AddAccount() {

    }

    /*
    编辑删除账户
     */
    @Test
    public void test192EditAccount() {

    }

    /*
    勿扰（免打扰）
    1.进入到声音与通知
    2.点击勿扰，进入到勿扰界面"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test193DoNotDisturb() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "提示音和通知");
            CommonAction.clickByText("提示音和通知");
            screenshotCap("soundsNotificationPage");
            CommonAction.clickByText("勿扰");
            screenshotCap("notDisturbPage");
            CommonAction.clickByText("仅允许优先打扰内容");
            screenshotCap("allowedInfoPage");
            UiObject2 clockObj = UiobjectFinder.findByText("闹钟");
            UiObject2 notifierSwitchObj = UiobjectFinder.findById("android:id/list").getChildren().get(1).findObject(By.res("android:id/switchWidget"));
            UiObject2 activtiySwitchObj = UiobjectFinder.findById("android:id/list").getChildren().get(2).findObject(By.res("android:id/switchWidget"));
            UiObject2 messageSwitchObj = UiobjectFinder.findById("android:id/list").getChildren().get(3).findObject(By.res("android:id/summary"));
            Assert.assertFalse("闹钟默认应该为不可用", clockObj.isEnabled());
            Assert.assertTrue("提醒开关默认应该为开", notifierSwitchObj.isChecked());
            Assert.assertTrue("活动开关默认应该为开", activtiySwitchObj.isChecked());
            Assert.assertEquals("消息默认值应该是无", "无", messageSwitchObj.getText());
            device.pressBack();
            CommonAction.clickByText("自动规则");
            screenshotCap("autoRulePage");
            UiObject2 titleObj = UiobjectFinder.findById("android:id/action_bar").getChildren().get(1);
            String titleString = titleObj.getText();
            Assert.assertEquals("没有能够进入到自动规则页面", "自动规则", titleString);
        }
    }

    /*
    【选项】设备锁定时通知
    1.进入到声音与通知
    2.点击通知，检查设备锁定时默认值"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test194CheckDeviceLockNotificationStyle() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "提示音和通知");
            CommonAction.clickByText("提示音和通知");
            screenshotCap("soundsNotificationPage");
            CommonAction.clickByText("通知");
            screenshotCap("notificationPage");
            UiObject2 showNotifierObj = UiobjectFinder.findByText("设备锁定时").getParent().getChildren().get(1);
            String showTypeString = showNotifierObj.getText();
            Assert.assertEquals("设备锁定时默认为显示所有通知内容", "显示所有通知内容", showTypeString);
        }
    }

    /*
    应用通知（权限控制列表）
    1.进入到声音和通知
    2.点击通知，点击应用通知"
     */
    @Test
    public void test195EnterAPPNotificationManager() {

    }

    /*
    通知使用权
    1.进入到声音与通知
    2.检查通知使用权"
     */
    @Test
    public void test196NotificationAccess() {

    }

    /*
    勿扰权限列表
    1.进入到声音与通知
    2.点击勿扰权限，进入到勿扰权限中"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test197DoNotDisturbList() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "提示音和通知");
            CommonAction.clickByText("提示音和通知");
            screenshotCap("soundsNotificationPage");
            CommonAction.clickByText("“勿扰”权限");
            screenshotCap("notDisturbPermissionPage");
            UiObject2 noDisturbObj = UiobjectFinder.findById("android:id/action_bar").getChildren().get(1);
            String noDisturbObjString = noDisturbObj.getText();
            Assert.assertEquals("未能够进入到“勿扰”权限中", "“勿扰”权限", noDisturbObjString);
        }
    }

    /*
    音效改善
    1.进入到声音和通知
    2.点击音效改善，检查默认状态"
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test198SoundEnhancementValues() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "提示音和通知");
            CommonAction.clickByText("提示音和通知");
            screenshotCap("soundsNotificationPage");
            CommonAction.clickByText("音效改善");
            screenshotCap("voiceImprovePage");
            boolean besA = UiobjectFinder.findByText("BesAudEnh").getParent().getParent().findObject(By.res("android:id/switchWidget")).isChecked();
            boolean besL = UiobjectFinder.findByText("BesLoudness").getParent().getParent().findObject(By.res("android:id/switchWidget")).isChecked();
            String besS = UiobjectFinder.findByText("BesSurround").getParent().getChildren().get(1).getText();
            boolean lossless = UiobjectFinder.findByText("无损蓝牙模式").getParent().getParent().findObject(By.res("android:id/switchWidget")).isChecked();
            Assert.assertFalse("BesAudEnh默认应该为关闭", besA);
            Assert.assertTrue("BesLoudness默认应该为打开", besL);
            Assert.assertEquals("BesSurround默认描述为音效环绕", "音效环绕", besS);
            Assert.assertFalse("无损蓝牙模式默认应该为关闭", lossless);
        }
    }

    /*
    外设媒体控制
     */
    @Test
    public void test199OutsideDeviceMediaControl() {

    }

    /*
    【选项】打印模式
    1.进入到打印中
    2.点击内置打印管理，检查默认打印模式"
     */
    @Test
    public void test200CheckDefaultPrintModel() {

    }

    /*
    文件浏览器
    1.进入到存储设备和USB
    2.点击浏览，进入到内部存储空间浏览"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test201EnterViewFromInteralStorageAndSpace() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "存储设备和 USB");
            CommonAction.clickByText("存储设备和 USB");
            screenshotCap("storageUSBPage");
            CommonAction.scrollToText("浏览");
            CommonAction.clickByText("浏览");
            screenshotCap("scanPage");
            UiObject2 storeObj = device.findObject(By.text("内部存储空间").pkg("com.android.documentsui"));
            Assert.assertNotNull("没有进入到内部存储空间页面", storeObj);
        }
    }

    /*
    恢复出厂设置
    1.进入到备份和重置
    2.点击恢复出厂设置，进入到恢复出厂页面"
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test202FactoryReset() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "其他设置");
            CommonAction.clickByText("其他设置");
            screenshotCap("oterSettingsPage");
            CommonAction.clickByText("备份和重置");
            screenshotCap("backupAndResetPage");
            CommonAction.clickById("com.android.settings:id/execute_master_clear");
            UiObject2 clearTextObj = UiobjectFinder.findByText("确认要清除您的用户数据吗?请注意,此操作确认后无法撤销!");
            Assert.assertNotNull("点击恢复出厂按钮无效", clearTextObj);
        }
    }

    /*
    重置DRM
    1.进入到备份和重置
    2.点击重置DRM，进入到重置DRM页面"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test203ResetDRM() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "备份和重置");
            CommonAction.clickByText("备份和重置");
            screenshotCap("backupAndResetPage");
            CommonAction.clickByText("重置DRM");
            screenshotCap("resetDRMPage");
            CommonAction.clickByText("删除所有DRM许可");
            screenshotCap("deleteAllDRMPage");
            UiObject2 messageObj = UiobjectFinder.findByText("此操作会删除您手机上所有DRM许可，是否继续?");
            Assert.assertNotNull("没有弹出对话框", messageObj);
            CommonAction.clickByText("确定");
            UiObject2 resetDRMObj = UiobjectFinder.findByText("删除所有DRM许可");
            Assert.assertFalse("重置DRM没有被置灰", resetDRMObj.isEnabled());
        }
    }

    /*
    重置网络设置
    1.进入到备份和重置
    2.点击重置网络设置，进入到重置网络设置界面"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test204ResetNetworkSettings() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "备份和重置");
            CommonAction.clickByText("备份和重置");
            screenshotCap("backupAndResetPage");
            CommonAction.clickByText("重置网络设置");
            screenshotCap("resetNetworkSettingsPage");
            CommonAction.clickById("com.android.settings:id/initiate_reset_network");
            UiObject2 resetObj = UiobjectFinder.findById("com.android.settings:id/execute_reset_network");
            Assert.assertNotNull("点击重置网络没有效果", resetObj);
        }
    }

    /*
    【开关】备份我的数据
    1.进入到备份和重置
    2.检查备份我的数据开关状态"
     */
    @Test
    public void test205BackupOfMyData() {

    }

    /*
    备份账户
    1.进入到备份和重置
    2.检查备份账户"
     */
    @Test
    public void test206BackupOfAccounts() {

    }

    /*
    【开关】自动还原
    1.进入到备份和重置
    2.检查自动还原开关状态"
     */
    @Test
    public void test207CheckAutoRestoreStatus() {

    }

    /*
    清除并恢复出厂设置
    1.进入到备份和重置
    2.点击清除并恢复出厂设置进入到该界面"
     */
    @Test
    public void test208ClearAndFactoryReset() {

    }

    /*
    文字转语音TTS输出
    1.进入到无障碍
    2.点击文字转语音TTS输出，进入到该界面"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test209WordsToSpellOutputs() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "无障碍");
            CommonAction.clickByText("无障碍");
            screenshotCap("accessiblePage");
            CommonAction.scrollToText("文字转语音 (TTS) 输出");
            CommonAction.clickByText("文字转语音 (TTS) 输出");
            screenshotCap("wordsToVoicePage");
            UiObject2 picoTTSObj = UiobjectFinder.findByText("Pico TTS");
            Assert.assertNotNull("没有找到首选引擎Pico TTS", picoTTSObj);
            CommonAction.clickById("com.android.settings:id/tts_engine_settings");
            CommonAction.clickByText("语言");
            CommonAction.clickByText("英文 (美国)");
            screenshotCap("selectLanPage");
            device.pressBack();
            screenshotCap("availbleFunPage");
            UiObject2 voiceSpeedObj = UiobjectFinder.findByText("语速");
            UiObject2 listenSampleObj = UiobjectFinder.findByText("收听示例");
            UiObject2 supLanObj = UiobjectFinder.findByText("完全支持英文 (美国)");
            Assert.assertTrue("语速不可点击", voiceSpeedObj.isEnabled());
            Assert.assertTrue("收听示例不可点击", listenSampleObj.isEnabled());
            Assert.assertNotNull("默认语言状态支持存在问题", supLanObj);
            CommonAction.clickById("com.android.settings:id/tts_engine_settings");
            CommonAction.clickByText("语言");
            CommonAction.clickByText("使用系统语言");
        }
    }

    /*
    Android安全补丁程序级别
    1.进入到关于设备
    2.检查安全补丁级别"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test210CheckAndroidPatchLevel() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            String patchLevel = UiobjectFinder.findByText("Android 安全补丁程序级别").getParent().getChildren().get(1).getText();
            if ("P1".equals(Build.MODEL)) {
                Assert.assertEquals("安全补丁等级不正确", P1_PATCH_LEVEL, patchLevel);
            } else if ("V1".equals(Build.MODEL)) {
                Assert.assertEquals("安全补丁等级不正确", V1_PATCH_LEVEL, patchLevel);
            }
        }
    }

    /*
    基带版本
    1.进入到关于设备
    2.检查基带版本"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test211CheckBaseBandVersion() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            String baseVersion = UiobjectFinder.findByText("基带版本").getParent().getChildren().get(1).getText();
            Assert.assertEquals("基带版本不正确", P1_BASE_CODE, baseVersion);
        }
    }

    /*
    内核版本
    1.进入到关于设备
    2.检查内核版本"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test212CheckKenerlVersion() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            CommonAction.scrollToText("内核版本");
            screenshotCap("kernelVersionPage");
            CommonAction.dragToCenter(UiobjectFinder.findByText("内核版本"));
            String kernelVersion_All = UiobjectFinder.findByText("内核版本").getParent().getChildren().get(1).getText();
            String kernelVersion = kernelVersion_All.split("\n")[0];
            if ("P1".equals(Build.MODEL)) {
                Assert.assertEquals("内核版本不正确", P1_KERNEL_CODE, kernelVersion);
            } else if ("V1".equals(Build.MODEL)) {
                Assert.assertEquals("内核版本不正确", V1_KERNEL_CODE, kernelVersion);
            }
        }
    }

    /*
    版本号
    1.进入到关于设备
    2.检查版本号"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test213CheckVersionNO() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            CommonAction.scrollToText("版本号");
            CommonAction.dragToCenter(UiobjectFinder.findByText("版本号"));
            screenshotCap("versionNoPage");
            String versionNo = UiobjectFinder.findByText("版本号").getParent().getChildren().get(1).getText();
            if ("P1".equals(Build.MODEL)) {
                Assert.assertEquals("版本号不正确", P1_VERSION_NAME, versionNo);
            } else if ("V1".equals(Build.MODEL)) {
                Assert.assertEquals("版本号不正确", V1_VERSION_NAME, versionNo);
            }
        }
    }

    /*
    固件版本
    1.进入到关于设备
    2.检查固件版本"
     */
    @Category({CategorySettings_Part2_P1.class, CategorySettings_Part2_V1.class})
    @Test
    public void test214CheckFrimvareVersion() {
        if ("P1".equals(Build.MODEL) || "V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            CommonAction.scrollToText("固件版本");
            CommonAction.dragToCenter(UiobjectFinder.findByText("固件版本"));
            screenshotCap("firmvareVersionPage");
            String fimewareNo = UiobjectFinder.findByText("固件版本").getParent().getChildren().get(1).getText();
            if ("P1".equals(Build.MODEL)) {
                Assert.assertEquals("固件版本号不正确", P1_VERSION_CODE, fimewareNo);
            } else if ("V1".equals(Build.MODEL)) {
                Assert.assertEquals("固件版本号不正确", V1_VERSION_CODE, fimewareNo);
            }
        }
    }

    /*
    FW
    1.进入到关于设备
    2.检查FW"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test215CheckFW() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            CommonAction.scrollToText("FW");
            CommonAction.dragToCenter(UiobjectFinder.findByText("FW"));
            screenshotCap("FWPage");
            String fw = UiobjectFinder.findByText("FW").getParent().getChildren().get(1).getText();
            Assert.assertEquals("固件版本号不正确", P1_FW, fw);
        }
    }

    /*
    安全固件版本
    1.进入到关于设备
    2.检查安全固件版本"
     */
    @Category({CategorySettings_Part2_P1.class})
    @Test
    public void test216SecurityFirmwareVersion() {
        if ("P1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            CommonAction.scrollToText("安全固件版本");
            CommonAction.dragToCenter(UiobjectFinder.findByText("安全固件版本"));
            screenshotCap("securityFirmwareVersionPage");
            String securityCode = UiobjectFinder.findByText("安全固件版本").getParent().getChildren().get(1).getText();
            Assert.assertEquals("安全固件版本号不正确", P1_SECURITY_CODE, securityCode);
        }
    }

    /*
    电池
    1.进入到关于设备
    2.点击进入到电池"
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test217CheckBatteryInAboutInterface() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            CommonAction.scrollToText("电池");
            CommonAction.dragToCenter(UiobjectFinder.findByText("电池"));
            CommonAction.clickByText("电池");
            screenshotCap("batteryPage");
            String battStr = UiobjectFinder.findById("android:id/action_bar").getChildren().get(1).getText();
            Assert.assertEquals("没有进入到电池页面", "电池", battStr);
        }
    }

    /*
    存储
    1.进入到关于设备
    2.点击存储进入到存储设备"
     */
    @Category({CategorySettings_Part2_V1.class})
    @Test
    public void test218EnterStorageFromAboutInterface() {
        if ("V1".equals(Build.MODEL)) {
            CommonAction.scrollToText("com.android.settings:id/dashboard", "关于设备");
            CommonAction.clickByText("关于设备");
            screenshotCap("aboutDevicePage");
            CommonAction.scrollToText("存储");
            CommonAction.dragToCenter(UiobjectFinder.findByText("存储"));
            CommonAction.clickByText("存储");
            screenshotCap("stroagePage");
            String storageStr = UiobjectFinder.findById("android:id/action_bar").getChildren().get(1).getText();
            Assert.assertEquals("没有进入到电池页面", "存储设置", storageStr);
        }
    }

    /*
    工程调试
     */
    @Test
    public void test219EngnieerDebugModel() {

    }

}
