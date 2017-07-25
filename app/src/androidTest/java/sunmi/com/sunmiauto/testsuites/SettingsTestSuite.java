package sunmi.com.sunmiauto.testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Suite;

import sunmi.com.sunmiauto.testcases.SunmiSettings;
import sunmi.com.sunmiauto.testcategory.CategorySettingsTests;

/**
 * Created by fengy on 2017/7/24.
 */

@RunWith(Categories.class)
@Suite.SuiteClasses(SunmiSettings.class)
@Categories.IncludeCategory(CategorySettingsTests.class)
public final class SettingsTestSuite {
}
