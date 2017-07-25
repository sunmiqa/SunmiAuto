package sunmi.com.sunmiauto.testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sunmi.com.sunmiauto.testcases.SunmiAppStore_v3_3_15;
import sunmi.com.sunmiauto.testcategory.CategoryAppStoreTests_v3_3_15;

/**
 * Created by fengy on 2017/7/24.
 */

@RunWith(Categories.class)
@Suite.SuiteClasses(SunmiAppStore_v3_3_15.class)
@Categories.IncludeCategory(CategoryAppStoreTests_v3_3_15.class)
public final class AppStoreTestSuite {
}
