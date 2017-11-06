package com.sunmi.sunmiauto.testutils;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import junit.framework.Assert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.sunmi.sunmiauto.testutils.TestConstants.LOGCAT_LOG_PATH;
import static com.sunmi.sunmiauto.testutils.TestConstants.LOG_V;
import static com.sunmi.sunmiauto.testutils.TestConstants.SNAPSHOT_PATH;
import static com.sunmi.sunmiauto.testutils.TestConstants.WAIT;


/**
 * Created by zhengjin on 2016/5/31.
 * <p>
 * Include the utils for shell ENV.
 */
public final class ShellUtils {

    private static final String TAG = ShellUtils.class.getSimpleName();

    private static final String COMMAND_SU = "su";
    private static final String COMMAND_SH = "sh";
    private static final String COMMAND_EXIT = "exit\n";
    private static final String COMMAND_LINE_END = "\n";

    private ShellUtils() {
    }

    public static CommandResult execCommand(
            String command, boolean isRoot, boolean isReturnResultMsg) {
        Log.e(LOG_V,"exec1");
        return execCommand(new String[]{command}, isRoot, isReturnResultMsg);
    }

    @SuppressWarnings("unused")
    public static CommandResult execCommand(
            List<String> commands, boolean isRoot, boolean isReturnResultMsg) {
        return execCommand((commands == null ? null : commands.toArray(new String[]{})),
                isRoot, isReturnResultMsg);
    }

    public static CommandResult execCommand(
            String[] commands, boolean isRoot, boolean isReturnResultMsg) {
        Log.e(LOG_V,"exec2");
        final int DEFAULT_ERROR_CODE = -1;
        final CommandResult defaultCommandResult =
                new CommandResult(DEFAULT_ERROR_CODE, null, null);
        if (commands == null || commands.length == 0) {
            return defaultCommandResult;
        }

        int result = DEFAULT_ERROR_CODE;
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            Log.e(LOG_V,"process OS...");
            for (String command : commands) {
                Log.e(LOG_V,"commands ...");
                if (StringUtils.isEmpty(command)) {
                    Log.e(LOG_V,"isempty ...");
                    continue;
                }
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            if (isReturnResultMsg) {
                final int DEFAULT_SIZE = 10;
                String tmpStr;

                successMsg = new StringBuilder(DEFAULT_SIZE);
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((tmpStr = successResult.readLine()) != null) {
                    successMsg.append(tmpStr);
                }

                errorMsg = new StringBuilder(DEFAULT_SIZE);
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((tmpStr = errorResult.readLine()) != null) {
                    errorMsg.append(tmpStr);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_V,"exec exception...");
            e.printStackTrace();
            return defaultCommandResult;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                Log.e(LOG_V,"exec4");
                process.destroy();
            }
        }
        Log.e(LOG_V,"exec3");
        return new CommandResult(result,
                (successMsg != null ? successMsg.toString() : null),
                (errorMsg != null ? errorMsg.toString() : null));
    }

    public static class CommandResult {

        public int mReturnCode;
        public String mSuccessMsg;
        public String mErrorMsg;

        CommandResult(int results, String successMsg, String errorMsg) {
            mReturnCode = results;
            mSuccessMsg = successMsg;
            mErrorMsg = errorMsg;
        }
    }

    public static void clearLogcatLog() {
        String cmdClear = "logcat -c";
        ShellUtils.CommandResult result = ShellUtils.execCommand(cmdClear, false, false);
        if (result.mReturnCode != 0) {
            Log.e(TAG, String.format("clearLogcatLog filed, return code: %d", result.mReturnCode));
        }
    }

    public static void dumpLogcatLog() {
        dumpLogcatLog(TestConstants.LOG_LEVEL_DEBUG);
    }

    public static void dumpLogcatLog(String logLevel) {
        if (!createTestingDirectory(LOGCAT_LOG_PATH)) {
            return;
        }

        String cmdLogcat =
                String.format("logcat -v time -d *:%s > %s", logLevel, getLogcatFilePath());
        ShellUtils.CommandResult result = ShellUtils.execCommand(cmdLogcat, false, false);
        if (result.mReturnCode != 0) {
            Log.e(TAG, String.format("dumpLogcatLog failed, return code: %d", result.mReturnCode));
        }
    }

    private static String getLogcatFilePath() {
        return String.format("%s/logcat_%s.log", LOGCAT_LOG_PATH, getCurrentDateTime());
    }

    public static Thread startLogcatLog(final String logLevel) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!createTestingDirectory(LOGCAT_LOG_PATH)) {
                    return;
                }
                String cmdLogcat = String.format("logcat -c && logcat -v time *:%s > %s"
                        , logLevel, getLogcatFilePath());
                Log.e(LOG_V,"before exec run...");
                ShellUtils.execCommand(cmdLogcat, false, false);
                Log.e(LOG_V,"after exec run...");
            }
        });
        t.start();
        Log.e(LOG_V,"after start run...");
        return t;
    }

    public static void stopLogcatLog(Thread t) {
        Log.e(LOG_V,"stop start...");
        String logcatPid;
        try {
            logcatPid = getLogcatProcessInfo().split("\\s+")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(LOG_V,"outofBounds...");
            e.printStackTrace();
            return;
        }
        killProcessById(logcatPid);
        Log.e(LOG_V,"stopLogcat1...");
        if (t != null && t.isAlive()) {
            Log.e(LOG_V,"stopLogcat2...");
            try {
                t.join(WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getLogcatProcessInfo() {
        String emptyInfo = "null";
        String cmdFind = "ps | grep logcat";

        ShellUtils.CommandResult result = ShellUtils.execCommand(cmdFind, false, true);
        if (result.mReturnCode != 0) {
            Log.e(TAG, String.format("getLogcatProcessInfo failed, return code: %d"
                    , result.mReturnCode));
            return emptyInfo;
        }
        if (StringUtils.isEmpty(result.mSuccessMsg)) {
            return emptyInfo;
        }

        return result.mSuccessMsg;
    }

    private static void killProcessById(String pid) {
        if (!StringUtils.isNumeric(pid)) {
            return;
        }
        Log.e(LOG_V,"killprocess");
        String cmdKill = String.format("kill %s", pid);
        ShellUtils.CommandResult result = ShellUtils.execCommand(cmdKill, false, false);
        Log.e(LOG_V,"afterkill...");
        if (result.mReturnCode != 0) {
            Log.e(TAG, String.format("Failed to kill process (%d)", result.mReturnCode));
        }
    }

    public static String takeScreenCapture(UiDevice device) {
        return takeScreenCapture(device, SNAPSHOT_PATH);
    }

    public static String takeScreenCapture(UiDevice device, String dirPath) {
        if (!createTestingDirectory(dirPath)) {
            Log.e(TAG, TestConstants.LOG_KEYWORD + "takeScreenCapture, failed to create directory: "
                    + dirPath);
            return "";
        }

        final String suffix = ".png";
        String filePath = dirPath + File.separator +
                String.format("snapshot_%s%s", ShellUtils.getCurrentDateTime(), suffix);
        if (!device.takeScreenshot(new File(filePath))) {
            Log.e(TAG, TestConstants.LOG_KEYWORD + "takeScreenCapture failed, saved path: "
                    + filePath);
        }

        return filePath;
    }

    private static boolean createTestingDirectory(String path) {
        File testDirPath = new File(path);
        return (testDirPath.exists() && testDirPath.isDirectory()) || testDirPath.mkdirs();
    }

    @SuppressWarnings("unused")
    public static int deleteAllFilesFromDirectory(String dirPath) {
        return deleteAllFilesFromDirectory(dirPath, "*");
    }

    public static int deleteAllFilesFromDirectory(String dirPath, String pattern) {
        final String tmpCmd = String.format("rm %s/%s", dirPath, pattern);
        ShellUtils.CommandResult cr = ShellUtils.execCommand(tmpCmd, false, true);
        if (cr.mReturnCode != 0) {
            Log.w(TAG, TestConstants.LOG_KEYWORD + cr.mErrorMsg);
        }

        return cr.mReturnCode;
    }

    public static void stopProcessByPackageName(String pkgName) {
        String cmdStopProcess = String.format("am force-stop %s", pkgName);
        ShellUtils.CommandResult result = ShellUtils.execCommand(cmdStopProcess, false, false);
        Assert.assertTrue("Force stop the app process.", (result.mReturnCode == 0));
    }

    public static void stopAndClearPackage(String pkgName) {
        String cmdStopProcess = String.format("pm clear %s", pkgName);
        ShellUtils.CommandResult result = ShellUtils.execCommand(cmdStopProcess, false, false);
        Assert.assertTrue("Clear the app package.", (result.mReturnCode == 0));
    }

    public static void startSpecifiedActivity(String pkgName, String actName) {
        String cmdStart = String.format("am start %s/%s", pkgName, actName);
        ShellUtils.CommandResult result = ShellUtils.execCommand(cmdStart, false, false);
        Assert.assertEquals("Start the specified activity.", 0, result.mReturnCode);
    }

    static CommandResult getTopFocusedActivity() {
        String cmd = "dumpsys activity | grep mFocusedActivity";
        return ShellUtils.execCommand(cmd, false, true);
    }

    public static void systemWaitByMillis(long ms) {
        SystemClock.sleep(ms);
    }

    @SuppressWarnings("unused")
    private static String getCurrentDate() {
        SimpleDateFormat formatter =
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat formatter =
                new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss-SSS", Locale.getDefault());
        Date curTime = new Date(System.currentTimeMillis());
        return formatter.format(curTime);
    }

    @Nullable
    public static String getRunningMethodName() {
        final String prefix = "test";

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 0, depth = 7; i < depth; i++) {
            if (elements[i].getMethodName().startsWith(prefix)) {
                return elements[i].getMethodName();
            }
        }

        return "null";
    }

}
