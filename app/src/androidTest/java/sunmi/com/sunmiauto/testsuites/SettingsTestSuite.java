package sunmi.com.sunmiauto.testsuites;

import android.os.RemoteException;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

import sunmi.com.sunmiauto.testcases.SunmiSettings;
import sunmi.com.sunmiauto.testutils.TestUtils;
import sunmi.com.sunmiauto.testcategory.CategorySettingsTests;

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
