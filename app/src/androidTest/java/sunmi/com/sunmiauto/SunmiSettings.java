package sunmi.com.sunmiauto;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static sunmi.com.sunmiauto.SunmiUtil.device;
import static sunmi.com.sunmiauto.SunmiUtil.screenshotCap;
import static sunmi.com.sunmiauto.SunmiUtil.sleep;

/**
 * Created by fengy on 2017/7/8.
 */

public class SunmiSettings {
    @Before
    public void setup(){
        device.pressHome();
    }

    @Test
    public void testWiFi(){
        device.swipe(5,device.getDisplayHeight()/2,device.getDisplayWidth()-5,device.getDisplayHeight()/2,20);
        sleep(2000);
        UiObject2 settingObj = device.findObject(By.text("设置"));
        settingObj.clickAndWait(Until.newWindow(),5000);
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(),5000);
        screenshotCap("wifi_interface");
        UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertEquals("Wifi开关应该为打开状态",true,wifiButton.isChecked());
    }

    @Test
    public void testDataUsage(){
        UiObject2 settingObj = device.findObject(By.text("设置"));
        settingObj.clickAndWait(Until.newWindow(),5000);
        screenshotCap("setting_interface");
        UiObject2 ethOps = device.findObject(By.text("流量使用情况"));
        ethOps.clickAndWait(Until.newWindow(),5000);
        screenshotCap("dataUsage_interface");
        UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况").clazz("android.widget.TextView"));
        Assert.assertNotNull("未找到流量使用情况标识，出错",dataUsageObj);
    }

    @Test
    public void testOpenBT() {
        screenshotCap("berfore_enter");
        sleep(2000);
        UiObject2 settingObj = device.findObject(By.text("设置"));
        settingObj.clickAndWait(Until.newWindow(), 1000);
        sleep(2000);
        screenshotCap("after_enter");
        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
        WlanObj.click();
        sleep(2000);
        screenshotCap("after_click");
        sleep(2000);
    }
}
