package com.sunmi.sunmiauto.testutils;

import java.util.ArrayList;

/**
 * Created by fengy on 2017/7/26.
 */

public class TestConstants {
    public static final int SHORT_SLEEP = 2000;//短暂休眠
    public static final int LONG_WAIT = 20000;//等待新窗口出现最长时间
    public static final int DOWNLOAD_WAIT = 300000;//等待应用市场应用下载完成最长时间
    public static final String NETWORKNAMETEST = "AutoTestNetwork";//添加网络名称
    public static final String NETWORKPWDTEST = "autotest388";//添加网络密码
    public static final String BT_NAME = "V1";//蓝牙名称
    public static final String USER_CENTER_PKG = "com.sunmi.usercenter";//用户中心包名
    public static final String SEARCH_APP = "DUDU";//应用市场搜索内容
    public static final String USERCENTER_ACCOUNT = "zhangjiyang@sunmi.com";//用户中心账号
    public static final String USERCENTER_PWD = "sunmi2016";//用户中心密码
    public static final String LOG_V = "myautotest";
    public static final ArrayList<String> SETTINGSLIST = new ArrayList<String>(){//设置列表所有菜单项
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
}
