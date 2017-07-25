package sunmi.com.sunmiauto.testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sunmi.com.sunmiauto.testcases.SunmiAppStore_v3_3_15;
import sunmi.com.sunmiauto.testcases.SunmiSettings;
import sunmi.com.sunmiauto.testcategory.CategoryAppStoreTest1;
import sunmi.com.sunmiauto.testcategory.CategoryAppStoreTests_v3_3_15;
import sunmi.com.sunmiauto.testcategory.CategorySettingsTests;
import sunmi.com.sunmiauto.testcategory.CategorySettingsTests1;

/**
 * Created by fengy on 2017/7/25.
 */

@RunWith(Categories.class)
@Suite.SuiteClasses({SunmiAppStore_v3_3_15.class, SunmiSettings.class})
@Categories.IncludeCategory({CategoryAppStoreTests_v3_3_15.class, CategorySettingsTests.class})
public class AppStoreSettingsSuite {
}
