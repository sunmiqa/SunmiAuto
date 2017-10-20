package com.sunmi.sunmiauto.testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by fengy on 2017/7/26.
 */

public class TestConstants{
    public final static String LOG_KEYWORD = "ZJTest => ";
    public final static long WAIT = 3000L;
    final static String SNAPSHOT_PATH = "/sdcard/snapshot";
    final static String LOGCAT_LOG_PATH = "/sdcard/log";
    public final static String LOG_LEVEL_DEBUG = "D";
    public static final int SHORT_SLEEP = 2000;//短暂休眠
    public static final int LONG_WAIT = 20000;//等待新窗口出现最长时间
    public static final int DOWNLOAD_WAIT = 300000;//等待应用市场应用下载完成最长时间
    public static final String NETWORKNAMETEST = "AutoTestNetwork";//添加网络名称
    public static final String NETWORKPWDTEST = "autotest388";//添加网络密码
    public static final String V1_BT_NAME = "V1";//蓝牙名称
    public static final String P1_BT_NAME = "Sunmi P1";
    public static final String USER_CENTER_PKG = "com.sunmi.usercenter";//用户中心包名
    public static final String SEARCH_APP = "DUDU";//应用市场搜索内容
    public static final String USERCENTER_ACCOUNT = "zhangjiyang@sunmi.com";//用户中心账号
    public static final String USERCENTER_PWD = "sunmi2016";//用户中心密码
    public static final String LOG_V = "myautotest";
    public static final ArrayList<String> V1_SETTINGSLIST = new ArrayList<String>(){//设置列表所有菜单项
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
            add("打印");
            add("其他设置");
            add("应用程序管理");
            add("无障碍");
            add("关于设备");
        }
    };

    public static final ArrayList<String> P1_SETTINGSLIST = new ArrayList<String>(){//设置列表所有菜单项
        {
            add("WLAN");
            add("蓝牙");
            add("流量使用情况");
            add("更多");
            add("显示");
            add("音量键自定义");
            add("提示音和通知");
            add("应用");
            add("存储设备和 USB");
            add("电池");
            add("内存");
            add("位置信息");
            add("安全");
            add("用户中心");
            add("语言和输入法");
            add("备份和重置");
            add("日期和时间");
            add("定时开关机");
            add("无障碍");
            add("打印");
            add("关于设备");
        }
    };
    public static final String P1_VERSION_NAME = getProp("P1_版本号");
    public static final String P1_VERSION_CODE = getProp("P1_固件版本");
    public static final String P1_SECURITY_CODE = getProp("P1_安全固件版本");
    public static final String P1_FW = getProp("P1_FW");
    public static final String P1_KERNEL_CODE = getProp("P1_内核版本");
    public static final String P1_BASE_CODE = getProp("P1_基带版本");
    public static final String P1_PATCH_LEVEL = getProp("P1_安全补丁");
    public static final String V1_VERSION_NAME = getProp("V1_版本号");
    public static final String V1_VERSION_CODE = getProp("V1_固件版本");
    public static final String V1_KERNEL_CODE = getProp("V1_内核版本");
    public static final String V1_PATCH_LEVEL = getProp("V1_安全补丁");

    private static String getProp(String key){
        InputStream is = null;
        Properties properties = null;
        try{
            is = new FileInputStream(new File("data/local/tmp/info.properties"));
            properties = new Properties();
            properties.load(is);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }
}
