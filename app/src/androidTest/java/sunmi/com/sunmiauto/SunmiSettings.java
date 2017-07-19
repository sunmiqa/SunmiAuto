

package sunmi.com.sunmiauto;

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

import static sunmi.com.sunmiauto.SunmiUtil.device;
import static sunmi.com.sunmiauto.SunmiUtil.screenshotCap;
import static sunmi.com.sunmiauto.SunmiUtil.sleep;

/**
 * Created by fengy on 2017/7/8.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SunmiSettings {
    final int timeoutSeconds = 20000;

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
        setObj.clickAndWait(Until.newWindow(),timeoutSeconds);
    }

    @Test
    public void test001WiFi(){
        screenshotCap("setting_interface");
        UiObject2 wifiops = device.findObject(By.text("WLAN"));
        wifiops.clickAndWait(Until.newWindow(),timeoutSeconds);
        screenshotCap("wifi_interface");
        UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
        Assert.assertEquals("Wifi开关默认应该为打开状态",true,wifiButton.isChecked());
    }

    @Test
    public void test002DataUsage(){
        screenshotCap("setting_interface");
        UiObject2 ethOps = device.findObject(By.text("流量使用情况"));
        ethOps.clickAndWait(Until.newWindow(),timeoutSeconds);
        screenshotCap("dataUsage_interface");
        UiObject2 dataUsageObj = device.findObject(By.text("流量使用情况").clazz("android.widget.TextView"));
        Assert.assertNotNull("未找到流量使用情况标识",dataUsageObj);
    }

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
    
    @Test
    public void test004ModifyIbeacon(){
    }
}
