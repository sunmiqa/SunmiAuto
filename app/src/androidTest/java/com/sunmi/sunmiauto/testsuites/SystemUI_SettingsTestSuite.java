package com.sunmi.sunmiauto.testsuites;

import android.os.RemoteException;

import com.sunmi.sunmiauto.testcases.SunmiSettings;
import com.sunmi.sunmiauto.testcases.SunmiSettings_Part2;
import com.sunmi.sunmiauto.testcases.SunmiSystemUI;
import com.sunmi.sunmiauto.testcategory.CategorySettings_Part2_P1;
import com.sunmi.sunmiauto.testcategory.CategorySystemUI_P1;
import com.sunmi.sunmiauto.testutils.TestUtils;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

/**
 * Created by fengy on 2017/9/27.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SunmiSystemUI.class,SunmiSettings.class,SunmiSettings_Part2.class})
public class SystemUI_SettingsTestSuite {
    @BeforeClass
    public static void setUp() throws IOException, RemoteException {
        TestUtils.initLiza();
    }
}
