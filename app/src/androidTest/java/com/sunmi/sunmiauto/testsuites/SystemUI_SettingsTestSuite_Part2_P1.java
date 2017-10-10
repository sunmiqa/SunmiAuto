package com.sunmi.sunmiauto.testsuites;

import android.os.RemoteException;

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

@RunWith(Categories.class)
@Categories.IncludeCategory({CategorySettings_Part2_P1.class,CategorySystemUI_P1.class})
@Suite.SuiteClasses({SunmiSettings_Part2.class,SunmiSystemUI.class})
public class SystemUI_SettingsTestSuite_Part2_P1 {
    @BeforeClass
    public static void setUp() throws IOException, RemoteException {
        TestUtils.initLiza();
    }
}
