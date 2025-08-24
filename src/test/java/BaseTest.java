
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.Constants;

import java.time.Duration;

public abstract class BaseTest {

    private Browser browser;

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;

    public enum Browser {
        CHROME, FIREFOX
    }

    public void setUp(Browser browser) {
        setUp(browser, !Constants.DEFAULT_IS_HEADLESS);
    }

    public void setUp(Browser browser, boolean isHeadless) {
        log.info("=== Setting up WebDriver for {} ===", browser);
        this.browser = browser;

        switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                var chromeOptions = new ChromeOptions();
                if (isHeadless == Constants.DEFAULT_IS_HEADLESS) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu"); // recommended on Linux}
                }
                driver = new ChromeDriver(chromeOptions);
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                var firefoxOptions = new FirefoxOptions();
                if (isHeadless == Constants.DEFAULT_IS_HEADLESS) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
            }
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        log.info("✅ {} started successfully", browser);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            log.info("Closing {}", browser);
            try {
                driver.quit();
            } catch (Exception e) {
                log.warn("Error while quitting {}", browser, e);
            }
        }
    }
}
