
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import utils.Constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HealthTest extends BaseTest {

    @ParameterizedTest
    @EnumSource(Browser.class)
    @Execution(ExecutionMode.CONCURRENT)
    void getTitleTest(Browser browser) {
        setUp(browser);
        driver.get(Constants.BASE_URL);
        assertTrue(driver.getCurrentUrl().startsWith(Constants.BASE_URL));
    }
}
