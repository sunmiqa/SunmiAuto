package com.sunmi.sunmiauto.testsuites;

import android.os.RemoteException;

import com.sunmi.sunmiauto.testcases.SunmiAppStore_v3_3_15;
import com.sunmi.sunmiauto.testcases.SunmiSystemUI;
import com.sunmi.sunmiauto.testcategory.CategoryAppStoreTests_v3_3_15;
import com.sunmi.sunmiauto.testcategory.CategorySystemUI_P1;
import com.sunmi.sunmiauto.testutils.TestUtils;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

/**
 * Created by fengy on 2017/7/24.
 */

@RunWith(Categories.class)
@Suite.SuiteClasses(SunmiSystemUI.class)
@Categories.IncludeCategory(CategorySystemUI_P1.class)
public final class SystemUITestSuite {
    @BeforeClass
    public static void setUp() throws IOException, RemoteException {
        TestUtils.initLiza();
    }
}
