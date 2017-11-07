package com.sunmi.sunmiauto.testcases;

import android.content.Context;
import android.os.Build;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.sunmi.sunmiauto.testcategory.CategoryForTest;
import com.sunmi.sunmiauto.testcategory.CategorySystemUI_P1;
import com.sunmi.sunmiauto.testutils.CommandUtils;
import com.sunmi.sunmiauto.testutils.CommonAction;
import com.sunmi.sunmiauto.testutils.TestUtils;
import com.sunmi.sunmiauto.testutils.UiobjectFinder;

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
import java.util.Calendar;

import javax.mail.MessagingException;

import static com.sunmi.sunmiauto.testutils.TestConstants.LONG_WAIT;
import static com.sunmi.sunmiauto.testutils.TestConstants.SHORT_SLEEP;
import static com.sunmi.sunmiauto.testutils.TestUtils.dayToChineseDay;
import static com.sunmi.sunmiauto.testutils.TestUtils.device;
import static com.sunmi.sunmiauto.testutils.TestUtils.hasSIMCard;
import static com.sunmi.sunmiauto.testutils.TestUtils.screenshotCap;
import static com.sunmi.sunmiauto.testutils.TestUtils.sleep;

/**
 * Created by fengy on 2017/9/18.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiSystemUI {
    @Before
    public void setUp() {
        device.pressHome();
        device.pressHome();
    }

    @After
    public void tearDown() {
        device.pressHome();
        device.pressHome();
        device.pressHome();
    }

    @BeforeClass
    //该测试类开始前执行操作
    public static void initLiza(){
        device.registerWatcher("allwatchers",TestUtils.allWatchers);
    }

    @AfterClass
    //该测试类结束后执行操作
    public static void clearDown() throws MessagingException, RemoteException {
        device.removeWatcher("allwatchers");
    }

    /*
    日期时间
    1.下拉通知栏
    2.查看时间和日期显示与系统时间一致
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test121CheckSysUIDateAndTime() {
        CommonAction.swipeToBottom();
        CommonAction.swipeToBottom();
        screenshotCap("systemUI");
        UiObject2 timeObj = UiobjectFinder.findById("com.android.systemui:id/time_view");
        UiObject2 dateObj = UiobjectFinder.findById("com.android.systemui:id/date_expanded");
        Calendar calendar = Calendar.getInstance();
        String ampmString;
        String minuteString;
        String hourString;
        String monthString;
        String dateString;
        int year = calendar.get(Calendar.YEAR);
        int hour_12 = calendar.get(Calendar.HOUR);
        int hour_24 = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int ampm = calendar.get(Calendar.AM_PM);
        if ("V1".equals(Build.MODEL)) {
            if (minute < 10) {
                minuteString = "0" + minute;
            } else {
                minuteString = String.valueOf(minute);
            }
            if (ampm == 0) {
                ampmString = "上午";
            } else {
                ampmString = "下午";
            }
            String time = ampmString + hour_12 + ":" + minuteString;
            String dateGroup = month + "月" + date + "日星期" + dayToChineseDay(day);
            Assert.assertEquals("时间不准确", time, timeObj.getText());
            Assert.assertEquals("日期不准确", dateGroup, dateObj.getText());
        } else if ("P1".equals(Build.MODEL)) {
            if (minute < 10) {
                minuteString = "0" + minute;
            } else {
                minuteString = String.valueOf(minute);
            }
            if (hour_24 < 10) {
                hourString = "0" + hour_24;
            } else {
                hourString = String.valueOf(hour_24);
            }
            String time = hourString + ":" + minuteString;
            if (month < 10) {
                monthString = "0" + month;
            } else {
                monthString = String.valueOf(month);
            }
            if (date < 10) {
                dateString = "0" + date;
            } else {
                dateString = String.valueOf(date);
            }
            String dateGroup = year + "-" + monthString + "-" + dateString;
            Assert.assertEquals("时间不准确", time, timeObj.getText());
            Assert.assertEquals("日期不准确", dateGroup, dateObj.getText());
        }
        CommonAction.swipeToTop();
        screenshotCap("homePage");
    }

    /*
    设置入口
    1.下拉通知栏
    2.点击设置快捷，能够进入到设置界面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test122EnterSettingThroughSysUIEntrence() {
        device.openQuickSettings();
        screenshotCap("quickSettings");
        CommonAction.clickById("com.android.systemui:id/settings_button");
        screenshotCap("settingsHomePage");
        Assert.assertEquals("com.android.settings", device.getCurrentPackageName());
    }

    /*
    电量及电池入口
    1.下拉通知栏
    2.点击电池相关，进入到电池界面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test123EnterBatteryDetailThroughSysUIEntrence() {
        device.openQuickSettings();
        screenshotCap("quickSettingsPage");
        CommonAction.clickById("com.android.systemui:id/battery_level");
        screenshotCap("batteryPage");
        UiObject2 batteryHistoryObj = UiobjectFinder.findById("com.android.settings:id/battery_history_chart");
        Assert.assertNotNull("未能够通过电池快捷进入到电池界面", batteryHistoryObj);
    }

    /*
    屏幕亮度调节
    1.下拉通知栏
    2.检查存在亮度调节控件
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test124CheckAdjustBrightnessThroughQuickSetting() {
        device.openQuickSettings();
        screenshotCap("quickSettingsPage");
        UiObject2 brightnessObj = UiobjectFinder.findById("com.android.systemui:id/slider");
        Assert.assertNotNull("未能够找到亮度调节控件", brightnessObj);
    }

    /*
    WIFI开关
    1.下拉通知栏
    2.检查Wifi开关状态
    3.点击可以进入到wifi界面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test125CheckWifiQuickSettings() {
        UiObject2 wifiGroupObj = null;
        CommonAction.swipeToBottom();
        CommonAction.swipeToBottom();
        screenshotCap("quickSettingsPage");
        if ("P1".equals(Build.MODEL)) {
            wifiGroupObj = UiobjectFinder.findByClazz("android.view.ViewGroup");
        } else if ("V1".equals(Build.MODEL)) {
            wifiGroupObj = UiobjectFinder.findByClazz("android.view.View", 3);
        }
        UiObject2 moreWifiObj = wifiGroupObj.findObject(By.clazz("android.widget.LinearLayout"));
        moreWifiObj.click();
        screenshotCap("quickSettingsWifiPage");
        CommonAction.clickById("android:id/button2");
        screenshotCap("settingsWifiPage");
        device.wait(Until.hasObject(By.text("WLAN").pkg("com.android.settings")), LONG_WAIT);
        UiObject2 wifiInterfaceObj = device.findObject(By.text("WLAN").pkg("com.android.settings"));
        Assert.assertNotNull("未能够进入到设置中的WLAN", wifiInterfaceObj);
    }

    /*
    蓝牙开关
    1.下拉通知栏
    2.检查蓝牙开关状态
    3.点击可以进入到蓝牙界面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test126CheckBTQuickSettings() {
        UiObject2 BTGroupObj = null;
        CommonAction.swipeToBottom();
        CommonAction.swipeToBottom();
        screenshotCap("quickSettingsPage");
        sleep(SHORT_SLEEP);
        if ("P1".equals(Build.MODEL)) {
            BTGroupObj = UiobjectFinder.findByClazz("android.view.ViewGroup", 3);
        } else if ("V1".equals(Build.MODEL)) {
            BTGroupObj = UiobjectFinder.findByClazz("android.widget.LinearLayout", 4);
        }
        UiObject2 moreBTObj = BTGroupObj.findObject(By.clazz("android.widget.LinearLayout"));
        moreBTObj.click();
        screenshotCap("quickSettingsBTPage");
        CommonAction.clickById("android:id/button2");
        screenshotCap("settingsBTPage");
        sleep(SHORT_SLEEP);
        if("P1".equals(Build.MODEL)){
            UiObject2 BTSwitch = UiobjectFinder.findByClazz("android.widget.Switch");
            if(BTSwitch.isChecked()){
                BTSwitch.click();
            }
        }
        UiObject2 BTInterfaceObj = device.findObject(By.text("蓝牙").pkg("com.android.settings"));
        Assert.assertNotNull("未能够进入到设置中的蓝牙", BTInterfaceObj);
    }

    /*
    移动网络开关
    1.下拉通知栏
    2.检查SIM卡状态
    3.点击可以进入到流量使用情况界面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test127CheckSIMCardClick() {
        CommonAction.swipeToBottom();
        CommonAction.swipeToBottom();
        screenshotCap("quickSettingsPage");
        sleep(SHORT_SLEEP);
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByClass("android.view.ViewGroup", 2);
        } else if ("V1".equals(Build.MODEL)) {
            UiObject2 uiObject2 = UiobjectFinder.findByClazz("android.view.View", 5);
            Log.v("myautotest", String.valueOf(uiObject2.getVisibleBounds().centerX()));
            uiObject2.getChildren().get(2).click();
        }
        screenshotCap("dataUsedPage");
        UiObject2 dataInterfaceObj = null;
        if(hasSIMCard()){
            dataInterfaceObj = UiobjectFinder.findBySelector(By.text("流量使用情况").pkg("com.android.systemui"));
        }else{
            dataInterfaceObj = UiobjectFinder.findBySelector(By.text("流量使用情况").pkg("com.android.settings"));
        }
        Assert.assertNotNull("未能够进入到设置中的流量使用情况", dataInterfaceObj);
    }

    /*
    飞行模式开关
    1.下拉通知栏
    2.检查飞行模式状态
    3.开关飞行模式
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test128CheckAirplaneClick() {
        CommonAction.swipeToBottom();
        CommonAction.swipeToBottom();
        screenshotCap("quickSettingsPage");
        sleep(SHORT_SLEEP);
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByClass("android.view.ViewGroup", 4);
        } else if ("V1".equals(Build.MODEL)) {
            UiObject2 uiObject2 = UiobjectFinder.findByClazz("android.view.View", 10);
            Log.v("myautotest", String.valueOf(uiObject2.getVisibleBounds().centerX()));
            uiObject2.click();
        }
        screenshotCap("airplaneMode");
        sleep(SHORT_SLEEP);
        Context context = InstrumentationRegistry.getContext();
        int airplane = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        Log.v("myautotest", String.valueOf(airplane));
        Assert.assertEquals("切换飞行模式失败", 1, airplane);
        if ("P1".equals(Build.MODEL)) {
            CommonAction.clickByClass("android.view.ViewGroup", 4);
        } else if ("V1".equals(Build.MODEL)) {
            UiObject2 uiObject2 = UiobjectFinder.findByClazz("android.view.View", 10);
            Log.v("myautotest", String.valueOf(uiObject2.getVisibleBounds().centerX()));
            uiObject2.click();
        }
    }

    /*
    通知列表
    1.下拉通知栏
    2.检查通知
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test129CheckNotificationList() {
        CommonAction.swipeToBottom();
        CommonAction.swipeToBottom();
        screenshotCap("quickSettingsPage");
        sleep(SHORT_SLEEP);
        UiObject2 notificationObj = UiobjectFinder.findById("com.android.systemui:id/notification_stack_scroller");
        Assert.assertNotNull("不存在通知列表", notificationObj);
    }

    /*
    返回键
    1.下拉通知栏
    2.按下返回键回到桌面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test130CheckPressBack() {
        device.openNotification();
        sleep(SHORT_SLEEP);
        screenshotCap("quickSettingsPage");
        device.runWatchers();
        Assert.assertEquals("没有进入到快捷设置面板", "com.android.systemui", device.getCurrentPackageName());
        device.pressBack();
        screenshotCap("homePage");
        device.runWatchers();
        Assert.assertEquals("返回键没有效果", "com.woyou.launcher", device.getCurrentPackageName());
    }

    /*
    HOME键
    1.下拉通知栏
    2.按下Home键回到桌面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test130CheckPressHome() {
        device.openNotification();
        sleep(SHORT_SLEEP);
        screenshotCap("quickSettingsPage");
        Assert.assertEquals("没有进入到快捷设置面板", "com.android.systemui", device.getCurrentPackageName());
        device.pressHome();
        screenshotCap("homePage");
        Assert.assertEquals("返回键没有效果", "com.woyou.launcher", device.getCurrentPackageName());
    }

    /*
    任务管理器键（概览）
    1.按下最近任务键
    2.检查弹出最近任务列表
     */
    @Category({CategorySystemUI_P1.class, CategoryForTest.class})
    @Test
    public void test131CheckPressMenu() throws RemoteException {
        device.pressHome();
        device.pressRecentApps();
        sleep(SHORT_SLEEP);
        device.pressHome();
        device.pressRecentApps();
        screenshotCap("recentAppsPage");
        UiObject2 clearBtn = UiobjectFinder.findById("com.android.systemui:id/loading");
        Assert.assertNotNull("菜单键没有效果", clearBtn);
    }

    /*
    批量清理进程
    1.按下最近任务键
    2.点击批量清理按钮，任务被批量清理
     */
    @Category({CategorySystemUI_P1.class, CategoryForTest.class})
    @Test
    public void test132CheckClearButton() throws RemoteException {
        TestUtils.findAppAndOpenByText("设置");
        screenshotCap("settingsPage");
        device.pressHome();
        device.pressRecentApps();
        sleep(SHORT_SLEEP);
        device.pressHome();
        device.pressRecentApps();
        sleep(SHORT_SLEEP);
        screenshotCap("recentAppsPage");
        UiObject2 blankRecentApps = device.findObject(By.text("您最近浏览过的屏幕会显示在此处"));
        Assert.assertNull("设置不显示在最近使用应用中", blankRecentApps);
        CommonAction.clickById("com.android.systemui:id/loading");
        device.pressHome();
        device.pressRecentApps();
        sleep(SHORT_SLEEP);
        device.pressHome();
        device.pressRecentApps();
        sleep(SHORT_SLEEP);
        screenshotCap("recentAppsPageAfterClear");
        UiObject2 blankRecentApps1 = device.findObject(By.text("您最近浏览过的屏幕会显示在此处"));
        Assert.assertNotNull("没有能够清空最近应用列表", blankRecentApps1);
    }

    /*
    充电动画
    1.锁屏，查看充电动画（注意充满与否状态不同）
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test133CheckLockScreenChargeAnimation() throws RemoteException, IOException {
        device.sleep();
        device.wakeUp();
        screenshotCap("lockScreenPage");
        if ("V1".equals(Build.MODEL) || "P1".equals(Build.MODEL)) {
            String batteryInfo = device.executeShellCommand("dumpsys battery");
            Log.v("myautotest", batteryInfo);
            int batteryLevel = Integer.valueOf(CommandUtils.match(batteryInfo, "level:", "\\n").get(0).trim());
            Log.v("myautotest", String.valueOf(batteryLevel));
            boolean chargeing;
            boolean ACCharge = Boolean.valueOf(CommandUtils.match(batteryInfo, "AC powered:", "\\n").get(0).trim());
            boolean USBCharge = Boolean.valueOf(CommandUtils.match(batteryInfo, "USB powered:", "\\n").get(0).trim());
            boolean WirelessCharge = Boolean.valueOf(CommandUtils.match(batteryInfo, "Wireless powered:", "\\n").get(0).trim());
            if (ACCharge || USBCharge || WirelessCharge) {
                UiObject2 chargeInfoObj = UiobjectFinder.findById("com.android.systemui:id/keyguard_indication_text");
                String chargeInfo = chargeInfoObj.getText();
                boolean b = chargeInfo.equals("已充满") || chargeInfo.equals("充电完成");
                if (batteryLevel == 100) {
                    CommonAction.swipeToTop();
                    screenshotCap("homePage");
                    Assert.assertTrue("当前电量为100，充电描述信息不正确",b);
                } else if (batteryLevel < 100) {
                    CommonAction.swipeToTop();
                    screenshotCap("homePage");
                    Log.v("myautotest", chargeInfo.substring(0, 4));
                    Assert.assertEquals("当前电量低于100，充电描述信息不正确", "正在充电", chargeInfo.substring(0, 4));
                }
            } else {
                CommonAction.swipeToTop();
                screenshotCap("homePage");
                Assert.fail("锁屏充电信息检查必须要设备处于充电状态，请连接充电设备再进行该项测试");
            }
        }
    }

    /*
    显示时间日期
    1.锁屏，检查显示的时间和日期格式
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test134CheckLockScreenTimeAndDate() throws RemoteException {
        device.sleep();
        device.wakeUp();
        screenshotCap("lockScreenPage");
        UiObject2 timeObj = UiobjectFinder.findById("com.android.systemui:id/clock_text");
        UiObject2 dateObj = UiobjectFinder.findById("com.android.systemui:id/date_view");
        UiObject2 ampmObj = device.findObject(By.res("com.android.systemui:id/am_pm"));
        Calendar calendar = Calendar.getInstance();
        String ampmString;
        String minuteString;
        String hourString;
        String monthString;
        String dateString;
        int year = calendar.get(Calendar.YEAR);
        int hour_12 = calendar.get(Calendar.HOUR);
        if(hour_12 == 0){
            hour_12 = 12;
        }
        int hour_24 = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int ampm = calendar.get(Calendar.AM_PM);
        if ("V1".equals(Build.MODEL)) {
            if (minute < 10) {
                minuteString = "0" + minute;
            } else {
                minuteString = String.valueOf(minute);
            }
            if (ampm == 0) {
                ampmString = "上午";
            } else {
                ampmString = "下午";
            }
            String time = hour_12 + ":" + minuteString;
            String dateGroup = month + "月" + date + "日星期" + dayToChineseDay(day);
            Assert.assertEquals("时间不准确", time, timeObj.getText());
            Assert.assertEquals("日期不准确", dateGroup, dateObj.getText());
            Assert.assertEquals("上下午不准确", ampmString, ampmObj.getText());
        } else if ("P1".equals(Build.MODEL)) {
            if (minute < 10) {
                minuteString = "0" + minute;
            } else {
                minuteString = String.valueOf(minute);
            }
            if (hour_24 < 10) {
                hourString = "0" + hour_24;
            } else {
                hourString = String.valueOf(hour_24);
            }
            String time = hourString + ":" + minuteString;
            if (month < 10) {
                monthString = "0" + month;
            } else {
                monthString = String.valueOf(month);
            }
            if (date < 10) {
                dateString = "0" + date;
            } else {
                dateString = String.valueOf(date);
            }
            String dateGroup = year + "-" + monthString + "-" + dateString;
            Assert.assertEquals("时间不准确", time, timeObj.getText());
            Assert.assertEquals("日期不准确", dateGroup, dateObj.getText());
        }
        CommonAction.swipeToTop();
    }

    /*
    解锁
    1.锁屏，解锁，进入到桌面
     */
    @Category({CategorySystemUI_P1.class})
    @Test
    public void test135CheckUnlockScreen() throws RemoteException {
        device.sleep();
        device.wakeUp();
        screenshotCap("lockScreenPage");
        CommonAction.swipeToTop();
        screenshotCap("homePage");
        Assert.assertEquals("滑动解锁没有成功", "com.woyou.launcher", device.getCurrentPackageName());
    }
}
