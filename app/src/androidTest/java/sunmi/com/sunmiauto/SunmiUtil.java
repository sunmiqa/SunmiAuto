package sunmi.com.sunmiauto;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.io.File;

/**
 * Created by fengy on 2017/7/8.
 */

public class SunmiUtil {

    static Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    static UiDevice device = UiDevice.getInstance(instrumentation);

    public static void screenshotCap(String tag) {
        StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
        String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
        String methodName = testClass.getMethodName();
        Log.v("Extenrnal", Environment.getExternalStorageState());
        File dir = new File(Environment.getExternalStorageDirectory()
                + File.separator + "app_spoon-screenshots" + File.separator
                + className + File.separator + methodName);
        Log.v("TEST", dir.getAbsolutePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String screenshotName = System.currentTimeMillis() + "_" + tag + ".png";
        File screenshotFile = new File(dir, screenshotName);
        device.takeScreenshot(screenshotFile, 0.2F, 50);
        sleep(2000);
    }


    public static StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
        for (StackTraceElement e : trace) {
            Log.v("traceTest", e.getClassName() + ":" + e.getMethodName());
        }
        for (int i = trace.length - 1; i >= 0; --i) {
            StackTraceElement element = trace[i];
            if ("android.test.InstrumentationTestCase".equals(element.getClassName()) && "runMethod".equals(element.getMethodName())) {
                if (Build.VERSION.SDK_INT >= 23) {
                    return trace[i - 2];
                } else {
                    return trace[i - 3];
                }
            }

            if ("org.junit.runners.model.FrameworkMethod$1".equals(element.getClassName()) && "runReflectiveCall".equals(element.getMethodName())) {
                if (Build.VERSION.SDK_INT >= 23) {
                    return trace[i - 2];
                } else {
                    return trace[i - 3];
                }
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
