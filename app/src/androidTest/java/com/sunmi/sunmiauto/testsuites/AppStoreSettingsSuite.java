package com.sunmi.sunmiauto.testsuites;

import android.os.RemoteException;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

import com.sunmi.sunmiauto.testcases.SunmiAppStore_v3_3_15;
import com.sunmi.sunmiauto.testcases.SunmiSettings;
import com.sunmi.sunmiauto.testutils.TestUtils;
import com.sunmi.sunmiauto.testcategory.CategoryAppStoreTests_v3_3_15;
import com.sunmi.sunmiauto.testcategory.CategorySettingsTests;

/**
 * Created by fengy on 2017/7/25.
 */

@RunWith(Categories.class)
@Suite.SuiteClasses({SunmiAppStore_v3_3_15.class, SunmiSettings.class})
@Categories.IncludeCategory({CategoryAppStoreTests_v3_3_15.class, CategorySettingsTests.class})
public class AppStoreSettingsSuite {
    @BeforeClass
    public static void setUp() throws IOException, RemoteException {
        TestUtils.initLiza();
    }
}
