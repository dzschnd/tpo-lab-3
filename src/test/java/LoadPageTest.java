
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.support.PageFactory;

import constants.Constants;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadPageTest extends BaseTest {

    @ParameterizedTest
    @EnumSource(Browser.class)
    @Execution(ExecutionMode.CONCURRENT)
    void getTitleTest(Browser browser) {
        setUp(browser);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        home.open();
        assertTrue(driver.getCurrentUrl().startsWith(Constants.BASE_URL));
    }
}
