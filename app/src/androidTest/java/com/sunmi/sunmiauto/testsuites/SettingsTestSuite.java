package com.sunmi.sunmiauto.testsuites;

import android.os.RemoteException;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

import com.sunmi.sunmiauto.testcases.SunmiSettings;
import com.sunmi.sunmiauto.testutils.TestUtils;
import com.sunmi.sunmiauto.testcategory.CategorySettingsTests;

/**
 * Created by fengy on 2017/7/24.
 */

@RunWith(Categories.class)
@Suite.SuiteClasses(SunmiSettings.class)
@Categories.IncludeCategory(CategorySettingsTests.class)
public final class SettingsTestSuite {
    @BeforeClass
    public static void setUp() throws IOException, RemoteException {
        TestUtils.initLiza();
    }
}
