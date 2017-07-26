package sunmi.com.sunmiauto.testsuites;

import android.os.RemoteException;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

import sunmi.com.sunmiauto.testcases.SunmiAppStore_v3_3_15;
import sunmi.com.sunmiauto.testcases.SunmiSettings;
import sunmi.com.sunmiauto.testutils.TestUtils;
import sunmi.com.sunmiauto.testcategory.CategoryAppStoreTests_v3_3_15;
import sunmi.com.sunmiauto.testcategory.CategorySettingsTests;

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
