

package sunmi.com.sunmiauto.testcases;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.SearchCondition;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;

import sunmi.com.sunmiauto.testcategory.CategorySettingsTests;
import sunmi.com.sunmiauto.testcategory.CategorySettingsTests1;

import static sunmi.com.sunmiauto.testcases.SunmiUtil.device;
import static sunmi.com.sunmiauto.testcases.SunmiUtil.screenshotCap;
import static sunmi.com.sunmiauto.testcases.SunmiUtil.sleep;

/**
 * Created by fengy on 2017/7/8.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiSettings {
    final int TIMESECONDS = 20000;
    final String NETWORKNAMETEST = "AutoTestNetwork";
    final String NETWORKPWDTEST = "autotest388";
    final String BT_NAME = "V1";
    final String USER_CENTER_PKG = "com.sunmi.usercenter";
    final ArrayList<String> SETTINGSLIST = new ArrayList<String>(){
        {
            add("WLAN");
            add("蓝牙");
            add("流量使用情况");
            add("更多");
            add("用户中心");
            add("显示");
            add("音量键自定义");
            add("安全");
            add("日期和时间");
            add("提示音和通知");
            add("语言和输入法");
            add("打印设置");
            add("其他设置");
            add("应用程序管理");
            add("无障碍");
            add("关于设备");
        }
    };

    @BeforeClass
    public static void beforeTestClass(){
        Configurator configurator = Configurator.getInstance();
        Long i1 = Configurator.getInstance().getActionAcknowledgmentTimeout();
        Long i2 = Configurator.getInstance().getKeyInjectionDelay();
        Long i3 = Configurator.getInstance().getScrollAcknowledgmentTimeout();
        Long i4 = Configurator.getInstance().getWaitForIdleTimeout();
        Long i5 = Configurator.getInstance().getWaitForSelectorTimeout();
        Log.v("ActionTimeOut",i1.toString());
        Log.v("ActionTimeOut",i2.toString());
        Log.v("ActionTimeOut",i3.toString());
        Log.v("ActionTimeOut",i4.toString());
        Log.v("ActionTimeOut",i5.toString());
        configurator.setKeyInjectionDelay(1000L);
        Log.v("mmmmm",String.valueOf(configurator.getKeyInjectionDelay()));
    }

    @Before
    public void setup() throws IOException, RemoteException {
        SunmiUtil.clearAllRecentApps();
        SunmiUtil.findAppByText("设置");
        long begin = System.currentTimeMillis();
        UiObject2 setObj = device.findObject(By.text("设置"));
        long end = System.currentTimeMillis();
        Log.v("sleepTime", String.valueOf(end-begin));
        setObj.clickAndWait(Until.newWindow(),TIMESECONDS);
    }

    @Category(CategorySettingsTests.class)
    @Test
    public void test001WiFi(){
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(),TIMESECONDS);
        screenshotCap("wifi_interface");
        UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertEquals("Wifi开关默认应该为打开状态",true,wifiButton.isChecked());
    }

    @Category(CategorySettingsTests.class)
    @Test
    public void test002DataUsage(){
        screenshotCap("setting_interface");
        UiObject2 ethOps = device.findObject(By.text("流量使用情况"));
        ethOps.clickAndWait(Until.newWindow(),TIMESECONDS);
        screenshotCap("dataUsage_interface");
        UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况").clazz("android.widget.TextView"));
        Assert.assertNotNull("未找到流量使用情况标识",dataUsageObj);
    }

    @Category(CategorySettingsTests.class)
    @Test
    public void test003OpenBT() {
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.click();
        sleep(2000);
        screenshotCap("after_click");
        UiObject2 BTButtonObj = device.findObject(By.res("com.android.settings:id/switch_widget"));
        sleep(2000);
        Assert.assertTrue("蓝牙开关默认不是打开状态",BTButtonObj.isChecked());

    }

    //检查WLAN中添加网络成功
    @Category(CategorySettingsTests.class)
    @Test
    public void test004AddNetwork(){
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("WLAN"));
        WlanObj.click();
        sleep(1000);
        UiObject2 moreBtn = device.findObject(By.clazz("android.widget.ImageButton").desc("更多选项"));
        moreBtn.click();
        sleep(1000);
        UiObject2 addNetworkObj = device.findObject(By.text("添加网络"));
        addNetworkObj.click();
        sleep(1000);
        UiObject2 networkName = device.findObject(By.focused(true));
        networkName.setText(NETWORKNAMETEST);
        sleep(1000);
        device.pressBack();
        UiObject2 securityOpt = device.findObject(By.res("com.android.settings:id/security"));
        securityOpt.click();
        sleep(1000);
        UiObject2 WPAWPA2PSK = device.findObject(By.text("WPA/WPA2 PSK"));
        WPAWPA2PSK.click();
        sleep(1000);
        UiObject2 pwdObj = device.findObject(By.res("com.android.settings:id/password"));
        pwdObj.setText(NETWORKPWDTEST);
        sleep(1000);
        UiObject2 saveObj = device.findObject(By.res("android:id/button1"));
        saveObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        sleep(1000);
        UiObject2 moreBtn1 = device.findObject(By.clazz("android.widget.ImageButton").desc("更多选项"));
        moreBtn1.click();
        sleep(1000);
        UiObject2 savedNetworkObj = device.findObject(By.text("已保存的网络"));
        savedNetworkObj.click();
        sleep(1000);
        UiObject2 addedNetwork = device.findObject(By.text(NETWORKNAMETEST));
        Assert.assertNotNull("未找到添加的"+NETWORKNAMETEST,addedNetwork);
        addedNetwork.click();
        sleep(1000);
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
            optObj.clickAndWait(Until.newWindow(),5000);
            if(SETTINGSLIST.get(i).equalsIgnoreCase("用户中心")){
                Assert.assertEquals("未找到"+SETTINGSLIST.get(i),USER_CENTER_PKG,device.getCurrentPackageName());
                device.pressBack();
                continue;
            }
            if(SETTINGSLIST.get(i).equalsIgnoreCase("音量键自定义")){
                UiObject2 volumeKeyObj = device.findObject(By.pkg("com.sunmi.sidekey").text("音量键自定义"));
                Assert.assertNotNull("未找到"+SETTINGSLIST.get(i),volumeKeyObj);
                device.pressBack();
                continue;
            }
            UiObject2 optEnterObj = device.findObject(By.clazz("android.widget.TextView").text(SETTINGSLIST.get(i)));
            Assert.assertNotNull("未找到"+SETTINGSLIST.get(i),optEnterObj);
            device.pressBack();
        }
    }
    //检查WLAN为打开状态
    @Category(CategorySettingsTests.class)
    @Test
    public void test006CheckWLANStatus(){
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(),TIMESECONDS);
        screenshotCap("wifi_interface");
        UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertEquals("Wifi开关默认应该为打开状态",true,wifiButton.isChecked());
    }

    //能够进入到WLAN高级菜单中
    @Category(CategorySettingsTests.class)
    @Test
    public void test007CheckEnterAdvPass(){
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(),TIMESECONDS);
        screenshotCap("wifi_interface");
        sleep(1000);
        UiObject2 moreBtn = device.findObject(By.clazz("android.widget.ImageButton").desc("更多选项"));
        moreBtn.click();
        sleep(1000);
        UiObject2 addNetworkObj = device.findObject(By.text("高级"));
        addNetworkObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        sleep(1000);
        UiObject2 advWLANObj = device.findObject(By.text("高级WLAN"));
        Assert.assertNotNull("没有能够进入到高级WLAN页面",advWLANObj);
    }

    //检查蓝牙默认设备名称正确
    @Category(CategorySettingsTests.class)
    @Test
    public void test008CheckBTNamePASS() {
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        sleep(1000);
        screenshotCap("after_click");
        UiObject2 moreOptBtn = device.findObject(By.desc("更多选项"));
        moreOptBtn.click();
        sleep(1000);
        UiObject2 renameObj = device.findObject(By.text("重命名此设备"));
        renameObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        UiObject2 nameObj = device.findObject(By.focused(true));
        String BTName = nameObj.getText();
        Assert.assertEquals("蓝牙名称不正确",BT_NAME,BTName);

    }

    //检查重命名蓝牙设备名称成功
    @Category(CategorySettingsTests.class)
    @Test
    public void test009CheckBTRenamePASS() {
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        sleep(1000);
        screenshotCap("after_click");
        UiObject2 moreOptBtn = device.findObject(By.desc("更多选项"));
        moreOptBtn.click();
        sleep(1000);
        UiObject2 renameObj = device.findObject(By.text("重命名此设备"));
        renameObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        UiObject2 nameObj = device.findObject(By.focused(true));
        nameObj.clear();
        nameObj.setText("TESTV1");
        UiObject2 renameBtn = device.findObject(By.text("重命名"));
        renameBtn.clickAndWait(Until.newWindow(),TIMESECONDS);
        UiObject2 moreOptBtn1 = device.findObject(By.desc("更多选项"));
        moreOptBtn1.click();
        sleep(1000);
        UiObject2 renameObj1 = device.findObject(By.text("重命名此设备"));
        renameObj1.clickAndWait(Until.newWindow(),TIMESECONDS);
        UiObject2 nameObj1 = device.findObject(By.focused(true));
        String BTName = nameObj1.getText();
        Assert.assertEquals("蓝牙改名失败","TESTV1",BTName);
        nameObj1.clear();
        nameObj1.setText(BT_NAME);
        UiObject2 renameBtn1 = device.findObject(By.text("重命名"));
        renameBtn1.clickAndWait(Until.newWindow(),TIMESECONDS);
    }

    //检查流量使用情况存在概览和WLAN选项
    @Category(CategorySettingsTests.class)
    @Test
    public void test010CheckDataUsage() {
        screenshotCap("after_enter");
        UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况"));
        dataUsageObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        UiObject2 gernalObj = device.findObject(By.selected(true).text("概览"));
        Assert.assertNotNull("未找到概览tab",gernalObj);
        UiObject2 wlanObj = device.findObject(By.text("WLAN").res("android:id/title"));
        Assert.assertNotNull("未找到WLANtab",wlanObj);
    }

    //检查更多中飞行模式开关状态正确
    @Category(CategorySettingsTests.class)
    @Test
    public void test011CheckAirPlaneStatus() {
        screenshotCap("after_enter");
        UiObject2 moreObj = device.findObject(By.text("更多"));
        moreObj.clickAndWait(Until.newWindow(),TIMESECONDS);
        UiObject2 airplaneSwitch = device.findObject(By.text("飞行模式")).getParent().getParent().findObject(By.res("android:id/switchWidget"));
        Boolean airplaneOpen = airplaneSwitch.isChecked();
        Assert.assertFalse("飞行模式不是关闭状态",airplaneOpen);
    }
    //检查网络共享与便携热点可用
    //检查Ibeacon内容
    //检查显示下亮度可用
    //检查显示下休眠时间正确
    //检查显示下字体大小正确
    //检查显示下投射可进入
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
