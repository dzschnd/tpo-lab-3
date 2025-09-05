
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.support.PageFactory;

import pages.HomePage;
import utils.Constants;

public class AuthTest extends BaseTest {

    @ParameterizedTest
    @EnumSource(Browser.class)
    @Execution(ExecutionMode.CONCURRENT)
    void login(Browser browser) {
        setUp(browser);
        driver.get(Constants.BASE_URL);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        home.confirmLocation();
        home.login("+71234567890");
    }
}
