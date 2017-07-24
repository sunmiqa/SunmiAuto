

package sunmi.com.sunmiauto.testcases;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created by fengy on 2017/7/8.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiSettings {
    final int timeoutSeconds = 20000;
    final String networkNameTest = "AutoTestNetwork";
    final String networkPwdTest = "autotest388";

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
        UiObject2 setObj = SunmiUtil.device.findObject(By.text("设置"));
        long end = System.currentTimeMillis();
        Log.v("sleepTime", String.valueOf(end-begin));
        setObj.clickAndWait(Until.newWindow(),timeoutSeconds);
    }

    @Test
    public void test001WiFi(){
        SunmiUtil.screenshotCap("setting_interface");
        UiObject2 wifiops = SunmiUtil.device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(),timeoutSeconds);
        SunmiUtil.screenshotCap("wifi_interface");
        UiObject2 wifiButton = SunmiUtil.device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertEquals("Wifi开关默认应该为打开状态",true,wifiButton.isChecked());
    }

    @Test
    public void test002DataUsage(){
        SunmiUtil.screenshotCap("setting_interface");
        UiObject2 ethOps = SunmiUtil.device.findObject(By.text("流量使用情况"));
        ethOps.clickAndWait(Until.newWindow(),timeoutSeconds);
        SunmiUtil.screenshotCap("dataUsage_interface");
        UiObject2 dataUsageObj = SunmiUtil.device.findObject(By.text("流量使用情况").clazz("android.widget.TextView"));
        Assert.assertNotNull("未找到流量使用情况标识",dataUsageObj);
    }

    @Test
    public void test003OpenBT() {
        SunmiUtil.screenshotCap("after_enter");
        UiObject2 WlanObj = SunmiUtil.device.findObject(By.text("蓝牙"));
        WlanObj.click();
        SunmiUtil.sleep(2000);
        SunmiUtil.screenshotCap("after_click");
        UiObject2 BTButtonObj = SunmiUtil.device.findObject(By.res("com.android.settings:id/switch_widget"));
        SunmiUtil.sleep(2000);
        Assert.assertTrue("蓝牙开关默认不是打开状态",BTButtonObj.isChecked());

    }

    @Test
    public void test004AddNetwork(){
//        screenshotCap("after_enter");
//        UiObject2 WlanObj = device.findObject(By.text("WLAN"));
//        WlanObj.clickAndWait(Until.newWindow(),timeoutSeconds);
//        UiObject2 moreBtn = device.findObject(By.clazz("android.widget.ImageButton").desc("更多选项"));
//        moreBtn.clickAndWait(Until.newWindow(),timeoutSeconds);
//        UiObject2 addNetworkObj = device.findObject(By.text("添加网络"));
//        addNetworkObj.clickAndWait(Until.newWindow(),timeoutSeconds);
//        UiObject2 networkName = device.findObject(By.focused(true));
//        networkName.setText(networkNameTest);
//        UiObject2 securityOpt = device.findObject(By.res("com.android.settings:id/security"));
//        securityOpt.clickAndWait(Until.newWindow(),timeoutSeconds);
//        UiObject2 WPAWPA2PSK = device.findObject(By.text("WPA/WPA2 PSK"));
//        WPAWPA2PSK.clickAndWait(Until.newWindow(),timeoutSeconds);
//        UiObject2 pwdObj = device.findObject(By.res("com.android.settings:id/password"));
//        pwdObj.setText(networkPwdTest);
//        UiObject2 saveObj = device.findObject(By.res("android:id/button1"));
//        saveObj.clickAndWait(Until.newWindow(),timeoutSeconds);
    }
    
    @Test
    public void test004ModifyIbeacon(){
    }
}
