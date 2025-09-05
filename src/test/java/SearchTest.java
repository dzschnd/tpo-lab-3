
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.support.PageFactory;

import pages.HomePage;
import utils.Constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

public class SearchTest extends BaseTest {

    @ParameterizedTest
    @EnumSource(Browser.class)
    @Execution(ExecutionMode.CONCURRENT)
    void searchFlights(Browser browser) {
        setUp(browser);
        driver.get(Constants.BASE_URL);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        home.confirmLocation();
        home.disableAds();
        home.acceptCookies();
        var search = home.searchFlights("спб", "Москв", LocalDate.now(),
                LocalDate.now().plusDays(7));
        assertTrue(search.isResultsLoaded());
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    @Execution(ExecutionMode.CONCURRENT)
    void pickFlight(Browser browser) {
        setUp(browser);
        driver.get(Constants.BASE_URL);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        home.confirmLocation();
        home.disableAds();
        home.acceptCookies();

        var search = home.searchFlights("москв", "санкт-Петербур", LocalDate.now(),
                LocalDate.now().plusDays(7));
        search.pickCheapestFlight();

        var isRedirected = !driver.getCurrentUrl().startsWith(Constants.BASE_URL);
        assertTrue(isRedirected);
    }
}
