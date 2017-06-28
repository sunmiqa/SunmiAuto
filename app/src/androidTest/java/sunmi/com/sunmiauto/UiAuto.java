package sunmi.com.sunmiauto;

import android.app.Instrumentation;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;

import javax.mail.MessagingException;

/**
 * Created by Administrator on 2017/6/7.
 */

public class UiAuto {
    static Instrumentation instrumentation =InstrumentationRegistry.getInstrumentation();
    static UiDevice device = UiDevice.getInstance(instrumentation);


    @Before
    //每条测试用例开始前执行操作
    public void setup() throws RemoteException {
        device.pressRecentApps();
        sleep(2000);
        UiObject2 cleanObj = device.findObject(By.res("com.android.systemui:id/clean"));
        sleep(2000);
        cleanObj.click();
        sleep(1000);
        device.pressBack();
        sleep(2000);

    }

    @After
    //每条测试用例结束后执行操作
    public void teardown() throws RemoteException {
        sleep(3000);
        device.pressHome();
    }

    @BeforeClass
    //该测试类开始前执行操作
    public static void initLiza() throws RemoteException {
        File dir=new File(Environment.getExternalStorageDirectory()+File.separator+"app_spoon-screenshots");
        class Inner {
            //递归删除文件夹
            private void deleteFile(File file) {
                if (file.exists()) {//判断文件是否存在
                    if (file.isFile()) {//判断是否是文件
                        file.delete();//删除文件
                    } else if (file.isDirectory()) {//否则如果它是一个目录
                        File[] files = file.listFiles();//声明目录下所有的文件 files[];
                        for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                            this.deleteFile(files[i]);//把每个文件用这个方法进行迭代
                        }
                        file.delete();//删除文件夹
                    }
                } else {
                    System.out.println("所删除的文件不存在");
                }
            }
        }
        new Inner().deleteFile(dir);
        dir.mkdir();
    }

    @AfterClass
    //该测试类结束后执行操作
    public static void clearDown() throws MessagingException {
//        new SendMail().sendEmail("hello,this is Alan");
        MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost("smtp.exmail.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUsername("zhangjiyang@sunmi.com");
        mailInfo.setPassword("Sunmi2017");// 您的邮箱密码
        mailInfo.setFromAddress("zhangjiyang@sunmi.com");
        mailInfo.setToAddress("zhangjiyang@sunmi.com");
        mailInfo.setSubject("设置邮箱标题"+Calendar.getInstance().getTime());

        //附件
        String[] attachFileNames={"sdcard//Download//Sunset.jpg"};
        mailInfo.setAttachFileNames(attachFileNames);

        // 这个类主要来发送邮件
        //mailInfo.setContent("设置邮箱内容");
        //SimpleMail.sendTextMail(mailInfo);// 发送文体格式
        StringBuffer demo = new StringBuffer();
        demo.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")
                .append("<html>")
                .append("<head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
                .append("<title>测试邮件</title>")
                .append("<style type=\"text/css\">")
                .append(".test{font-family:\"Microsoft Yahei\";font-size: 18px;color: red;}")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<span class=\"test\">大家好，这里是测试Demo</span>")
                .append("</body>")
                .append("</html>");
        mailInfo.setContent(demo.toString());
        Boolean b = SimpleMail.sendHtmlMail(mailInfo);// 发送html格式
        Log.v("aftersend",b+""+"66666666666");
    }

    @Test
    public void testWiFi(){
        device.swipe(5,device.getDisplayHeight()/2,device.getDisplayWidth()-5,device.getDisplayHeight()/2,20);
//        sleep(2000);
//        UiObject2 settingObj = device.findObject(By.text("Settings"));
//        settingObj.clickAndWait(Until.newWindow(),5000);
//        screenshotCap("setting_interface");
//        UiObject2 wifiops = device.findObject(By.text("Wi‑Fi"));
//        wifiops.clickAndWait(Until.newWindow(),5000);
//        screenshotCap("wifi_interface");
//        UiObject2 wifiButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
//        Assert.assertEquals("Wifi开关应该为打开状态",true,wifiButton.isChecked());
    }

//    @Test
//    public void testEthernet(){
//        UiObject2 settingObj = device.findObject(By.text("Settings"));
//        settingObj.clickAndWait(Until.newWindow(),5000);
//        screenshotCap("setting_interface");
//        UiObject2 ethOps = device.findObject(By.text("Ethernet"));
//        ethOps.clickAndWait(Until.newWindow(),5000);
//        screenshotCap("eth_interface");
//        UiObject2 ethButton = device.findObject(By.res("com.android.settings:id/switch_widget"));
//        Assert.assertEquals("有线网开关应该为打开状态",true,ethButton.isChecked());
//    }
//
//
//    @Test
//    public void testDataUsage(){
//        UiObject2 settingObj = device.findObject(By.text("Settings"));
//        settingObj.clickAndWait(Until.newWindow(),5000);
//        screenshotCap("setting_interface");
//        UiObject2 ethOps = device.findObject(By.text("Data usage"));
//        ethOps.clickAndWait(Until.newWindow(),5000);
//        screenshotCap("dataUsage_interface");
//        UiObject2 dataUsageObj = device.findObject(By.text("Data usage").clazz("android.widget.TextView"));
//        Assert.assertNotNull("未找到Data usage标识，出错",dataUsageObj);
//    }
//
//    @Test
//    public void testUserCenter() {
//        sleep(3000);
//        screenshotCap("before_enter");
//        UiObject2 settingObj = device.findObject(By.text("用户中心"));
//        settingObj.click();
//        sleep(2000);
//        screenshotCap("after_enter");
//        UiObject2 regObj = device.findObject(By.res("com.sunmi.usercenter:id/edit_login_username"));
//        regObj.setText("zhangjiyang@sunmi.com");
//        sleep(2000);
//        screenshotCap("after_click");
//            sleep(2000);
//    }
//
//    @Test
//    public void testOpenWiFi() {
//        screenshotCap("berfore_enter");
//        UiObject2 settingObj = device.findObject(By.text("设置"));
//        settingObj.clickAndWait(Until.newWindow(), 1000);
//        sleep(2000);
//        screenshotCap("after_enter");
//        UiObject2 WlanObj = device.findObject(By.text("WLAN"));
//        WlanObj.click();
//        sleep(2000);
//        screenshotCap("after_click");
//        sleep(2000);
//    }
//
//    @Test
//    public void testOpenBT() {
//        screenshotCap("berfore_enter");
//        sleep(2000);
//        UiObject2 settingObj = device.findObject(By.text("设置"));
//        settingObj.clickAndWait(Until.newWindow(), 1000);
//        sleep(2000);
//        screenshotCap("after_enter");
//        UiObject2 WlanObj = device.findObject(By.text("蓝牙"));
//        WlanObj.click();
//        sleep(2000);
//        screenshotCap("after_click");
//        sleep(2000);
//    }

    public void screenshotCap(String tag){
        StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
        String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
        String methodName = testClass.getMethodName();
        File dir=new File(Environment.getExternalStorageDirectory()
                +File.separator+"app_spoon-screenshots"+File.separator
                +className+File.separator+methodName);
        Log.v("TEST", dir.getAbsolutePath());
        if(!dir.exists()){
            dir.mkdirs();
        }
        String screenshotName = System.currentTimeMillis() + "_" + tag + ".png";
        File screenshotFile = new File(dir, screenshotName);
        device.takeScreenshot(screenshotFile);
        sleep(2000);
    }



    StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
        for(int i = trace.length - 1; i >= 0; --i) {
            StackTraceElement element = trace[i];
            if("android.test.InstrumentationTestCase".equals(element.getClassName()) && "runMethod".equals(element.getMethodName())) {
                return trace[i - 2];
            }

            if("org.junit.runners.model.FrameworkMethod$1".equals(element.getClassName()) && "runReflectiveCall".equals(element.getMethodName())) {
                return trace[i - 2];
            }
        }

        throw new IllegalArgumentException("Could not find test class!");
    }

    public static void sleep(int sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
